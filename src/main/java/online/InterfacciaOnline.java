package online;

import java.io.IOException;
import java.util.ArrayList;

import model.GameBoard;
import model.Pastore;
import model.Regione;
import model.Strada;
import model.Terreno;
import controller.Giocatore;
import controller.Offerta;

/**
 * Classe che si occupa della comunicazione tra server e client. La classe si
 * avvale di un encoder per crare dele stringhe codificate che rappresentano le
 * istruzioni atomiche.
 * 
 */
public abstract class InterfacciaOnline {
	private Encoder encoder = new Encoder();

	/**
	 * Metodo che viene utilizzato per leggere le mosse del giocatore corrente.
	 * Se il giocatore risulta disconnesso, si attende un timeout e si avvisano
	 * gli altri giocatori. Se dopo il timeout risulta ancora disonnesso, avvisa
	 * tutti i giocatori e lancia un'eccezione.
	 * 
	 * @param giocatoreCorrente
	 * @return l'intero inviato dal giocatore.
	 * @throws DisconnessoGiocatoreCorrenteException
	 *             se, anche dopo un timeout, il giocatore risulta disconnesso
	 */
	public abstract int leggiProssimoIntero(Giocatore giocatoreCorrente)
			throws DisconnessoGiocatoreCorrenteException;

	/**
	 * Metodo che riceve una risposta dal giocatore corrente sotto forma di
	 * stringa. Se il giocatore risulta disconnesso, si attende un timeout e si
	 * avvisano gli altri giocatori. Se dopo il timeout risulta ancora
	 * disonnesso, avvisa tutti i giocatori e lancia un'eccezione.
	 * 
	 * @param giocatoreCorrente
	 * @return la strigna inviata dal client
	 * @throws DisconnessoGiocatoreCorrenteException
	 *             se, anche dopo un timeout, il giocatore risulta disconnesso
	 */
	public abstract String leggiProssimaStringa(Giocatore giocatoreCorrente)
			throws DisconnessoGiocatoreCorrenteException;

	/**
	 * Metodo che invia un'istruzione ad un singolo giocatore. Viene usato
	 * durante il turno dello stesso.
	 * 
	 * @param giocatore
	 * @param outputString
	 * @throws DisconnessoGiocatoreCorrenteException
	 *             se il giocatore si disconnette.
	 */
	public abstract void inviaSingoloGiocatore(Giocatore giocatore,
			String outputString) throws DisconnessoGiocatoreCorrenteException;

	/**
	 * Metodo che invia a tutti i client un'istruzione sotto forma di stringa.
	 * 
	 * @param outputString
	 */
	public abstract void inviaTuttiGiocatori(String outputString);

	/**
	 * Metodo per sincronizzare il client che si connette al server. Il client
	 * riceve lo stato del gioco codificato in stringa.
	 * 
	 * @param indice
	 *            del giocatore da sincronizzare
	 * @param gameBoard
	 *            la gameboard attuale
	 * @param giocatori
	 * @param carteDisponibili
	 * @throws IOException
	 */
	public abstract void sincronizzaClient(int indice, GameBoard gameBoard,
			Giocatore[] giocatori, int[] carteDisponibili) throws IOException;

	/**
	 * Metodo che invia ad un giocatore l'istruzione per posizionare un pastore
	 * e attende la risposta.
	 * 
	 * @param giocatoreCorrente
	 * @param pastoreDaPosizionare
	 * @param stradeLibere
	 * @return l'id della strada scelta.
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public int posizionamentoInizialePastore(Giocatore giocatoreCorrente,
			Pastore pastoreDaPosizionare, ArrayList<Strada> stradeLibere)
			throws DisconnessoGiocatoreCorrenteException {
		inviaSingoloGiocatore(giocatoreCorrente,
				encoder.posizionamentoInizialePastore(giocatoreCorrente,
						pastoreDaPosizionare, stradeLibere));
		return leggiProssimoIntero(giocatoreCorrente);
	}

	/**
	 * Metodo che avvisa che un pastore è bloccato. Potrebbe capitare se il
	 * giocatore non ha danari e il pastore è circondato da strade occupate.
	 */
	public void pastoreBloccato() {
		inviaTuttiGiocatori(encoder.pastoreBloccato());
	}

