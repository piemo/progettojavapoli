package online;

import java.util.ArrayList;

import view.InterfacciaPartitaUtente;
import controller.Giocatore;
import controller.Offerta;
import controller.Partita;
import model.Agnello;
import model.GameBoard;
import model.Lupo;
import model.Pastore;
import model.PecoraAdulta;
import model.PecoraNera;
import model.Regione;
import model.Strada;
import model.Terreno;

/**
 * Questa classe permette di decodificare le stringhe composte dal server al
 * fine di eseguire le funzioni dal lato client. Tutti i metodi ricevono in
 * ingresso una stringa e i metodi che restituiscono un intero devono fornire il
 * giocatore corrente. tutte le stringhe non possiedono l'id al loro interno.
 * Infatti questa classe viene chiamata da una classe superiore attraverso il
 * metodo opportuno. la lista di metodi è la stessa lista presente nella classe
 * encoder, fatta eccezione per i metodi di decodifica private.
 * 
 */
public class Decoder {
	private GameBoard gameBoard;
	private InterfacciaPartitaUtente interfaccia;
	private Giocatore[] giocatori;
	private Integer indicePersonale;
	private Partita partita;

	/**
	 * Costruttore della classe.
	 * @param gameBoard viene richiesta la gameboard usata nella interfaccia grafica
	 * @param interfaccia Viene passata l'interfaccia grafica usata nel client.
	 * @param giocatori Lista dei giocatori
	 * @param indicePersonale Indice del giocatore sulla macchina attuale.
	 */
	public Decoder(GameBoard gameBoard, InterfacciaPartitaUtente interfaccia,
			Giocatore[] giocatori, Integer indicePersonale) {
		this.gameBoard = gameBoard;
		this.interfaccia = interfaccia;
		this.giocatori = giocatori;
		this.indicePersonale = indicePersonale;
		partita = new Partita(interfaccia, gameBoard);
	}

	/**
	 * Il metodo restituisce un array di strade di lunghezza non definita a
	 * priori
	 * 
	 * @param input
	 * @return
	 */
	private ArrayList<Strada> stringToListStrade(String input) {
		int lunghezzaLista = stringTo2InteroCifre(input);
		ArrayList<Strada> listaStrade = new ArrayList<Strada>();
		String listaString = input.substring(2);
		for (int i = 0; i < lunghezzaLista; i++) {
			if (stringTo2InteroCifre(listaString) != 99) {
				listaStrade.add(gameBoard
						.getStrada(stringTo2InteroCifre(listaString)));
				listaString = listaString.substring(2);
			} else {
				listaStrade.add(null);
				listaString = listaString.substring(2);
			}
		}
		return listaStrade;
	}

	/**
	 * il metodo restituisce un array di interi di lunghezza definita partendo
	 * da una stringa decodificata
	 * 
	 * @param input
	 * @param lunghezzaArray
	 * @return
	 */
	private int[] stringToArrayInt(String input, int lunghezzaArray) {
		int[] arrayDiRitorno = new int[lunghezzaArray];
		for (int i = 0; i < lunghezzaArray; i++) {
			arrayDiRitorno[i] = stringTo2InteroCifre(input);
			input = input.substring(2);
		}
		return arrayDiRitorno;
	}

	/**
	 * Il metodo permette di consumare un numero specificato di caratteri
	 * rappresentanti interi.
	 * 
	 * @param stringa
	 * @param numeroInteri
	 *            Quanti interi si vogliono cancellare
	 * @return
	 */
	private String consumaNumInteriDa2(String input, int numeroInteri) {
		return input.substring(2 * numeroInteri);
	}

	/**
	 * Il metodo permette di cancellare una stringa di Interi di lunghezza non
	 * conosciuta a priori.
	 * 
	 * @param stringa
	 * @return
	 */
	private String consumaListaInteri(String input) {
		return consumaNumInteriDa2(input, 1 + stringTo2InteroCifre(input));
	}

