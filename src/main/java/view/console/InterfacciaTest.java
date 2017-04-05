package view.console;
import java.util.ArrayList;

import model.GameBoard;
import model.Pastore;
import model.Regione;
import model.Strada;
import model.Terreno;
import controller.Giocatore;
import controller.Offerta;
import view.InterfacciaPartitaUtente;

public class InterfacciaTest extends InterfacciaPartitaUtente {
	private int contatore = 0;
	private int numeroGiocatori;
	private String name = "Computer";
	private ConsoleView consoleViewTest = new ConsoleView();

	@Override
	public int posizionamentoInizialePastore(Giocatore giocatoreCorrente,
			Pastore pastoreDaPosizionare, ArrayList<Strada> stradeLibere) {
		contatore++;
		return contatore;
	}

	@Override
	public int inserimentoNumeroGiocatori() {
		numeroGiocatori = ((int) (Math.random() * 2) + 1) + 2;
		return numeroGiocatori;
	}

	@Override
	public String inserimentoNomeGiocatore(int i, Giocatore[] giocatori) {
		return name+" "+(i+1);
	}

	@Override
	public void createAndShowMap(GameBoard gameBoard, int[] carte,
			Giocatore[] giocatori) {
		//consoleViewTest.createAndShowMap(gameBoard, carte, giocatori);
	}

	@Override
	public void mostraCartaIniziale(Giocatore giocatore) {

	}

	@Override
	public void pastoreBloccato() {
		//consoleViewTest.pastoreBloccato();
	}

	@Override
	public int sceltaMossa(boolean[] mosseAbilitate, Giocatore giocatoreCorrente) {
		while (true) {
			int i = (int) (Math.random() * mosseAbilitate.length);
			if (mosseAbilitate[i])
				return i;
		}
	}

	@Override
	public void giocatoreSaltaTurno(Giocatore giocatoreCorrente) {
		//consoleViewTest.giocatoreSaltaTurno(giocatoreCorrente);
	}

	@Override
	public int sceltaPastore(Giocatore giocatoreCorrente,
			boolean pastore1Abilitato, boolean pastore2Abilitato) {
		if (pastore1Abilitato)
			return 1;
		return 2;
	}

	@Override
	public void fineTurno(Giocatore giocatoreCorrente) {
		//consoleViewTest.fineTurno(giocatoreCorrente);
	}

	@Override
	public void spostaPecoraNera(Regione destinazione) {
		//consoleViewTest.spostaPecoraNera(destinazione);
	}

	@Override
	public int movimentoPecoraNera(Giocatore giocatoreCorrente) {
		return (int) (Math.random() * 6) + 1;
	}

	@Override
	public int sceltaStradaPastore(int danari,
			ArrayList<Strada> stradeLibereLontane,
			ArrayList<Strada> stradeLibereVicine, Giocatore giocatoreCorrente) {
		if (stradeLibereVicine.size() > 0)
			return stradeLibereVicine.get(
					(int) (Math.random() * stradeLibereVicine.size())).getID();
		return stradeLibereLontane.get(
				(int) (Math.random() * stradeLibereLontane.size())).getID();
	}

	@Override
	public void movimentoPastore(Strada nuovaStradaPastore,
			Pastore pastoreMosso, boolean haPagato) {
		//consoleViewTest.movimentoPastore(nuovaStradaPastore, pastoreMosso,haPagato);
	}

	@Override
	public int sceltaCarta(int danari, int[] carteDisponibili, int numCarte,
			Giocatore giocatoreCorrente) {
		int i = 0;
		for (; i < carteDisponibili.length; i++)
			if (carteDisponibili[i] > 0 && danari > numCarte - carteDisponibili[i])
				return i;
		return i;
	}

	@Override
	public void acquistoCarta(Terreno tipoCarta, Giocatore giocatoreCorrente,
			int prezzo) {
		//consoleViewTest.acquistoCarta(tipoCarta, giocatoreCorrente, prezzo);
	}

	@Override
	public int sceltaRegioneAbbattimento(Regione[] regioniAdiacenti,
			Regione regionePecoraNera, Giocatore giocatoreCorrente) {
		if (regioniAdiacenti[0].getNumeroPecore() > 0)
			return regioniAdiacenti[0].getID();
		return regioniAdiacenti[1].getID();
	}

	@Override
	public int sceltaAnimaleDaAbbattere(Regione regioneAbbattimento,
			boolean presenzaPecoraNera, Giocatore giocatoreCorrente) {
		if (presenzaPecoraNera)
			return 3;
		if (regioneAbbattimento.getNumeroAgnelli() > 0)
			return 0;
		else if (regioneAbbattimento.getNumeroMontoni() > 0)
			return 2;
		else if (regioneAbbattimento.getNumeroPecoreFemmine() > 0)
			return 1;
		return 0;
	}

	@Override
	public void abbattimento(int animaleAbbattuto, Regione regioneAbbattimento) {
		//consoleViewTest.abbattimento(animaleAbbattuto, regioneAbbattimento);
	}

	@Override
	public int sceltaRegioneAccoppiamento(Regione[] regioniAccoppiamento,
			Giocatore giocatoreCorrente) {
		if (regioniAccoppiamento[0].accoppiamentoPossibile())
			return regioniAccoppiamento[0].getID();
		return regioniAccoppiamento[1].getID();
	}

	@Override
	public void accoppiamento(Regione regioneAccoppiamento) {
		//consoleViewTest.accoppiamento(regioneAccoppiamento);

	}

	@Override
	public int sceltaRegioneSpostamento(Regione[] regioniAdiacenti,
			Regione regionePecoraNera, Giocatore giocatoreCorrente) {
		if (regioniAdiacenti[0].getNumeroPecore() > 0)
			return regioniAdiacenti[0].getID();
		return regioniAdiacenti[1].getID();
	}

