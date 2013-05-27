import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Klasa zarządzająca logiką gry.
 */

public class GameLogic implements Runnable {
	
	private Thread worker;
	private Semaphore mainSync;
	private volatile GameplayObjectsList objectsList;
	private volatile boolean running; 
	private Random ran;
	private static final int speed = 50;
	
	GameLogic(Semaphore mainSync, GameplayObjectsList ol) throws Exception {
		
		this.mainSync = mainSync;
		this.objectsList = ol;
		this.running = false;
		this.ran = new Random();
		
	}
	
	public boolean running() {
		return running;
	}
	
	public void Start() {
		running = true;
		if(worker == null) {
			worker = new Thread(this);
			worker.start();
		}
	}
	
	public void Stop() {
		/**
		 * Gdy zażądano przerwania gry.
		 * Ta metoda może być wywołana również przez dowolny panel.
		 */
		System.out.println("GameLogic kończy grę.");
		mainSync.release();
		running = false;
	}
	
	// TODO: zarządzanie elementami gry - addGameObject, removeGameObject itd.
	
	public void createTroglodyte() {
		//System.out.println("gameLogic: Tworzę troglodytę.");
		Troglodyte t = new Troglodyte();
		t.place(ran.nextInt(580), ran.nextInt(380));
		synchronized(objectsList) {
			objectsList.addGameObject(t);
			//System.out.println("gameLogic: No stworzyłem.");
		}
		t.setVisible(true);
		
	}
	
	public void run() {
		/**
		 * Główna pętla logiki gry.
		 */
		while(running) {
			//System.out.println("GameLogic melduje działanie.");
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
