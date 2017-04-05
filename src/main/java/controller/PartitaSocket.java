package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import online.Decoder;
import online.socket.ServerSocketSheep;
import view.InterfacciaPartitaUtente;
import view.console.ConsoleView;
import view.graphic.GraphicUserInterface;
import model.GameBoard;
import model.Pastore;

/**
 * Classe che gestisce la partita sul client con comunicazione via socket.
 * 
 */
public class PartitaSocket {

	private Decoder decoder;
	private int sessionID;
	private Integer indicePersonale;
	private InterfacciaPartitaUtente interfaccia;
	private Socket socket;
	private GameBoard gameBoard;
	private Giocatore[] giocatori;
	private int[] carteDisponibili;
	int numeroGiocatori;

	private BufferedReader in;
	private BufferedWriter out;

	/**
	 * Costruttore per la partita.
	 * 
	 * @param Grafica
	 *            true per la grafica, false per la console.
	 */
	public PartitaSocket(boolean Grafica) {
		if (Grafica)
			interfaccia = new GraphicUserInterface();
		else
			interfaccia = new ConsoleView();
		sessionID = -1;
		indicePersonale = -1;
	}

	/**
	 * Costruttore per i test.
	 * 
	 * @param interfaccia
	 */
	public PartitaSocket(InterfacciaPartitaUtente interfaccia) {
		this.interfaccia = interfaccia;
		sessionID = -1;
		indicePersonale = -1;
	}

	/**
	 * Metodo che fa iniziare la partita.
	 */
	public void startPartita() {
		entraInGioco();
		faseDiGioco();
	}

	/**
	 * Metodo per collegarsi al server ed entare in gioco ricevendo i dati di
	 * gioco dal server.
	 * 
	 */
	private void entraInGioco() {
		boolean connesso = false;
		while (!connesso) {
			try {
				connessione();
				setUp();
				connesso = true;
				System.out.println("Connesso alla partita.");
			} catch (Exception e) {
				entraInGioco();
			}
		}
	}

	/**
	 * Metodo per la connessione al server.
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private void connessione() throws UnknownHostException, IOException {
		boolean connesso = false;
		while (!connesso) {
			System.out.println("Connessione in corso...");
			socket = new Socket("127.0.0.1", ServerSocketSheep.PORT);
			System.out.println("Connesso al server.");
			out = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			out.write(sessionID);
			out.flush();
			sessionID = in.read();
			System.out.println("Session ID " + sessionID);
			out.write(indicePersonale);
			out.flush();
			indicePersonale = in.read();
			if (indicePersonale == -1) {
				System.out
						.println("Accesso non valido! Verrai reindirizzato in una nuova partita!");
				return;
			}
			interfaccia.indicePersonale(indicePersonale);
			connesso = true;
		}
	}

	/**
	 * Metodo che gestisce la fase di gioco riccevendo le istruzioni dal server
	 * e comunicando a sua volta le scelte del giocatore durante il suo turno.
	 * 
	 */
	private void faseDiGioco() {
		interfaccia.mostraCarte(giocatori[indicePersonale]);
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			boolean finePartita = false;
			while (!finePartita) {
				String istruzione = in.readLine();
				int ID = Integer.parseInt(istruzione.substring(0, 2));
				istruzione = istruzione.substring(2);
				int scelta;
				switch (ID) {
				case (0):
					scelta = decoder.posizionamentoInizialePastore(istruzione);
					out.write(scelta);
					out.flush();
					break;
				case (1):
					decoder.pastoreBloccato(istruzione);
					break;
				case (2):
					scelta = decoder.sceltaMossa(istruzione);
					out.write(scelta);
					out.flush();
					break;
				case (3):
					decoder.giocatoreSaltaTurno(istruzione);
					break;
				case (4):
					scelta = decoder.sceltaPastore(istruzione);
					out.write(scelta);
					out.flush();
					break;
				case (5):
					decoder.fineTurno(istruzione);
					break;
				case (6):
					decoder.spostaPecoraNera(istruzione);
					break;
				case (7):
					scelta = decoder.movimentoPecoraNera(istruzione);
					out.write(scelta);
					out.flush();
					break;
				case (8):
					scelta = decoder.sceltaStradaPastore(istruzione);
					out.write(scelta);
					out.flush();
					break;
				case (9):
					decoder.movimentoPastore(istruzione);
					break;
				case (10):
					scelta = decoder.sceltaCarta(istruzione);
					out.write(scelta);
					out.flush();
					break;
				case (11):
					decoder.acquistoCarta(istruzione);
					break;
				case (12):
					scelta = decoder.sceltaRegioneAbbattimento(istruzione);
					out.write(scelta);
					out.flush();
					break;
				case (13):
					scelta = decoder.sceltaAnimaleDaAbbattere(istruzione);
					out.write(scelta);
					out.flush();
					break;
				case (14):
					decoder.abbattimento(istruzione);
					break;
				case (15):
					scelta = decoder.sceltaRegioneAccoppiamento(istruzione);
					out.write(scelta);
					out.flush();
					break;
				case (16):
					decoder.accoppiamento(istruzione);
					break;
				case (17):
					scelta = decoder.sceltaRegioneSpostamento(istruzione);
					out.write(scelta);
					out.flush();
					break;
				case (18):
					scelta = decoder.sceltaPecoraDaSpostare(istruzione);
					out.write(scelta);
					out.flush();
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
					out.write(offertaFattaString + "\n");
					out.flush();
					break;
				case (25):
					decoder.crescitaAgnello(istruzione);
					break;
				case (26):
					Offerta offertaAccettata = decoder
							.accettaOfferta(istruzione);
					String offertaString = decoder
							.offertaToString(offertaAccettata);
					out.write(offertaString + "\n");
					out.flush();
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
					out.write(scelta);
					out.flush();
					break;
				case (32):
					decoder.inizioAcquistiMercato(istruzione);
					break;
				case (33):
					scelta = decoder.tiroDadoGiocatore(istruzione);
					out.write(scelta);
					out.flush();
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
			entraInGioco();
		}
	}

	/**
	 * Metodo per inizializzare la gameboard e la partita.
	 * 
	 * @throws IOException
	 */
	private void setUp() throws IOException {
		numeroGiocatori = in.read();
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
		String sincronizzazione = in.readLine();

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

}