	/**
	 * Converte una stringa rappresentante intero di lunghezza non nota in un
	 * intero.
	 * 
	 * @param string
	 * @return
	 */
	private int stringToIntero(String string) {
		return Integer.parseInt(string);
	}

	/**
	 * Converte una stringa rappresentate un intero di 2 cifre in un intero
	 * 
	 * @param string
	 * @return
	 */
	private int stringTo2InteroCifre(String input) {
		if (input.substring(0, 2).contains("-1"))
			return -1;
		return Integer.parseInt(input.substring(0, 2));
	}

	/**
	 * converte i primi 2 caratteri di una stringa a booleano
	 * 
	 * @param input
	 * @return
	 */
	private boolean stringToBoolean(String input) {
		if (input.substring(0, 2).contains("t"))
			return true;
		else {
			return false;
		}
	}

	/**
	 * converte una stringa rappresentate un vettore di dimensioni note di
	 * booleani
	 * 
	 * @param input
	 * @param lunghezzaArray
	 * @return
	 */
	private boolean[] stringToArrayBoolean(String input, int lunghezzaArray) {
		boolean[] vettoreDiRitorno = new boolean[lunghezzaArray];
		for (int i = 0; i < lunghezzaArray; i++) {
			vettoreDiRitorno[i] = stringToBoolean(input);
			input = consumaValore(input);
		}
		return vettoreDiRitorno;

	}

	/**
	 * converte una stringa in una rray di regione di lunghezza non definita a
	 * priori
	 * 
	 * @param input
	 * @param lunghezzaArray
	 * @return
	 */

	private Regione[] stringToArrayRegione(String input, int lunghezzaArray) {
		Regione[] outputRegiones = new Regione[lunghezzaArray];
		for (int i = 0; i < lunghezzaArray; i++) {
			outputRegiones[i] = stringToRegione(input);
			input = consumaValore(input);
		}
		return outputRegiones;
	}

	/**
	 * converte una stringa in una regione
	 * 
	 * @param input
	 * @return
	 */
	private Regione stringToRegione(String input) {
		if (input.equals("-1"))
			return null;
		return gameBoard.getRegione(stringTo2InteroCifre(input));
	}

	/**
	 * converte un intero a stringa da 2
	 * 
	 * @param numero
	 * @return
	 */
	private String interoToString(int numero) {
		if (numero < 10)
			return "0" + numero;
		else {
			return "" + numero;
		}
	}

	/**
	 * Codifica una offerta secondo questa sequenza id tipo terreno + int id
	 * giocatore + prezzo
	 * 
	 * @param offerta
	 */
	public String offertaToString(Offerta offerta) {
		if (offerta == null)
			return "-1-1-1";
		String output = interoToString(offerta.getTipoCarta().ordinal());
		output += interoToString(offerta.getVenditore().getIndice());
		output += interoToString(offerta.getPrezzo());
		return output;
	}

	/**
	 * Codifica una lista di offerte di lunghezza non nota a priori in una
	 * stringa secondo la seguente convenzione:ù int rappresentante la lunghezza
	 * della stringa + n offerte
	 * 
	 * @param lista
	 * @return
	 */
	@SuppressWarnings("unused")
	private String listOffertaToString(ArrayList<Offerta> lista) {
		String output = interoToString(lista.size());
		for (Offerta offerta : lista) {
			output = offertaToString(offerta);
		}
		return output;
	}

	/**
	 * decodifica una strina in una offerta
	 * 
	 * @param input
	 * @return
	 */
	public Offerta stringToOfferta(String input) {
		if (input.contains("-1"))
			return null;
		Terreno terrenoCarta = Terreno.getTerreno(stringTo2InteroCifre(input));
		input = input.substring(2);
		Giocatore venditore = giocatori[stringTo2InteroCifre(input)];
		input = input.substring(2);
		int prezzo = stringTo2InteroCifre(input);
		return new Offerta(terrenoCarta, venditore, prezzo);
	}

