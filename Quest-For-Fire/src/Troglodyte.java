import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Random;


public class Troglodyte extends GameplayObject {

	private Map map;
	private int width, height;
	private int x, y;
	private Color color;
	private Random ran;
	private LinkedList<Order> orders;
	
	
	Troglodyte(Map map) {
		super();
		this.map = map;
		this.width = 1;
		this.height = 1;
		this.orders = new LinkedList<Order>();
		this.color = Color.lightGray;
		this.ran = new Random();
	}
	
	@Override
	public void place(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);
		g.fillRect(x*10, y*10, width*10, height*10);
	}
	
	public void addOrder(Order o) {
		orders.add(o);
	}
	
	public void move(int dir) {
		/**
		 * Przemieść się; wg klucza:
		 * 1 - N
		 * 2 - NE
		 * 3 - E
		 * 4 - SE
		 * 5 - S
		 * 6 - SW
		 * 7 - W
		 * 8 - NW
		 */
		int nx = x;
		int ny = y;
		
		switch(dir) {
		case 1:
			if(y > 0) ny -= 1;
			break;
		case 2:
			if(y > 0) ny -= 1;
			if(x < map.getWidth()-1) nx += 1;
			break;
		case 3:
			if(x < map.getWidth()-1) nx += 1;
			break;
		case 4:
			if(y < map.getHeight()-1) ny += 1;
			if(x < map.getWidth()-1) nx += 1;
			break;
		case 5:
			if(y < map.getHeight()-1) ny += 1;
			break;
		case 6:
			if(y < map.getHeight()-1) ny += 1;
			if(x > 0) nx -= 1;
			break;
		case 7:
			if(x > 0) nx -= 1;
			break;
		case 8:
			if(x > 0) nx -= 1;
			if(y > 0) ny -= 1;
			break;
		default:
			break;
		}
		
		Cell target = map.get(nx, ny);
		if(target.empty()) {
			map.get(x, y).del(this);
			target.add(this);
			x = nx;
			y = ny;
		}
	}

	@Override
	public void wake() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sleep() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void work() {
		/**
		 * Sprawdza czy istnieją jakieś rozkazy.
		 * Jeśli tak, wybiera pierwszy z kolejki i sprawdza, czy jest już wykonany.
		 * Jeśli nie jest, interpretuje go i wykonuje.
		 * 		* Sprawdza wartość pola repeat, jeśli jest dodatnia, 
		 * 		* wówczas wykonuje rozkaz i zmniejsza repeat o 1.
		 * W przeciwnym wypadku usuwa go z kolejki i wykonuje się rekurencyjnie
		 * dopóki kolejka nie będzie pusta.
		 */
		
		if(orders.size() > 0) {
			Order o = orders.getFirst();
			if(!o.isDone()) {
				if(o.repeat > 0) {
					String st = o.statement;
					if(st == "stray") {
						int dir = ran.nextInt(8) + 1;
						move(dir);
					}
					o.repeat--;
				}
				else o.setDone();
			}
			else {
				orders.remove(o);
				work();
			}
		}
		
	}

}
