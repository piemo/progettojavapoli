package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import online.InterfacciaOnline;
import view.InterfacciaPartitaUtente;

/**
 * Classe che rappresenta il tavolo di gioco. Contiene le regioni, le strade, i
 * pastori, la pecora nera e il lupo.
 */
public class GameBoard {
	private static final int NUM_STRADE = 42;
	private static final int NUM_REGIONI = 19;
	private Regione[] regioni;
	private Strada[] strade;
	private ArrayList<Pastore> pastori;
	private PecoraNera pecoraNera;
	private Lupo lupo;

	/**
	 * Costruttore per la GameBoard che inizializza la mappa di gioco e le varie
	 * pedine, in relazione al numero di giocatori.
	 * 
	 * @param numeroGiocatori
	 */
	public GameBoard(int numeroGiocatori) {

		regioni = new Regione[NUM_REGIONI];
		strade = new Strada[NUM_STRADE];

		regioni[0] = new Regione(Terreno.SHEEPSBURG, 0);

		inizializzaRegioniConTerreni();

		setUpStrade();

		setUpRegioni();

		pecoraNera = new PecoraNera(regioni[0]);
		lupo = new Lupo(regioni[0]);

		pastori = new ArrayList<Pastore>();

		if (numeroGiocatori > 2)
			for (int i = 0; i < numeroGiocatori; i++)
				pastori.add(new Pastore(i, false));
		else
			for (int i = 0; i < numeroGiocatori; i++) {
				pastori.add(new Pastore(i, false));
				pastori.add(new Pastore(i, true));
			}

	}

	public Pastore getPastore(int numeroPastore) {
		return pastori.get(numeroPastore);
	}