	/**
	 * decodifica una lista di offerte di lunghezza non definita da una stringa
	 * 
	 * @param input
	 * @return
	 */
	private ArrayList<Offerta> stringtoListOfferta(String input) {
		ArrayList<Offerta> output = new ArrayList<Offerta>();
		int lunghezza = stringTo2InteroCifre(input);
		input = consumaValore(input);
		for (int i = 0; i < lunghezza; i++) {
			Offerta offerta = stringToOfferta(input);
			output.add(offerta);
			input = consumaNumInteriDa2(input, 3);
		}
		return output;
	}

	private String consumaValore(String input) {
		return input.substring(2);
	}

	/**
	 * procedura che decodifica la stringa rappresentante una gameboard ( stato
	 * delle stra,de posizione elementi sulla mappa, carte e soldi dei giocatori
	 * )
	 * 
	 * @param gameBoard
	 * @param giocatori
	 * @param carteDisponibili
	 * @param sincronizzazione
	 */
	public void sincronizza(GameBoard gameBoard, Giocatore[] giocatori,
			Integer[] carteDisponibili, String sincronizzazione,
			int indicePersonale) {
		Regione[] regioni = gameBoard.getRegioni();
		this.gameBoard = gameBoard;
		for (int i = 0; i < regioni.length; i++) {
			// lettura numero pecore in una regione
			int numAgnelli = stringToIntero(sincronizzazione.substring(0, 1));
			int numMontoni = stringToIntero(sincronizzazione.substring(1, 2));
			int numPecoreFemmine = stringToIntero(sincronizzazione.substring(2,
					3));
			// inserimento agnelli in regione
			while (numAgnelli > 0) {
				regioni[i].posizionaPecora(new Agnello());
				numAgnelli--;
			}
			// inserimento montoni in regione
			while (numMontoni > 0) {
				regioni[i].posizionaPecora(new PecoraAdulta(true));
				numMontoni--;
			}
			// inserimento pecore in regione
			while (numPecoreFemmine > 0) {
				regioni[i].posizionaPecora(new PecoraAdulta(false));
				numPecoreFemmine--;
			}
			sincronizzazione = sincronizzazione.substring(3);
		}
		// piazzamento Pecora Nera
		int regionePecoranera = stringTo2InteroCifre(sincronizzazione);
		if (regionePecoranera == 99)
			gameBoard.eliminaPecoraNera();
		else {
			gameBoard.getPecoraNera().muoviti(regioni[regionePecoranera]);
		}
		sincronizzazione = sincronizzazione.substring(2);

		// Piazzamento Lupo
		int regioneLupo = stringTo2InteroCifre(sincronizzazione);
		@SuppressWarnings("unused")
		Lupo lupoDaPiazzareLupo = gameBoard.getLupo();
		lupoDaPiazzareLupo = new Lupo(regioni[regioneLupo]);
		sincronizzazione = sincronizzazione.substring(2);

		// Piazzamento Pastori
		int NumPastori = stringTo2InteroCifre(sincronizzazione);
		ArrayList<Strada> stradePastori = stringToListStrade(sincronizzazione);
		for (int i = 0; i < NumPastori; i++) {
			if (stradePastori.get(i) == null)
				;
			else
				gameBoard.getPastori().get(i)
						.posizionaPastore(stradePastori.get(i));
		}

		sincronizzazione = consumaListaInteri(sincronizzazione);

		// Piazzamento Cancelli

		int NumCancelli = stringTo2InteroCifre(sincronizzazione);
		ArrayList<Strada> stradeOccupate = stringToListStrade(sincronizzazione);
		for (int i = 0; i < NumCancelli; i++) {
			gameBoard.getStrada(stradeOccupate.get(i).getID()).setRecinto();
			gameBoard.getStrada(stradeOccupate.get(i).getID()).setOccupata();
		}
		sincronizzazione = consumaListaInteri(sincronizzazione);

		// ottengo carte disponibili
		int[] arraytemp = new int[6];
		arraytemp = stringToArrayInt(sincronizzazione, 6);
		for (int i = 0; i < 6; i++)
			carteDisponibili[i] = arraytemp[i];
		sincronizzazione = consumaNumInteriDa2(sincronizzazione, 6);

		// assegno carte al giocatore
		giocatori[indicePersonale].resetCarte();
		for (int i = 0; i < 6; i++) {
			int quantitàCarte = stringTo2InteroCifre(sincronizzazione);
			sincronizzazione = sincronizzazione.substring(2);
			for (int j = quantitàCarte; j > 0; j--)
				giocatori[indicePersonale].assegnaCarta(Terreno.getTerreno(i));

		}

		// assegno soldi
		for (int i = 0; i < giocatori.length; i++) {
			int danari = stringTo2InteroCifre(sincronizzazione);
			sincronizzazione = sincronizzazione.substring(2);
			giocatori[i].aumentaDanari(danari);
		}
	}

