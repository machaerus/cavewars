import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Semaphore;

import javax.swing.JPanel;


public class MapPanel extends JPanel implements Runnable {

	
	private static final long serialVersionUID = 1794571289521271349L;
	private static final int PWIDTH = 600; 
	private static final int PHEIGHT = 400;
	private volatile int speed;
	
	private Thread animator; 
	private boolean running; 
	private volatile boolean gameOver = false; 
	//private boolean isPaused = false;
	private Graphics2D dbg;
	private Image dbImage = null;
	private GameLogic gameLogic;
	private Semaphore semaphore;
	private volatile GameplayObjectsList objectsList;
	private volatile Map map;
	
	MapPanel(int speed, GameLogic gameLogic, Semaphore semaphore, GameplayObjectsList ol, Map map) throws Exception {
		this.speed = speed;
		this.gameLogic = gameLogic;
		this.semaphore = semaphore;
		this.objectsList = ol;
		this.map = map;
		this.running = false;
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		setPreferredSize(new Dimension(PWIDTH, PHEIGHT));
		
		// ...
		// wczytywanie zasobów, przygotowywanie planszy
		
		setFocusable(true);
		requestFocus();
		
		// definiowanie interakcji i kontroli
		
		setControls();
	}

	public void addNotify()
	/**
	 * Wait for the JPanel to be added to the JFrame/JApplet before starting.
	 */
	{
		super.addNotify();
		startGame();
	}
	
	private void setControls() {
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				testPress(e.getX(), e.getY());
			}
		});
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				
				if(keyCode == KeyEvent.VK_Q) {
					System.out.println("mapPanel: Proszę o zakończenie.");
					stopGame();
				}
				else if(keyCode == KeyEvent.VK_SPACE) {
					System.out.println("mapPanel: Proszę o stworzenie troglodyty.");
					gameLogic.createTroglodyte();
				}
				// ...
				// interpretujemy wejście z klawiatury
			}
		});
	}
	
	private void testPress(int x, int y)
	/**
	 * Kontrola myszy
	 */
	{
		if (!gameOver) {
			// zrób coś
		}
	}

	private void gameUpdate() {
		
		// pobieramy dane o obiektach gry ze schowka głównego wątku
		
		// sprawdzamy, czy gra jeszcze się nie zakończyła
		running = gameLogic.running();
		
	}
	
	private void gameRender() {
		/**
		 * Tworzy obrazy przeznaczone do narysowania na ekranie. 
		 */
		if (dbImage == null) { // tworzyme bufor
			dbImage = createImage(PWIDTH, PHEIGHT);
			if (dbImage == null) {
				System.out.println("dbImage is null");
				return;
			} else
				dbg = (Graphics2D) dbImage.getGraphics();
		}
		
		// czyścime tło
		
		dbg.setColor(Color.white);
		dbg.fillRect(0, 0, PWIDTH, PHEIGHT);
		//dbg.drawImage(background, 0, 0, null);
		
		// rysujemy tło mapy
		
		for(int i = 0; i < map.getWidth(); i++) {
			for(int j = 0; j < map.getHeight(); j++) {
				dbg.setColor(Color.darkGray);
				dbg.fillRect(i*10, j*10, 10, 10);
			}
		}
		
		// rysujemy widoczne elementy z objectsList
		
		GameplayObject gpo;
		objectsList.resetPointer();
		synchronized(objectsList) {
			while(true) {
				gpo = objectsList.nextVisible();
				if(gpo != null) gpo.draw(dbg);
				else break;
			}
		}
	}
	
	private void paintScreen() {
		Graphics2D g;
		try {
			g = (Graphics2D) this.getGraphics(); 
			if ((g != null) && (dbImage != null))
				g.drawImage(dbImage, 0, 0, null);
			Toolkit.getDefaultToolkit().sync(); 
			g.dispose();
		} catch (Exception e) {
			System.out.println("Graphics context error: " + e);
		}
	} 
	
	public void paintComponent(Graphics2D g) {
		super.paintComponent(g);
		if (dbImage != null)
			g.drawImage(dbImage, 0, 0, null);
	}
	
	private void startGame() {
		if (animator == null || !running) {
			animator = new Thread(this);
			animator.start();
		}
	} 
	
	public void resumeGame() {
		//isPaused = false;
	}

	public void pauseGame() {
		//isPaused = true;
	}

	public void stopGame() {
		/**
		 * Prosi gameLogic o zakończenie gry (nie może sam o tym zadecydować).
		 */
		gameLogic.Stop();
	}
	
	public void gameOver() {
		//
		gameOver = true;
	}
	
	public void run() {
		do {
			gameUpdate();
			gameRender();
			paintScreen(); 
			try {
				Thread.sleep(speed); 
			} catch (InterruptedException ex) {}
		} while(running);
		
		System.out.println("MapPanel melduje zakończenie i zwalnia semafor...");
		semaphore.release();
	}
	
}
