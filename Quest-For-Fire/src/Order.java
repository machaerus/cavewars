
public class Order {
	
	public String statement;
	public int repeat;
	private boolean done;
	
	Order(String st, int r) {
		done = false;
		this.statement = st;
		this.repeat = r;
	}
	
	public void setDone() {
		done = true;
	}
	
	public boolean isDone() {
		return done;
	}
}