	/**
	 * 00 : decofidica stringa posizionamentoInizialePastore
	 * 
	 * @param input
	 * @param giocatoreCorrente
	 * @return
	 */
	public int posizionamentoInizialePastore(String input) {
		int primoOrSecondPastore = stringTo2InteroCifre(input);
		Pastore pastoreDaPosizionare;
		// trovo che pastore voglio muovere
		if (primoOrSecondPastore == 1)
			pastoreDaPosizionare = giocatori[indicePersonale].getPastore();
		else {
			pastoreDaPosizionare = giocatori[indicePersonale]
					.getSecondoPastore();
		}
		input = consumaValore(input);
		// decodifica strade libere
		ArrayList<Strada> stradeLibere = stringToListStrade(input);
		return interfaccia.posizionamentoInizialePastore(
				giocatori[indicePersonale], pastoreDaPosizionare, stradeLibere);
	}

	/**
	 * 01 Annuncia blocco pastore
	 * 
	 * @param pastoreScelto
	 */
	public void pastoreBloccato(String string) {
		interfaccia.pastoreBloccato();
	}

	/**
	 * 02 : 5 bool rimane in attesa di un int
	 * 
	 * @param mosseAbilitate
	 * @param giocatoreCorrente
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public int sceltaMossa(String input) {
		// decodifico il vettore booleano delle mosse abilitate
		boolean[] mosseAbilitate = stringToArrayBoolean(input, 5);
		return interfaccia.sceltaMossa(mosseAbilitate,
				giocatori[indicePersonale]);
	}

	/**
	 * 03 : int id giocatore
	 */
	public void giocatoreSaltaTurno(String input) {
		interfaccia.giocatoreSaltaTurno(giocatori[stringTo2InteroCifre(input)]);
	}

	/**
	 * 04 : bool pastore1Abilitato + bool pastore2 abilitato
	 * 
	 * @param giocatoreCorrente
	 * @param pastore1Abilitato
	 * @param pastore2Abilitato
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public int sceltaPastore(String input) {
		boolean pastore1Abilitato = stringToBoolean(input);
		input = consumaValore(input);
		boolean pastore2Abilitato = stringToBoolean(input);
		return interfaccia.sceltaPastore(giocatori[indicePersonale],
				pastore1Abilitato, pastore2Abilitato);
	}

	/**
	 * 05 : int id giocatore
	 * 
	 * @param giocatoreCorrente
	 */
	public void fineTurno(String input) {
		interfaccia.fineTurno(giocatori[stringTo2InteroCifre(input)]);

	}

	/**
	 * 06 : int id regione destinazione
	 * 
	 * @param destinazione
	 */
	public void spostaPecoraNera(String input) {
		PecoraNera pecoraNera = gameBoard.getPecoraNera();
		Regione nuovaRegione = gameBoard
				.getRegione(stringTo2InteroCifre(input));
		if (nuovaRegione != null) {
			pecoraNera.muoviti(nuovaRegione);
			partita.spostaPecoraNera(nuovaRegione.getID());
			return;
		}
		partita.spostaPecoraNera(-1);

	}

