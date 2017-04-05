package online;

import java.util.ArrayList;

import model.Agnello;
import model.GameBoard;
import model.Lupo;
import model.Pastore;
import model.PecoraNera;
import model.Regione;
import model.Strada;
import model.Terreno;
import controller.Giocatore;
import controller.Mercato;
import controller.Offerta;

/**
 * Classe che si occupa di gestire la logica di gioco sui server.
 * 
 */
public class PartitaOnline {
	public static final int NUM_CANCELLI = 20;
	public static final int NUM_MOSSE = 5;
	public static final int NUM_MOSSE_TURNO = 3;
	public static final int NUM_TERRENI = 6;
	public static final int NUM_CARTE = 5;

	private GameBoard gameBoard;
	private Giocatore[] giocatori;
	private Integer cancelliPosizionati;
	private int numeroGiocatori;
	private int[] carteDisponibili;
	private InterfacciaOnline interfaccia;
	private Giocatore vincitore;

	/**
	 * Costruttore per la partita online.
	 * @param interfaccia interfaccia socket o RMI.
	 * @param numeroGiocatori
	 */
	public PartitaOnline(InterfacciaOnline interfaccia, int numeroGiocatori) {
		this.interfaccia = interfaccia;
		this.numeroGiocatori = numeroGiocatori;
		inizializzaGameboard(numeroGiocatori);
	}

	/**
	 * Metodo per far iniziare la partita e che alla fine ritorna al gestore della partita il vincitore.
	 * @return
	 */
	public Giocatore startGame() {
		posizionamentoInizialePastori();
		faseDiGioco();
		finePartita();
		return vincitore;
	}

	/**
	 * Questo metodo serve a creare una gameboard di partenza, dato il numero di
	 * giocatori.
	 * 
	 * @param numeroGiocatori
	 */
	private void inizializzaGameboard(int numeroGiocatori) {
		gameBoard = new GameBoard(numeroGiocatori);
		giocatori = new Giocatore[numeroGiocatori];
		gameBoard.setUpPecore();

		for (int i = 0; i < numeroGiocatori; i++) {
			String nome = ("Giocatore " + i);
			giocatori[i] = new Giocatore(nome, i);
		}

		if (numeroGiocatori > 2)
			for (int i = 0; i < numeroGiocatori; i++) {
				Pastore pastore = gameBoard.getPastore(i);
				giocatori[i].assegnaPastore(pastore, false);
				giocatori[i].setDanari(20);
			}
		else
			for (int i = 0; i < numeroGiocatori; i++) {
				Pastore pastore = gameBoard.getPastore(i * 2);
				giocatori[i].assegnaPastore(pastore, false);
				pastore = gameBoard.getPastore((i * 2) + 1);
				giocatori[i].assegnaPastore(pastore, true);
				giocatori[i].setDanari(30);
			}

		cancelliPosizionati = 0;

		carteDisponibili = new int[NUM_TERRENI];
		for (int i = 0; i < NUM_TERRENI; i++)
			carteDisponibili[i] = NUM_CARTE;

		assegnamentoCarteInziali();

	}

	/**
	 * Metodo che assegna casualmente le carte iniziali a tutti i giocatori
	 * della partita.
	 */
	private void assegnamentoCarteInziali() {
		ArrayList<Terreno> carteIniziali = new ArrayList<Terreno>();
		carteIniziali.add(Terreno.AGRICOLO);
		carteIniziali.add(Terreno.ARIDO);
		carteIniziali.add(Terreno.FIUME);
		carteIniziali.add(Terreno.FORESTA);
		carteIniziali.add(Terreno.MONTAGNA);
		carteIniziali.add(Terreno.PRATO);
		for (int i = 0; i < numeroGiocatori; i++) {
			int cartaCasuale = (int) (Math.random() * carteIniziali.size());
			giocatori[i].assegnaCarta(carteIniziali.get(cartaCasuale));
			carteIniziali.remove(cartaCasuale);
		}

	}

