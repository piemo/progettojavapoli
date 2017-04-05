package view;

import java.util.ArrayList;

import model.GameBoard;
import model.Pastore;
import model.Regione;
import model.Strada;
import model.Terreno;
import controller.Giocatore;
import controller.Offerta;

public abstract class InterfacciaPartitaUtente {

	/**
	 * Il metodo serve a chiedere al giocatore corrente su quale strada vuole
	 * posizionare il suo pastore (o uno dei suoi due pastori) all'inizio della
	 * partita.
	 * 
	 * @param giocatoreCorrente
	 * @param pastoreDaPosizionare
	 * @param stradeLibere
	 *            lista delle strade su cui potre posizionare il pastore.
	 * @return l'ID della strada scelta.
	 */
	public abstract int posizionamentoInizialePastore(
			Giocatore giocatoreCorrente, Pastore pastoreDaPosizionare,
			ArrayList<Strada> stradeLibere);

	/**
	 * Metodo che chiede all'utente il numero di giocatore he vogliono
	 * partecipare alla partita.
	 * 
	 * @return il numero di giocatori inseriti dall'utente.
	 */
	public abstract int inserimentoNumeroGiocatori();

	/**
	 * Questo metodo serve a inserire il nome del giocatore i-esimo,
	 * controllando che il nome non sia gi� in uso.
	 * 
	 * @param i
	 *            indice del giocatore di cui inserire il nome.
	 * @param giocatori
	 *            array di tutti giocatori.
	 * @return il nome inserito dal giocatore i-esimo.
	 */
	public abstract String inserimentoNomeGiocatore(int i, Giocatore[] giocatori);

	/**
	 * Questo metodo crea una rappresentazione della mappa e della situazione di
	 * gioco da mostrare ai giocatori.
	 * 
	 * @param gameBoard
	 * @param giocatori
	 * @param carteDisponibili
	 */
	public abstract void createAndShowMap(GameBoard gameBoard,
			int[] carteDisponibili, Giocatore[] giocatori);

	/**
	 * Il metodo mostra ad un giocatore quale carta iniziale ha appena pescato.
	 * 
	 * @param giocatore
	 *            il giocatore a cui mostrare la rispettiva carta.
	 */
	public abstract void mostraCartaIniziale(Giocatore giocatore);

	/**
	 * Il metodo avvisa il giocatore che il pastore che ha scelto per il turno
	 * corrente � ora bloccato.
	 * 
	 * @param pastoreScelto
	 *            pastore in uso nel turno corrente.
	 */
	public abstract void pastoreBloccato();

	/**
	 * Questo metodo serve a chiedere al giocatore corrente quale mossa vuole
	 * effettuare, scegliendo tra quelle disponibili.
	 * 
	 * @param mosseAbilitate
	 * @return la scelta del giocatore
	 */
	public abstract int sceltaMossa(boolean[] mosseAbilitate,
			Giocatore giocatoreCorrente);

	/**
	 * Il metodo avvisa il giocatore corrente che, non potendo effettuare la
	 * mossa "Movimento Pastore", salta il turno. Pu� succedere se il giocatore
	 * non ha pi� danari e il suo pastore, o i suoi pastori, sono circondati da
	 * cancelli o da strade occupate.
	 * 
	 * @param giocatoreCorrente
	 */
	public abstract void giocatoreSaltaTurno(Giocatore giocatoreCorrente);

	/**
	 * Il metodo serve a far scelgiere al giocatore corrente quale pastore vuole
	 * utilizzare per il suo turno di gioco. Questo metodo viene utilizzato in
	 * cui ci siano solo 2 giocatori nella partita.
	 * 
	 * @param giocatoreCorrente
	 * @param pastore1Abilitato
	 *            indica se il primo pastore pu� muoversi.
	 * @param pastore2Abilitato
	 *            indica se il secondo pastore pu� muoversi.
	 * @return il pastore scelto.
	 */
	public abstract int sceltaPastore(Giocatore giocatoreCorrente,
			boolean pastore1Abilitato, boolean pastore2Abilitato);

	/**
	 * Questo metodo avvisa il giocatore corrente che il suo turno di gioco �
	 * finito.
	 * 
	 * @param giocatoreCorrente
	 */
	public abstract void fineTurno(Giocatore giocatoreCorrente);

