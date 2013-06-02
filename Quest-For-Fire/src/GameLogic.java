import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Klasa zarządzająca logiką gry.
 */

public class GameLogic implements Runnable {
	
	private Thread worker;
	private Semaphore mainSync;
	private volatile GameplayObjectsList objectsList;
	private volatile Map map;
	private volatile boolean running; 
	private Random ran;
	private volatile int speed;
	
	GameLogic(int speed, Semaphore mainSync, GameplayObjectsList ol, Map map) throws Exception {
		
		this.speed = speed;
		this.mainSync = mainSync;
		this.objectsList = ol;
		this.map = map;
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
		Troglodyte t = new Troglodyte(map);
		map.place(t, ran.nextInt(58), ran.nextInt(38));
		synchronized(objectsList) {
			objectsList.addGameObject(t);
			//System.out.println("gameLogic: No stworzyłem.");
		}
		t.setVisible(true);
		t.addOrder(new Order("stray",100));
	}
	
	private void update() {
		GameplayObject gpo;
		objectsList.resetPointer();
		synchronized(objectsList) {
			while(true) {
				gpo = objectsList.nextVisible();
				if(gpo != null) gpo.work();
				else break;
			}
		}
	}
	
	public void run() {
		/**
		 * Główna pętla logiki gry.
		 */
		while(running) {
			//System.out.println("GameLogic melduje działanie.");
			
			update();
			
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
