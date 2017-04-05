package online.RMI;

import java.rmi.RemoteException;

import model.GameBoard;
import controller.Giocatore;
import online.DisconnessoGiocatoreCorrenteException;
import online.Encoder;
import online.InterfacciaOnline;

/**
 * Classe che si occupa della comunicazione tra server e client che comunicano
 * via RMI.
 * 
 */
public class InterfacciaRMIServer extends InterfacciaOnline {
	private static final long TIMER = 5000;
	private CanaleRMIImpl[] canali;
	private Encoder encoder;
	private PingPongImpl[] pings;

	/**
	 * Costruttore dell'interfaccia di comunicazione RMI.
	 * @param canali2
	 */
	public InterfacciaRMIServer(CanaleRMIImpl[] canali2) {
		this.canali = canali2;
		encoder = new Encoder();
		pings = new PingPongImpl[canali.length];
		for(int i=0;i<canali.length; i++)
			try {
				pings[i] = (PingPongImpl) canali[i].getPingPong();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
	}

	@Override
	public int leggiProssimoIntero(Giocatore giocatoreCorrente)
			throws DisconnessoGiocatoreCorrenteException {
		int indice = giocatoreCorrente.getIndice();
		long beforeTime = 0;
		long elapsedTime = 0;
		boolean freezed = false;
		while (TIMER - elapsedTime > 0) {
			try {
				Integer numLetto=null;
				while(numLetto == null){
					pings[indice].pinga();
					numLetto = canali[indice].readIntServer();
				}
				return numLetto;
			} catch (RemoteException e) {
				if (!freezed) {
					beforeTime = System.currentTimeMillis();
					inviaTuttiGiocatori(encoder.freeze(giocatoreCorrente)); // Freeze
					freezed = true;
				}
			}
			elapsedTime = System.currentTimeMillis() - beforeTime;
		}
		canali[indice].disattiva();
		inviaTuttiGiocatori(encoder.disconnesso(giocatoreCorrente));// Disconnesso
		throw new DisconnessoGiocatoreCorrenteException();
	}

	@Override
	public String leggiProssimaStringa(Giocatore giocatoreCorrente)
			throws DisconnessoGiocatoreCorrenteException {
		int indice = giocatoreCorrente.getIndice();
		long beforeTime = 0;
		long elapsedTime = 0;
		boolean freezed = false;
		while (TIMER - elapsedTime > 0) {
			try {
				String stringaLetta=null;
				while(stringaLetta == null){
					pings[indice].pinga();
					stringaLetta = canali[indice].readLineServer();
				}
				return stringaLetta;
			} catch (RemoteException e) {
				if (!freezed) {
					beforeTime = System.currentTimeMillis();
					inviaTuttiGiocatori(encoder.freeze(giocatoreCorrente)); // Freeze
					freezed = true;
				}
			}
			elapsedTime = System.currentTimeMillis() - beforeTime;
		}
		canali[indice].disattiva();
		inviaTuttiGiocatori(encoder.disconnesso(giocatoreCorrente));// Disconnesso
		throw new DisconnessoGiocatoreCorrenteException();
	}

	@Override
	public void inviaSingoloGiocatore(Giocatore giocatore, String outputString)
			throws DisconnessoGiocatoreCorrenteException {
		int indice = giocatore.getIndice();
		long beforeTime = 0;
		long elapsedTime = 0;
		boolean freezed = false;
		while (TIMER - elapsedTime > 0) {
			try {
				pings[indice].pinga();
				canali[indice].writeServer(outputString);
				return;
			} catch (Exception e) {
				if (!freezed) {
					beforeTime = System.currentTimeMillis();
					inviaTuttiGiocatori(encoder.freeze(giocatore)); // Freeze
					freezed = true;
				}
			}
			elapsedTime = System.currentTimeMillis() - beforeTime;
		}
		canali[indice].disattiva();
		inviaTuttiGiocatori(encoder.disconnesso(giocatore));// disconnesso
		throw new DisconnessoGiocatoreCorrenteException();

	}

	@Override
	public void inviaTuttiGiocatori(String outputString) {
		for (int i = 0; i < canali.length; i++)
			if(canali[i].isAttivo())
				try {
					canali[i].writeServer(outputString);
				} catch (RemoteException e) {
					;
				}

	}

	@Override
	public void sincronizzaClient(int indice, GameBoard gameBoard,
			Giocatore[] giocatori, int[] carteDisponibili)
			throws RemoteException {

		canali[indice].writeServer(giocatori.length);
		String sincronizzazione = encoder.startUpDataToString(indice,
				gameBoard, giocatori, carteDisponibili);
		canali[indice].writeServer(sincronizzazione);
	}

}
