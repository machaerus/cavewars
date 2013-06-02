import java.util.concurrent.Semaphore;
import javax.swing.JFrame;

public class GameWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7712354877306446942L;
	private static String title = "Quest for Fire";
	private volatile int speed;
	private GameLogic gameLogic;
	private MapPanel mapPanel;
	private Semaphore panelSemaphore;
	//private Semaphore mainSync;
	private volatile GameplayObjectsList objectsList;
	private volatile Map map;

	GameWindow(int speed, GameLogic gameLogic, Semaphore mainSync, GameplayObjectsList ol, Map map) throws Exception {
		super(title);
		this.speed = speed;
		this.gameLogic = gameLogic;
		//this.mainSync = mainSync;
		this.objectsList = ol;
		this.map = map;
		setLocation(50, 50);
	}
	
	public void Start() {
		
		panelSemaphore = new Semaphore(0, true);
		try {
			mapPanel = new MapPanel(speed, gameLogic, panelSemaphore, objectsList, map);
		} catch (Exception e) {
			System.err.println("Błąd podczas tworzenia obiektu mapPanel.");
			e.printStackTrace();
		}
		
		setContentPane(mapPanel);
		mapPanel.setFocusable(true);
		mapPanel.requestFocusInWindow();
		pack();
		setVisible(true);
		
		try {
			panelSemaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("GameWindow melduje zakończenie działania");
	}
	
}
