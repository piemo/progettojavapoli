package model;

/**
 * Classe che rappresenta la pedina del pastore. Possiede il numero del
 * giocatore a cui appartiene, la posizione attuale e se � il secondo pastore
 * (nel caso di partita con 2 soli gioatori).
 */
public class Pastore {
	private int giocatore;
	private Strada stradaAttuale;
	private boolean secondoPastore;

	/**
	 * Costruttore che assegna il giocatore al pastore.
	 * 
	 * @param numGiocatore
	 * @param secondoPastore
	 *            indica se si tratta del secondo pastore (n caso di partita con
	 *            2 soli giocatori).
	 */
	public Pastore(int numGiocatore, boolean secondoPastore) {
		giocatore = numGiocatore;
		this.secondoPastore = secondoPastore;
	}

	/**
	 * Metodo per sapere di chi è il pastore.
	 * @return
	 */
	public int getGiocatore() {
		return giocatore;
	}

	/**
	 * Metodo per spostare un pastore su una determinata strada durante la
	 * partita. In seguito allo spostamento viene messo un recinto sulla strada
	 * da cui si � spostato.
	 * 
	 * @param nuovaStrada
	 */
	public void spostaPastore(Strada nuovaStrada) {
		stradaAttuale.setRecinto();
		posizionaPastore(nuovaStrada);
	}

	/**
	 * Metodo per posizionare il pastore all'inizio del gioco.
	 * 
	 * @param nuovaStrada
	 */
	public void posizionaPastore(Strada nuovaStrada) {
		stradaAttuale = nuovaStrada;
		stradaAttuale.setOccupata();
	}

	/**
	 * Metodo per sapere su quale strada è posizionato.
	 * @return
	 */
	public Strada getStrada() {
		return stradaAttuale;
	}

	public String toString() {

		switch (giocatore) {

		case (0):
			if (secondoPastore)
				return "Secondo pastore ROSSO";
			else
				return "Pastore ROSSO";
		case (1):
			if (secondoPastore)
				return "Secondo pastore BLUE";
			else
				return "Pastore BLUE";
		case (2):
			if (secondoPastore)
				return "Secondo pastore VERDE";
			return "Pastore VERDE";
		case (3):
			if (secondoPastore)
				return "Secondo pastore GIALLO";
			else
				return "Pastore GIALLO";
		case (4):
			if (secondoPastore)
				return "Secondo pastore NERO";
			else
				return "Pastore NERO";
		case (5):
			if (secondoPastore)
				return "Secondo pastore BIANCO";
			else
				return "Pastore BIANCO";
		}

		return "Pastore";
	}

	/**
	 * Metodo per sapere se è il primo o il secondo pastore.
	 * @return
	 */
	public boolean isSecondo() {
		return secondoPastore;
	}

}
