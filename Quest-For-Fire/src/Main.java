import java.util.concurrent.Semaphore;

public class Main {

	/**
	 * @param args
	 * Główna klasa tworząca wątki i zarządzająca grą.
	 * 
	 */
	
	private static GameplayObjectsList objectsList;
	
	public static void main(String[] args) {
		
		GameLogic gameLogic = null;
		GameWindow gameWindow = null;
		Semaphore mainSync = new Semaphore(0,true);
		objectsList = new GameplayObjectsList();
		
		try {
			gameLogic = new GameLogic(mainSync, objectsList);
		} catch (Exception e) {
			System.err.println("Main: Błąd przy tworzeniu obiektu GameLogic");
			e.printStackTrace();
			System.exit(1);
		}
		
		try {
			gameWindow = new GameWindow(gameLogic, mainSync, objectsList);
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