	/**
	 * Metodo per posizionare i pastori dei giocatori prima dell'inizio del
	 * gioco.
	 */
	private void posizionamentoInizialePastori() {
		for (int i = 0; i < numeroGiocatori; i++) {
			ArrayList<Strada> stradeLibere = gameBoard.getStradeLibere();
			Pastore pastoreDaPosizionare = giocatori[i].getPastore();
			Strada stradaSuCuiPosizionare;
			int iDstradaSuCuiPosizionare;
			try {
				iDstradaSuCuiPosizionare = interfaccia
						.posizionamentoInizialePastore(giocatori[i],
								pastoreDaPosizionare, stradeLibere);
				stradaSuCuiPosizionare = gameBoard
						.getStrada(iDstradaSuCuiPosizionare);
				pastoreDaPosizionare.posizionaPastore(stradaSuCuiPosizionare);
			} catch (DisconnessoGiocatoreCorrenteException e) {
				System.out
						.println("Errore posizionamento iniziale pastori. disconnessione giocatore "
								+ (i + 1));
				giocatori[i].disconnetti();
				stradaSuCuiPosizionare = stradeLibere.get(i);
				pastoreDaPosizionare.posizionaPastore(stradaSuCuiPosizionare);
			}
			interfaccia.posizionatoPastore(giocatori[i], pastoreDaPosizionare,
					stradaSuCuiPosizionare);
			if (numeroGiocatori == 2) {
				stradeLibere.remove(stradaSuCuiPosizionare);
				pastoreDaPosizionare = giocatori[i].getSecondoPastore();
				try {
					iDstradaSuCuiPosizionare = interfaccia
							.posizionamentoInizialePastore(giocatori[i],
									pastoreDaPosizionare, stradeLibere);
					stradaSuCuiPosizionare = gameBoard
							.getStrada(iDstradaSuCuiPosizionare);
					pastoreDaPosizionare
							.posizionaPastore(stradaSuCuiPosizionare);
				} catch (DisconnessoGiocatoreCorrenteException e) {
					System.out
							.println("Errore posizionamento iniziale pastori. disconnessione giocatore "
									+ (i + 1));
					giocatori[i].disconnetti();
					stradaSuCuiPosizionare = stradeLibere.get(i + 1);
					pastoreDaPosizionare
							.posizionaPastore(stradaSuCuiPosizionare);
				}
				interfaccia.posizionatoPastore(giocatori[i],
						pastoreDaPosizionare, stradaSuCuiPosizionare);
			}
		}
	}

	private void faseDiGioco() {
		int turno = 0;
		Giocatore giocatoreCorrente;

		// Fase Centrale di Gioco
		while (cancelliPosizionati < NUM_CANCELLI) {
			for (turno = 0; turno < numeroGiocatori
					&& cancelliPosizionati < NUM_CANCELLI; turno++) {
				giocatoreCorrente = giocatori[turno];
				if (giocatoreCorrente.isConnesso()) {
					movimentoPecoraNera();
					interfaccia.inizioTurno(giocatoreCorrente);
					try {
						turno(giocatoreCorrente);
						interfaccia.fineTurno(giocatoreCorrente);
					} catch (DisconnessoGiocatoreCorrenteException e) {
						giocatoreCorrente.disconnetti();
					}
					gameBoard.crescitaPecorelleOnline(interfaccia);
				}
			}
			movimentoLupo();
			faseMercato();
		}

		// Ultimo giro
		if (turno < numeroGiocatori) {
			interfaccia.faseFinale();
			for (; turno < numeroGiocatori; turno++) {
				giocatoreCorrente = giocatori[turno];
				if (giocatoreCorrente.isConnesso()) {
					movimentoPecoraNera();
					interfaccia.inizioTurno(giocatoreCorrente);
					try {
						turno(giocatoreCorrente);
						interfaccia.fineTurno(giocatoreCorrente);
					} catch (DisconnessoGiocatoreCorrenteException e) {
						giocatoreCorrente.disconnetti();
					}
				}
				gameBoard.crescitaPecorelleOnline(interfaccia);
			}
			movimentoLupo();
		}

	}

	/**
	 * Metodo che gestisce la seuqenza di azioni nel mercato. Vengono gestite le
	 * messe in offerta in modo sequenziale. Viene poi estratto in modo casuale
	 * un giocatore dal quale inizia la vendita in modo ordinato.
	 */
	private void faseMercato() {
		interfaccia.inizioMercato();
		Mercato mercatoAttuale = new Mercato();

		for (int i = 0; i < numeroGiocatori; i++) {
			Giocatore giocatoreAttuale = giocatori[i];
			if (giocatoreAttuale.isConnesso()) {
				try {
					Offerta offertaGiocatore = interfaccia
							.offriMercato(giocatoreAttuale);
					if (offertaGiocatore != null)
						mercatoAttuale.aggiungiOfferta(offertaGiocatore);
				} catch (DisconnessoGiocatoreCorrenteException e) {
					giocatoreAttuale.disconnetti();
				}
			}
		}

		ArrayList<Offerta> listaOfferte = mercatoAttuale.getListaOfferte();
		interfaccia.inizioAcquistiMercato(listaOfferte);

		int primoAcquirente = (int) (Math.random() * numeroGiocatori);
		for (int i = 0; i < numeroGiocatori && listaOfferte.size() > 0; i++) {
			int acquirenteAttuale = (primoAcquirente + i) % numeroGiocatori;
			Giocatore acquirente = giocatori[acquirenteAttuale];
			if (acquirente.isConnesso()) {
				try {
					String accettaOfferta = interfaccia.accettaOfferta(
							acquirente, listaOfferte);
					Offerta offerta = stringToOfferta(accettaOfferta);
					interfaccia.offertaAccettata(offerta, acquirente);
					if (offerta != null)
						mercatoAttuale.accettaOfferta(offerta, acquirente);
				} catch (DisconnessoGiocatoreCorrenteException e) {
					acquirente.disconnetti();
				}
			}
		}

		interfaccia.fineMercato();
	}