	/**
	 * Metodo che chiede al giocatore corrente quale mossa vuole effettuare.
	 * 
	 * @param mosseAbilitate
	 * @param giocatoreCorrente
	 * @return l'id della mossa scelta.
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public int sceltaMossa(boolean[] mosseAbilitate, Giocatore giocatoreCorrente)
			throws DisconnessoGiocatoreCorrenteException {
		inviaSingoloGiocatore(giocatoreCorrente,
				encoder.sceltaMossa(mosseAbilitate, giocatoreCorrente));
		return leggiProssimoIntero(giocatoreCorrente);
	}

	/**
	 * Metodo che avvisa che il giocatore corrente salta il turno per
	 * impossibilità di muovere il pastore.
	 * 
	 * @param giocatoreCorrente
	 */
	public void giocatoreSaltaTurno(Giocatore giocatoreCorrente) {
		inviaTuttiGiocatori(encoder.giocatoreSaltaTurno(giocatoreCorrente));
	}

	/**
	 * Metodo che chiede al giocatore corrente quale pastore vuole utilizzare
	 * per il suo turno. Utilizzato per partita a 2 giocatori.
	 * 
	 * @param giocatoreCorrente
	 * @param pastore1Abilitato
	 * @param pastore2Abilitato
	 * @return il pastore scelto.
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public int sceltaPastore(Giocatore giocatoreCorrente,
			boolean pastore1Abilitato, boolean pastore2Abilitato)
			throws DisconnessoGiocatoreCorrenteException {
		inviaSingoloGiocatore(giocatoreCorrente, encoder.sceltaPastore(
				giocatoreCorrente, pastore1Abilitato, pastore2Abilitato));
		return leggiProssimoIntero(giocatoreCorrente);
	}

	/**
	 * Metodo che avvisa che è finito un turno.
	 * 
	 * @param giocatoreCorrente
	 */
	public void fineTurno(Giocatore giocatoreCorrente) {
		inviaTuttiGiocatori(encoder.fineTurno(giocatoreCorrente));
	}

	/**
	 * Metodo che comunica lo spostamento della pecora nera.
	 * 
	 * @param destinazione
	 */
	public void spostaPecoraNera(Regione destinazione) {
		inviaTuttiGiocatori(encoder.spostaPecoraNera(destinazione));
	}

	/**
	 * Metodo che chiede al giocatore di tirare il dado per determinare dove
	 * deve muoversi la pecora nera.
	 * 
	 * @param giocatoreCorrente
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	@Deprecated
	public int movimentoPecoraNera(Giocatore giocatoreCorrente)
			throws DisconnessoGiocatoreCorrenteException {
		inviaSingoloGiocatore(giocatoreCorrente,
				encoder.movimentoPecoraNera(giocatoreCorrente));
		return leggiProssimoIntero(giocatoreCorrente);
	}

	/**
	 * Metodo che chiede al giocatore, che vuole spostare il pastore, su quale
	 * srada vuole spostarlo.
	 * 
	 * @param danari
	 * @param stradeLibereLontane
	 * @param stradeLibereVicine
	 * @param giocatoreCorrente
	 * @return l'id della strdaa scelta.
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public int sceltaStradaPastore(int danari,
			ArrayList<Strada> stradeLibereLontane,
			ArrayList<Strada> stradeLibereVicine, Giocatore giocatoreCorrente)
			throws DisconnessoGiocatoreCorrenteException {
		inviaSingoloGiocatore(giocatoreCorrente, encoder.sceltaStradaPastore(
				danari, stradeLibereLontane, stradeLibereVicine,
				giocatoreCorrente));
		return leggiProssimoIntero(giocatoreCorrente);
	}

	/**
	 * Metodo che notifica i giocatori del fatto che un pastore è stato
	 * spostato.
	 * 
	 * @param nuovaStradaPastore
	 * @param pastoreMosso
	 * @param giocatoreCorrente
	 * @param haPagato
	 */
	public void movimentoPastore(Strada nuovaStradaPastore,
			Pastore pastoreMosso, Giocatore giocatoreCorrente, boolean haPagato) {
		inviaTuttiGiocatori(encoder.movimentoPastore(nuovaStradaPastore,
				pastoreMosso, giocatoreCorrente, haPagato));

	}