	@Override
	public int sceltaPecoraDaSpostare(Regione regioneSpostamento,
			boolean presenzaPecoraNera, Giocatore giocatoreCorrente) {
		if (presenzaPecoraNera)
			return 3;
		if (regioneSpostamento.getNumeroAgnelli() > 0)
			return 0;
		else if (regioneSpostamento.getNumeroMontoni() > 0)
			return 2;
		else if (regioneSpostamento.getNumeroPecoreFemmine() > 0)
			return 1;
		return 0;
	}

	@Override
	public void spostamentoPecora(int pecoraScelta, Regione regioneSpostamento,
			Regione regioneDiDestinazione) {
		//consoleViewTest.spostamentoPecora(pecoraScelta, regioneSpostamento,regioneDiDestinazione);

	}

	@Override
	public void mossoLupo(int regioneDestinazione, boolean haMangiato,
			int tipoPecora) {
		//consoleViewTest.mossoLupo(regioneDestinazione, haMangiato, tipoPecora);

	}

	@Override
	public void refreshTurno(GameBoard gameBoard, int[] carteDisponibili,
			Giocatore[] giocatori, Giocatore giocatore) {
		
	}

	@Override
	public int tiraDado6(Giocatore giocatoreCorrente) {
		return (int) (Math.random() * 6) + 1;
	}

	@Override
	public int tiroDadoGiocatore(Giocatore GiocatoreCorrente) {
		return (int) (Math.random() * 6) + 1;
	}

	@Override
	public void nonPagaSilenzio(Giocatore giocatore) {
		//consoleViewTest.nonPagaSilenzio(giocatore);

	}

	@Override
	public void pagaSilenzio(Giocatore giocatoreCorrente,
			Giocatore giocatoreDaPagare) {
		//consoleViewTest.pagaSilenzio(giocatoreCorrente, giocatoreDaPagare);

	}

	@Override
	public Offerta offriMercato(Giocatore giocatoreAttuale) {
		int[] carte = giocatoreAttuale.getCarte();
		int indiceRandom = (int) (Math.random() * carte.length);
		if (carte[indiceRandom] > 1) {
			Terreno tipoCarta = Terreno.getTerreno(indiceRandom);
			int prezzoRandom = (int) (Math.random() * 4) + 1;
			return new Offerta(tipoCarta, giocatoreAttuale, prezzoRandom);
		}
		return null;
	}




	@Override
	public void inizioMercato() {
		//consoleViewTest.inizioMercato();
	}

	@Override
	public void crescitaAgnello(Regione regione, int tipoPecora) {
		//consoleViewTest.crescitaAgnello(regione, tipoPecora);
	}

	@Override
	public void finePartita() {
		//consoleViewTest.finePartita();
	}

	@Override
	public void annunciaVincitore(Giocatore vincitore, Giocatore[] punteggi) {
		consoleViewTest.annunciaVincitore(vincitore, punteggi);
	}

	@Override
	public void faseFinale() {
		//consoleViewTest.faseFinale();

	}

	@Override
	public int tiroDadoAbbattimento(Pastore pastoreScelto,
			Giocatore giocatoreCorrente) {
		return 1;
	}


	@Override
	public void mostraCarte(Giocatore gicatoreCorrente) {
		//consoleViewTest.mostraCarte(gicatoreCorrente);

	}

	@Override
	public void disconnessionePersonale() {
		//consoleViewTest.disconnessionePersonale();

	}

	@Override
	public void inizioTurno(Giocatore giocatore) {
		//consoleViewTest.inizioTurno(giocatore);

	}

	@Override
	public void freeze(Giocatore giocatore) {
		//consoleViewTest.freeze(giocatore);

	}

	@Override
	public void disconnesso(Giocatore giocatore) {
		//consoleViewTest.disconnesso(giocatore);

	}

	@Override
	public void riconnesso(Giocatore giocatore) {
		//consoleViewTest.riconnesso(giocatore);

	}

	@Override
	public void posizionatoPastore(Giocatore giocatore,
			Pastore pastoreDaPosizionare, Strada stradaSuCuiPosizionare) {
		//consoleViewTest.posizionatoPastore(giocatore, pastoreDaPosizionare,stradaSuCuiPosizionare);

	}

	@Override
	public Offerta accettaOfferta(Giocatore acquirente,
			ArrayList<Offerta> listaOfferte) {
		int indiceOffertaScelta = (int)( Math.random() * listaOfferte.size());
		Offerta offertaScelta = listaOfferte.get(indiceOffertaScelta);
		if (offertaScelta.getVenditore().equals(acquirente) || offertaScelta.getPrezzo()>acquirente.getDanari())
			return null;
		else
			return offertaScelta;
	}

	@Override
	public void inizioAcquistiMercato(ArrayList<Offerta> listaOfferte) {
		//consoleViewTest.inizioAcquistiMercato(listaOfferte);

	}

	@Override
	public void fineMercato() {
		//consoleViewTest.fineMercato();

	}

	@Override
	public void offertaAccettata(Offerta offerta, Giocatore acquirente) {
		//consoleViewTest.offertaAccettata(offerta, acquirente);

	}

	@Override
	public void posizionaCancello(Strada strada) {
		//consoleViewTest.posizionaCancello(strada);
		
	}

	@Override
	public void inserimentoNumeroGiocatori(int num) {
		//consoleViewTest.inserimentoNumeroGiocatori(num);
		
	}

	@Override
	public void indicePersonale(int indice) {
		//consoleViewTest.indicePersonale(indice);
	}

}