	/**
	 * Questo metodo comunica ai giocatori in quale regione la pecora nera si �
	 * spostata.
	 * 
	 * @param destinazione
	 */
	public abstract void spostaPecoraNera(Regione destinazione);

	/**
	 * Questo metodo chiede al giocatore corrente di tirare il dado per
	 * determinare se e dove la pecora dovr� spostarsi.
	 * 
	 * @param giocatoreCorrente
	 * @return il risultato del dado.
	 */
	public abstract int movimentoPecoraNera(Giocatore giocatoreCorrente);

	/**
	 * Questo metodo chiede al giocatore corrente, che ha scelto di muovere il
	 * pastore, su quale strada vuole spostarlo. Il giocatore potr� scegliere
	 * una strada libera vicina, oppure, se possiede danari, una strada libera
	 * qualsiasi.
	 * 
	 * @param danari
	 *            numero di danari posseduti dal giocatore.
	 * @param stradeLibereLontane
	 *            lista delle strade libere lontane dal pastore.
	 * @param stradeLibereVicine
	 *            lista delle strade libere vicine al pastore.
	 * @return l'ID della strada scelta.
	 */
	public abstract int sceltaStradaPastore(int danari,
			ArrayList<Strada> stradeLibereLontane,
			ArrayList<Strada> stradeLibereVicine, Giocatore giocatore);

	/**
	 * Questo metodo aggiorna gli utenti sulla nuova posizione di un pastore che
	 * � stato appena mosso.
	 * 
	 * @param nuovaStradaPastore
	 *            nuova posizione del pastore mosso
	 * @param pastoreMosso
	 */
	public abstract void movimentoPastore(Strada nuovaStradaPastore,
			Pastore pastoreMosso, boolean haPagato);

	/**
	 * 
	 * @param danari
	 * @param carteDisponibili
	 * @param numCarte
	 * @return
	 */
	public abstract int sceltaCarta(int danari, int[] carteDisponibili,
			int numCarte, Giocatore giocatoreCorrente);

	/**
	 * Questo metodo notifica gli utenti riguardo quale carta ha appena
	 * acquistato il giocatore corrente.
	 * 
	 * @param tipoCarta
	 *            il terreno acquistato.
	 * @param giocatoreCorrente
	 */
	public abstract void acquistoCarta(Terreno tipoCarta,
			Giocatore giocatoreCorrente, int Prezzo);

	/**
	 * Questo metodo chiede al giocatore corrente, che ha scelto di effettuare
	 * un Abbattimento, in quale regione vuole effettuare quest'ultimo.
	 * 
	 * @param regioniAdiacenti
	 *            le due regioni accanto al pastore.
	 * @param regionePecoraNera
	 *            la regione in cui si trova la pecora nera.
	 * @return l'ID della regione scelta.
	 */
	public abstract int sceltaRegioneAbbattimento(Regione[] regioniAdiacenti,
			Regione regionePecoraNera, Giocatore giocatore);

	/**
	 * Questo metodo chiede al giocatore corrente quale tipo di pecora vuole
	 * abbattere nella regione precedentemente scelta.
	 * 
	 * @param regioneAbbattimento
	 *            la regione in cui effettuare l'abbattimento.
	 * @param presenzaPecoraNera
	 *            indica se � presente anche la pecora nera.
	 * @return un numero che rappresenta il tipo di pecora scelta.
	 */
	public abstract int sceltaAnimaleDaAbbattere(Regione regioneAbbattimento,
			boolean presenzaPecoraNera, Giocatore giocatore);

	/**
	 * Il metodo informa gli uteni riguardo a quale animale � appena stato
	 * abbattuto.
	 * 
	 * @param animaleAbbattuto
	 * @param regioneAbbattimento
	 */
	public abstract void abbattimento(int animaleAbbattuto,
			Regione regioneAbbattimento);

	/**
	 * Il metodo chiede al giocatore corrente, che ha scelto di efettuare un
	 * accoppiamento tra pecore, in quale regione vuole effettuarlo.
	 * 
	 * @param regioniAccoppiamento
	 * @return l'ID della regione scelta.
	 */
	public abstract int sceltaRegioneAccoppiamento(
			Regione[] regioniAccoppiamento, Giocatore giocatore);

