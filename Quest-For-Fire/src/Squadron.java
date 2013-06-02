import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;


public class Squadron {

	private LinkedList<Troglodyte> troops;
	private Queue<Order> orders;
	private int x, y;
	
	Squadron(int x, int y, int n) {
		this.x = x;
		this.y = y;
		troops = new LinkedList<Troglodyte>();
		Troglodyte tmp;
//		for(int i = 0; i < n; i++) {
//			tmp = new Troglodyte();
//			tmp.place(this.x + 10 * i, this.y);
//			troops.add(tmp);
//		}
		// kolejka rozkazów ograniczona - maksymalnie N na raz oczekujacych
		orders = new LinkedBlockingQueue<Order>(10);
	}
	
	public boolean addOrder(Order o) {
		return orders.offer(o);
	}
	
}
