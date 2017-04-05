package view.graphic;

import java.util.ArrayList;

import model.GameBoard;
import model.Pastore;
import model.Regione;
import model.Strada;
import model.Terreno;
import controller.Giocatore;
import controller.Offerta;
import view.InterfacciaPartitaUtente;
import view.console.InterfacciaTest;

/**
 * Interfaccia che viene utilizzata per testare la grafica del gioco. Vengono mandati dei dati casuali quando richiesti dalla logica,
 * Mentre la grafica si aggiorna solo quando ci sono metodi opportuni.
 *
 *
 */
public class InterfacciaGraficaTest extends InterfacciaPartitaUtente{
	private InterfacciaTest input;
	private GraphicUserInterface graphic;
	
	/**
	 * Costruttore
	 */
	public InterfacciaGraficaTest() {
		input = new InterfacciaTest();
		graphic = new GraphicUserInterface();
	}
	
	@Override
	public int posizionamentoInizialePastore(Giocatore giocatoreCorrente,
			Pastore pastoreDaPosizionare, ArrayList<Strada> stradeLibere) {
		return input.posizionamentoInizialePastore(giocatoreCorrente, pastoreDaPosizionare, stradeLibere);
	}

	@Override
	public int inserimentoNumeroGiocatori() {
		int num= input.inserimentoNumeroGiocatori();
		graphic.inserimentoNumeroGiocatoriTest(num);
		return num;
	}

	@Override
	public String inserimentoNomeGiocatore(int i, Giocatore[] giocatori) {
		return input.inserimentoNomeGiocatore(i, giocatori);
	}

	@Override
	public void createAndShowMap(GameBoard gameBoard, int[] carteDisponibili,
			Giocatore[] giocatori) {
		graphic.createAndShowMap(gameBoard, carteDisponibili, giocatori);
	}

	@Override
	public void mostraCartaIniziale(Giocatore giocatore) {
		graphic.mostraCartaIniziale(giocatore);
		
	}

	@Override
	public void pastoreBloccato() {
		graphic.pastoreBloccato();
		
	}

	@Override
	public int sceltaMossa(boolean[] mosseAbilitate, Giocatore giocatoreCorrente) {
		return input.sceltaMossa(mosseAbilitate, giocatoreCorrente);
	}

	@Override
	public void giocatoreSaltaTurno(Giocatore giocatoreCorrente) {
		graphic.giocatoreSaltaTurno(giocatoreCorrente);
		
	}

	@Override
	public int sceltaPastore(Giocatore giocatoreCorrente,
			boolean pastore1Abilitato, boolean pastore2Abilitato) {
		return input.sceltaPastore(giocatoreCorrente, pastore1Abilitato, pastore2Abilitato);
	}

	@Override
	public void fineTurno(Giocatore giocatoreCorrente) {
		graphic.fineTurno(giocatoreCorrente);	
	}

	@Override
	public void spostaPecoraNera(Regione destinazione) {
		graphic.spostaPecoraNera(destinazione);
	}

	@Override
	public int movimentoPecoraNera(Giocatore giocatoreCorrente) {
		return input.movimentoPecoraNera(giocatoreCorrente);
	}

	@Override
	public int sceltaStradaPastore(int danari,
			ArrayList<Strada> stradeLibereLontane,
			ArrayList<Strada> stradeLibereVicine, Giocatore giocatore) {
		return input.sceltaStradaPastore(danari, stradeLibereLontane, stradeLibereVicine, giocatore);
	}

	@Override
	public void movimentoPastore(Strada nuovaStradaPastore,
			Pastore pastoreMosso, boolean haPagato) {
		graphic.movimentoPastore(nuovaStradaPastore, pastoreMosso, haPagato);
	}

	@Override
	public int sceltaCarta(int danari, int[] carteDisponibili, int numCarte,
			Giocatore giocatoreCorrente) {
		return input.sceltaCarta(danari, carteDisponibili, numCarte, giocatoreCorrente);
	}

	@Override
	public void acquistoCarta(Terreno tipoCarta, Giocatore giocatoreCorrente,
			int prezzo) {
		graphic.acquistoCarta(tipoCarta, giocatoreCorrente, prezzo);
		
	}

	@Override
	public int sceltaRegioneAbbattimento(Regione[] regioniAdiacenti,
			Regione regionePecoraNera, Giocatore giocatore) {
		return input.sceltaRegioneAbbattimento(regioniAdiacenti, regionePecoraNera, giocatore);
	}

	@Override
	public int sceltaAnimaleDaAbbattere(Regione regioneAbbattimento,
			boolean presenzaPecoraNera, Giocatore giocatore) {
		return input.sceltaAnimaleDaAbbattere(regioneAbbattimento, presenzaPecoraNera, giocatore);
	}

	@Override
	public void abbattimento(int animaleAbbattuto, Regione regioneAbbattimento) {
		graphic.abbattimento(animaleAbbattuto, regioneAbbattimento);
	}

