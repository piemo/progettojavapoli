package view.graphic;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import view.InterfacciaPartitaUtente;
import model.GameBoard;
import model.Pastore;
import model.Regione;
import model.Strada;
import model.Terreno;
import controller.Giocatore;
import controller.Offerta;

/**
 * Interfaccia che viene usata per la GUI.
 * 
 *
 */
public class GraphicUserInterface extends InterfacciaPartitaUtente {
	private MainFrame gui;
	private PonteLogicaGrafica ponte = new PonteLogicaGrafica();
	private NumeroGiocatoriFrame numeroGiocatori;
	private PanelGameboard pannelloElementi;
	private MessaggiPanel lavagna;

	/**
	 * Costruttore. Inizializza il frame;
	 */
	public GraphicUserInterface() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				gui = new MainFrame(ponte);
			}
		});
		ponte.whatClicked();
		ponte.reset();
		pannelloElementi = gui.getElementPanel();
		lavagna = pannelloElementi.getLavagna();
	}

	@Override
	public int posizionamentoInizialePastore(Giocatore giocatoreCorrente,
			Pastore pastoreDaPosizionare, ArrayList<Strada> stradeLibere) {
		ponte.reset();
		lavagna.posizionamentoPastore(giocatoreCorrente);
		for (Strada x : stradeLibere)
			pannelloElementi.abilitaStradaVicina(x.getID());
		int scelta = ponte.whatClicked();
		pannelloElementi.disabilitaStrade();
		ponte.reset();
		return scelta;
	}

	@Override
	public int inserimentoNumeroGiocatori() {
		numeroGiocatori = new NumeroGiocatoriFrame(ponte);
		pannelloElementi.add(numeroGiocatori);
		numeroGiocatori.setLocation(350, 275);
		numeroGiocatori.setVisible(true);
		pannelloElementi.repaint(); pannelloElementi.revalidate();
		int num = ponte.whatClicked();
		numeroGiocatori.dispose();
		ponte.reset();
		pannelloElementi.aggiungiGiocatori(num);
		return num;
	}
	
	@Override
	public void inserimentoNumeroGiocatori(int num){
		pannelloElementi.aggiungiGiocatori(num);
	}

	@Override
	public String inserimentoNomeGiocatore(int i, Giocatore[] giocatori) {
		return "Giocatore" + (i + 1);
	}

	@Override
	public void createAndShowMap(GameBoard gameBoard, int[] carteDisponibili,
			Giocatore[] giocatori) {
		//pecore
		for (Regione x : gameBoard.getRegioni()) {
			int numPecore = x.getNumeroPecoreFemmine();
			int numMontoni = x.getNumeroMontoni();
			int numAgnelli = x.getNumeroAgnelli();
			pannelloElementi.getRegione(x.getID()).setPecore(numAgnelli, numPecore,
					numMontoni);
		}
		//pecora nera
		if(gameBoard.getPecoraNera()==null)
			pannelloElementi.spostaPecoraNera(null);
		else
			pannelloElementi.spostaPecoraNera(gameBoard.getPecoraNera().getRegioneAttuale());
		//lupo
		pannelloElementi.spostaLupo(gameBoard.getLupo().getRegioneAttuale().getID());
		//pastori
		for(int i=0; i<giocatori.length; i++){
			Strada stradaPastore = giocatori[i].getPastore().getStrada();
			if(stradaPastore!=null)
				pannelloElementi.spostaPastore(i, stradaPastore.getID());
			if(giocatori.length==2){
				stradaPastore = giocatori[i].getSecondoPastore().getStrada();
				if(stradaPastore!=null)
					pannelloElementi.spostaPastore(i+4, stradaPastore.getID());
			}
		}
		//cancelli
		for (Strada x : gameBoard.getStrade())
			pannelloElementi.getStrada(x.getID()).clear();
		ArrayList<Strada> stradeRecintate = gameBoard.getStradeRecintate();
		for (Strada x : stradeRecintate)
			pannelloElementi.getStrada(x.getID()).posizionaCancello();
		pannelloElementi.settaNumeroCancelli(stradeRecintate.size());
		//carte Disponibili
		pannelloElementi.settaCarte(carteDisponibili);
		//danari
		pannelloElementi.settaDanari(giocatori);
	}

	@Override
	public void mostraCartaIniziale(Giocatore giocatore) {
		mostraCarte(giocatore);
	}

	@Override
	public void pastoreBloccato() {
		lavagna.pastoreBloccato();
		JOptionPane.showMessageDialog(gui, "Il tuo pastore si è bloccato","PASTORE BLOCCATO", JOptionPane.WARNING_MESSAGE);
	}

	@Override
	public int sceltaMossa(boolean[] mosseAbilitate, Giocatore giocatoreCorrente) {
		ponte.reset();
		lavagna.sceltaMossa(giocatoreCorrente);
		MossaPanel[] mosse = pannelloElementi.getMosse();
		for (int i=0; i<mosse.length; i++)
			if(mosseAbilitate[i])
				mosse[i].attiva();
		int scelta = ponte.whatClicked();
		pannelloElementi.disabilitaMosse();
		ponte.reset();
		return scelta;
	}

	@Override
	public void giocatoreSaltaTurno(Giocatore giocatoreCorrente) {
		lavagna.saltaTurno(giocatoreCorrente);
		JOptionPane.showMessageDialog(gui, giocatoreCorrente+ "Non può muovere il pastore!", "SALTA IL TURNO",JOptionPane.WARNING_MESSAGE);
	}

	@Override
	public int sceltaPastore(Giocatore giocatoreCorrente,boolean pastore1Abilitato, boolean pastore2Abilitato) {
		ponte.reset();
		lavagna.sceltaPastore(giocatoreCorrente);
		if (pastore1Abilitato)
			pannelloElementi.abilitaPastore(false);
		if (pastore2Abilitato)
			pannelloElementi.abilitaPastore(true);
		int scelta = ponte.whatClicked();
		pannelloElementi.disabilitaSceltaPastori();
		ponte.reset();
		return (scelta+1);
	}

	@Override
	public void fineTurno(Giocatore giocatoreCorrente) {
		lavagna.fineTurno(giocatoreCorrente);
	}

	@Override
	public void spostaPecoraNera(Regione destinazione) {
		if (destinazione!=null)
			lavagna.movimentoPecoraNera();
		pannelloElementi.spostaPecoraNera(destinazione);
	}

	@Override
	public int movimentoPecoraNera(Giocatore giocatoreCorrente) {
		lavagna.tiraDado(giocatoreCorrente);
		return tiraDado6(giocatoreCorrente);
	}

	@Override
	public int sceltaStradaPastore(int danari,ArrayList<Strada> stradeLibereLontane,ArrayList<Strada> stradeLibereVicine, Giocatore giocatoreCorrente) {
		ponte.reset();
		lavagna.sceltaStradaPastore(giocatoreCorrente);
		for (Strada x : stradeLibereVicine)
			pannelloElementi.abilitaStradaVicina(x.getID());
		if (danari > 0)
			for (Strada x : stradeLibereLontane)
				pannelloElementi.abilitaStradaLontana(x.getID());
		int scelta = ponte.whatClicked();
		pannelloElementi.disabilitaStrade();
		ponte.reset();
		return scelta;
	}

	@Override
	public void movimentoPastore (Strada nuovaStradaPastore, Pastore pastoreMosso, boolean haPagato) {
		int giocatore = pastoreMosso.getGiocatore();
		lavagna.movimentoPastore(giocatore);
		int idPastore = giocatore;
		if(pastoreMosso.isSecondo())
			idPastore+=4;
		int idStrada = nuovaStradaPastore.getID();
		pannelloElementi.spostaPastore(idPastore, idStrada);
		if (haPagato)
			pannelloElementi.pagamento(giocatore, 1);
	}

	@Override
	public int sceltaCarta(int danari, int[] carteDisponibili, int numCarte,Giocatore giocatore) {
		ponte.reset();
		lavagna.sceltaCarta(giocatore);
		for (int i = 0; i < carteDisponibili.length; i++)
			if (carteDisponibili[i] > 0 && danari > numCarte - carteDisponibili[i])
				pannelloElementi.abilitaCarta(i);
		int scelta = ponte.whatClicked();
		pannelloElementi.disabilitaCarte();
		ponte.reset();
		return scelta;
	}

	@Override
	public void acquistoCarta(Terreno tipoCarta, Giocatore giocatoreCorrente,int prezzo) {
		lavagna.acquistoCarta(giocatoreCorrente);
		pannelloElementi.animazioneCarta(tipoCarta.ordinal());
		pannelloElementi.getCarta(tipoCarta.ordinal()).togliCarta();
		pannelloElementi.pagamento(giocatoreCorrente.getIndice(), prezzo);
		pannelloElementi.getCartePersonali().aggiungiCarta(tipoCarta.ordinal());
	}

	@Override
	public int sceltaRegioneAbbattimento(Regione[] regioniAdiacenti,Regione regionePecoraNera, Giocatore giocatoreCorrente) {
		ponte.reset();
		lavagna.sceltaRegioneAbbattimento(giocatoreCorrente);
		if(regioniAdiacenti[0].getNumeroPecore() > 0)
			pannelloElementi.getRegione(regioniAdiacenti[0].getID()).attiva();
		if(regioniAdiacenti[1].getNumeroPecore() > 0)
			pannelloElementi.getRegione(regioniAdiacenti[1].getID()).attiva();
		
		int scelta = ponte.whatClicked();
		pannelloElementi.disabilitaRegioni();
		ponte.reset();
		return scelta;
	}

	@Override
	public int sceltaAnimaleDaAbbattere(Regione regioneAbbattimento,boolean presenzaPecoraNera, Giocatore giocatoreCorrente) {
		ponte.reset();
		lavagna.sceltaAnimaleDaAbbattere(giocatoreCorrente);
		if (regioneAbbattimento.getNumeroAgnelli() > 0)
			pannelloElementi.abilitaPecora(0);
		if (regioneAbbattimento.getNumeroPecoreFemmine() > 0)
			pannelloElementi.abilitaPecora(1);
		if (regioneAbbattimento.getNumeroMontoni() > 0)
			pannelloElementi.abilitaPecora(2);
		if (presenzaPecoraNera)
			pannelloElementi.abilitaPecora(3);
		int scelta = ponte.whatClicked();
		pannelloElementi.disabilitaPecore();
		ponte.reset();
		return scelta;
	}

	@Override
	public void abbattimento(int animaleAbbattuto, Regione regioneAbbattimento) {
		lavagna.abbattimento(animaleAbbattuto);
		int idRegione = regioneAbbattimento.getID();
		pannelloElementi.animazioneTeschio(idRegione);
		if(animaleAbbattuto==3)
			pannelloElementi.eliminaPecoraNera();
		else 
			pannelloElementi.getRegione(idRegione).prelevaPecora(animaleAbbattuto);
		
	}

	@Override
	public int sceltaRegioneAccoppiamento(Regione[] regioniAccoppiamento,Giocatore giocatoreCorrente) {
		ponte.reset();
		lavagna.sceltaRegione(giocatoreCorrente);
		if (regioniAccoppiamento[0].accoppiamentoPossibile())
			pannelloElementi.getRegione(regioniAccoppiamento[0].getID()).attiva();;
		if (regioniAccoppiamento[1].accoppiamentoPossibile())
			pannelloElementi.getRegione(regioniAccoppiamento[1].getID()).attiva();;
		int scelta = ponte.whatClicked();
		pannelloElementi.disabilitaRegioni();
		ponte.reset();
		return scelta;
	}

	@Override
	public void accoppiamento (Regione regioneAccoppiamento) {
		lavagna.accoppiamento();
		int idRegione = regioneAccoppiamento.getID();
		pannelloElementi.animazioneCuore(idRegione);
		pannelloElementi.getRegione(idRegione).posizionaPecora(0);
	}

	@Override
	public int sceltaRegioneSpostamento(Regione[] regioniAdiacenti,Regione regionePecoraNera, Giocatore giocatoreCorrente) {
		ponte.reset();
		lavagna.sceltaRegione(giocatoreCorrente);
		if (regioniAdiacenti[0].getNumeroPecore() > 0)
			pannelloElementi.getRegione(regioniAdiacenti[0].getID()).attiva();
		if (regioniAdiacenti[1].getNumeroPecore() > 0)
			pannelloElementi.getRegione(regioniAdiacenti[1].getID()).attiva();
		int scelta = ponte.whatClicked();
		pannelloElementi.disabilitaRegioni();
		ponte.reset();
		return scelta;
	}

	@Override
	public int sceltaPecoraDaSpostare(Regione regioneSpostamento,boolean presenzaPecoraNera, Giocatore giocatoreCorrente) {
		ponte.reset();
		lavagna.sceltaPecoraDaSpostare(giocatoreCorrente);
		if (regioneSpostamento.getNumeroAgnelli() > 0)
			pannelloElementi.abilitaPecora(0);
		if (regioneSpostamento.getNumeroPecoreFemmine() > 0)
			pannelloElementi.abilitaPecora(1);
		if (regioneSpostamento.getNumeroMontoni() > 0)
			pannelloElementi.abilitaPecora(2);
		if (presenzaPecoraNera)
			pannelloElementi.abilitaPecora(3);
		int scelta = ponte.whatClicked();
		pannelloElementi.disabilitaPecore();
		ponte.reset();
		return scelta;
	}

	@Override
	public void spostamentoPecora (int pecoraScelta, Regione regioneSpostamento, Regione regioneDiDestinazione) {
		lavagna.spostamentoPecora(pecoraScelta);
		int idPartenza = regioneSpostamento.getID();
		int idArrivo = regioneDiDestinazione.getID();
		if(pecoraScelta<3){
			pannelloElementi.getRegione(idPartenza).prelevaPecora(pecoraScelta);
			pannelloElementi.spostaPecora(idPartenza, idArrivo);
			pannelloElementi.getRegione(idArrivo).posizionaPecora(pecoraScelta);
		}else {
			pannelloElementi.spostaPecoraNera(regioneDiDestinazione);
		}
	}

	@Override
	public void mossoLupo(int regioneDestinazione, boolean haMangiato,int tipoPecora) {
		lavagna.movimentoLupo();
		pannelloElementi.spostaLupo(regioneDestinazione);
		if(haMangiato){
			pannelloElementi.animazioneCarne(regioneDestinazione);
			if(tipoPecora<3)
				pannelloElementi.getRegione(regioneDestinazione).prelevaPecora(tipoPecora);
			else
				pannelloElementi.eliminaPecoraNera();
		}
	}

	@Override
	public void refreshTurno(GameBoard gameBoard, int[] carteDisponibili,Giocatore[] giocatori, Giocatore giocatore) {
		inizioTurno(giocatore);
		pannelloElementi.mostraBandiera(giocatore.getIndice());
		mostraCarte(giocatore);
	}

	@Override
	public int tiraDado6(Giocatore giocatoreCorrente) {
		ponte.reset();
		pannelloElementi.abilitaDado();
		int valoreDado = ponte.whatClicked();
		pannelloElementi.disabilitaDado();
		ponte.reset();
		lavagna.risultatoDado(valoreDado);
		return valoreDado;
	}


	@Override
	public void nonPagaSilenzio(Giocatore giocatore) {
		
	}

	@Override
	public void pagaSilenzio(Giocatore giocatoreCorrente,Giocatore giocatoreDaPagare) {
		lavagna.pagaSilenzio(giocatoreCorrente, giocatoreDaPagare);
		pannelloElementi.pagamento(giocatoreCorrente.getIndice(), 2);
		pannelloElementi.incassa(giocatoreDaPagare.getIndice(), 2);
	}

	@Override
	public Offerta offriMercato(Giocatore giocatoreAttuale) {
		lavagna.turnoMercato(giocatoreAttuale);
		int[] carteDisponibili = giocatoreAttuale.getCarte();
		int cartaScelta;
		OffriPanel offriPanel = pannelloElementi.getOffriPanel();
		Offerta offertaGiocatore = null;
		ponte.reset();
		offriPanel.visualizza(carteDisponibili);
		cartaScelta = ponte.whatClicked();
		ponte.reset();
		if(cartaScelta>=0){
			pannelloElementi.getOffriPanel().disattiva();
			int prezzo = offriPanel.getPrezzoCarta(cartaScelta);
			ponte.reset();
			offertaGiocatore = new Offerta(Terreno.getTerreno(cartaScelta), giocatoreAttuale, prezzo);
		}
		offriPanel.nascondi();
		lavagna.offertaFatta(giocatoreAttuale);
		return offertaGiocatore;
	}

	@Override
	public void inizioMercato() {
		lavagna.inizioMercato();
	}

	@Override
	public void crescitaAgnello (Regione regione, int tipoPecora) {
		int idRegione = regione.getID();
		pannelloElementi.animazioneCrescita(regione.getID());
		pannelloElementi.getRegione(idRegione).prelevaPecora(0);
		pannelloElementi.getRegione(idRegione).posizionaPecora(tipoPecora);
	}

	@Override
	public void finePartita() {
		lavagna.finePartita();
	}

	@Override
	public void annunciaVincitore(Giocatore vincitore, Giocatore[] punteggi) {
		for(int i=0; i<punteggi.length; i++)
			pannelloElementi.settaDanari(punteggi);
		lavagna.annunciaVincitore(vincitore);
	}

	@Override
	public void faseFinale() {
		lavagna.faseFinale();
	}

	@Override
	public int tiroDadoAbbattimento(Pastore pastoreScelto,Giocatore giocatoreCorrente) {
		int valoreStrada = pastoreScelto.getStrada().getValore();
		lavagna.tiraDadoAbbattimento(giocatoreCorrente, valoreStrada);
		int dado = tiraDado6(giocatoreCorrente);
		if (dado == valoreStrada)
			return 1;
		else
			return 0;
	}

	

	@Override
	public void mostraCarte(Giocatore giocatoreCorrente) {
		int[] cartePersonali = giocatoreCorrente.getCarte();
		pannelloElementi.settaCartePersonali(cartePersonali);
	}

	@Override
	public void disconnessionePersonale() {
		JOptionPane.showMessageDialog(gui, "La connessione col server è stata persa. "
				+ "Attendi..",
				"DISCONNESSO", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void inizioTurno(Giocatore giocatore) {
		lavagna.turno(giocatore);
	}

	@Override
	public void freeze(Giocatore giocatore) {
		JOptionPane.showMessageDialog(gui, giocatore+" si è disconnesso."
				+ " \nIl gioco è momentaneamente sospseso...",
				"Attesa "+giocatore, JOptionPane.WARNING_MESSAGE);
	}

	@Override
	public void disconnesso(Giocatore giocatore) {
		JOptionPane.showMessageDialog(gui, "Il tempo di attesa è scaduto."
				+ " \nLa partita riprende senza "+giocatore,
				"Disconnessione "+giocatore, JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void riconnesso(Giocatore giocatore) {
		JOptionPane.showMessageDialog(gui, giocatore+" si è riconnesso e torna a giocare.",
				"Riconnessione "+giocatore, JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void posizionatoPastore(Giocatore giocatore,Pastore pastoreDaPosizionare, Strada stradaSuCuiPosizionare) {
		int idPastore = giocatore.getIndice();
		if(pastoreDaPosizionare.isSecondo())
			idPastore+=4;
		pannelloElementi.spostaPastore(idPastore, stradaSuCuiPosizionare.getID());
	}

	@Override
	public Offerta accettaOfferta(Giocatore acquirente, ArrayList<Offerta> listaOfferte) {
		lavagna.turnoMercato(acquirente);
		pannelloElementi.visualizzaMercato(listaOfferte);
		Offerta offertaAccettata = null;
		boolean sceltaValida = false;
		while(!sceltaValida){
			ponte.reset();
			int numeroOttenuto=ponte.whatClicked();
			if (numeroOttenuto==-1){
				sceltaValida = true;
			}else if(listaOfferte.get(numeroOttenuto).getVenditore().equals(acquirente)){
				JOptionPane.showMessageDialog(gui, "Non puoi comprare la tua carta!",
						"Scelta non valida", JOptionPane.WARNING_MESSAGE);
			}else if(listaOfferte.get(numeroOttenuto).getPrezzo()>acquirente.getDanari()){
				JOptionPane.showMessageDialog(gui, "Non hai abbastanza soldi!",
						"Scelta non valida", JOptionPane.WARNING_MESSAGE);
			}else {
				sceltaValida = true;
				offertaAccettata = listaOfferte.get(numeroOttenuto);
				pannelloElementi.getCartePersonali().aggiungiCarta(offertaAccettata.getTipoCarta().ordinal());
			}
		}
		pannelloElementi.nascondiMercato();
		return offertaAccettata;
	}

	@Override
	public void inizioAcquistiMercato(ArrayList<Offerta> listaOfferte) {
		lavagna.inizioAcquistiMercato();
		
	}

	@Override
	public void fineMercato() {
		lavagna.fineMercato();
	}

	@Override
	public void offertaAccettata(Offerta offerta, Giocatore acquirente) {
		if(offerta!=null){
			int somma = offerta.getPrezzo();
			pannelloElementi.pagamento(acquirente.getIndice(), somma);
			pannelloElementi.incassa(offerta.getVenditore().getIndice(), somma);
		}
	}

	@Override
	public void posizionaCancello(Strada strada) {
		pannelloElementi.getStrada(strada.getID()).posizionaCancello();
		pannelloElementi.aumentaContatoreCancelli();
	}

	@Override@Deprecated
	public int tiroDadoGiocatore(Giocatore GiocatoreCorrente) {
		return 0;
	}
	
	@Override
	public void indicePersonale(int indice){
		pannelloElementi.mostraBandiera(indice);
	}
	/**
	 * metodo usato nel test per inserire un valore di numero di giocatori
	 * @param num
	 */
	public void inserimentoNumeroGiocatoriTest(int num){
		pannelloElementi.aggiungiGiocatori(num);
	}

}
