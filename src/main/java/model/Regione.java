package model;

import java.util.ArrayList;

import online.InterfacciaOnline;
import view.InterfacciaPartitaUtente;

/**
 * Classe che rappresenta la regione. La regione possiede un ID, un tipo di
 * terreno e una lista delle strade sui suoi confini. Inoltre in ogni regione
 * c'è una lista di tutte le pecore presenti e dei contatori per montoni, pecore
 * femmine e agnelli.
 * 
 */
public class Regione {
	private int ID;
	private Terreno tipoTerreno;
	private ArrayList<Strada> stradeConfinanti = new ArrayList<Strada>();

	private ArrayList<Agnello> pecore;
	private int numeroAgelli = 0;
	private int numMontoni = 0;
	private int numPecoreFemmine = 0;

	/**
	 * Costruttore. Crea la regione con determinati ID e tipo di terreno.
	 * Inizializza l'ArrayList di Agnello.
	 * 
	 * @param tipoTerreno
	 * @param ID
	 */
	Regione(Terreno tipoTerreno, int ID) {
		this.tipoTerreno = tipoTerreno;
		this.ID = ID;
		pecore = new ArrayList<Agnello>();
	}

	public Terreno getTerreno() {
		return tipoTerreno;
	}

	public String toString() {
		return ("Regione n° " + ID + " [" + tipoTerreno + "]");
	}

	/**
	 * Metodo che cerca tra le strade sui confini se ce n'� una con un
	 * determinato valore.
	 * 
	 * @param x
	 *            il valore della strada cercata.
	 * @return la strada cercata. null se non � presente.
	 */
	public Strada getStradaConValoreX(int x) {
		for (Strada y : stradeConfinanti)
			if (y.getValore() == x)
				return y;
		return null;
	}

	public void setStradaVicina(Strada stradaDaAggiungere) {
		this.stradeConfinanti.add(stradaDaAggiungere);
	}

	/**
	 * Metodo che determia se le strade sui confini sono tutte recintate.
	 * 
	 * @return true se la regioen � circondata da strade recintate.
	 */
	public boolean isTuttaRecintata() {
		for (Strada x : stradeConfinanti)
			if (!(x.isRecintata()))
				return false;
		return true;
	}

	/**
	 * Metodo che determina se le strade sui confini sono tutte occupate da un
	 * recinto o da un pastore.
	 * 
	 * @return true se le strade confinanti sono tutte occupate.
	 */
	public boolean isTuttaOccupata() {
		for (Strada x : stradeConfinanti)
			if (!(x.isOccupata()))
				return false;
		return true;
	}

	/**
	 * Metodo che verifica la presenza di almeno un montone e una pecora
	 * femmina. Serve a determinare la possibilità di effettuare un
	 * accoppiamento.
	 * 
	 * @return true se � possibile effettuare l'accoppiamento nella regione.
	 */
	public boolean accoppiamentoPossibile() {
		return ((numMontoni > 0) && (numPecoreFemmine > 0));
	}


	/**
	 * Metodo che aggiunge alla lista una pecora di un tipo casuale ( 1/3
	 * Agnello, 1/3 Montone, 1/3 PecoraFemmina). Il metodo viene utilizzato
	 * all'inizio del gioco, per inizializzare le regioni.
	 */
	public void aggiungiPecoraCasualmente() {
		if (((int) (Math.random() * 3)) >= 1) {
			PecoraAdulta nuovaPecoraAdulta = new PecoraAdulta();
			pecore.add(nuovaPecoraAdulta);
			if (nuovaPecoraAdulta.isMaschio())
				numMontoni++;
			else
				numPecoreFemmine++;
		} else {
			pecore.add(new Agnello());
			numeroAgelli++;
		}
	}

	public int getNumeroPecore() {
		return pecore.size();
	}

	/**
	 * Metodo che prende una pecora dalla lista in modo casuale e la toglie da
	 * questa. Viene utilizzato in seguito al movimento del lupo.
	 * 
	 * @return la pecora prelevata.
	 */
	public Agnello mangiaPecora() {
		int indiceCasuale = (int) (Math.random() * pecore.size());
		Agnello pecoraMangiata = pecore.get(indiceCasuale);
		prelevaAnimale(pecoraMangiata.getTipo());
		return pecoraMangiata;
	}

	/**
	 * Metodo che fa crescere tutte le pecore presenti nella regione. Per ogni
	 * agnello che diventa adulto, viene notificata l'interfaccia.
	 * 
	 * @param interfaccia
	 */
	public void crescitaPecorelle(InterfacciaPartitaUtente interfaccia) {
		for (int i = 0; i < pecore.size(); i++) {
			Agnello pecoraConsiderata = pecore.get(i);
			if (pecoraConsiderata != null && pecoraConsiderata.crescita()) {
				pecore.remove(pecoraConsiderata);
				numeroAgelli--;
				pecoraConsiderata = new PecoraAdulta();
				posizionaPecora(pecoraConsiderata);
				interfaccia.crescitaAgnello(this, pecoraConsiderata.getTipo());
			}
		}
	}

