
public class PriorityQueue {
	private int rear, front;
	private QueueElement[] elements;
	private int temprear, tempfront;
	private QueueElement[] tempelements;

	public PriorityQueue(int capacity) {
		elements = new QueueElement[capacity];
		rear = -1;
		front = 0;
		tempelements = new QueueElement[capacity];
		temprear = -1;
		tempfront = 0;
	}

	public void enqueue(QueueElement item) {
		if (isFull()) {
			System.out.println("Queue overflow!");
		} else {
			while (!isEmpty() && item.getPriority() <= peek().getPriority()) {
				tempEnqueue(dequeue());
			}
			tempEnqueue(item);
			while (!isEmpty()) {
				tempEnqueue(dequeue());
			}
			while (!isEmptyTemp()) {
				simpleEnqueue(tempDequeue());
			}
		}
	}

	public void tempEnqueue(QueueElement item) {
		if (isFullTemp()) {
			System.out.println("Queue overflow!");
		} else {
			temprear = (temprear + 1) % tempelements.length;
			tempelements[temprear] = item;
		}
	}

	public void simpleEnqueue(QueueElement item) {
		if (isFull()) {
			System.out.println("Queue overflow!");
		} else {
			rear = (rear + 1) % elements.length;
			elements[rear] = item;
		}
	}

	public QueueElement dequeue() {
		if (isEmpty()) {
			System.out.println("Queue is empty!");
			return null;
		} else {
			QueueElement item = elements[front];
			elements[front] = null;
			front = (front + 1) % elements.length;
			return item;
		}
	}

	public QueueElement tempDequeue() {
		if (isEmptyTemp()) {
			System.out.println("Queue is empty!");
			return null;
		} else {
			QueueElement item = tempelements[tempfront];
			tempelements[tempfront] = null;
			tempfront = (tempfront + 1) % tempelements.length;
			return item;
		}
	}

	public QueueElement peek() {
		if (isEmpty()) {
			System.out.println("Queue is empty!");
			return null;
		} else {
			return elements[front];
		}
	}

	public boolean isEmpty() {
		return elements[front] == null;
	}

	public boolean isEmptyTemp() {
		return tempelements[tempfront] == null;
	}

	public boolean isFull() {
		return (front == (rear + 1) % elements.length && elements[front] != null && elements[rear] != null);
	}

	public boolean isFullTemp() {
		return (tempfront == (temprear + 1) % tempelements.length && tempelements[tempfront] != null
				&& tempelements[temprear] != null);
	}

	public int size() {
		if (rear >= front)
			return rear - front + 1;
		else if (elements[front] != null)
			return elements.length - (front - rear) + 1;
		else
			return 0;
	}
}