	@Override
	public int sceltaRegioneAccoppiamento(Regione[] regioniAccoppiamento,
			Giocatore giocatore) {
		return input.sceltaRegioneAccoppiamento(regioniAccoppiamento, giocatore);
	}

	@Override
	public void accoppiamento(Regione regioneAccoppiamento) {
		graphic.accoppiamento(regioneAccoppiamento);
	}

	@Override
	public int sceltaRegioneSpostamento(Regione[] regioniAdiacenti,
			Regione regionePecoraNera, Giocatore giocatore) {
		return input.sceltaRegioneSpostamento(regioniAdiacenti, regionePecoraNera, giocatore);
	}

	@Override
	public int sceltaPecoraDaSpostare(Regione regioneSpostamento,
			boolean presenzaPecoraNera, Giocatore giocatore) {
		return input.sceltaPecoraDaSpostare(regioneSpostamento, presenzaPecoraNera, giocatore);
	}

	@Override
	public void spostamentoPecora(int pecoraScelta, Regione regioneSpostamento,
			Regione regioneDiDestinazione) {
		graphic.spostamentoPecora(pecoraScelta, regioneSpostamento, regioneDiDestinazione);
		
	}

	@Override
	public void mossoLupo(int regioneDestinazione, boolean haMangiato,
			int pecoraMangiata) {
		graphic.mossoLupo(regioneDestinazione, haMangiato, pecoraMangiata);
	}

	@Override
	public void refreshTurno(GameBoard gameBoard, int[] carteDisponibili,
			Giocatore[] giocatori, Giocatore giocatore) {
		graphic.refreshTurno(gameBoard, carteDisponibili, giocatori, giocatore);
	}

	@Override
	public int tiraDado6(Giocatore giocatore) {
		return input.tiraDado6(giocatore);
	}

	@Override
	public int tiroDadoGiocatore(Giocatore GiocatoreCorrente) {
		return input.tiroDadoGiocatore(GiocatoreCorrente);
	}

	@Override
	public void nonPagaSilenzio(Giocatore giocatore) {
		graphic.nonPagaSilenzio(giocatore);
	}

	@Override
	public void pagaSilenzio(Giocatore giocatoreCorrente,
			Giocatore giocatoreDaPagare) {
		graphic.pagaSilenzio(giocatoreCorrente, giocatoreDaPagare);
		
	}

	@Override
	public Offerta offriMercato(Giocatore giocatoreAttuale) {
		return input.offriMercato(giocatoreAttuale);
	}

	@Override
	public Offerta accettaOfferta(Giocatore acquirente,
			ArrayList<Offerta> listaOfferte) {
		return input.accettaOfferta(acquirente, listaOfferte);
	}

	@Override
	public void inizioMercato() {
		graphic.inizioMercato();
		
	}

	@Override
	public void crescitaAgnello(Regione regione, int tipoPecora) {
		graphic.crescitaAgnello(regione, tipoPecora);
	}

	@Override
	public void finePartita() {
		graphic.finePartita();
	}

	@Override
	public void annunciaVincitore(Giocatore vincitore, Giocatore[] giocatori) {
		graphic.annunciaVincitore(vincitore, giocatori);
		
	}

	@Override
	public void faseFinale() {
		graphic.faseFinale();
		
	}

	@Override
	public int tiroDadoAbbattimento(Pastore pastoreScelto,
			Giocatore giocatoreCorrente) {
		return 1;
	}

	@Override
	public void inizioAcquistiMercato(ArrayList<Offerta> listaOfferte) {
		graphic.inizioAcquistiMercato(listaOfferte);
		
	}

	@Override
	public void mostraCarte(Giocatore gicatoreCorrente) {
		graphic.mostraCarte(gicatoreCorrente);
	}

	@Override
	public void disconnessionePersonale() {
		
	}

	@Override
	public void inizioTurno(Giocatore giocatore) {
		graphic.inizioTurno(giocatore);
		
	}

	@Override
	public void freeze(Giocatore giocatore) {
		
	}

	@Override
	public void disconnesso(Giocatore giocatore) {
	
	}

	@Override
	public void riconnesso(Giocatore giocatore) {
		
	}

	@Override
	public void posizionatoPastore(Giocatore giocatoreAttuale,
			Pastore pastoreDaPosizionare, Strada stradaSuCuiPosizionare) {
		graphic.posizionatoPastore(giocatoreAttuale, pastoreDaPosizionare, stradaSuCuiPosizionare);
	}

	@Override
	public void fineMercato() {
		graphic.fineMercato();
	}

	@Override
	public void offertaAccettata(Offerta offerta, Giocatore acquirente) {
		graphic.offertaAccettata(offerta, acquirente);
	}

	@Override
	public void posizionaCancello(Strada strada) {
		graphic.posizionaCancello(strada);
	}

	@Override
	public void inserimentoNumeroGiocatori(int num) {
		graphic.inserimentoNumeroGiocatori(num);
	}

	@Override
	public void indicePersonale(int indice) {
		graphic.indicePersonale(indice);
	}

}
