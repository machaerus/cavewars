import java.util.concurrent.Semaphore;

public class Main {

	/**
	 * @param args
	 * Główna klasa tworząca wątki i zarządzająca grą.
	 * 
	 */
	
	private static GameplayObjectsList objectsList;
	private static Map map;
	private static int speed = 300;
	
	public static void main(String[] args) {
		
		GameLogic gameLogic = null;
		GameWindow gameWindow = null;
		Semaphore mainSync = new Semaphore(0,true);
		objectsList = new GameplayObjectsList();
		map = new Map(60,40);
		
		try {
			gameLogic = new GameLogic(speed, mainSync, objectsList, map);
		} catch (Exception e) {
			System.err.println("Main: Błąd przy tworzeniu obiektu GameLogic");
			e.printStackTrace();
			System.exit(1);
		}
		
		try {
			gameWindow = new GameWindow(speed, gameLogic, mainSync, objectsList, map);
		} catch (Exception e) {
			System.err.println("Main: Błąd przy tworzeniu obiektu GameWindow");
			e.printStackTrace();
			System.exit(1);
		}
		
		gameLogic.Start();
		gameWindow.Start();
		
		try {
			mainSync.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.exit(0);

	}

}
