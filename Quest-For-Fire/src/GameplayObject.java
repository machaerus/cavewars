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
	/**
	 * Umieszcza obiekt na planszy, aby poznał swoje położenie
	 * @param x współrzędna w osi x
	 * @param y współrzędna w osi y
	 */
	
	public abstract void draw(Graphics2D g);
	
	public abstract void wake();
	/**
	 * Budzi obiekt, aby zaczął obserwować, reagować, działać etc.
	 * Metoda wywoływana po umieszczeniu na planszy (place())
	 */
	
	public abstract void sleep();
	/**
	 * Usypia obiekt.
	 */
	
	public abstract void work();
	/**
	 * Nakazuje kontynuować działanie.
	 */
	
	
}
