package startGame;

import controller.Partita;
import controller.PartitaRMI;
import controller.PartitaSocket;

/**
 * Classe che viene lanciata all'apertura del gioco per settare le opzioni.
 * 
 */
public class Start {
	private boolean GRAFICA;
	private boolean ON_LINE;
	private boolean RMI;
	private PonteLogicaGraficaStart ponte;

	Start() {
		GRAFICA = new Boolean(false);
		ON_LINE = new Boolean(false);
		RMI = new Boolean(false);
		ponte = new PonteLogicaGraficaStart();
	}

	/**
	 * Metodo main.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Start startGame = new Start();
		startGame.start();
	}

	/**
	 * Metodo per avviare il gioco.
	 */
	public void start() {
		IntroFrame introFrame = new IntroFrame(ponte);
		GRAFICA = ponte.isGraphic();
		ON_LINE = ponte.isOnline();
		if (!ON_LINE) {
			introFrame.dispose();
			new Partita(GRAFICA).startPartita();
		} else {
			RMI = ponte.isRmi();
			introFrame.dispose();
			if (RMI)
				new PartitaRMI(GRAFICA).startPartita();
			else
				new PartitaSocket(GRAFICA).startPartita();
		}

	}
}