	/**
	 * 07
	 * 
	 * @param giocatoreCorrente
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public int movimentoPecoraNera(String input) {
		return interfaccia.movimentoPecoraNera(giocatori[indicePersonale]);
	}

	/**
	 * 08 : int danari + diversi int rappresentati strade vicine + diversi int
	 * rappresentati strade lontane
	 * 
	 * @param danari
	 * @param stradeLibereLontane
	 * @param stradeLibereVicine
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public int sceltaStradaPastore(String input) {
		int danari = stringTo2InteroCifre(input);
		input = consumaValore(input);
		ArrayList<Strada> stradeLibereVicine = stringToListStrade(input);
		input = consumaListaInteri(input);
		ArrayList<Strada> stradeLibereLontane = stringToListStrade(input);
		input = consumaListaInteri(input);
		return interfaccia.sceltaStradaPastore(danari, stradeLibereLontane,
				stradeLibereVicine, giocatori[indicePersonale]);
	}

	/**
	 * 09 : int nuova strada pastore + int indice giocatore + int primo or
	 * secondo pastore
	 * 
	 * @param nuovaStradaPastore
	 * @param pastoreMosso
	 */
	public void movimentoPastore(String input) {
		// ottengo la nuova strada
		Strada nuovaStradaPastore = gameBoard
				.getStrada(stringTo2InteroCifre(input));
		input = consumaValore(input);
		// ottengo l'id del giocatore che ha spostato il pastore
		int indiceGiocatore = stringTo2InteroCifre(input);
		input = consumaValore(input);
		// prendo il pastore che è stato spostato, il primo o il secondo
		Pastore pastoreMosso = null;
		if (stringTo2InteroCifre(input) == 1)
			pastoreMosso = giocatori[indiceGiocatore].getPastore();
		else {
			pastoreMosso = giocatori[indiceGiocatore].getSecondoPastore();
		}
		input = consumaValore(input);
		boolean haPagato = stringToBoolean(input);
		if (haPagato)
			giocatori[indiceGiocatore].pagamento(1);
		partita.movimentoPastore(pastoreMosso, nuovaStradaPastore, haPagato);

	}

	/**
	 * 10 : int danari + 6 int carteDisponibili
	 * 
	 * @param danari
	 * @param carteDisponibili
	 * @param numCarte
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public int sceltaCarta(String input) {
		int danari = stringTo2InteroCifre(input);
		input = consumaValore(input);
		int[] carteDisponibili = stringToArrayInt(input, 6);
		return interfaccia.sceltaCarta(danari, carteDisponibili, 5,
				giocatori[indicePersonale]);
	}

	/**
	 * 11: int id tipo carta + int id giocatore corrente + int prezzo
	 * 
	 * @param tipoCarta
	 * @param giocatoreCorrente
	 */
	public void acquistoCarta(String input) {
		Terreno tipoCarta = Terreno.getTerreno(stringTo2InteroCifre(input));
		input = consumaValore(input);
		int indiceGiocatore = stringTo2InteroCifre(input);
		input = consumaValore(input);
		int prezzo = stringTo2InteroCifre(input);
		giocatori[indiceGiocatore].assegnaCarta(tipoCarta);
		interfaccia.acquistoCarta(tipoCarta, giocatori[indiceGiocatore], prezzo);
		if(indiceGiocatore==(int)indicePersonale)
			interfaccia.mostraCarte(giocatori[indicePersonale]);
	}

