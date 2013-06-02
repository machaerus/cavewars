import java.util.LinkedList;


public class GameplayObjectsList {

	private LinkedList<GameplayObject> objectsList;
	private int pointer;
	
	GameplayObjectsList() {
		objectsList = new LinkedList<GameplayObject>();
		pointer = 0;
	}
	
	public int size() {
		return objectsList.size();
	}
	
	public void resetPointer() {
		this.pointer = 0;
	}
	
	public void addGameObject(GameplayObject gpo) {
		objectsList.add(gpo);
	}
	
	public void delGameObject(GameplayObject gpo) {
		objectsList.remove(gpo);
	}
	
	public GameplayObject getByName(String name) {
		/*
		 * Zwraca pierwszy obiekt o danym identyfikatorze lub null gdy nic nie znajdzie.
		 */
		GameplayObject tmp;
		for(int i = 0; i < objectsList.size(); i++) {
			tmp = objectsList.get(i);
			if(tmp.getName().equals(name)) return tmp;
		}
		return null;
	}
	
	public GameplayObject next() {
		if(pointer < objectsList.size()) return objectsList.get(pointer++);
		else return null;
	}
	
	public GameplayObject nextVisible() {
		/*
		 * Zwraca następny widzialny obiekt z objectsList i null, jeśli brak
		 * widzialnych obiektów lub doszliśmy do końca listy
		 */
		GameplayObject tmp;
		do {
			if(pointer < objectsList.size()) tmp = objectsList.get(pointer);
			else return null;
			if(tmp.isVisible()) {
				pointer++;
				return tmp;
			}
			else pointer++;
		} while(true);
	}
	
}