	public void crescitaPecorelleNonCasuale(boolean isMaschio) {
		prelevaAgnello();
		pecore.add(new PecoraAdulta(isMaschio));
		if (isMaschio)
			numMontoni++;
		else {
			numPecoreFemmine++;
		}
	}

	public void prelevaAnimale(int tipoPecora) {
		switch (tipoPecora) {
		case 0:
			prelevaAgnello();
			break;

		case 1:
			prelevaPecoraFemmina();
			break;

		case 2:
			prelevaMontone();
			break;

		case 3:
			prelevaPecoraNera();
			break;

		}
	}

	/**
	 * Metodo che cerca la pecora nera nella lista di pecore, la toglie e la
	 * restiuisce.
	 * 
	 * @return la pecora nera, null se non viene trovata.
	 */
	public Agnello prelevaPecoraNera() {
		for (int i = 0; i < pecore.size(); i++) {
			if (pecore.get(i) instanceof PecoraNera) {
				PecoraNera pecoraNera = (PecoraNera) pecore.remove(i);
				return pecoraNera;
			}
		}
		return null;

	}

	/**
	 * Metodo che cerca un montone nella lista di pecore, lo toglie e lo
	 * restiuisce.
	 * 
	 * @return un montone, null se non viene trovato.
	 */
	public Agnello prelevaMontone() {
		for (int i=0; i<pecore.size(); i++)
			if ((pecore.get(i) instanceof PecoraAdulta)
					&& (((PecoraAdulta) pecore.get(i)).isMaschio())) {
				numMontoni--;
				return pecore.remove(i);
			}
		return null;
	}

	/**
	 * Metodo che cerca una pecora femmina nella lista di pecore, la toglie e la
	 * restiuisce.
	 * 
	 * @return una pecora femmina, null se non viene trovata.
	 */
	public Agnello prelevaPecoraFemmina() {
		for (int i=0; i<pecore.size(); i++)
			if ((pecore.get(i) instanceof PecoraAdulta)
					&& !(((PecoraAdulta) pecore.get(i)).isMaschio())) {
				numPecoreFemmine--;
				return pecore.remove(i);
			}
		return null;
	}

	/**
	 * Metodo che cerca un agnello nella lista di pecore, lo toglie e lo
	 * restiuisce.
	 * 
	 * @return un agnello, null se non viene trovato.
	 */
	public Agnello prelevaAgnello() {
		for (int i=0; i<pecore.size(); i++)
			if (!(pecore.get(i) instanceof PecoraAdulta)
					&& !(pecore.get(i) instanceof PecoraNera)) {
				numeroAgelli--;
				return pecore.remove(i);
			}
		return null;
	}

	/**
	 * Metodo per sapere il numero di agnelli nella regione.
	 * @return
	 */
	public int getNumeroAgnelli() {
		return numeroAgelli;
	}

	/**
	 * Metodo per sapere il numero di montoni nella regione.
	 * @return
	 */
	public int getNumeroMontoni() {
		return numMontoni;
	}

	/**
	 * Metodo per sapere il numero di pecore femmine nella regione.
	 * @return
	 */
	public int getNumeroPecoreFemmine() {
		return numPecoreFemmine;
	}

	/**
	 * Metodo che aggiunge una determinata pecora alla lista. In base al tipo di
	 * pecora, viene incrementato il relativo contatore.
	 * 
	 * @param pecora
	 */
	public void posizionaPecora(Agnello pecora) {
		pecore.add(pecora);
		if (pecora instanceof PecoraAdulta) {
			if (((PecoraAdulta) pecora).isMaschio())
				numMontoni++;
			else
				numPecoreFemmine++;
			return;
		} else if (!(pecora instanceof PecoraNera))
			numeroAgelli++;
	}

	/**
	 * Metodo per aggiungere un nuovo agnello alla lista. Il metodo viene usato
	 * in seguito ad un accoppiamento.
	 */
	public void nascitaAgnello() {
		pecore.add(new Agnello());
		numeroAgelli++;
	}

	/**
	 * Metodo per sapere l'ID della regione.
	 * @return
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Metodo per far crescere gli agnelli nella partita online.
	 * @param interfaccia
	 */
	public void crescitaPecorelleOnline(InterfacciaOnline interfaccia) {
		for (int i = 0; i < pecore.size(); i++) {
			Agnello pecoraConsiderata = pecore.get(i);
			if (pecoraConsiderata != null && pecoraConsiderata.crescita()) {
				pecore.remove(pecoraConsiderata);
				numeroAgelli--;
				pecoraConsiderata = new PecoraAdulta();
				posizionaPecora(pecoraConsiderata);
				interfaccia.crescitaAgnello(this, pecoraConsiderata.getTipo());
			}
		}

	}

}
