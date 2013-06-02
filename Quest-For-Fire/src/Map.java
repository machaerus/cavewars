
public class Map {

	private int width;
	private int height;
	private Cell map[][];
	
	Map(int width, int height) {
		this.width = width;
		this.height = height;
		map = new Cell[width][height]; 
		init();
	}
	
	private void init() {
		/**
		 * Tworzy pustą mapę
		 */
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				map[i][j] = new Cell();
			}
		}
	}
	
	public Cell get(int x, int y) {
		return map[x][y];
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void place(GameplayObject obj, int x, int y) {
		map[x][y].add(obj);
		obj.place(x, y);
		obj.wake();
	}
	
}
