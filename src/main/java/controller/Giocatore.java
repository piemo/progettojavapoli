package controller;

import model.Pastore;
import model.Terreno;

/**
 * Classe che rappresenta i giocatori della partita.
 *
 */
public class Giocatore {
	private boolean connesso;
	private String nome;

	private Pastore pastore;
	private Pastore pastore2;

	private int danari;
	private int indice;
	private int numAGRICOLI;
	private int numARIDI;
	private int numFIUMI;
	private int numFORESTE;
	private int numMONTAGNE;
	private int numPRATI;

	/**
	 * Costruttore.
	 * @param nome il nome del giocatore.
	 * @param indice l'indice del giocatore.
	 */
	public Giocatore(String nome, int indice) {
		this.nome = nome;
		pastore2 = null;
		connesso = true;
		this.indice = indice;
	}

	/**
	 * metodo chiamato per assegnare pastori ai giocatori. Nel caso si volesse
	 * assegnare il secondo pastore bisogna passare un valore true ( valido per
	 * 2 giocatori )
	 * 
	 * @param pastore
	 * @param secondoPastore
	 */
	public void assegnaPastore(Pastore pastore, boolean secondoPastore) {
		if (!secondoPastore)
			this.pastore = pastore;
		else
			this.pastore2 = pastore;
	}

	/**
	 * Metodo per prendere l'indice del giocatore.
	 * @return
	 */
	public int getIndice() {
		return this.indice;
	}

	/**
	 * Metodo per prendere il primo pastore del giocatore
	 * @return
	 */
	public Pastore getPastore() {
		return pastore;
	}

	/**
	 * Metodo per prendere il secondo pastore del giocatore.
	 * @return
	 */
	public Pastore getSecondoPastore() {
		return pastore2;
	}

	
	public String toString() {
		return nome;
	}

	/**
	 * @return
	 */
	public int getDanari() {
		return danari;
	}

	/**
	 * @param danari
	 */
	public void setDanari(int danari) {
		this.danari = danari;
	}

	/**
	 * Metodo chiamato per diminuire la quantit� di danari del giocatore
	 * 
	 * @param quantità
	 */
	public void pagamento(int quantita) {
		if (danari >= quantita)
			danari -= quantita;
	}

	/**
	 * Metodo chiamato per aumentare la quantit� di danari del giocatore
	 * 
	 * @param quantità
	 */
	public void aumentaDanari(int quantita) {
		danari += quantita;
	}

	/**
	 * Metodo che restituisce un array di interi rappresentate il numero di
	 * carte possedute. Segue l'ordine dell'enum di terreni
	 * 
	 * @return
	 */
	public int[] getCarte() {
		int[] carte = new int[6];
		carte[0] = numAGRICOLI;
		carte[1] = numARIDI;
		carte[2] = numFIUMI;
		carte[3] = numFORESTE;
		carte[4] = numMONTAGNE;
		carte[5] = numPRATI;
		return carte;
	}

	/**
	 * Metodo chiamato per aumentare il numero di carte di un tipo terreno dato
	 * 
	 * @param tipo
	 */
	public void assegnaCarta(Terreno tipo) {
		switch (tipo) {
		case AGRICOLO:
			numAGRICOLI++;
			return;
		case ARIDO:
			numARIDI++;
			return;
		case FIUME:
			numFIUMI++;
			return;
		case FORESTA:
			numFORESTE++;
			return;
		case MONTAGNA:
			numMONTAGNE++;
			return;
		case PRATO:
			numPRATI++;
			return;
		default:
			return;
		}
	}

	/**
	 * Metodo che rimuove i danari associati al prezzo di una carta acquistata,
	 * che viene poi assegnata.
	 * 
	 * @param tipo
	 *            il tipo di terreno rappresentato in una carta
	 * @param prezzo
	 */
	public void acquistoCarta(Terreno tipo, int prezzo) {
		danari -= prezzo;
		assegnaCarta(tipo);
	}

	/**
	 * Metodo chiamato per diminuire il numero di carte di un tipo terreno dato
	 * 
	 * @param tipo
	 */
	public void togliCarta(Terreno tipo) {
		switch (tipo) {
		case AGRICOLO:
			numAGRICOLI--;
			return;
		case ARIDO:
			numARIDI--;
			return;
		case FIUME:
			numFIUMI--;
			return;
		case FORESTA:
			numFORESTE--;
			return;
		case MONTAGNA:
			numMONTAGNE--;
			return;
		case PRATO:
			numPRATI--;
			return;
		default:
			return;
		}
	}

	/**
	 * Metodo che aumenta i danari associati al prezzo di una carta venduta.
	 * 
	 * @param tipo
	 *            il tipo di terreno rappresentato in una carta
	 * @param prezzo
	 */
	public void venditaCarta(Terreno tipo, int prezzo) {
		danari += prezzo;
		togliCarta(tipo);
	}

	/**
	 * Metodo per cambiare lo stato del giocatore a disconnesso.
	 */
	public void disconnetti() {
		connesso = false;
	}

	/**
	 * Metodo per cambiare lo stato del giocatore a connesso.
	 */
	public void riconnetti() {
		connesso = true;
	}

	/**
	 * Metodo per sapere se il giocatore è connesso
	 * @return
	 */
	public boolean isConnesso() {
		return connesso;
	}

	/**
	 * Metodo per impostare il nome a un giocatore.
	 * @param nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	/**
	 * Metodo per azzerare le cartedi un giocatore.
	 */
	public void resetCarte(){
		numAGRICOLI = 0;
		numARIDI = 0;
		numFIUMI = 0;
		numFORESTE = 0;
		numMONTAGNE = 0;
		numPRATI = 0;
	}

}