	/**
	 * Metodo per settare i confini di ciascuna regione. Utilizza un file di
	 * testo ("regioniVicinanze.txt") da cui leggere gli ID delle regioni
	 * confinanti.
	 */
	private void setUpRegioni() {
		try {
			InputStream fin = getClass().getResourceAsStream("/documents/regioniVicinanze.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fin));
			String rigaLetta;
			int contatore = 0;
			while ((rigaLetta = br.readLine()) != null) {
				rigaLetta = rigaLetta.substring(3);
				rigaLetta = rigaLetta.replaceAll("\\s+", "");
				while (rigaLetta.length() > 0) {
					int numStradaDaAggiungere = Integer.parseInt(rigaLetta
							.substring(0, 2));
					regioni[contatore]
							.setStradaVicina(strade[numStradaDaAggiungere]);
					rigaLetta = rigaLetta.substring(2);
				}

				contatore++;
			}
			br.close();
			fin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo per inizializzare le regioni associando a ciascuna il proprio tipo
	 * di terreno. Utilizza un file di testo ("terreni.txt") per acquisire i
	 * dati.
	 */
	private void inizializzaRegioniConTerreni() {
		try {
			InputStream fin = getClass().getResourceAsStream("/documents/terreni.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fin));
			String terrenoLetto = new String();
			for (int i = 1; i < NUM_REGIONI; i++) {
				terrenoLetto = br.readLine().substring(3);
				regioni[i] = new Regione(Terreno.getTerreno(terrenoLetto), i);
			}
			br.close();
			fin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metodo che inizializza tutte le strade, settandone il valore e le
	 * relative vicinanze. Utilizza un file di testo ("strade.txt") per leggere
	 * i dati.
	 */
	private void setUpStrade() {
		for (int i = 0; i < NUM_STRADE; i++) {
			strade[i] = new Strada(i);
		}
		try {
			InputStream fin = getClass().getResourceAsStream("/documents/strade.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fin));
			String rigaLetta;
			int contatore = 0;
			while ((rigaLetta = br.readLine()) != null) {
				rigaLetta = rigaLetta.substring(3);
				rigaLetta = rigaLetta.replaceAll("\\s+", "");
				int valoreStrada = Integer.parseInt(rigaLetta.substring(0, 1));
				Regione regione1 = regioni[Integer.parseInt(rigaLetta
						.substring(1, 3))];
				Regione regione2 = regioni[Integer.parseInt(rigaLetta
						.substring(3, 5))];
				strade[contatore]
						.setAdiacenze(valoreStrada, regione1, regione2);
				ArrayList<Strada> stradeDaAggiungere = new ArrayList<Strada>();
				rigaLetta = rigaLetta.substring(6);
				while (rigaLetta.length() > 0) {
					stradeDaAggiungere.add(strade[Integer.parseInt(rigaLetta
							.substring(0, 2))]);
					rigaLetta = rigaLetta.substring(2);
				}
				strade[contatore].setStradeVicine(stradeDaAggiungere);
				contatore++;
			}
			br.close();
			fin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metodo che chiama su ciascuna regione il relativo metodo per aggiungere
	 * una pecora di un tipo a caso tra: Agnello e PecoraAdulta, che può essere
	 * maschio o femmina. Usato per l'inizializzazione del tavolo di gioco.
	 */
	public void setUpPecore() {
		for (int i = 1; i < NUM_REGIONI; i++)
			regioni[i].aggiungiPecoraCasualmente();
	}

	public Regione[] getRegioni() {
		return regioni;
	}

	/**
	 * Metodo che restituisce la regione che si trova nella posizione passata
	 * come parametro.
	 * 
	 * @param numeroRegione
	 *            la posizione della regione, corrisponde al suo ID.
	 * @return la regione richiesta.
	 */
	public Regione getRegione(int numeroRegione) {
		if (numeroRegione >= 0 && numeroRegione < NUM_REGIONI)
			return regioni[numeroRegione];
		return null;
	}

	/**
	 * Metodo che restituisce la lista delle strade completamente libere.
	 * 
	 * @return le strade libere in un ArrayList.
	 */
	public ArrayList<Strada> getStradeLibere() {
		ArrayList<Strada> stradeLibere = new ArrayList<Strada>();
		for (Strada x : strade)
			if (!x.isOccupata())
				stradeLibere.add(x);
		return stradeLibere;
	}

	/**
	 * Metodo che restituisce la lista delle strade recintate.
	 * 
	 * @return le strade recintate in un ArrayList.
	 */
	public ArrayList<Strada> getStradeRecintate() {
		ArrayList<Strada> stradeRecintate = new ArrayList<Strada>();
		for (Strada x : strade)
			if (x.isRecintata())
				stradeRecintate.add(x);
		return stradeRecintate;
	}

	/**
	 * Metodo che restituisce la strada che nell'array di strada si trova a una
	 * determinata posizione.
	 * 
	 * @param numeroStrada
	 *            la posizione della strada, corrisponde al suo ID.
	 * @return la strada richiesta.
	 */
	public Strada getStrada(int numeroStrada) {
		return strade[numeroStrada];
	}

	/**
	 * Metodo che invoca su ogni regione una funzione per inrementare l'et�
	 * degli agnelli e verificare se sono diventati adulti. Viene invocato dopo
	 * il turno di ogni giocatore.
	 * 
	 * @param interfaccia
	 *            l'interfaccia con l'utente, che servir� per comunicare che
	 *            degli agnelli sono cresciuti.
	 */
	public void crescitaPecorelle(InterfacciaPartitaUtente interfaccia) {
		for (int i = 0; i < NUM_REGIONI; i++) {
			regioni[i].crescitaPecorelle(interfaccia);
		}
	}

	public ArrayList<Pastore> getPastori() {
		return pastori;
	}

	public PecoraNera getPecoraNera() {
		if (pecoraNera == null)
			return null;
		return pecoraNera;
	}

	/**
	 * Metodo per l'eliminzaione della pecora nera a seguito di una mossa di
	 * abbattimento su questa.
	 */
	public void eliminaPecoraNera() {
		Regione regionePecoraNera = pecoraNera.getRegioneAttuale();
		regionePecoraNera.prelevaPecoraNera();
		pecoraNera = null;
	}

	/**
	 * Metodo per prendere il lupo.
	 * @return
	 */
	public Lupo getLupo() {
		return lupo;
	}

	/**
	 * Metodo per far crescere gli agnelli nella partita online.
	 * @param interfaccia
	 */
	public void crescitaPecorelleOnline(InterfacciaOnline interfaccia) {
		for (int i = 0; i < NUM_REGIONI; i++) {
			regioni[i].crescitaPecorelleOnline(interfaccia);
		}
	}
	
	/**
	 * Metodo per prendere tutte le strade.
	 * @return
	 */
	public Strada[] getStrade(){
		return strade;
	}
}