	/**
	 * Metodo che chiede ad un giocatore, il quale ha scelto di acquisare una
	 * carta, quale vuole acquistare.
	 * 
	 * @param danari
	 * @param carteDisponibili
	 * @param numCarte
	 * @param giocatoreCorrente
	 * @return l'id del tipo di terreno della carta scelta.
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public int sceltaCarta(int danari, int[] carteDisponibili, int numCarte,
			Giocatore giocatoreCorrente)
			throws DisconnessoGiocatoreCorrenteException {
		inviaSingoloGiocatore(giocatoreCorrente, encoder.sceltaCarta(danari,
				carteDisponibili, numCarte, giocatoreCorrente));
		return leggiProssimoIntero(giocatoreCorrente);
	}

	/**
	 * Metodo che avvisa tutti del fatto che un giocatore ha acquistato una
	 * carta.
	 * 
	 * @param tipoCarta
	 * @param giocatoreCorrente
	 * @param prezzo
	 */
	public void acquistoCarta(Terreno tipoCarta, Giocatore giocatoreCorrente,
			int prezzo) {
		inviaTuttiGiocatori(encoder.acquistoCarta(tipoCarta, giocatoreCorrente,
				prezzo));
	}

	/**
	 * Metodo che chiede a un giocatore, il quale ha scelto di abbatere una
	 * pecora, in quale regione vuole abbatterla.
	 * 
	 * @param regioniAdiacenti
	 * @param regionePecoraNera
	 * @param giocatoreCorrente
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public int sceltaRegioneAbbattimento(Regione[] regioniAdiacenti,
			Regione regionePecoraNera, Giocatore giocatoreCorrente)
			throws DisconnessoGiocatoreCorrenteException {
		inviaSingoloGiocatore(giocatoreCorrente,
				encoder.sceltaRegioneAbbattimento(regioniAdiacenti,
						regionePecoraNera, giocatoreCorrente));
		return leggiProssimoIntero(giocatoreCorrente);
	}

	/**
	 * Metodo che chiede a un giocatore, il quale ha scelto di abbattere una
	 * pecora in una determinata regione, quale pecora vuole abbattere.
	 * 
	 * @param regioneAbbattimento
	 * @param presenzaPecoraNera
	 * @param giocatoreCorrente
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public int sceltaAnimaleDaAbbattere(Regione regioneAbbattimento,
			boolean presenzaPecoraNera, Giocatore giocatoreCorrente)
			throws DisconnessoGiocatoreCorrenteException {
		inviaSingoloGiocatore(giocatoreCorrente,
				encoder.sceltaAnimaleDaAbbattere(regioneAbbattimento,
						presenzaPecoraNera, giocatoreCorrente));
		return leggiProssimoIntero(giocatoreCorrente);
	}

	/**
	 * Metodo che comunica ai giocatore che una pecora è stata abbattuta.
	 * 
	 * @param animaleAbbattuto
	 * @param regioneAbbattimento
	 */
	public void abbattimento(int animaleAbbattuto, Regione regioneAbbattimento) {
		inviaTuttiGiocatori(encoder.abbattimento(animaleAbbattuto,
				regioneAbbattimento));
	}

	/**
	 * Metodo che chiede a un giocatore, il quale ha scelto di effettuare un
	 * accoppiamento, in quale reigone vuole effettuarlo.
	 * 
	 * @param regioniAccoppiamento
	 * @param giocatoreAttuale
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public int sceltaRegioneAccoppiamento(Regione[] regioniAccoppiamento,
			Giocatore giocatoreAttuale)
			throws DisconnessoGiocatoreCorrenteException {
		inviaSingoloGiocatore(giocatoreAttuale,
				encoder.sceltaRegioneAccoppiamento(regioniAccoppiamento,
						giocatoreAttuale));
		return leggiProssimoIntero(giocatoreAttuale);
	}

	/**
	 * Metodo che comunica a tutti che è stato effettuato un accoppiamento.
	 * 
	 * @param regioneAccoppiamento
	 */
	public void accoppiamento(Regione regioneAccoppiamento) {
		inviaTuttiGiocatori(encoder.accoppiamento(regioneAccoppiamento));
	}

