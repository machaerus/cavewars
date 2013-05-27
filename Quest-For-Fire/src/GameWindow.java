import java.util.concurrent.Semaphore;
import javax.swing.JFrame;

public class GameWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7712354877306446942L;
	private static String title = "Quest for Fire";
	private GameLogic gameLogic;
	private MapPanel mapPanel;
	private Semaphore panelSemaphore;
	//private Semaphore mainSync;
	private volatile GameplayObjectsList objectsList;

	GameWindow(GameLogic gameLogic, Semaphore mainSync, GameplayObjectsList ol) throws Exception {
		super(title);
		this.gameLogic = gameLogic;
		//this.mainSync = mainSync;
		this.objectsList = ol;
		setLocation(50, 50);
	}
	
	public void Start() {
		
		panelSemaphore = new Semaphore(0, true);
		try {
			mapPanel = new MapPanel(gameLogic, panelSemaphore, objectsList);
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