	/**
	 * 12: int id regione pecora nera + 2 * int id regioni vicine
	 * 
	 * @param regioniAdiacenti
	 * @param regionePecoraNera
	 * @param giocatoreCorrente
	 * @return rappresenta l'id della strada in cui si vuole effettuare
	 *         l'abbattimento
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public int sceltaRegioneAbbattimento(String input) {
		Regione regionePecoraNera = stringToRegione(input);
		input = consumaValore(input);
		Regione[] regioniAdiacenti = stringToArrayRegione(input, 2);
		return interfaccia.sceltaRegioneAbbattimento(regioniAdiacenti,
				regionePecoraNera, giocatori[indicePersonale]);
	}

	/**
	 * 13 : int id regione abbattimento + bool presenza pecora nera
	 * 
	 * @param regioneAbbattimento
	 * @param presenzaPecoraNera
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public int sceltaAnimaleDaAbbattere(String input) {
		Regione regioneAbbattimento = stringToRegione(input);
		input = consumaValore(input);
		boolean presenzaPecoraNera = stringToBoolean(input);
		return interfaccia.sceltaAnimaleDaAbbattere(regioneAbbattimento,
				presenzaPecoraNera, giocatori[indicePersonale]);
	}

	/**
	 * 14 : int tipo di animale abbattuto + int id regione dell'abbattimento
	 * 
	 * @param animaleAbbattuto
	 * @param regioneAbbattimento
	 */
	public void abbattimento(String input) {
		int animaleAbbattuto = stringTo2InteroCifre(input);
		input = consumaValore(input);
		Regione regioneAbbattimento = stringToRegione(input);
		partita.abbattimento(animaleAbbattuto, regioneAbbattimento);

	}

	/**
	 * 15 : 2 int id regioni Accoppiamento
	 * 
	 * @param regioniAccoppiamento
	 * @param giocatoreAttuale
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public int sceltaRegioneAccoppiamento(String input) {
		Regione[] regioniAccoppiamento = stringToArrayRegione(input, 2);

		return interfaccia.sceltaRegioneAccoppiamento(regioniAccoppiamento,
				giocatori[indicePersonale]);
	}

	/**
	 * 16 : int id regione
	 * 
	 * @param regioneAccoppiamento
	 */
	public void accoppiamento(String input) {
		partita.accoppiamento(stringToRegione(input));
	}

	/**
	 * 17 : 2 * int id regioni adiacenti + int id regione pecora nera
	 * 
	 * @param regioniAdiacenti
	 * @param regionePecoraNera
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public int sceltaRegioneSpostamento(String input) {
		Regione[] regioniAdiacenti = stringToArrayRegione(input, 2);
		input = consumaNumInteriDa2(input, 2);
		Regione regionePecoraNera = stringToRegione(input);
		return interfaccia.sceltaRegioneSpostamento(regioniAdiacenti,
				regionePecoraNera, giocatori[indicePersonale]);
	}

	/**
	 * 18 : int id regione spostamento + bool presenza pecora
	 * 
	 * @param regioneSpostamento
	 * @param presenzaPecoraNera
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public int sceltaPecoraDaSpostare(String input) {
		Regione regioneSpostamento = stringToRegione(input);
		input = consumaValore(input);
		boolean presenzaPecoraNera = stringToBoolean(input);
		return interfaccia.sceltaPecoraDaSpostare(regioneSpostamento,
				presenzaPecoraNera, giocatori[indicePersonale]);
	}

	/**
	 * 19 : int tipo pecora + int id regione di partenza + int id regione di
	 * arrivo
	 * 
	 * @param pecoraScelta
	 * @param regioneSpostamento
	 * @param regioneDiDestinazione
	 */
	public void spostamentoPecora(String input) {
		int pecoraScelta = stringTo2InteroCifre(input);
		input = consumaValore(input);
		Regione regioneSpostamento = stringToRegione(input);
		input = consumaValore(input);
		Regione regioneDiDestinazione = stringToRegione(input);
		partita.spostaPecora(pecoraScelta, regioneSpostamento,
				regioneDiDestinazione);

	}

	/**
	 * 20 : id
	 * 
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public void inizioTurno(String input) {
		int indiceGiocatore = Integer.parseInt(input);
		interfaccia.inizioTurno(giocatori[indiceGiocatore]);
	}

	/**
	 * 21 : int id regione arrivo + bool haMangiato + int tipo pecora
	 * 
	 * @param regioneDestinazione
	 * @param pecoraMangiata
	 */
	public void mossoLupo(String input) {
		int regioneDestinazione = stringTo2InteroCifre(input);
		input = consumaValore(input);
		boolean haMangiato = stringToBoolean(input);
		input = consumaValore(input);
		int pecoraMangiata = stringTo2InteroCifre(input);
		if (haMangiato)
			gameBoard.getRegioni()[regioneDestinazione]
					.prelevaAnimale(pecoraMangiata);
		if (regioneDestinazione >= 0)
			gameBoard.getLupo().spostaLupo(
					gameBoard.getRegione(regioneDestinazione));
		interfaccia.mossoLupo(regioneDestinazione, haMangiato, pecoraMangiata);

	}