	/**
	 * decodifica una strina in una offerta
	 * 
	 * @param input
	 * @return
	 */
	private Offerta stringToOfferta(String input) {
		if (input.contains("-1"))
			return null;
		Terreno terrenoCarta = Terreno.getTerreno(Integer.parseInt(input
				.substring(0, 2)));
		Giocatore venditore = giocatori[Integer.parseInt(input.substring(2, 4))];
		int prezzo = Integer.parseInt(input.substring(4, 6));
		return new Offerta(terrenoCarta, venditore, prezzo);
	}

	/**
	 * Metodo che gestisce il movimento casuale del lupo ad inizio turno.
	 */
	private void movimentoLupo() {
		int tipoPecora = -1;
		boolean haMangiato;
		Lupo lupo = gameBoard.getLupo();
		Agnello pecoraMangiata = null;
		int risultatoDado = (int) (Math.random() * 6) + 1;
		int regioneDestinazione = lupo.movimentoCasuale(risultatoDado);
		if (regioneDestinazione >= 0) {
			pecoraMangiata = lupo.sbrana();
			if (pecoraMangiata instanceof PecoraNera)
				gameBoard.eliminaPecoraNera();
		}
		if (pecoraMangiata == null)
			haMangiato = false;
		else {
			haMangiato = true;
			tipoPecora = pecoraMangiata.getTipo();
		}
		interfaccia.mossoLupo(regioneDestinazione, haMangiato, tipoPecora);
	}

	/**
	 * Metodo che gestisce il movimento casuale della pecora nera ad inizio
	 * turno. Se la pecora nera è stata abbattuta non viene eseguito nulla.
	 * 
	 * @param giocatoreCorrente
	 *            Rappresenta quale giocatore deve tirare il dado per spostare
	 *            la pecora.
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	private void movimentoPecoraNera() {
		PecoraNera pecoraNera = gameBoard.getPecoraNera();
		if (pecoraNera != null) {
			int risultatoDado = (int) (Math.random() * 6) + 1;
			int destinazione = pecoraNera.movimentoCasuale(risultatoDado);
			if (destinazione>=0){
				Regione regioneDestinazione = gameBoard.getRegione(destinazione);
				interfaccia.spostaPecoraNera(regioneDestinazione);
			}
		}
	}

	/**
	 * Metodo che gestisce le mosse del turno eseguibili dal giocatore. Se il
	 * giocatore è bloccato per mancanza di soldi o di mosse pastore, salta il
	 * turno.
	 * 
	 * @param giocatoreCorrente
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	private void turno(Giocatore giocatoreCorrente)
			throws DisconnessoGiocatoreCorrenteException {
		int[] mosseFatte = { -1, -1, -1 };
		boolean[] mosseAbilitate;

		if (isAbilitatoTurno(giocatoreCorrente)) {

			Pastore pastoreScelto;
			if (numeroGiocatori == 2) {
				pastoreScelto = sceltaPastore(giocatoreCorrente);
			} else
				pastoreScelto = giocatoreCorrente.getPastore();

			// 0: Movimento Pastore, 1: Spostamento Pecora, 2: Acquisto Carta,
			// 3: Accoppiamento, 4: Abbattimento
			for (int numMossa = 0; numMossa < NUM_MOSSE_TURNO; numMossa++) {
				mosseAbilitate = abilitaMosse(numMossa, mosseFatte,
						pastoreScelto, giocatoreCorrente);
				if (!mosseAbilitate[0] && !pastoreMosso(mosseFatte)) {
					interfaccia.pastoreBloccato();
					break;
				}
				int mossaScelta = interfaccia.sceltaMossa(mosseAbilitate,
						giocatoreCorrente);
				mosseFatte[numMossa] = mossaScelta;
				mossa(mossaScelta, pastoreScelto, giocatoreCorrente);
			}
		} else {
			interfaccia.giocatoreSaltaTurno(giocatoreCorrente);
		}

	}

	/**
	 * Metodo che rileva se il pastore è stato mosso almeno una volta.
	 * 
	 * @param mosseFatte
	 * @return
	 */
	private boolean pastoreMosso(int[] mosseFatte) {
		if (mosseFatte[0] != 0 && mosseFatte[1] != 0)
			return false;
		return true;
	}

