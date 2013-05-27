import java.awt.Graphics2D;


public abstract class GameplayObject {

	private boolean visible;
	private String name;
	
	GameplayObject(String name) {
		visible = false;
		this.name = name;
	}
	
	GameplayObject() {
		visible = false;
		this.name = null;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isVisible() {
		return this.visible;
	}
	
	public abstract void place(int x, int y);
	
	public abstract void draw(Graphics2D g);
	
}