	/**
	 * Metodo che chiede a un giocatore, il quale ha scelto di spostare una
	 * pecora, da quale reigone intende prelevarla.
	 * 
	 * @param regioniAdiacenti
	 * @param regionePecoraNera
	 * @param giocatoreCorrente
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public int sceltaRegioneSpostamento(Regione[] regioniAdiacenti,
			Regione regionePecoraNera, Giocatore giocatoreCorrente)
			throws DisconnessoGiocatoreCorrenteException {
		inviaSingoloGiocatore(giocatoreCorrente,
				encoder.sceltaRegioneSpostamento(regioniAdiacenti,
						regionePecoraNera, giocatoreCorrente));
		return leggiProssimoIntero(giocatoreCorrente);
	}

	/**
	 * Metodo che chiede ad un giocatore, il quale ha scelto di spostare una
	 * pecora da una regione, che tipo di pecora vuole prelevare.
	 * 
	 * @param regioneSpostamento
	 * @param presenzaPecoraNera
	 * @param giocatoreCorrente
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public int sceltaPecoraDaSpostare(Regione regioneSpostamento,
			boolean presenzaPecoraNera, Giocatore giocatoreCorrente)
			throws DisconnessoGiocatoreCorrenteException {
		inviaSingoloGiocatore(giocatoreCorrente,
				encoder.sceltaPecoraDaSpostare(regioneSpostamento,
						presenzaPecoraNera, giocatoreCorrente));
		return leggiProssimoIntero(giocatoreCorrente);
	}

	/**
	 * Metodo che comunica a tutti che una pecora è stata spostata.
	 * 
	 * @param pecoraScelta
	 * @param regioneSpostamento
	 * @param regioneDiDestinazione
	 */
	public void spostamentoPecora(int pecoraScelta, Regione regioneSpostamento,
			Regione regioneDiDestinazione) {
		inviaTuttiGiocatori(encoder.spostamentoPecora(pecoraScelta,
				regioneSpostamento, regioneDiDestinazione));
	}

	/**
	 * Metodo che comunica a tutti lo spostamento del lupo.
	 * 
	 * @param regioneDestinazione
	 * @param haMangiato
	 * @param TipoPecora
	 */
	public void mossoLupo(int regioneDestinazione, boolean haMangiato,
			int TipoPecora) {
		inviaTuttiGiocatori(encoder.mossoLupo(regioneDestinazione, haMangiato,
				TipoPecora));

	}

	/**
	 * Metodo che comunica che un giocatore, che comunica che un giocatore, che
	 * ha assistito ad un abbattimento, non deve essere pagato per il silenzio.
	 * 
	 * @param giocatore
	 */
	public void nonPagaSilenzio(Giocatore giocatore) {
		inviaTuttiGiocatori(encoder.nonPagaSilenzio(giocatore));

	}

	/**
	 * Metodo che comunica che un giocatore che ha assistito ad un abbattimento
	 * deve essere pagato per il suo silenzio.
	 * 
	 * @param giocatoreCorrente
	 * @param giocatoreDaPagare
	 */
	public void pagaSilenzio(Giocatore giocatoreCorrente,
			Giocatore giocatoreDaPagare) {
		inviaTuttiGiocatori(encoder.pagaSilenzio(giocatoreCorrente,
				giocatoreDaPagare));

	}

	/**
	 * Metodo che, data una stringa, ritorna un intero.
	 * 
	 * @param input
	 * @return
	 */
	public int stringToInt(String input) {
		return Integer.parseInt(input);
	}

	/**
	 * Metodo che chiede a un giocatore cosa vuole offrire per la fase di
	 * mercato.
	 * 
	 * @param giocatoreAttuale
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public Offerta offriMercato(Giocatore giocatoreAttuale)
			throws DisconnessoGiocatoreCorrenteException {
		inviaSingoloGiocatore(giocatoreAttuale,
				encoder.offriMercato(giocatoreAttuale));
		String inputString = leggiProssimaStringa(giocatoreAttuale);
		if (inputString.contains("-1"))
			return null;
		Terreno tipoCarta = Terreno.getTerreno(stringToInt(inputString
				.substring(0, 2)));
		int prezzo = stringToInt(inputString.substring(4, 6));
		return new Offerta(tipoCarta, giocatoreAttuale, prezzo);
	}

	/**
	 * Metodo che comunica a tutti l'inizio del mercato.
	 */
	public void inizioMercato() {
		inviaTuttiGiocatori(encoder.inizioMercato());
	}

	/**
	 * Metodo che comunica a tutti la crescita di un agnello in una regione.
	 * 
	 * @param regione
	 * @param tipoPecora
	 */
	public void crescitaAgnello(Regione regione, int tipoPecora) {
		inviaTuttiGiocatori(encoder.crescitaAgnello(regione, tipoPecora));
	}