	/**
	 * Metodo che in seguito a un intero, permette di eseguire la mossa relativa
	 * al numero passato. Le mosse necessitano di sapere che pastore viene usato
	 * e che giocatore esegue la mossa.
	 * 
	 * @param mossaScelta
	 * @param pastoreScelto
	 * @param giocatoreCorrente
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	private void mossa(int mossaScelta, Pastore pastoreScelto,
			Giocatore giocatoreCorrente)
			throws DisconnessoGiocatoreCorrenteException {
		switch (mossaScelta) {
		case 0:
			mossaMovimentoPastore(giocatoreCorrente, pastoreScelto);
			break;
		case 1:
			mossaSpostamentoPecora(pastoreScelto);
			break;
		case 2:
			mossaAcquistoCarta(giocatoreCorrente, pastoreScelto);
			break;
		case 3:
			mossaAccoppiamento(pastoreScelto);
			break;
		case 4:
			mossaAbbattimento(pastoreScelto, giocatoreCorrente);
			break;
		}
		return;

	}

	/**
	 * Metodo relativo all'abbattimeno di una pecorella. Le regole
	 * dell'abbattimento sono come da regolamento.
	 * 
	 * @param pastoreScelto
	 *            pastore che esegue l'abbattimento.
	 * @param giocatoreCorrente
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	private void mossaAbbattimento(Pastore pastoreScelto,
			Giocatore giocatoreCorrente)
			throws DisconnessoGiocatoreCorrenteException {
		if (interfaccia.tiroDadoAbbattimento(pastoreScelto, giocatoreCorrente) == 1) {
			Strada stradaPastore = pastoreScelto.getStrada();
			ArrayList<Giocatore> giocatoriDaPagare = giocatoriDaPagarePerSilenzio(
					stradaPastore, giocatoreCorrente);
			Regione regionePecoraNera;
			Regione[] regioniAdiacenti = stradaPastore.getRegioniAdicaenti();
			PecoraNera pecoraNera = gameBoard.getPecoraNera();
			if (pecoraNera == null)
				regionePecoraNera = null;
			else
				regionePecoraNera = pecoraNera.getRegioneAttuale();

			int regioneScelta = interfaccia.sceltaRegioneAbbattimento(
					regioniAdiacenti, regionePecoraNera, giocatoreCorrente);
			Regione regioneAbbattimento = gameBoard.getRegione(regioneScelta);

			boolean presenzaPecoraNera = false;
			if (pecoraNera != null)
				presenzaPecoraNera = (regioneAbbattimento == regionePecoraNera);
			int animaleDaAbbattere = interfaccia.sceltaAnimaleDaAbbattere(
					regioneAbbattimento, presenzaPecoraNera, giocatoreCorrente);

			abbattiPecora(animaleDaAbbattere, regioneAbbattimento);
			for (Giocatore x : giocatoriDaPagare)
				if (interfaccia.tiroDadoGiocatore(x) >= 5) {
					pagaSilenzio(giocatoreCorrente, x);
				} else {
					interfaccia.nonPagaSilenzio(x);
				}
		}
	}

	/**
	 * Metodo che gestisce il pagamento del silenzio dopo l'abbattimento.
	 * 
	 * @param giocatoreCorrente
	 * @param giocatoreDaPagare
	 */
	private void pagaSilenzio(Giocatore giocatoreCorrente,
			Giocatore giocatoreDaPagare) {
		giocatoreCorrente.pagamento(2);
		giocatoreDaPagare.aumentaDanari(2);
		interfaccia.pagaSilenzio(giocatoreCorrente, giocatoreDaPagare);
	}

	/**
	 * Metodo che elimina un dato animale da una data regione.
	 * 
	 * @param animaleDaAbbattere
	 * @param regioneAbbattimento
	 */
	private void abbattiPecora(int animaleDaAbbattere,
			Regione regioneAbbattimento) {
		switch (animaleDaAbbattere) {
		case 0:
			regioneAbbattimento.prelevaAgnello();
			break;
		case 1:
			regioneAbbattimento.prelevaPecoraFemmina();
			break;
		case 2:
			regioneAbbattimento.prelevaMontone();
			break;
		case 3:
			gameBoard.eliminaPecoraNera();
			break;
		}
		interfaccia.abbattimento(animaleDaAbbattere, regioneAbbattimento);

	}

