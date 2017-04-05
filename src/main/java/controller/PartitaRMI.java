package controller;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import model.GameBoard;
import model.Pastore;
import online.Decoder;
import online.RMI.CanaleRMI;
import online.RMI.LogIn;
import online.RMI.PingPong;
import online.RMI.ServerRMISheep;
import view.InterfacciaPartitaUtente;
import view.console.ConsoleView;
import view.graphic.GraphicUserInterface;

/**
 * CLasse che gestisce la partita lato client con comunicazione RMI.
 *
 */
public class PartitaRMI {
	private Decoder decoder;
	private int sessionID;
	private int indicePersonale;
	private InterfacciaPartitaUtente interfaccia;
	private CanaleRMI canale;
	private GameBoard gameBoard;
	private Giocatore[] giocatori;
	private int[] carteDisponibili;
	private int numeroGiocatori;
	private Thread pingPongThread = new Thread(new Runnable() {
		
		public void run() {
			while(true){
				try {
					pingPong.ponga();
					synchronized(this){
					this.wait(1000);}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}); 
	
	private PingPong pingPong;

	
	
	/**
	 * Costruttore per la partita RMI, che può essere giocata via console o con grafica.
	 * @param GUI se è true, viene istanziata l'interfaccia grafica, altrimenti quella per la console.
	 */
	public PartitaRMI(boolean GUI) {
		if (GUI)
			interfaccia = new GraphicUserInterface();
		else
			interfaccia = new ConsoleView();
	}

	/**
	 * Costruttore usato per i test.
	 * @param interfaccia
	 */
	public PartitaRMI(InterfacciaPartitaUtente interfaccia) {
		this.interfaccia = interfaccia;
	}

	/**
	 * Metodo per far iniziare la partita comprensivo di connessione al server e fase di gioco.
	 */
	public void startPartita() {
		entraInGioco();
		faseDiGioco();
	}

	/*
	 * Metodo che gestisce la fase di gioco vera e propria ricevendo le istruzioni dal server.
	 * 
	 */
	private void faseDiGioco() {
		interfaccia.mostraCarte(giocatori[indicePersonale]);
		try {
			boolean finePartita = false;
			while (!finePartita) {
				String istruzione = canale.readLineClient();
				int ID = Integer.parseInt(istruzione.substring(0, 2));
				istruzione = istruzione.substring(2);
				int scelta;
				switch (ID) {
				case (0):
					scelta = decoder.posizionamentoInizialePastore(istruzione);
					canale.writeClient(scelta);
					break;
				case (1):
					decoder.pastoreBloccato(istruzione);
					break;
				case (2):
					scelta = decoder.sceltaMossa(istruzione);
					canale.writeClient(scelta);
					break;
				case (3):
					decoder.giocatoreSaltaTurno(istruzione);
					break;
				case (4):
					scelta = decoder.sceltaPastore(istruzione);
					canale.writeClient(scelta);
					break;
				case (5):
					decoder.fineTurno(istruzione);
					break;
				case (6):
					decoder.spostaPecoraNera(istruzione);
					break;
				case (7):
					scelta = decoder.movimentoPecoraNera(istruzione);
					canale.writeClient(scelta);
					break;
				case (8):
					scelta = decoder.sceltaStradaPastore(istruzione);
					canale.writeClient(scelta);
					break;
				case (9):
					decoder.movimentoPastore(istruzione);
					break;
				case (10):
					scelta = decoder.sceltaCarta(istruzione);
					canale.writeClient(scelta);
					break;
				case (11):
					decoder.acquistoCarta(istruzione);
					break;
				case (12):
					scelta = decoder.sceltaRegioneAbbattimento(istruzione);
					canale.writeClient(scelta);
					break;
				case (13):
					scelta = decoder.sceltaAnimaleDaAbbattere(istruzione);
					canale.writeClient(scelta);
					break;
				case (14):
					decoder.abbattimento(istruzione);
					break;
				case (15):
					scelta = decoder.sceltaRegioneAccoppiamento(istruzione);
					canale.writeClient(scelta);
					break;
				case (16):
					decoder.accoppiamento(istruzione);
					break;
				case (17):
					scelta = decoder.sceltaRegioneSpostamento(istruzione);
					canale.writeClient(scelta);
					break;
				case (18):
					scelta = decoder.sceltaPecoraDaSpostare(istruzione);
					canale.writeClient(scelta);
					break;
				case (19):
					decoder.spostamentoPecora(istruzione);
					break;
				case (20):
					decoder.inizioTurno(istruzione);
					break;
				case (21):
					decoder.mossoLupo(istruzione);
					break;
				case (22):
					decoder.nonPagaSilenzio(istruzione);
					break;
				case (23):
					decoder.pagaSilenzio(istruzione);
					break;
				case (24):
					Offerta offertaFatta = decoder.offriMercato(istruzione);
					String offertaFattaString = decoder
							.offertaToString(offertaFatta);
					canale.writeClient(offertaFattaString);
					break;
				case (25):
					decoder.crescitaAgnello(istruzione);
					break;
				case (26):
					Offerta offertaAccettata = decoder
							.accettaOfferta(istruzione);
					String offertaString = decoder
							.offertaToString(offertaAccettata);
					canale.writeClient(offertaString);
					break;
				case (27):
					decoder.inizioMercato(istruzione);
					break;
				case (28):
					decoder.finePartita(istruzione);
					break;
				case (29):
					decoder.annunciaVincitore(istruzione);
					finePartita = true;
					break;
				case (30):
					decoder.faseFinale(istruzione);
					break;
				case (31):
					scelta = decoder.tiroDadoAbbattimento(istruzione);
					canale.writeClient(scelta);
					break;
				case (32):
					decoder.inizioAcquistiMercato(istruzione);
					break;
				case (33):
					scelta = decoder.tiroDadoGiocatore(istruzione);
					canale.writeClient(scelta);
					break;
				case (34):
					decoder.fineMercato();
					break;
				case (35):
					decoder.posizionatoPastore(istruzione);
					break;
				case (36):
					decoder.offertaAccettata(istruzione);
					break;
				case (37):
					decoder.fineMercato();
					break;
				case (97):
					decoder.freeze(istruzione);
					break;
				case (98):
					decoder.disconnesso(istruzione);
					break;
				case (99):
					decoder.riconnesso(istruzione);
					break;
				}
			}
		} catch (IOException e) {
			interfaccia.disconnessionePersonale();
			pingPongThread.interrupt();
			ritornaInGioco();
		}
	}

	/**
	 * Metodo chiamato in caso di disconnessione personale che continua a tentare la riconnessione.
	 */
	private void ritornaInGioco() {
		boolean connesso = false;
		while (!connesso) {
			try {
				riconnessione();
				setUp();
				connesso = true;
			} catch (Exception e) {
				ritornaInGioco();
			}
		}
	}

	/**
	 * Metodo per la riconnessione alla partita.
	 * @throws RemoteException
	 */
	private void riconnessione() throws RemoteException{
		canale.reconnect(indicePersonale);
	}

	/**
	 * Metodo per entrare nella partita, comprende la connessione e la fase di set up. 
	 */
	private void entraInGioco() {
		boolean connesso = false;
		while (!connesso) {
			try {
				connessione();
				setUp();
				connesso = true;
			} catch (Exception e) {
				entraInGioco();
			}
		}

	}

	/**
	 * Metodo per inizizializzare la partita e sincronizzarsi col server.
	 * @throws RemoteException
	 */
	private void setUp() throws RemoteException {
		numeroGiocatori = canale.readIntClient();
		pingPongThread.start();
		System.out.println(numeroGiocatori + " Giocatori");
		interfaccia.inserimentoNumeroGiocatori(numeroGiocatori);
		gameBoard = new GameBoard(numeroGiocatori);
		giocatori = new Giocatore[numeroGiocatori];

		for (int i = 0; i < numeroGiocatori; i++)
			giocatori[i] = new Giocatore("Giocatore" + (i + 1), i);

		if (numeroGiocatori > 2)
			for (int i = 0; i < numeroGiocatori; i++) {
				Pastore pastore = gameBoard.getPastore(i);
				giocatori[i].assegnaPastore(pastore, false);
			}
		else
			for (int i = 0; i < numeroGiocatori; i++) {
				Pastore pastore = gameBoard.getPastore(i * 2);
				giocatori[i].assegnaPastore(pastore, false);
				pastore = gameBoard.getPastore((i * 2) + 1);
				giocatori[i].assegnaPastore(pastore, true);
			}
		String sincronizzazione = canale.readLineClient();

		carteDisponibili = new int[6];
		Integer[] carteTempInteger = new Integer[6];
		decoder = new Decoder(gameBoard, interfaccia, giocatori,
				indicePersonale);
		decoder.sincronizza(gameBoard, giocatori, carteTempInteger,
				sincronizzazione, indicePersonale);
		for (int i = 0; i < 6; i++) {
			carteDisponibili[i] = carteTempInteger[i];
		}
		interfaccia.createAndShowMap(gameBoard, carteDisponibili, giocatori);

	}

	/**
	 * Metodo per la connessione al server RMI che si collega al registro del server.
	 */
	private void connessione() {
		try {
			Registry registry = LocateRegistry.getRegistry("localhost",
					ServerRMISheep.SERVER_PORT);

			LogIn log = (LogIn) registry.lookup(ServerRMISheep.SERVER_NAME);

			canale = (CanaleRMI) log.getConnection();
			pingPong = (PingPong) canale.getPingPong();
			sessionID = canale.getSessionID();
			indicePersonale = canale.getIndicePersonale();

			System.out.println("Connection gained: sessionID " + sessionID);
			interfaccia.indicePersonale(indicePersonale);
		} catch (RemoteException e) {
			System.err.println("Remote exception:");
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.err.println("Name " + ServerRMISheep.SERVER_NAME
					+ " not bound.");
		}

	}

}