	/**
	 * Il metodo comunica agli utenti che nella regione designata � stato
	 * effettuato un accoppiamento.
	 * 
	 * @param regioneAccoppiamento
	 */
	public abstract void accoppiamento(Regione regioneAccoppiamento);

	/**
	 * Il metodo chiede all'utente, che ha scelto di effettuare lo spostamento
	 * di una pecora, da quale regione vuole prelevare l'animale da spostare.
	 * 
	 * @param regioniAdiacenti
	 *            le due regioni accanto al pastore.
	 * @param regionePecoraNera
	 *            la regione in cui c'� la pecora nera.
	 * @return l'ID della regione scelta.
	 */
	public abstract int sceltaRegioneSpostamento(Regione[] regioniAdiacenti,
			Regione regionePecoraNera, Giocatore giocatore);

	/**
	 * Il metodo chiede all'utente che pecora spostare ( rappresentata con un
	 * numero ) in una data regione La presenza della pecora nera deve essere
	 * esplicitata in precedenza per permettere la scelta di quest'ultima
	 * 
	 * @param regioneSpostamento
	 * @param presenzaPecoraNera
	 * @return
	 */
	public abstract int sceltaPecoraDaSpostare(Regione regioneSpostamento,
			boolean presenzaPecoraNera, Giocatore giocatore);

	/**
	 * Metodo la cui funzione � quella di dare un feedback
	 * 
	 * @param pecoraScelta
	 * @param regioneSpostamento
	 * @param regioneDiDestinazione
	 */
	public abstract void spostamentoPecora(int pecoraScelta,
			Regione regioneSpostamento, Regione regioneDiDestinazione);

	/**
	 * Il metodo annuncia cosa il lupo fa nel suo movimento
	 * 
	 * @param regioneDestinazione
	 * @param pecoraMangiata
	 *            eventuale pecora mangiata dal lupo. Nel caso non ci fosse va
	 *            passato null
	 */
	public abstract void mossoLupo(int regioneDestinazione, boolean haMangiato,
			int pecoraMangiata);

	/**
	 * metodo chiamato all'avvio di ogni turno. Necessario per cambiare i dati
	 * mostrati a video ( in console necessario per mostrare lo stato di gioco a
	 * inizio turno)
	 * 
	 * @param gameBoard
	 * @param giocatore
	 */
	public abstract void refreshTurno(GameBoard gameBoard,
			int[] carteDisponibili, Giocatore[] giocatori, Giocatore giocatore);

	/**
	 * metodo che ritorna un valore intero tra 1 e 6
	 * 
	 * @return
	 */
	public abstract int tiraDado6(Giocatore giocatore);

	/**
	 * Metodo che ritorna un valore da 1 a 6 e richiede l'interazione
	 * dell'utente per lanciare il dado
	 * 
	 * @param GiocatoreCorrente
	 * @return
	 */
	public abstract int tiroDadoGiocatore(Giocatore GiocatoreCorrente);

	/**
	 * Messaggio che annuncia il NON pagamento del silenzio in seguito a un
	 * abbattimento
	 * 
	 * @param giocatore
	 *            Rappresenta il giocatore che non paga il silenzio
	 */
	public abstract void nonPagaSilenzio(Giocatore giocatore);

	/**
	 * Messaggio che annuncia il pagamento del silenzio in seguito ad un
	 * abbattimento
	 * 
	 * @param giocatoreCorrente
	 *            rappresenta il giocatore che ha eseguito l'abbattimento
	 * @param giocatoreDaPagare
	 */
	public abstract void pagaSilenzio(Giocatore giocatoreCorrente,
			Giocatore giocatoreDaPagare);

	/**
	 * Il metodo restituisce un'offerta associata a un giocatore lanciando
	 * richieste a video.
	 * 
	 * @param giocatoreAttuale
	 *            il giocatore a cui � associata l'offerta
	 * @return
	 */
	public abstract Offerta offriMercato(Giocatore giocatoreAttuale);