	/**
	 * Metodo che fornisce una lista di giocatori che rappresentano potenziali
	 * pagamenti di silenzio.
	 * 
	 * @param stradaPastore
	 *            strada in cui si trova il pastore che esegue l'abbattimento.
	 * @param giocatoreCorrente
	 *            giocatore che possiede il pastore.
	 * @return
	 */
	private ArrayList<Giocatore> giocatoriDaPagarePerSilenzio(
			Strada stradaPastore, Giocatore giocatoreCorrente) {
		ArrayList<Giocatore> giocatoriDaPagare = new ArrayList<Giocatore>();
		ArrayList<Strada> stradeVicine = stradaPastore.getStradeVicine();
		for (Strada x : stradeVicine)
			if (x.isOccupata() && !x.isRecintata())
				for (Pastore pastoreVicino : gameBoard.getPastori())
					if (pastoreVicino.getStrada() == x
							&& giocatoreCorrente != giocatori[pastoreVicino
									.getGiocatore()])
						giocatoriDaPagare.add(giocatori[pastoreVicino
								.getGiocatore()]);
		return giocatoriDaPagare;
	}

	/**
	 * metodo che esegue l'accoppiamento tra due pecore.
	 * 
	 * @param pastoreScelto
	 *            Pastore che deve effettuare l'accoppiamento.
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	private void mossaAccoppiamento(Pastore pastoreScelto)
			throws DisconnessoGiocatoreCorrenteException {
		Regione[] regioniAccoppiamento = pastoreScelto.getStrada()
				.getRegioniAdicaenti();
		int regioneScelta = interfaccia.sceltaRegioneAccoppiamento(
				regioniAccoppiamento, giocatori[pastoreScelto.getGiocatore()]);
		Regione regioneAccoppiamento = gameBoard.getRegione(regioneScelta);
		regioneAccoppiamento.nascitaAgnello();
		interfaccia.accoppiamento(regioneAccoppiamento);
	}

	/**
	 * Metodo che gestisce l'acquisto di carte di terreni adiancenti a un dato
	 * pastore.
	 * 
	 * @param giocatoreCorrente
	 * @param pastore
	 *            Pastore che si sta usando in un dato momento.
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	private void mossaAcquistoCarta(Giocatore giocatoreCorrente, Pastore pastore)
			throws DisconnessoGiocatoreCorrenteException {
		int[] carteAcquistabili = { -1, -1, -1, -1, -1, -1 };
		Regione[] regioniAdiacenti = pastore.getStrada().getRegioniAdicaenti();
		int tipoTerrenoVicino = regioniAdiacenti[0].getTerreno().ordinal();
		if (tipoTerrenoVicino < 6)
			carteAcquistabili[tipoTerrenoVicino] = carteDisponibili[tipoTerrenoVicino];
		tipoTerrenoVicino = regioniAdiacenti[1].getTerreno().ordinal();
		if (tipoTerrenoVicino < 6)
			carteAcquistabili[tipoTerrenoVicino] = carteDisponibili[tipoTerrenoVicino];
		int cartaScelta = interfaccia.sceltaCarta(
				giocatoreCorrente.getDanari(), carteAcquistabili, NUM_CARTE,
				giocatoreCorrente);
		Terreno tipoCarta = Terreno.getTerreno(cartaScelta);
		int prezzo = NUM_CARTE - carteDisponibili[cartaScelta];
		AcquistoCarta(giocatoreCorrente, tipoCarta, prezzo);
	}

	public void AcquistoCarta(Giocatore giocatore, Terreno tipoCarta, int Prezzo)
			throws DisconnessoGiocatoreCorrenteException {
		carteDisponibili[tipoCarta.ordinal()] -= 1;
		giocatore.acquistoCarta(tipoCarta, Prezzo);
		interfaccia.acquistoCarta(tipoCarta, giocatore, Prezzo);
	}

	/**
	 * Metodo che gestisce il funzionamento di spostamento di una pecora.
	 * 
	 * @param pastoreScelto
	 *            Il pastore che va ad effettuare lo spostamento
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	private void mossaSpostamentoPecora(Pastore pastoreScelto)
			throws DisconnessoGiocatoreCorrenteException {
		Strada stradaPastore = pastoreScelto.getStrada();
		Regione[] regioniAdiacenti = stradaPastore.getRegioniAdicaenti();
		Regione regionePecoraNera;
		PecoraNera pecoraNera = gameBoard.getPecoraNera();
		if (pecoraNera == null)
			regionePecoraNera = null;
		else
			regionePecoraNera = pecoraNera.getRegioneAttuale();

		int regioneScelta = interfaccia.sceltaRegioneSpostamento(
				regioniAdiacenti, regionePecoraNera,
				giocatori[pastoreScelto.getGiocatore()]);
		Regione regioneSpostamento = gameBoard.getRegione(regioneScelta);
		Regione regioneDiDestinazione = stradaPastore
				.getRegioneOpposta(regioneSpostamento);

		boolean presenzaPecoraNera = false;
		if (pecoraNera != null)
			if (regioneSpostamento == regionePecoraNera)
				presenzaPecoraNera = true;
		int pecoraScelta = interfaccia.sceltaPecoraDaSpostare(
				regioneSpostamento, presenzaPecoraNera,
				giocatori[pastoreScelto.getGiocatore()]);
		spostaPecora(pecoraScelta, regioneSpostamento, regioneDiDestinazione);
		interfaccia.spostamentoPecora(pecoraScelta, regioneSpostamento,
				regioneDiDestinazione);
	}

	/**
	 * Metodo che esegue l'effettivo spostamento di una pecora da una regione ad
	 * un'altra
	 * 
	 * @param pecoraScelta
	 *            Intero che rappresenta il tipo di pecora
	 * @param regioneSpostamento
	 * @param regioneDiDestinazione
	 */
	private void spostaPecora(int pecoraScelta, Regione regioneSpostamento,
			Regione regioneDiDestinazione) {
		Agnello pecoraDaSpostare;

		switch (pecoraScelta) {
		case 0:
			pecoraDaSpostare = regioneSpostamento.prelevaAgnello();
			break;
		case 1:
			pecoraDaSpostare = regioneSpostamento.prelevaPecoraFemmina();
			break;
		case 2:
			pecoraDaSpostare = regioneSpostamento.prelevaMontone();
			break;
		case 3:
			pecoraDaSpostare = regioneSpostamento.prelevaPecoraNera();
			((PecoraNera) pecoraDaSpostare).muoviti(regioneDiDestinazione);
			return;
		default:
			pecoraDaSpostare = null;
		}

		regioneDiDestinazione.posizionaPecora(pecoraDaSpostare);

	}

