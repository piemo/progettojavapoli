package online.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import online.DisconnessoGiocatoreCorrenteException;
import online.Encoder;
import online.InterfacciaOnline;
import model.GameBoard;
import controller.Giocatore;

/**
 * Classe che si occupa della comunicazione ra cient e server che utilizzano
 * socket. Utilizza un encoder per formattare le informazioni in signole
 * stringhe.
 */
public class InterfacciaSocketServer extends InterfacciaOnline {
	private static final long TIMER = 5000;
	private Socket[] sockets;
	private Encoder encoder;

	/**
	 * Costruttore che riceve un array di socket.
	 * @param sockets
	 */
	public InterfacciaSocketServer(Socket[] sockets) {
		this.sockets = sockets;
		encoder = new Encoder();
	}

	@Override
	public int leggiProssimoIntero(Giocatore giocatoreCorrente)
			throws DisconnessoGiocatoreCorrenteException {
		long beforeTime = 0;
		long elapsedTime = 0;
		boolean freezed = false;
		while (TIMER - elapsedTime > 0) {
			try {
				Socket socket = sockets[giocatoreCorrente.getIndice()];
				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				int numRicevuto = in.read();
				if (numRicevuto == -1) {
					throw new IOException();
				} else
					return numRicevuto;
			} catch (IOException e) {
				if (!freezed) {
					beforeTime = System.currentTimeMillis();
					inviaTuttiGiocatori(encoder.freeze(giocatoreCorrente)); // Freeze
					freezed = true;
				}
			}
			elapsedTime = System.currentTimeMillis() - beforeTime;
		}
		inviaTuttiGiocatori(encoder.disconnesso(giocatoreCorrente));// Disconnesso
		throw new DisconnessoGiocatoreCorrenteException();

	}

	@Override
	public String leggiProssimaStringa(Giocatore giocatoreCorrente)
			throws DisconnessoGiocatoreCorrenteException {
		long beforeTime = 0;
		long elapsedTime = 0;
		boolean freezed = false;
		while (TIMER - elapsedTime > 0) {
			try {
				Socket socket = sockets[giocatoreCorrente.getIndice()];
				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				String lettura = in.readLine();
				if (lettura == null) {
					throw new IOException();
				} else
					return lettura;
			} catch (IOException e) {
				if (!freezed) {
					beforeTime = System.currentTimeMillis();
					inviaTuttiGiocatori(encoder.freeze(giocatoreCorrente)); // Freeze
					freezed = true;
				}
			}
			elapsedTime = System.currentTimeMillis() - beforeTime;
		}
		inviaTuttiGiocatori(encoder.disconnesso(giocatoreCorrente));// disconnesso
		throw new DisconnessoGiocatoreCorrenteException();
	}

	@Override
	public void inviaSingoloGiocatore(Giocatore giocatore, String outputString)
			throws DisconnessoGiocatoreCorrenteException {
		long beforeTime = 0;
		long elapsedTime = 0;
		boolean freezed = false;
		while (TIMER - elapsedTime > 0) {
			try {
				Socket socket = sockets[giocatore.getIndice()];
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
						socket.getOutputStream()));
				out.write(outputString + "\n");
				out.flush();
				return;
			} catch (IOException e) {
				if (!freezed) {
					beforeTime = System.currentTimeMillis();
					inviaTuttiGiocatori(encoder.freeze(giocatore)); // Freeze
					freezed = true;
				}
			}
			elapsedTime = System.currentTimeMillis() - beforeTime;
		}
		inviaTuttiGiocatori(encoder.disconnesso(giocatore));// disconnesso
		throw new DisconnessoGiocatoreCorrenteException();
	}

	@Override
	public void inviaTuttiGiocatori(String outputString) {
		for (int i = 0; i < sockets.length; i++) {
			try {
				PrintWriter out = new PrintWriter(sockets[i].getOutputStream());
				out.print(outputString + "\n");
				out.flush();
			} catch (IOException e) {
			}
		}
	}

	@Override
	public void sincronizzaClient(int indice, GameBoard gameBoard,
			Giocatore[] giocatori, int[] carteDisponibili) throws IOException {
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
				sockets[indice].getOutputStream()));
		String sincronizzazione = "";
		out.write(giocatori.length);
		out.flush();
		sincronizzazione = encoder.startUpDataToString(indice, gameBoard,
				giocatori, carteDisponibili);
		out.write(sincronizzazione);
		out.flush();
	}

}