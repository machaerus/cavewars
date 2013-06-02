
public class Cell {
	
	private GameplayObjectsList occupants;
	
	Cell() {
		occupants = new GameplayObjectsList();
	}
	
	public void add(GameplayObject obj) {
		occupants.addGameObject(obj);
	}
	
}
