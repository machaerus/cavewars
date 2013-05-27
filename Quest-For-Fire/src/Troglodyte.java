import java.awt.Color;
import java.awt.Graphics2D;


public class Troglodyte extends GameplayObject {

	private int width, height;
	private int x, y;
	private Color color;
	
	
	Troglodyte() {
		super();
		this.width = 10;
		this.height = 10;
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
		g.fillRect(x, y, width, height);
	}

}