	/**
	 * Metodo che gestisce il movimento di un pastore. Vengono chiamati dei
	 * sottometodi per definire le strade.
	 * 
	 * @param giocatoreCorrente
	 * @param pastoreScelto
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	private void mossaMovimentoPastore(Giocatore giocatoreCorrente,
			Pastore pastoreScelto) throws DisconnessoGiocatoreCorrenteException {
		ArrayList<Strada> stradeLibereLontane = gameBoard.getStradeLibere();
		Strada stradaPastore = pastoreScelto.getStrada();
		ArrayList<Strada> stradeLibereVicine = stradaPastore
				.getStradeVicineLibere();
		for (Strada x : stradeLibereVicine)
			stradeLibereLontane.remove(x);
		int danari = giocatoreCorrente.getDanari();

		int stradaScelta = interfaccia.sceltaStradaPastore(danari,
				stradeLibereLontane, stradeLibereVicine, giocatoreCorrente);
		Strada nuovaStradaPastore = gameBoard.getStrada(stradaScelta);
		boolean pagamento = false;

		if (!stradeLibereVicine.contains(nuovaStradaPastore)) {
			giocatoreCorrente.pagamento(1);
			pagamento = true;
		}
		pastoreScelto.spostaPastore(nuovaStradaPastore);
		interfaccia.movimentoPastore(nuovaStradaPastore, pastoreScelto,
				giocatoreCorrente, pagamento);
		cancelliPosizionati++;
	}

	/**
	 * Metodo che restituisce un booleano rappresentate l'abilitazione di un
	 * giocatore ad effettauare mosse. In caso di risposta false il giocatore
	 * dovr� saltare il turno.
	 * 
	 * @param giocatoreCorrente
	 * @return
	 */
	private boolean isAbilitatoTurno(Giocatore giocatoreCorrente) {
		if (numeroGiocatori > 2)
			return isAbilitatoPastore(giocatoreCorrente,
					giocatoreCorrente.getPastore());

		return (isAbilitatoPastore(giocatoreCorrente,
				giocatoreCorrente.getPastore()) && isAbilitatoPastore(
				giocatoreCorrente, giocatoreCorrente.getSecondoPastore()));
	}