	/**
	 * Metodo che comunica a tutti che la partita è finita.
	 * 
	 */
	public void finePartita() {
		inviaTuttiGiocatori(encoder.finePartita());

	}

	/**
	 * Metodo che comunica a tutti il vincitore della partita.
	 * 
	 * @param vincitore
	 */
	public void annunciaVincitore(Giocatore vincitore, int[] punteggi) {
		inviaTuttiGiocatori(encoder.annunciaVincitore(vincitore, punteggi));
	}

	/**
	 * Metodo che comunica a tutti cheinizia la fase finale ed è l'ultimo giro.
	 */
	public void faseFinale() {
		inviaTuttiGiocatori(encoder.faseFinale());
	}

	/**
	 * Metodo che chiede a un giocatore che vuole effettuare l'abbattimento di
	 * tirare il dado.
	 * 
	 * @param pastoreScelto
	 * @param giocatoreCorrente
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public int tiroDadoAbbattimento(Pastore pastoreScelto,
			Giocatore giocatoreCorrente)
			throws DisconnessoGiocatoreCorrenteException {
		inviaSingoloGiocatore(giocatoreCorrente,
				encoder.tiroDadoAbbattimento(pastoreScelto, giocatoreCorrente));
		return leggiProssimoIntero(giocatoreCorrente);
	}

	/**
	 * Metodo che comunica a tutti che inizia la fase di acquisti al mercato.
	 * 
	 * @param listaOfferte
	 */
	public void inizioAcquistiMercato(ArrayList<Offerta> listaOfferte) {
		inviaTuttiGiocatori(encoder.inizioAcquistiMercato(listaOfferte));
	}

	/**
	 * +Metodo che chiede a un giocatore di tirare il dado.
	 * 
	 * @param giocatoreCorrente
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public int tiroDadoGiocatore(Giocatore giocatoreCorrente)
			throws DisconnessoGiocatoreCorrenteException {
		inviaSingoloGiocatore(giocatoreCorrente,
				encoder.tiroDadoGiocatore(giocatoreCorrente));
		return leggiProssimoIntero(giocatoreCorrente);
	}

	/**
	 * Metodo che comunica a tutti che un giocatore si è riconnesso.
	 * 
	 * @param indice
	 */
	public void notificaRiconnessione(int indice) {
		inviaTuttiGiocatori(encoder.notificaRiconnessione(indice));
	}

	/**
	 * Metodo che comunica a tutti che un pastore è stato posizionato.
	 * 
	 * @param giocatoreAttuale
	 * @param pastoreDaPosizionare
	 * @param stradaSuCuiPosizionare
	 */
	public void posizionatoPastore(Giocatore giocatoreAttuale,
			Pastore pastoreDaPosizionare, Strada stradaSuCuiPosizionare) {
		inviaTuttiGiocatori(encoder.posizionatoPastore(giocatoreAttuale,
				pastoreDaPosizionare, stradaSuCuiPosizionare));

	}

	/**
	 * Metodo che chiede a un giocatore se e quale offerta del mercato vuole
	 * accettare.
	 * 
	 * @param acquirente
	 * @param listaOfferte
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	public String accettaOfferta(Giocatore acquirente,
			ArrayList<Offerta> listaOfferte)
			throws DisconnessoGiocatoreCorrenteException {
		String accettaOfferta = encoder
				.accettaOfferta(acquirente, listaOfferte);
		inviaSingoloGiocatore(acquirente, accettaOfferta);
		String offertaAccettata = leggiProssimaStringa(acquirente);
		return offertaAccettata;
	}

	/**
	 * Metodo che comunica a tutti l'inizio di un turno.
	 * 
	 * @param giocatoreCorrente
	 */
	public void inizioTurno(Giocatore giocatoreCorrente) {
		inviaTuttiGiocatori(encoder.inizioTurno(giocatoreCorrente));
	}

	/**
	 * Metodo che comunica a tutti che un giocatore ha accettao un'offerta al
	 * mercato.
	 * 
	 * @param offertaAccettata
	 * @param acquirente
	 */
	public void offertaAccettata(Offerta offertaAccettata, Giocatore acquirente) {
		String offerta = encoder.offertaAccettata(offertaAccettata, acquirente);
		inviaTuttiGiocatori(offerta);
	}

	/**
	 * Metodo che comunica a tutti che è finita la fase del mercato.
	 * 
	 */
	public void fineMercato() {
		inviaTuttiGiocatori(encoder.fineMercato());
	}
}