	/**
	 * Metodo che si interfaccia con l'utente per effettuare l'acquisto di
	 * offerta nel mercato tra una lista fornita. Viene restituito un elemento
	 * Offerta che rappresenta la scelta. Se viene restituto null, non � stata
	 * scelta nessuna offerta
	 * 
	 * @param acquirente
	 * @param listaOfferte
	 * @return
	 */
	public abstract Offerta accettaOfferta(Giocatore acquirente,
			ArrayList<Offerta> listaOfferte);

	
	/**
	 * Metodo chiamato all'inizio della fase di mercato
	 */
	public abstract void inizioMercato();

	/**
	 * Metodo chiamato per annunciare la crescita di un dato agnello in una data
	 * regione
	 * 
	 * @param regione
	 * @param pecoraAdulta
	 */
	public abstract void crescitaAgnello(Regione regione, int tipoPecora);

	/**
	 * Metodo chiamato alla fine della partita.
	 */
	public abstract void finePartita();

	/**
	 * Metodo che mostra il vincitore della partita.
	 * 
	 * @param vincitore
	 * @param punteggi 
	 */
	public abstract void annunciaVincitore(Giocatore vincitore, Giocatore[] punteggi);

	/**
	 * Metodo che viene chiamato all'inizio della fase finale della partita.
	 */
	public abstract void faseFinale();

	/**
	 * Elemento che chiede al giocatore di effettuare un tiro del dado per
	 * abilitare l'abbattimento. Se il risultato � uguale al valore della strada
	 * su cui si trova il pastore, restituisce true, altrimenti false
	 * 
	 * @param pastoreScelto
	 * @param giocatoriDaPagare
	 * @return
	 */
	public abstract int tiroDadoAbbattimento(Pastore pastoreScelto,
			Giocatore giocatoreCorrente);

	/**
	 * Metodo per annunciare l'inizio della fase di acquisto al mercato.
	 * 
	 * @param listaOfferte
	 */
	public abstract void inizioAcquistiMercato(ArrayList<Offerta> listaOfferte);



	/**
	 * Metodo che mostra a video le carte di una determinato giocatore.
	 * 
	 * @param gicatoreCorrente
	 */
	public abstract void mostraCarte(Giocatore gicatoreCorrente);

	/**
	 * Metodo che avvisa che ci sono problemi di connessione col server.
	 */
	public abstract void disconnessionePersonale();

	/**
	 * Metodo che avvisa che inizia il turno di un determinato giocatore.
	 * 
	 * @param giocatore
	 */
	public abstract void inizioTurno(Giocatore giocatore);

	/**
	 * Metodo che notifica la sospensione momentanea della partia.
	 * 
	 * @param giocatore
	 */
	public abstract void freeze(Giocatore giocatore);

	/**
	 * Metodo che notifca che un giocatore si è disconnesso.
	 * 
	 * @param giocatore
	 */
	public abstract void disconnesso(Giocatore giocatore);

	/**
	 * Metodo che avvisa che un giocatore si è riconnesso
	 * 
	 * @param giocatore
	 */
	public abstract void riconnesso(Giocatore giocatore);

	/**
	 * metodo che annuncia il posizionamento di un pastore su una strada
	 * 
	 * @param pastoreDaPosizionare
	 * @param stradaSuCuiPosizionare
	 */
	public abstract void posizionatoPastore(Giocatore giocatoreAttuale,
			Pastore pastoreDaPosizionare, Strada stradaSuCuiPosizionare);

	/**
	 * Questo metodo annuncia la fine del mercato.
	 */
	public abstract void fineMercato();

	/**
	 * Metodo che notifica del fatto che un giocatore ha accettato un offerta
	 * del mercato.
	 * 
	 * @param offerta
	 * @param acquirente
	 */
	public abstract void offertaAccettata(Offerta offerta, Giocatore acquirente);

	/**
	 * Metodo usato per posizionare un recinto sulla mappa.
	 * @param strada
	 */
	public abstract void posizionaCancello(Strada strada);

	/**
	 * Metodo per inserire nell'interfaccia il numero di giocatori quando viene comunicato dal server.
	 * @param num
	 */
	public abstract void inserimentoNumeroGiocatori(int num);

	public abstract void indicePersonale(int indice);

}