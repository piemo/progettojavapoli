package online;

import java.util.ArrayList;

import model.GameBoard;
import model.Pastore;
import model.PecoraNera;
import model.Regione;
import model.Strada;
import model.Terreno;
import controller.Giocatore;
import controller.Offerta;

/**
 * Questa classe permette di econdificare una chiamata di un metodo in una
 * stringa. ad ogni stringa viene assegnato un id rappresentate il metodo e
 * vengono concatenati i vari parametri codificati secondo quanto riportato
 * nella documentazione la numerazione dei metodi è uguale sia per l'encoder che
 * per il decoder.
 * 
 */
public class Encoder {

	/**
	 * Metodo che converte un booleano in una stringa che vale "tt" o "ff".
	 * @param bool
	 * @return
	 */
	private String boolToString(boolean bool) {
		if (bool)
			return "tt";
		else {
			return "ff";
		}
	}

	/**
	 * Codifica una offerta secondo questa sequenza id tipo terreno + int id
	 * giocatore + prezzo
	 * 
	 * @param offerta
	 */
	private String offertaToString(Offerta offerta) {
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
	private String listOffertaToString(ArrayList<Offerta> lista) {
		String output = interoToString(lista.size());
		for (Offerta offerta : lista) {
			output += offertaToString(offerta);
		}
		return output;
	}

	/**
	 * crea una stringa di interi a due cifre concatenati. il primo intero
	 * rappresenta quanto è lunga la lista
	 * 
	 * @param strade
	 * @return
	 */
	private String listaStradaToString(ArrayList<Strada> strade) {
		String output = interoToString(strade.size());
		for (Strada stradaAttuale : strade) {
			if (stradaAttuale == null)
				output += interoToString(99);
			else
				output += interoToString(stradaAttuale.getID());
		}
		return output;
	}

	/**
	 * ritorna una stringa che rappresenta se si tratta del primo o del secondo
	 * pastore di un giocatore
	 * 
	 * @param GiocatoreAttuale
	 * @param pastore
	 * @return
	 */
	private String primoOrSecondPastore(Giocatore GiocatoreAttuale,
			Pastore pastore) {
		if (pastore.isSecondo())
			return "02";
		else
			return "01";
	}

	/**
	 * converte un intero in una stringa da 2 caratteri
	 * 
	 * @param numero
	 * @return
	 */
	private String interoToString(int numero) {
		if (numero < 10) {
			if (numero == -1)
				return "-1";
			else
				return "0" + numero;
		} else {
			return "" + numero;
		}
	}

	/**
	 * converte una regione in una stringa da 2 caratteri rappresentanti l'id
	 * numerico della regione
	 * 
	 * @param regione
	 * @return
	 */
	private String regioneToString(Regione regione) {
		if (regione != null)
			return interoToString(regione.getID());
		return "-1";
	}

	/**
	 * converte un array di booleani in una stringa
	 * 
	 * @param array
	 * @return
	 */
	private String arrayBoolToString(boolean[] array) {
		String outputString = "";
		for (boolean b : array) {
			outputString += boolToString(b);
		}
		return outputString;
	}

	/**
	 * converte un array di interi in una stringa
	 * 
	 * @param array
	 * @return
	 */
	private String arrayIntToString(int[] array) {
		String output = "";
		for (int i : array) {
			output += interoToString(i);
		}
		return output;
	}

	/**
	 * converte un array di regioni in un equivalente di stringa
	 * 
	 * @param array
	 * @return
	 */
	private String arrayRegionetoString(Regione[] array) {
		String output = "";
		for (Regione regione : array) {
			output += interoToString(regione.getID());
		}
		return output;
	}

	/**
	 * 00 : id + int primo o secondo pastore + n interi di strade libere
	 * 
	 * @param giocatoreCorrente
	 * @param pastoreDaPosizionare
	 * @param stradeLibere
	 * @return
	 */
	public String posizionamentoInizialePastore(Giocatore giocatoreCorrente,
			Pastore pastoreDaPosizionare, ArrayList<Strada> stradeLibere) {
		String output = "00";
		output += primoOrSecondPastore(giocatoreCorrente, pastoreDaPosizionare);
		output += listaStradaToString(stradeLibere);
		return output;
	}

	/**
	 * 01 : id Annuncia blocco pastore
	 * 
	 * @param pastoreScelto
	 */
	public String pastoreBloccato() {
		return "01";

	}

	/**
	 * 02 : id + 5 bool rimane in attesa di un int
	 * 
	 * @param mosseAbilitate
	 * @param giocatoreCorrente
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public String sceltaMossa(boolean[] mosseAbilitate,
			Giocatore giocatoreCorrente) {
		String outString = "02";
		outString += arrayBoolToString(mosseAbilitate);
		return outString;
	}

	/**
	 * 03 : ID + int id giocatore
	 */
	public String giocatoreSaltaTurno(Giocatore giocatoreCorrente) {
		return "03" + interoToString(giocatoreCorrente.getIndice());

	}

	/**
	 * 04 : id + bool pastore1Abilitato + bool pastore2 abilitato
	 * 
	 * @param giocatoreCorrente
	 * @param pastore1Abilitato
	 * @param pastore2Abilitato
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public String sceltaPastore(Giocatore giocatoreCorrente,
			boolean pastore1Abilitato, boolean pastore2Abilitato) {
		return "04" + boolToString(pastore1Abilitato)
				+ boolToString(pastore2Abilitato);
	}

	/**
	 * 05 : id + int id giocatore
	 * 
	 * @param giocatoreCorrente
	 */
	public String fineTurno(Giocatore giocatoreCorrente) {
		return "05" + interoToString(giocatoreCorrente.getIndice());

	}

	/**
	 * 06 : id + int id regione destinazione
	 * 
	 * @param destinazione
	 */
	public String spostaPecoraNera(Regione destinazione) {
		if (destinazione != null)
			return "06" + interoToString(destinazione.getID());
		else
			return "06" + "-1";
	}

	/**
	 * 07 : id
	 * 
	 * @param giocatoreCorrente
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public String movimentoPecoraNera(Giocatore giocatoreCorrente) {
		return "07";
	}

	/**
	 * 08 : ID + int danari + diversi int rappresentati strade vicine + diversi
	 * int rappresentati strade lontane
	 * 
	 * @param danari
	 * @param stradeLibereLontane
	 * @param stradeLibereVicine
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public String sceltaStradaPastore(int danari,
			ArrayList<Strada> stradeLibereLontane,
			ArrayList<Strada> stradeLibereVicine, Giocatore giocatore) {
		String output = "08";
		output += interoToString(danari);
		output += listaStradaToString(stradeLibereVicine);
		output += listaStradaToString(stradeLibereLontane);
		return output;
	}

	/**
	 * 09 : id + int nuova strada pastore + int indice giocatore + int primo or
	 * secondo pastore + booleanPagamento
	 * 
	 * @param nuovaStradaPastore
	 * @param pastoreMosso
	 */
	public String movimentoPastore(Strada nuovaStradaPastore,
			Pastore pastoreMosso, Giocatore giocatoreCorrente, boolean haPagato) {
		String output = "09";
		output += interoToString(nuovaStradaPastore.getID());
		output += interoToString(giocatoreCorrente.getIndice()); // invia quale
																	// giocatore
																	// così da
																	// risalire
																	// al
																	// pastore
		if (giocatoreCorrente.getPastore().equals(pastoreMosso))
			output += "01";
		else {
			output += "02";
		}
		output += boolToString(haPagato);
		return output;
	}

	/**
	 * 10 id + int danari + 6 int carteDisponibili + int costante numero di
	 * carte
	 * 
	 * @param danari
	 * @param carteDisponibili
	 * @param numCarte
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public String sceltaCarta(int danari, int[] carteDisponibili, int numCarte,
			Giocatore giocatoreCorrente) {
		String output = "10";
		output += interoToString(danari);
		output += arrayIntToString(carteDisponibili);
		output += interoToString(numCarte);
		return output;
	}

	/**
	 * 11: ID + int id tipo carta + int id giocatore corrente + int prezzo
	 * 
	 * @param tipoCarta
	 * @param giocatoreCorrente
	 */
	public String acquistoCarta(Terreno tipoCarta, Giocatore giocatoreCorrente,
			int Prezzo) {
		String outString = "11";
		outString += interoToString(tipoCarta.ordinal());
		outString += interoToString(giocatoreCorrente.getIndice());
		outString += interoToString(Prezzo);
		return outString;
	}

	/**
	 * 12: id+ int id regione pecora nera + 2 * int id regioni vicine
	 * 
	 * @param regioniAdiacenti
	 * @param regionePecoraNera
	 * @param giocatoreCorrente
	 * @return rappresenta l'id della strada in cui si vuole effettuare
	 *         l'abbattimento
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public String sceltaRegioneAbbattimento(Regione[] regioniAdiacenti,
			Regione regionePecoraNera, Giocatore giocatore) {
		String output = "12";
		output += regioneToString(regionePecoraNera); // 1 solo
		output += arrayRegionetoString(regioniAdiacenti); // sono 2
		return output;
	}

	/**
	 * 13 : id + int id regione abbattimento + bool presenza pecora nera
	 * 
	 * @param regioneAbbattimento
	 * @param presenzaPecoraNera
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public String sceltaAnimaleDaAbbattere(Regione regioneAbbattimento,
			boolean presenzaPecoraNera, Giocatore giocatore) {
		String output = "13";
		output += regioneToString(regioneAbbattimento);
		output += boolToString(presenzaPecoraNera);
		return output;
	}

	/**
	 * 14 : id + int tipo di animale abbattuto + int id regione
	 * dell'abbattimento
	 * 
	 * @param animaleAbbattuto
	 * @param regioneAbbattimento
	 */
	public String abbattimento(int animaleAbbattuto, Regione regioneAbbattimento) {
		return "14" + interoToString(animaleAbbattuto)
				+ regioneToString(regioneAbbattimento);

	}

	/**
	 * 15 : id + 2 int id regioni Accoppiamento
	 * 
	 * @param regioniAccoppiamento
	 * @param giocatoreAttuale
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public String sceltaRegioneAccoppiamento(Regione[] regioniAccoppiamento,
			Giocatore giocatore) {
		return "15" + arrayRegionetoString(regioniAccoppiamento);
	}

	/**
	 * 16 : id + int id regione
	 * 
	 * @param regioneAccoppiamento
	 */
	public String accoppiamento(Regione regioneAccoppiamento) {
		return "16" + regioneToString(regioneAccoppiamento);
	}

	/**
	 * 17 id + 2 * int id regioni adiacenti + int id regione pecora nera
	 * 
	 * @param regioniAdiacenti
	 * @param regionePecoraNera
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public String sceltaRegioneSpostamento(Regione[] regioniAdiacenti,
			Regione regionePecoraNera, Giocatore giocatore) {
		String outString = "17";
		outString += arrayRegionetoString(regioniAdiacenti);
		outString += regioneToString(regionePecoraNera);
		return outString;
	}

	/**
	 * 18 : id + int id regione spostamento + bool presenza pecora
	 * 
	 * @param regioneSpostamento
	 * @param presenzaPecoraNera
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public String sceltaPecoraDaSpostare(Regione regioneSpostamento,
			boolean presenzaPecoraNera, Giocatore giocatore) {
		String outString = "18";
		outString += regioneToString(regioneSpostamento);
		outString += boolToString(presenzaPecoraNera);
		return outString;
	}

	/**
	 * 19 : id + int tipo pecora + int id regione di partenza + int id regione
	 * di arrivo
	 * 
	 * @param pecoraScelta
	 * @param regioneSpostamento
	 * @param regioneDiDestinazione
	 */
	public String spostamentoPecora(int pecoraScelta,
			Regione regioneSpostamento, Regione regioneDiDestinazione) {
		String outString = "19";
		outString += interoToString(pecoraScelta);
		outString += regioneToString(regioneSpostamento);
		outString += regioneToString(regioneDiDestinazione);
		return outString;
	}

	/**
	 * 20 : id
	 * 
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public String inizioTurno(Giocatore giocatore) {
		return "20" + interoToString(giocatore.getIndice());
	}

	/**
	 * 21 : id + int id regione arrivo + bool haMangiato + int tipo pecora
	 * 
	 * @param regioneDestinazione
	 * @param pecoraMangiata
	 */
	public String mossoLupo(int regioneDestinazione, boolean haMangiato,
			int pecoraMangiata) {
		String outString = "21";
		outString += interoToString(regioneDestinazione);
		outString += boolToString(haMangiato);
		outString += interoToString(pecoraMangiata);
		return outString;
	}

	/**
	 * 22 : id + int id giocatore che non riceve soldi silenzio
	 * 
	 * @param giocatore
	 */
	public String nonPagaSilenzio(Giocatore giocatore) {
		return "22" + interoToString(giocatore.getIndice());
	}

	/**
	 * 23 : id + int id giocatore che riceve soldi silenzio dal
	 * GiocatoreCorrente
	 * 
	 * @param giocatore
	 */
	public String pagaSilenzio(Giocatore giocatoreCorrente,
			Giocatore giocatoreDaPagare) {
		return "23" + interoToString(giocatoreCorrente.getIndice())
				+ interoToString(giocatoreDaPagare.getIndice());

	}

	/**
	 * 24 : id + int id giocatore
	 * 
	 * 
	 * @param giocatoreAttuale
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public String offriMercato(Giocatore giocatoreAttuale) {
		return "24" + interoToString(giocatoreAttuale.getIndice());

	}

	/**
	 * 25 : id + int id regione + int tipoPecora
	 * 
	 * @param regione
	 * @param pecoraAdulta
	 */
	public String crescitaAgnello(Regione regione, int tipoPecora) {
		return "25" + regioneToString(regione) + interoToString(tipoPecora);

	}

	/**
	 * 26 : id + int id giocatore acquirente + n offerte + int id giocatore
	 * attuale
	 * 
	 * @param acquirente
	 * @param listaOfferte
	 * @return
	 */
	public String accettaOfferta(Giocatore acquirente,
			ArrayList<Offerta> listaOfferte) {
		String output = "26";
		output += interoToString(acquirente.getIndice());
		output += listOffertaToString(listaOfferte);
		return output;
	}

	/**
	 * 27 : id
	 */
	public String inizioMercato() {
		return "27";
	}

	/**
	 * 28 : id
	 */
	public String finePartita() {
		return "28";
	}

	/**
	 * 29 : id + int id giocatore
	 * 
	 * @param vincitore
	 */
	public String annunciaVincitore(Giocatore vincitore, int[] punteggi) {
		String vittoria = "29" + interoToString(vincitore.getIndice());
		for(int i=0; i<punteggi.length; i++)
			vittoria += interoToString(punteggi[i]);
		return vittoria;
	}

	/**
	 * 30 : id
	 */
	public String faseFinale() {
		return "30";

	}

	/**
	 * 31 : id + int id giocatore + int primo o secondo pastore
	 * 
	 * @param pastoreScelto
	 * @param giocatoreCorrente
	 * @return ritorna 1 se è stato effettuato l'abbattimento, 0 altrimenti.
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public String tiroDadoAbbattimento(Pastore pastoreScelto,
			Giocatore giocatoreCorrente) {
		String outString = "31";
		outString += interoToString(giocatoreCorrente.getIndice());
		outString += primoOrSecondPastore(giocatoreCorrente, pastoreScelto);
		return outString;
	}

	/**
	 * 32 : ID
	 * 
	 * @param listaOfferte
	 */
	public String inizioAcquistiMercato(ArrayList<Offerta> listaOfferte) {
		return "32" + listOffertaToString(listaOfferte);

	}

	/**
	 * 33 : id
	 * 
	 * @param GiocatoreCorrente
	 * @return
	 */
	public String tiroDadoGiocatore(Giocatore GiocatoreCorrente) {
		return "33";
	}

	/**
	 * 34 : id
	 * 
	 * @return
	 */
	public String fineMercato() {
		return "34";
	}

	/**
	 * 35 : id + int id giocatore + primoOrSecondoPastore + int id strada
	 * 
	 * @param pastoreDaPosizionare
	 * @param stradaSuCuiPosizionare
	 * @return
	 */
	public String posizionatoPastore(Giocatore giocatoreAttuale,
			Pastore pastoreDaPosizionare, Strada stradaSuCuiPosizionare) {
		String output = "35";
		output += interoToString(giocatoreAttuale.getIndice());
		output += primoOrSecondPastore(giocatoreAttuale, pastoreDaPosizionare);
		output += interoToString(stradaSuCuiPosizionare.getID());
		return output;
	}

	/**
	 * 36 : id + offerta + int id giocatore acquirente
	 * 
	 * @param offertaAccettata
	 * @param acquirente
	 * @return
	 */
	public String offertaAccettata(Offerta offertaAccettata,
			Giocatore acquirente) {
		String outString = "36";
		outString += offertaToString(offertaAccettata);
		outString += interoToString(acquirente.getIndice());
		return outString;
	}

	/**
	 * 97 : id + int id giocatore
	 * 
	 * @param giocatoreDisconnesso
	 * @return
	 */
	public String freeze(Giocatore giocatoreDisconnesso) {
		return ("97" + interoToString(giocatoreDisconnesso.getIndice()));
	}

	/**
	 * 98: id
	 * 
	 * @param giocatore
	 * @return
	 */
	public String disconnesso(Giocatore giocatore) {
		return ("98" + interoToString(giocatore.getIndice()));
	}

	/**
	 * 99: id
	 * 
	 * @param giocatore
	 * @return
	 */

	public String notificaRiconnessione(int indice) {
		return "99" + interoToString(indice);
	}

	/**
	 * Codifica tutte le informazioni necessarie ai client per fare il setup
	 * della loro gameboard.
	 * 
	 * @param indice
	 * @param gameBoard
	 * @param giocatori
	 * @param carteDisponibili
	 * @return
	 */
	public String startUpDataToString(int indice, GameBoard gameBoard,
			Giocatore[] giocatori, int[] carteDisponibili) {
		String output = "";
		output += gameBoardToString(gameBoard);
		output += carteToString(carteDisponibili, giocatori[indice].getCarte());
		output += danariToString(giocatori);
		output += "\n";
		return output;
	}

	/**
	 * Metodo che codifica i danari dei giocatori in una stringa.
	 * 
	 * @param giocatori
	 * @return
	 */
	private String danariToString(Giocatore[] giocatori) {
		String danari = "";
		for (int i = 0; i < giocatori.length; i++)
			danari += interoToString(giocatori[i].getDanari());
		return danari;
	}

	/**
	 * Codifica le carte disponibili della partita e le carte di un particolare
	 * giocatore in una stringa.
	 * 
	 * @param carteDisponibili
	 * @param cartePersonali
	 * @return la stringa codificata.
	 */
	private String carteToString(int[] carteDisponibili, int[] cartePersonali) {
		String carte = "";
		for (int i = 0; i < carteDisponibili.length; i++)
			carte += interoToString(carteDisponibili[i]);
		for (int i = 0; i < carteDisponibili.length; i++)
			carte += interoToString(cartePersonali[i]);
		return carte;
	}

	/**
	 * Restituisce una stringa che rappresenta lo stato della GameBoard. Per
	 * ogni regione, dalla 0 alla 18, viene indicato il numero di agnelli,
	 * montoni e pecore femmine; in seguito vengono indicati gli ID delle
	 * regioni della pecora nera e del lupo; poi la posizione dei pastori,
	 * infine le strade recintate.
	 * 
	 * @param gameBoard
	 * @return una stringa codificata
	 */
	public String gameBoardToString(GameBoard gameBoard) {
		// Animali
		String gameBoardString = "";
		Regione[] regioni = gameBoard.getRegioni();
		for (int i = 0; i < regioni.length; i++) {
			gameBoardString += regioni[i].getNumeroAgnelli();
			gameBoardString += regioni[i].getNumeroMontoni();
			gameBoardString += regioni[i].getNumeroPecoreFemmine();
		}
		PecoraNera pecoraNera = gameBoard.getPecoraNera();
		if (pecoraNera != null)
			gameBoardString += interoToString(pecoraNera.getRegioneAttuale()
					.getID());
		else
			gameBoardString += 99;
		gameBoardString += interoToString(gameBoard.getLupo()
				.getRegioneAttuale().getID());

		// Pastori
		ArrayList<Strada> stradePastori = new ArrayList<Strada>();
		for (Pastore x : gameBoard.getPastori())
			stradePastori.add(x.getStrada());
		gameBoardString += listaStradaToString(stradePastori);

		// Recinti
		gameBoardString += listaStradaToString(gameBoard.getStradeRecintate());

		return gameBoardString;
	}

}
