import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Stack;

public class Main {
	// file reading varibles_
	private static FileReader fr;
	private static BufferedReader br;
	private static String[] allfile;
	private static int lineamount;
	private static String[] splittedData;
	// Other necessary variables
	static PriorityQueue busStation1 = new PriorityQueue(1000); // first Station
	static PriorityQueue busStation2 = new PriorityQueue(1000); // second Station
	static PriorityQueue busStation3 = new PriorityQueue(1000); // last station
	static PriorityQueue bus = new PriorityQueue(8); // The bus
	static int studentTicket = 1, normalTicket = 2, agedTicket = 0; // Ticket amounts can be changed later
	static int priorityAgeLimit = 70; // Also, priorityAgeLimit can be changed later
	static int busIncome = 0;

	public static void readFile(String str) throws Exception {
		fr = new FileReader(str);
		br = new BufferedReader(fr);
		while (br.readLine() != null) { // Getting line amount
			lineamount++;
		}
		allfile = new String[lineamount];
		fr.close();
		fr = new FileReader(str); // Reading the file line by line
		br = new BufferedReader(fr);
		for (int i = 0; i < allfile.length; i++) {
			allfile[i] = br.readLine();
		}
	}

	public static void printBusQueues() {
		System.out.println("BUS1:\nFront");
		for (int i = 0; i < busStation1.size(); i++) { // print process simple enqueue dequeue
			System.out.println(busStation1.peek().getData());
			busStation1.simpleEnqueue(busStation1.dequeue());
		}
		System.out.println("Rear\nBUS2:\nFront");
		for (int i = 0; i < busStation2.size(); i++) {
			System.out.println(busStation2.peek().getData());
			busStation2.simpleEnqueue(busStation2.dequeue());
		}
		System.out.println("Rear");
	}

	public static void addBusStationQueues() {
		splittedData = new String[allfile.length];
		for (int i = 0; i < allfile.length; i++) { // Appends Bus Queue
			splittedData = allfile[i].split(" ");
			if (splittedData[1].equalsIgnoreCase("B1")) {
				// Given priority 1 to over 70 years old, others priority is 0
				if (Integer.parseInt(splittedData[2]) >= priorityAgeLimit) {
					busStation1.enqueue(new QueueElement(allfile[i], 1));
				} else {
					busStation1.enqueue(new QueueElement(allfile[i], 0));
				}
			} else if (splittedData[1].equalsIgnoreCase("B2")) {
				// Given priority 1 to over 70 years old, others priority is 0
				if (Integer.parseInt(splittedData[2]) >= priorityAgeLimit) {
					busStation2.enqueue(new QueueElement(allfile[i], 1));
				} else {
					busStation2.enqueue(new QueueElement(allfile[i], 0));
				}
			}
		}
	}

	public static void busTour() {
		String[] temp = new String[4]; // Gets info to eliminate ticket money
		int tour = 1; // tour counter. If a passanger waits in station, bus tour will continue
		while (!busStation1.isEmpty() || !busStation2.isEmpty()) {
			while (!bus.isFull() && !busStation1.isEmpty()) {
				String str = busStation1.peek().getData().toString(); // Gets info.
				temp = str.split(" ");
				if (Integer.parseInt(temp[2]) >= 65) { // over 65 years old has free ticket;
					busIncome += agedTicket; // Adds 0 TL
				} else if (temp[3].equalsIgnoreCase("student")) {
					busIncome += studentTicket; // Adds 1 TL
				} else if (temp[3].equalsIgnoreCase("normal")) {
					busIncome += normalTicket; // Adds 2 TL
				}
				bus.simpleEnqueue(busStation1.dequeue());
			}
			while (!bus.isFull() && !busStation2.isEmpty()) {
				String str = busStation2.peek().getData().toString(); // Gets info.
				temp = str.split(" ");
				if (Integer.parseInt(temp[2]) >= 65) { // over 65 years old has free ticket;
					busIncome += agedTicket; // Adds 0 TL
				} else if (temp[3].equalsIgnoreCase("student")) {
					busIncome += studentTicket; // Adds 1 TL
				} else if (temp[3].equalsIgnoreCase("normal")) {
					busIncome += normalTicket; // Adds 2 TL
				}
				bus.simpleEnqueue(busStation2.dequeue());
			}
			while (!bus.isEmpty()) { // Last station, all passengers get off the bus
				busStation3.simpleEnqueue(bus.dequeue());
			}
			System.out.println("\n*** After Tour " + tour + " ***\n");
			printBusQueues();
			tour++;
		}
	}

	public static void main(String[] args) throws Exception {
		readFile("data.txt"); // Reading file
		addBusStationQueues(); // Appends bus station queue from txt file
		printBusQueues(); // Prints the whole passengers that wait in stations
		busTour(); // Doing bus tour until all station with non passenger;
		System.out.println("\n--> Overall Bus Income: " + busIncome);
	}

}
