import java.awt.Color;
import java.awt.Graphics2D;


public class Troglodyte extends GameplayObject {

	private Map map;
	private int width, height;
	private int x, y;
	private Color color;
	
	
	Troglodyte(Map map) {
		super();
		this.map = map;
		this.width = 1;
		this.height = 1;
		this.color = Color.lightGray;
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
		switch(dir) {
		case 1:
			if(y > 0) y -= 1;
			break;
		case 2:
			if(y > 0) y -= 1;
			if(x < map.getWidth()-1) x += 1;
			break;
		case 3:
			if(x < map.getWidth()-1) x += 1;
			break;
		case 4:
			if(y < map.getHeight()-1) y += 1;
			if(x < map.getWidth()-1) x += 1;
			break;
		case 5:
			if(y < map.getHeight()-1) y += 1;
			break;
		case 6:
			if(y < map.getHeight()-1) y += 1;
			if(x > 0) x -= 1;
			break;
		case 7:
			if(x > 0) x -= 1;
			break;
		case 8:
			if(x > 0) x -= 1;
			if(y > 0) y -= 1;
			break;
		default:
			break;
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

}