	/**
	 * 22 : int id giocatore che non riceve soldi silenzio
	 * 
	 * @param giocatore
	 */
	public void nonPagaSilenzio(String input) {
		interfaccia.nonPagaSilenzio(giocatori[stringTo2InteroCifre(input)]);
	}

	/**
	 * 23 : int id giocatore che riceve soldi silenzio dal GiocatoreCorrente
	 * 
	 * @param giocatore
	 */
	public void pagaSilenzio(String input) {
		Giocatore giocatoreCorrente = giocatori[stringTo2InteroCifre(input)];
		input = consumaValore(input);
		Giocatore giocatoreDaPagare = giocatori[stringTo2InteroCifre(input)];
		partita.pagaSilenzio(giocatoreCorrente, giocatoreDaPagare);

	}

	/**
	 * 24 : int id giocatore
	 * 
	 * 
	 * @param giocatoreAttuale
	 * @return viene ricevuta una stringa del tipo (int tipo terreno + int
	 *         prezzo );
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public Offerta offriMercato(String input) {
		return interfaccia.offriMercato(giocatori[stringTo2InteroCifre(input)]);
	}

	/**
	 * 25 : int id regione + int tipoPecora
	 * 
	 * @param regione
	 * @param pecoraAdulta
	 */
	public void crescitaAgnello(String input) {
		boolean isMaschio;
		Regione regione = stringToRegione(input);
		input = consumaValore(input);
		int tipoPecora = stringTo2InteroCifre(input);
		if (tipoPecora == 1)
			isMaschio = false;
		else {
			isMaschio = true;
		}
		regione.crescitaPecorelleNonCasuale(isMaschio);
		interfaccia.crescitaAgnello(regione, tipoPecora);
	}

	/**
	 * 26 : int id giocatore acquirente + n offerte + int id giocatore attuale
	 * 
	 * @param acquirente
	 * @param listaOfferte
	 * @param giocatore
	 * @return viene restituito un valore di stringa che codifica un'offerta
	 */
	public Offerta accettaOfferta(String input) {
		Giocatore acquirente = giocatori[stringTo2InteroCifre(input)];
		input = consumaValore(input);
		ArrayList<Offerta> listaOfferte = stringtoListOfferta(input);
		return interfaccia.accettaOfferta(acquirente, listaOfferte);
	}

	/**
	 * 27 :
	 */
	public void inizioMercato(String string) {
		interfaccia.inizioMercato();
	}

	/**
	 * 28 :
	 */
	public void finePartita(String string) {
		interfaccia.finePartita();
	}

	/**
	 * 29 : int id giocatore
	 * 
	 * @param vincitore
	 */
	public void annunciaVincitore(String input) {
		Giocatore vincitore = giocatori[stringTo2InteroCifre(input)];
		input = consumaValore(input);
		for(int i =0; i<giocatori.length; i++){
			int punteggio = stringTo2InteroCifre(input);
			input = consumaValore(input);
			giocatori[i].setDanari(punteggio);
		}
		interfaccia.annunciaVincitore(vincitore, giocatori);
	}

	/**
	 * 30 :
	 */
	public void faseFinale(String input) {
		interfaccia.faseFinale();

	}

