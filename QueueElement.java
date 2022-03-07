
public class QueueElement {
	private Object data;
	private int priority;

	public QueueElement(Object data, int priority) {
		this.data = data;
		this.priority = priority;
	}

	public Object getData() {
		return data;
	}

	public int getPriority() {
		return priority;
	}
}