	/**
	 * Metodo che analizza quali mosse è possibile eseguire. Viene restituito un
	 * vettore di boolean che rappresentano in ordine di enum la fattibilit� di
	 * una determinata mossa.
	 * 
	 * @param numMossa
	 *            rappresenta il numero di mossa in un turno ( da 1 a 3 )
	 * @param mosseFatte
	 *            Vettore di interi ordinato secondo la enum di mosse che
	 *            rappresenta quali e quante mosse sono state fatte
	 * @param pastore
	 *            Pastore che si sta controllando
	 * @param giocatoreCorrente
	 * @return
	 */
	private boolean[] abilitaMosse(int numMossa, int[] mosseFatte,
			Pastore pastore, Giocatore giocatoreCorrente) {
		boolean[] mosseAbilitate = { true, true, true, true, true };

		mosseAbilitate[0] = isAbilitatoPastore(giocatoreCorrente, pastore);

		// Se non ho ancora mosso il pastore, posso fare solo quello.
		if (numMossa == 2 && !pastoreMosso(mosseFatte)) {
			for (int i = 1; i < NUM_MOSSE; i++)
				mosseAbilitate[i] = false;
			return mosseAbilitate;
		}

		Regione[] regioniLaterali = pastore.getStrada().getRegioniAdicaenti();

		mosseAbilitate[1] = isAbilitatoSpostamentoPecora(regioniLaterali);

		mosseAbilitate[2] = isAbilitatoAcquistoCarta(giocatoreCorrente, pastore);

		mosseAbilitate[3] = isAbilitatoAccoppiamento(regioniLaterali);

		mosseAbilitate[4] = isAbilitatoAbbattimento(pastore, giocatoreCorrente);

		// Disabilito la mossa appena fatta
		if (numMossa > 0)
			if (mosseFatte[numMossa - 1] != 0)
				mosseAbilitate[mosseFatte[numMossa - 1]] = false;

		return mosseAbilitate;

	}

	/**
	 * Metodo che verifica l'effettiva possibilit� di effettuare un abbattimento
	 * con un dato pastore
	 * 
	 * @param pastore
	 * @param giocatoreCorrente
	 * @return
	 */
	private boolean isAbilitatoAbbattimento(Pastore pastore,
			Giocatore giocatoreCorrente) {
		Regione[] regioniLaterali = pastore.getStrada().getRegioniAdicaenti();
		ArrayList<Strada> stradeVicine = pastore.getStrada().getStradeVicine();

		if (regioniLaterali[0].getNumeroPecore()<=0 && regioniLaterali[1].getNumeroPecore()<=0)
			return false;

		int sommaPerSilenzio = 0;
		for (Strada x : stradeVicine)
			if (x.isOccupata() && !x.isRecintata())
				sommaPerSilenzio += 2;
		if (sommaPerSilenzio > giocatoreCorrente.getDanari())
			return false;
		return true;
	}

	/**
	 * Metodo che verifica l'effettiva possibilit� di effettuare un
	 * accopppiamento in un insieme di regioni ( quelle che circondano il
	 * pastore )
	 * 
	 * @param regioniLaterali
	 * @return
	 */
	private boolean isAbilitatoAccoppiamento(Regione[] regioniLaterali) {
		if (!regioniLaterali[0].accoppiamentoPossibile()
				&& !regioniLaterali[1].accoppiamentoPossibile())
			return false;
		return true;
	}

	/**
	 * Metodo che verifica l'effettiva possibilit� di effettuare un acquisato di
	 * carte.
	 * 
	 * @param giocatoreCorrente
	 * @return
	 */
	private boolean isAbilitatoAcquistoCarta(Giocatore giocatoreCorrente, Pastore pastore) {
		Regione[] regioniAdiacenti = pastore.getStrada().getRegioniAdicaenti();
		int terreno1 = regioniAdiacenti[0].getTerreno().ordinal();
		int terreno2 = regioniAdiacenti[1].getTerreno().ordinal();
		if(terreno1<6)
			if ((giocatoreCorrente.getDanari() >= (NUM_CARTE - carteDisponibili[terreno1]))	&& (carteDisponibili[terreno1] > 0))
				return true;
		if(terreno2<6)
			if ((giocatoreCorrente.getDanari() >= (NUM_CARTE - carteDisponibili[terreno2]))	&& (carteDisponibili[terreno2] > 0))
				return true;
		return false;
	}

	/**
	 * Metodo che verifica l'effettiva possibilit� di effettuare uno spostamento
	 * in un insieme di regioni ( quelle che circondano il pastore )
	 * 
	 * @param regioniLaterali
	 * @return
	 */
	private boolean isAbilitatoSpostamentoPecora(Regione[] regioniLaterali) {
		if (regioniLaterali[0].getNumeroPecore() == 0
				&& regioniLaterali[1].getNumeroPecore() == 0)
			return false;
		return true;
	}

