package online.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.print.attribute.standard.MediaSize.Other;

import online.DisconnessoGiocatoreCorrenteException;
import online.PartitaOnline;
import view.InterfacciaPartitaUtente;
import controller.Giocatore;
import controller.Mercato;
import controller.Offerta;
import controller.Partita;
import model.Agnello;
import model.GameBoard;
import model.Lupo;
import model.Pastore;
import model.PecoraNera;
import model.Regione;
import model.Strada;
import model.Terreno;

/**
 * Classe presente sul server che si occupa di gestire una partita via socket.
 * 
 */
@SuppressWarnings("unused")
public class GestorePartitaSocket extends Thread {

	private InterfacciaSocketServer interfaccia;
	private PartitaOnline partita;
	private Socket[] sockets;
	private Giocatore vincitore;
	private int numeroGiocatori;
	private boolean partitaFinita;

	/**
	 * Costruttore.
	 * 
	 * @param sockets
	 */
	public GestorePartitaSocket(Socket[] sockets) {
		this.sockets = sockets;
		for (int i = 0; i < numeroGiocatori; i++)
			try {
				this.sockets[i].setTcpNoDelay(true);
			} catch (SocketException e) {
				e.printStackTrace();
			}
		numeroGiocatori = sockets.length;
		partitaFinita = false;
	}

	public void run() {
		System.out.println("Starting Game");
		interfaccia = new InterfacciaSocketServer(sockets);
		partita = new PartitaOnline(interfaccia, numeroGiocatori);
		for (int i = 0; i < numeroGiocatori; i++)
			try {
				interfaccia.sincronizzaClient(i, partita.getGameBoard(),
						partita.getGiocatori(), partita.getCarteDisponibili());
			} catch (IOException e) {
				e.printStackTrace();
			}
		try {
			sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		vincitore = partita.startGame();
		partitaFinita = true;
		int[] punteggi = new int[numeroGiocatori];
		for(int i=0; i<numeroGiocatori; i++)
			punteggi[i]=partita.getGiocatore(i).getDanari();
		interfaccia.annunciaVincitore(vincitore,punteggi);
		for (Giocatore x : partita.getGiocatori()) {
			if (x.isConnesso())
				;
			else
				while (!x.isConnesso())
					;
		}
		return;
	}

	/**
	 * Metodo per riconnettere alla partita un giocatore.
	 * 
	 * @param socket
	 */
	public void riconnetti(Socket socket) {
		synchronized (sockets) {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
						socket.getOutputStream()));
				socket.setTcpNoDelay(true);
				int indice = in.read();
				if (indice < 0 || indice > 4
						|| partita.getGiocatore(indice).isConnesso()) {
					out.write(-1);
					out.flush();
					return;
				} else {
					sockets[indice] = socket;
					out.write(indice);
					out.flush();
					if (!partitaFinita) {
						interfaccia.sincronizzaClient(indice,
								partita.getGameBoard(), partita.getGiocatori(),
								partita.getCarteDisponibili());
						interfaccia.notificaRiconnessione(indice);
						partita.getGiocatore(indice).riconnetti();

					} else {
						interfaccia.finePartita();
						int[] punteggi = new int[numeroGiocatori];
						for(int i=0; i<numeroGiocatori; i++)
							punteggi[i]=partita.getGiocatore(i).getDanari();
						interfaccia.annunciaVincitore(vincitore,punteggi);
						partita.getGiocatore(indice).riconnetti();
					}
				}
			} catch (Exception e) {
				return;
			}
		}
	}

}
