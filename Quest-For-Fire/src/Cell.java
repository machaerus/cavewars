
public class Cell {
	
	private GameplayObjectsList occupants;
	
	Cell() {
		occupants = new GameplayObjectsList();
	}
	
	public void add(GameplayObject obj) {
		occupants.addGameObject(obj);
	}
	
	public void del(GameplayObject obj) {
		occupants.delGameObject(obj);
	}
	
	public boolean empty() {
		if(occupants.size() == 0) return true;
		else return false;
	}
	
}