	/**
	 * Nel caso in cui i giocatori sono 2, questo metodo ad inizio turno
	 * permette di scegliere uno dei due pastori per il resto del turno
	 * 
	 * @param giocatoreCorrente
	 * @return
	 * @throws DisconnessoGiocatoreCorrenteException
	 */
	private Pastore sceltaPastore(Giocatore giocatoreCorrente)
			throws DisconnessoGiocatoreCorrenteException {
		boolean pastore1Abilitato, pastore2Abilitato;
		Pastore pastore1 = giocatoreCorrente.getPastore();
		Pastore pastore2 = giocatoreCorrente.getSecondoPastore();
		pastore1Abilitato = isAbilitatoPastore(giocatoreCorrente, pastore1);
		pastore2Abilitato = isAbilitatoPastore(giocatoreCorrente, pastore2);

		int pastoreScelto = interfaccia.sceltaPastore(giocatoreCorrente,
				pastore1Abilitato, pastore2Abilitato);

		if (pastoreScelto == 1)
			return pastore1;
		return pastore2;
	}

	/**
	 * Metodo che restituisce un booleano rappresentate la possibilit� di un
	 * pastore di poter effetuare mosse.
	 * 
	 * @param giocatoreCorrente
	 * @param pastore
	 * @return
	 */
	private boolean isAbilitatoPastore(Giocatore giocatoreCorrente,
			Pastore pastore) {
		int soldi = giocatoreCorrente.getDanari();
		Strada stradaPastore = pastore.getStrada();
		if (stradaPastore.getStradeVicineLibere() == null && soldi <= 0)
			return false;
		return true;
	}

	/**
	 * Metodo che gestisce la fine della partita. Viene calcolato il vincitore e
	 * mostrato a schermo.
	 */
	private void finePartita() {
		interfaccia.finePartita();
		int[] terreni = valutazioneTerreni();
		vincitore = individuaVincitore(giocatori, terreni);
	}

	/**
	 * Metodo che calcola i moltiplicatori di valore del terreno a fine partita.
	 * Il metodo servirà poi a individuare il vincitore.
	 * 
	 * @param terreni
	 *            la lista dei terreni da valutare.
	 */
	public int[] valutazioneTerreni() {
		int[] terreni = new int[NUM_TERRENI];
		Regione[] regioni = gameBoard.getRegioni();
		for (int i = 1; i < regioni.length; i++) {
			int indiceTerreno = regioni[i].getTerreno().ordinal();
			int numeroPecore = regioni[i].getNumeroPecore();
			terreni[indiceTerreno] += numeroPecore;
		}
		PecoraNera pecoraNera = gameBoard.getPecoraNera();
		int tipoTerrenoPecoraNera;
		if (pecoraNera != null) {
			tipoTerrenoPecoraNera = pecoraNera.getRegioneAttuale().getTerreno()
					.ordinal();
			if (tipoTerrenoPecoraNera < 6)
				terreni[tipoTerrenoPecoraNera] += 1;
			// Aumenta solo di 1 perch� 1 viene già aggiunto prima (PecoraNera è
			// in numero pecore).
		}

		return terreni;
	}

	/**
	 * Metodo che calcola il punteggio finale di tutti i giocatori. Viene
	 * restituito quello con punteggio piu alto
	 * 
	 * @param giocatori
	 *            Lista dei giocatori.
	 * @param terreni
	 *            Lista dei terreni.
	 * @return il vincitore.
	 */

	public Giocatore individuaVincitore(Giocatore[] giocatori, int[] terreni) {
		int vincitore = 0, puntiMax = 0;

		for (int j = 0; j < numeroGiocatori; j++) {
			int[] cartePossedute = giocatori[j].getCarte();
			for (int i = 0; i < NUM_TERRENI; i++)
				giocatori[j].aumentaDanari(cartePossedute[i] * terreni[i]);
			int danariPosseduti = giocatori[j].getDanari();
			if (danariPosseduti > puntiMax) {
				vincitore = j;
				puntiMax = danariPosseduti;
			}
		}

		return giocatori[vincitore];

	}

	/**
	 * Metodo per prendere la gameborad.
	 * @return
	 */
	public GameBoard getGameBoard() {
		return gameBoard;
	}

	/**
	 * Metodo per prendere le carte disponibili.
	 * @return
	 */
	public int[] getCarteDisponibili() {
		return carteDisponibili;
	}

	/**
	 * Metodo per prendere il giocatore ad un determinato indice.
	 * @param indice
	 * @return
	 */
	public Giocatore getGiocatore(int indice) {
		return giocatori[indice];
	}

	/**
	 * Metodo per prendere il numero di cancelli posizionati.
	 * @return
	 */
	public Integer getCancelliPosizionati() {
		return cancelliPosizionati;
	}

	/**
	 * Metodo per prendere tutti i giocatori.
	 * @return
	 */
	public Giocatore[] getGiocatori() {
		return giocatori;
	}

}