	/**
	 * 31 : int id giocatore + int primo o secondo pastore
	 * 
	 * @param pastoreScelto
	 * @param giocatoreCorrente
	 * @return ritorna 1 se è stato effettuato l'abbattimento, 0 altrimenti.
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public int tiroDadoAbbattimento(String input) {
		int idGiocatore = stringTo2InteroCifre(input);
		input = consumaValore(input);
		int numPastore = stringTo2InteroCifre(input);
		Pastore pastoreScelto;
		if (numPastore == 1)
			pastoreScelto = giocatori[idGiocatore].getPastore();
		else {
			pastoreScelto = giocatori[idGiocatore].getSecondoPastore();
		}
		return interfaccia.tiroDadoAbbattimento(pastoreScelto,
				giocatori[indicePersonale]);
	}

	/**
	 * 32: id
	 */
	public void inizioAcquistiMercato(String input) {
		ArrayList<Offerta> listaOfferte = stringtoListOfferta(input);
		interfaccia.inizioAcquistiMercato(listaOfferte);

	}

	/**
	 * 33 : id
	 * 
	 * @param GiocatoreCorrente
	 * @return
	 */
	public int tiroDadoGiocatore(String input) {
		return interfaccia.tiroDadoGiocatore(giocatori[indicePersonale]);
	}

	/**
	 * 34: id.
	 */
	public void fineMercato() {
		interfaccia.fineMercato();
	}

	/**
	 * 35 : int id giocatore + primoOrSecondoPastore + int id strada
	 * 
	 * @param pastoreDaPosizionare
	 * @param stradaSuCuiPosizionare
	 * @return
	 */
	public void posizionatoPastore(String input) {
		Giocatore giocatoreAttuale = giocatori[stringTo2InteroCifre(input)];
		input = consumaValore(input);
		Pastore pastoreDaPosizionare;
		if (stringTo2InteroCifre(input) == 1)
			pastoreDaPosizionare = giocatoreAttuale.getPastore();
		else
			pastoreDaPosizionare = giocatoreAttuale.getSecondoPastore();
		input = consumaValore(input);
		Strada stradaSuCuiPosizionare = gameBoard
				.getStrada(stringTo2InteroCifre(input));
		pastoreDaPosizionare.posizionaPastore(stradaSuCuiPosizionare);
		interfaccia.posizionatoPastore(giocatoreAttuale, pastoreDaPosizionare,
				stradaSuCuiPosizionare);
	}

	/**
	 * 36: id.
	 * 
	 * @param istruzione
	 */
	public void offertaAccettata(String istruzione) {
		Offerta offerta = stringToOfferta(istruzione.substring(0, 6));
		Giocatore acquirente = giocatori[stringTo2InteroCifre(istruzione
				.substring(6, 8))];
		Giocatore venditore = null;
		if (offerta != null) {
			venditore = offerta.getVenditore();
			acquirente.acquistoCarta(offerta.getTipoCarta(),
					offerta.getPrezzo());
			venditore.venditaCarta(offerta.getTipoCarta(),
					offerta.getPrezzo());
		}
		interfaccia.offertaAccettata(offerta, acquirente);
		if(offerta!=null)
		 if(acquirente.getIndice()==(int)indicePersonale || venditore.getIndice() ==(int)indicePersonale)
			interfaccia.mostraCarte(giocatori[indicePersonale]);
	}

	/**
	 * 97:id + indice giocatore da aspettare.
	 * 
	 * @param istruzione
	 */
	public void freeze(String istruzione) {
		int indiceGiocatore = Integer.parseInt(istruzione);
		interfaccia.freeze(giocatori[indiceGiocatore]);
	}

	/**
	 * 98:id + indice giocatore disconnesso.
	 * 
	 * @param istruzione
	 */
	public void disconnesso(String istruzione) {
		int indiceGiocatore = Integer.parseInt(istruzione);
		interfaccia.disconnesso(giocatori[indiceGiocatore]);
	}

	/**
	 * 99:id + indice giocatore riconnesso.
	 * 
	 * @param istruzione
	 */
	public void riconnesso(String istruzione) {
		int indiceGiocatore = Integer.parseInt(istruzione);
		interfaccia.riconnesso(giocatori[indiceGiocatore]);
	}

}
