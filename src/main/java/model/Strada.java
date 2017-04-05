package model;

import java.util.ArrayList;

/**
 * Classe he rappresenta la strada. La strada possiede un ID, un proprio valore,
 * un array di due regioni adiacenti, e una lista di strade a lei vicine.
 * Inoltre la strada pu� diventare occupata quando � presente un pastore per poi
 * diventare anche recintata quando questo si sposta.
 */
public class Strada {
	private int ID;
	private Regione[] regioniAdiacenti;
	private ArrayList<Strada> stradeVicine;
	private int valore;
	private boolean recinto;
	private boolean occupata;

	/**
	 * Costruttore. Crea la strada con un determinato ID e inizializza i
	 * booleani che rappresentano l'occupazione a false.
	 * 
	 * @param ID
	 *            l'ID della strada creata.
	 */
	Strada(int ID) {
		this.ID = ID;
		regioniAdiacenti = new Regione[2];
		recinto = false;
		occupata = false;
		stradeVicine = new ArrayList<Strada>();
	}

	/**
	 * Metodo per settare le due regioni adiacenti e il valore della strada. Il
	 * metodo viene usato nell'inizializzazione della mappa di gioco.
	 * 
	 * @param valore
	 * @param regioneAdiacente1
	 * @param regioneAdiacente2
	 */
	public void setAdiacenze(int valore, Regione regioneAdiacente1,
			Regione regioneAdiacente2) {
		this.valore = valore;
		regioniAdiacenti[0] = regioneAdiacente1;
		regioniAdiacenti[1] = regioneAdiacente2;

	}

	/**
	 * Metodo che serve a settare le strade vicine. Il metodo viene utilizzato
	 * nell'inizializzazione della mappa di gioco.
	 * 
	 * @param strade
	 */
	public void setStradeVicine(ArrayList<Strada> strade) {
		stradeVicine = strade;
	}

	public String toString() {
		return ("Strada n°" + ID);
	}

	/**
	 * Metodo che restituisce la lista delle strade vicine libere, ovvero che
	 * non sono occupate da un pastore o da un recinto.
	 * 
	 * @return la lista di starde libere vicine.
	 */
	public ArrayList<Strada> getStradeVicineLibere() {
		ArrayList<Strada> stradeVicineLibere = new ArrayList<Strada>();
		for (Strada x : stradeVicine)
			if (!x.isOccupata())
				stradeVicineLibere.add(x);
		return stradeVicineLibere;
	}

	/**
	 * Questo metodo serve, data una regione adiacente, a trovare quale regione
	 * si trova dall'altro lato della strada.
	 * 
	 * @param regioneScelta
	 *            la regione assegnata.
	 * @return la regione dall'altro lato della strada, rispetto a quella
	 *         assegnata.
	 */
	public Regione getRegioneOpposta(Regione regioneScelta) {
		if (regioniAdiacenti[0].equals(regioneScelta))
			return regioniAdiacenti[1];
		return regioniAdiacenti[0];
	}

	/**
	 * Metodo per sapere il valore della strada.
	 * @return
	 */
	public int getValore() {
		return valore;
	}

	/**
	 * Metodo che ritorna l'ID della strada.
	 * @return
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Metodo per posizionare un pastore.
	 */
	public void setOccupata() {
		occupata = true;
	}

	/**
	 * Metodo per mettere un recinto.
	 */
	public void setRecinto() {
		recinto = true;
	}

	/**
	 * Metodo per sapere se la strada è recintata.
	 * @return
	 */
	public boolean isRecintata() {
		return recinto;
	}

	/**
	 * Metodo per sapere se la strada è occupata da un pastore.
	 * @return
	 */
	public boolean isOccupata() {
		return occupata;
	}

	/**
	 * Metodo per prendere le regioni adiacenti.
	 * @return
	 */
	public Regione[] getRegioniAdicaenti() {
		return regioniAdiacenti;
	}

	/**
	 * Metodo per prendere le strade vicine.
	 * @return
	 */
	public ArrayList<Strada> getStradeVicine() {
		return stradeVicine;
	}

}
