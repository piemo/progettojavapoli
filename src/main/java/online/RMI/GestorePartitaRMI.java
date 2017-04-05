package online.RMI;

import java.io.IOException;

import online.PartitaOnline;
import controller.Giocatore;

/**
 * Classe che si occupa di getsire una partita via RMI.
 * 
 */
public class GestorePartitaRMI extends Thread {
	private InterfacciaRMIServer interfaccia;
	private PartitaOnline partita;
	private CanaleRMIImpl[] canali;
	private Giocatore vincitore;
	private int numeroGiocatori;
	private boolean partitaFinita;
	
	/**
	 * Costruttore del gestore della partita.
	 * @param canali
	 */
	public GestorePartitaRMI(CanaleRMIImpl[] canali) {
		this.canali = canali;
		numeroGiocatori = canali.length;
		partitaFinita = false;
	}

	/**
	 * Metodo run per iniziare la partita sincronizzando i client.
	 */
	public void run() {
		System.out.println("Starting Game");
		interfaccia = new InterfacciaRMIServer(canali);
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
		interfaccia.annunciaVincitore(vincitore, punteggi);
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
	 * Metodo per riconnetere un giocatore alla partita.
	 * 
	 * @param indicePersonale
	 * @param nuovoCanale
	 */
	public synchronized void riconnetti(int indicePersonale) {
		try {
			if (!partitaFinita) {
				interfaccia.sincronizzaClient(indicePersonale,
						partita.getGameBoard(), partita.getGiocatori(),
						partita.getCarteDisponibili());
				interfaccia.notificaRiconnessione(indicePersonale);
				canali[indicePersonale].attiva();
				partita.getGiocatore(indicePersonale).riconnetti();
			} else {
				interfaccia.finePartita();
				int[] punteggi = new int[numeroGiocatori];
				for(int i=0; i<numeroGiocatori; i++)
					punteggi[i]=partita.getGiocatore(i).getDanari();
				interfaccia.annunciaVincitore(vincitore,punteggi);
				canali[indicePersonale].attiva();
				partita.getGiocatore(indicePersonale).riconnetti();
			}
		} catch (Exception e) {
		}

	}
}
