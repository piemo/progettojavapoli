package view.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import view.InterfacciaPartitaUtente;
import model.GameBoard;
import model.Lupo;
import model.Pastore;
import model.PecoraNera;
import model.Regione;
import model.Strada;
import model.Terreno;
import controller.Giocatore;
import controller.Mosse;
import controller.Offerta;

@SuppressWarnings("resource")
public class ConsoleView extends InterfacciaPartitaUtente {

	@Override
	public void mostraCartaIniziale(Giocatore giocatoreCorrente) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			System.out.println(giocatoreCorrente
					+ ", premi INVIO per vedere la tua carta segreta.");
			System.out.println("");
			br.readLine();
			int[] carte = giocatoreCorrente.getCarte();
			for (int i = 0; i < carte.length; i++)
				if (carte[i] > 0) {
					System.out.println("Hai pescato una carta "
							+ Terreno.getTerreno(i));
					System.out.println("");
					break;
				}
			System.out.println("Premi INVIO per continuare.");
			System.out.print("");
			br.readLine();
			System.out
					.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int posizionamentoInizialePastore(Giocatore giocatoreCorrente,
			Pastore pastoreDaPosizionare, ArrayList<Strada> stradeLibere) {
		System.out.print("\n\n" + giocatoreCorrente + " posiziona il tuo "
				+ pastoreDaPosizionare + ".\n\n");
		mostraStradeLibere(stradeLibere);
		ArrayList<Integer> iDStradeLibere = new ArrayList<Integer>();
		for (Strada x : stradeLibere)
			iDStradeLibere.add(x.getID());
		int stradaScelta;
		do {
			System.out.print("Scegli una strada libera: \n");
			stradaScelta = inputNumero();
			if (!iDStradeLibere.contains(stradaScelta))
				System.out.print("ATTENZIONE: Scelta non valida! \n\n");
		} while (!iDStradeLibere.contains(stradaScelta));

		return stradaScelta;
	}

	/**
	 * Questo metodo mostra agli uteni la lista delle strade attualmente libere.
	 * 
	 * @param stradeLibere
	 */
	private void mostraStradeLibere(ArrayList<Strada> stradeLibere) {
		System.out.print("Queste sono le strade libere: \n\n");
		for (int i = 0; i < stradeLibere.size(); i++) {
			System.out.print(stradeLibere.get(i) + " ");
			if ((i + 1) % 10 == 0)
				System.out.print("\n");
		}
		System.out.print("\n");
	}

	@Override
	public int inserimentoNumeroGiocatori() {
		int numeroGiocatori;

		do {
			System.out.print("Inserire un numero di giocatori tra 2 e 4: \n");
			numeroGiocatori = inputNumero();
		} while (numeroGiocatori < 2 || numeroGiocatori > 4);

		return numeroGiocatori;
	}

	@Override
	public String inserimentoNomeGiocatore(int indiceGiocatore,
			Giocatore[] giocatori) {
		Scanner in = new Scanner(System.in);
		boolean valido;
		String nome;

		do {
			valido = true;
			System.out.print("Inserire nome Giocatore " + (indiceGiocatore + 1)
					+ ": \n\n");
			nome = in.next().toUpperCase();
			if (nome.length() > 0) {
				for (int i = 0; i < giocatori.length && valido; i++)
					if (giocatori[i] != null)
						if (giocatori[i].toString().equals(nome)) {
							valido = false;
							System.out
									.print("ATTENZIONE: Nome giè in uso!\n\n");
						}
			} else {
				valido = false;
				System.out
						.print("ATTENZIONE: Inserire almeno un carattere!\n\n");
			}
		} while (!valido);

		return nome;
	}

	@Override
	public void createAndShowMap(GameBoard gameBoard, int[] carteDisponibili,
			Giocatore[] giocatori) {
		ArrayList<Strada> stradeRecintate = gameBoard.getStradeRecintate();
		ArrayList<Pastore> pastori = gameBoard.getPastori();
		PecoraNera pecoraNera = gameBoard.getPecoraNera();
		Lupo lupo = gameBoard.getLupo();
		Regione[] regioni = gameBoard.getRegioni();

		System.out.print("\n\n");

		for (int i = 0; i < regioni.length; i++)
			mostraPecoreDellaRegione(regioni[i]);
		System.out.print("\n");

		if (stradeRecintate.size() > 0) {
			System.out.print("Sono state recintate le strade:\n");
			for (Strada x : stradeRecintate)
				System.out.println(x);
		} else
			System.out.print("Nessun cancello è stato ancora posizionato.\n");
		System.out.print("\n");

		if (pastori.get(0).getStrada() == null) {
			System.out.print("Pastori non ancora posizionati.\n");
		} else {
			for (int i = 0; i < pastori.size(); i++) {
				Strada stradaPastore = pastori.get(i).getStrada();
				System.out.print(pastori.get(i) + " si trova su "
						+ stradaPastore + ".\n");
			}
		}

		if (pecoraNera != null)
			System.out.print("\n");
		System.out.print(pecoraNera + " si trova in "
				+ pecoraNera.getRegioneAttuale() + ".\n");

		System.out.print("\n");
		System.out.print(lupo + " si trova in " + lupo.getRegioneAttuale()
				+ ".\n\n");

		System.out.print("\n");
		System.out.print("Carte Disponibili:\n");
		for (int i = 0; i < carteDisponibili.length; i++)
			System.out.println("-" + Terreno.getTerreno(i) + ": "
					+ carteDisponibili[i]);

		System.out.print("\n");
		System.out.print("Danari dei giocatori:\n");
		for (int i = 0; i < giocatori.length; i++)
			System.out.println(giocatori[i] + ": " + giocatori[i].getDanari());
		System.out.print("\n\n");
	}

	@Override
	public void pastoreBloccato() {
		System.out.print("ATTENZIONE il pastore è bloccato!\n");
	}

	@Override
	public int sceltaMossa(boolean[] mosseAbilitate, Giocatore giocatoreCorrente) {
		int scelta;
		boolean valida;
		System.out.print("Scegli una mossa:\n\n");
		for (int i = 0; i < mosseAbilitate.length; i++) {
			if (mosseAbilitate[i] == true)
				System.out.println((i + 1) + ". " + Mosse.getMossa(i));
			else
				System.out.println((i + 1) + ".| " + Mosse.getMossa(i)
						+ "| NON DISPONIBILE");
		}
		System.out.print("\n\n");
		do {
			valida = true;
			scelta = (inputNumero() - 1);
			if ((scelta < 0 || scelta >= mosseAbilitate.length)) {
				valida = false;
				System.out.print("ATTENZIONE: Scelta NON valida! \n\n");
			} else if (mosseAbilitate[scelta] == false) {
				valida = false;
				System.out.print("ATTENZIONE: Mossa NON valida! \n\n");
			}
		} while (!valida);

		return scelta;
	}

	@Override
	public void giocatoreSaltaTurno(Giocatore giocatoreCorrente) {
		System.out.print("ATTENZIONE! " + giocatoreCorrente
				+ " non puoi effettuare il movimento del pastore:"
				+ "salti il turno!\n");
	}

	@Override
	public int sceltaPastore(Giocatore giocatoreCorrente,
			boolean pastore1Abilitato, boolean pastore2Abilitato) {
		int scelta;
		if (!pastore1Abilitato || !pastore2Abilitato) {
			if (!pastore2Abilitato) {
				System.out.print("Puoi usare soltanto il tuo primo pastore!"
						+ "\n Premi INVIO per continuare.\n");
				scelta = 1;
			} else {
				System.out.print("Puoi usare soltanto il tuo secondo pastore!"
						+ "\n Premi INVIO per continuare.\n");
				scelta = 2;
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			try {
				br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			System.out.print("Scegli quale pastore usare per questo turno:\n"
					+ "1. Primo Pastore, 2.Secondo Pastore\n");
			do {
				scelta = inputNumero();
				if (scelta != 1 && scelta != 2)
					System.out.print("ATTENZIONE: Scelta non valida!\n");
			} while (scelta != 1 && scelta != 2);
		}

		return scelta;
	}

	/**
	 * Questo metodo avvisa il giocatore corrente che inizia il suo turno e
	 * richiede una sua interazine per proseguire.
	 * 
	 * @param giocatoreCorrente
	 */
	public void inizioTurno(Giocatore giocatoreCorrente) {
		System.out
				.print("\n\nInizia il turno di " + giocatoreCorrente + "\n\n");
	}

	/**
	 * Il metodo mostra i dati del giocatore corrente: le sue carte, i suoi
	 * danari, e la posiizone del/dei pastore/i.
	 * 
	 * @param giocatoreCorrente
	 */
	private void mostraDatiGiocatore(Giocatore giocatoreCorrente) {
		System.out.print(giocatoreCorrente + "\n\n");
		mostraDanari(giocatoreCorrente);
		mostraCarte(giocatoreCorrente);
		mostraPastori(giocatoreCorrente);
	}

	/**
	 * Il metodo mostra la posizione dei/del pastori/e del giocatore corrente.
	 * 
	 * @param giocatoreCorrente
	 */
	private void mostraPastori(Giocatore giocatoreCorrente) {
		Pastore pastore1 = giocatoreCorrente.getPastore();
		System.out.print(pastore1 + " su strada " + pastore1.getStrada()
				+ "\n\n\n");
		Pastore pastore2 = giocatoreCorrente.getSecondoPastore();
		if (pastore2 != null)
			System.out.print(pastore2 + " su strada " + pastore2.getStrada()
					+ "\n\n\n");
	}

	private void mostraDanari(Giocatore giocatoreCorrente) {
		int danari = giocatoreCorrente.getDanari();
		System.out.print("DANARI: " + danari + ".\n\n");
	}

	public void mostraCarte(Giocatore giocatoreCorrente) {
		int[] carte = giocatoreCorrente.getCarte();
		System.out.println("CARTE: ");
		System.out.println("1-> " + carte[0] + " carte del tipo AGRICOLO.");
		System.out.println("2-> " + carte[1] + " carte del tipo ARIDO.");
		System.out.println("3-> " + carte[2] + " carte del tipo FIUME.");
		System.out.println("4-> " + carte[3] + " carte del tipo FORESTA.");
		System.out.println("5-> " + carte[4] + " carte del tipo MONTAGNA.");
		System.out.println("6-> " + carte[5] + " carte del tipo PRATO.");
		System.out.print("\n\n");
	}

	@Override
	public void fineTurno(Giocatore giocatoreCorrente) {
		System.out.print(giocatoreCorrente + ", FINE DEL TUO TURNO!\n\n");
	}

	@Override
	public int movimentoPecoraNera(Giocatore giocatoreCorrente) {
		System.out.print("\n\nMOVIMENTO DELLA PECORA NERA\n\n\n");
		return tiroDadoGiocatore(giocatoreCorrente);
	}

	@Override
	public void spostaPecoraNera(Regione regioneDestinazione) {
		if (regioneDestinazione == null)
			System.out.print("La pecora nera NON si muove\n\n");
		else
			System.out.print("La pecora nera si sposta nella "
					+ regioneDestinazione + "\n\n");
	}

	@Override
	public int sceltaStradaPastore(int danari,
			ArrayList<Strada> stradeLibereLontane,
			ArrayList<Strada> stradeLibereVicine, Giocatore giocatore) {
		int stradaScelta;
		boolean sceltaValida;
		ArrayList<Integer> iDStradePossibili = new ArrayList<Integer>();

		System.out.print("\n\nMOVIMENTO DEL PASTORE!\n\n\n");
		if (stradeLibereVicine != null) {
			System.out
					.print("Puoi muovere il pastore gratutitamente in una delle strade libere vicine:\n");
			for (Strada x : stradeLibereVicine) {
				System.out.println(x);
				iDStradePossibili.add(x.getID());
			}
		}
		if (danari > 0) {
			System.out
					.print("\nPuoi muovere il pastore pagando 1 danaro in una delle strade libere lontane:\n");
			for (Strada x : stradeLibereLontane) {
				System.out.println(x);
				iDStradePossibili.add(x.getID());
			}
		}
		do {
			sceltaValida = true;
			stradaScelta = inputNumero();
			if (!iDStradePossibili.contains(stradaScelta)) {
				System.out.print("ATTENZIONE: Scelta NON valida!\n\n");
				sceltaValida = false;
			}
		} while (!sceltaValida);

		return stradaScelta;
	}

	@Override
	public void movimentoPastore(Strada nuovaStradaPastore,
			Pastore pastoreMosso, boolean haPagato) {
		if (haPagato)
			System.out.print(pastoreMosso + " si muove su "
					+ nuovaStradaPastore + " Pagando 1 danaro!\n\n");
		else {
			System.out.print(pastoreMosso + " si muove su "
					+ nuovaStradaPastore + " Senza pagare!\n\n");

		}
	}

	@Override
	public int sceltaCarta(int danari, int[] carteDisponibili, int numCarte,
			Giocatore giocatoreCorrente) {
		int cartaScelta;
		boolean sceltaValida;
		System.out.print("\n\nACQUISTO CARTA TERRENO!\n\n\n");
		System.out.print("Carte Disponibili:\n");
		for (int i = 0; i < carteDisponibili.length; i++) {
			Terreno tipo = Terreno.getTerreno(i);
			int prezzo = numCarte - carteDisponibili[i];
			if (carteDisponibili[i] < 0)
				System.out
						.println((i + 1) + ". " + tipo + " NON ACQUISTABILI.");
			else if (carteDisponibili[i] == 0)
				System.out.println((i + 1) + ". " + tipo + " ESAURITE.");
			else
				System.out.println((i + 1) + ". " + tipo + " x"
						+ carteDisponibili[i] + " ==> Prezzo: " + prezzo);
		}

		do {
			sceltaValida = true;
			cartaScelta = inputNumero();
			cartaScelta -= 1;
			if (cartaScelta < 0 || cartaScelta > carteDisponibili.length) {
				System.out.print("ATTENZIONE: scelta NON valida!\n");
				sceltaValida = false;
			} else if (carteDisponibili[cartaScelta] <= 0) {
				System.out.print("ATTENZIONE: carta NON disponibile!\n");
				sceltaValida = false;
			} else if (danari < numCarte - carteDisponibili[cartaScelta]) {
				System.out.print("ATTENZIONE: danari NON sufficienti!\n");
				sceltaValida = false;
			}
		} while (!sceltaValida);

		return cartaScelta;
	}

	@Override
	public void acquistoCarta(Terreno tipoCarta, Giocatore giocatoreCorrente,
			int prezzo) {
		System.out.print(giocatoreCorrente + " ha acquistato una carta "
				+ tipoCarta + " per " + prezzo + " danari\n\n");
	}

	@Override
	public int sceltaRegioneAbbattimento(Regione[] regioniAdiacenti,
			Regione regionePecoraNera, Giocatore giocatore) {
		int regioneScelta;
		System.out.print("\n\nABBATTIMENTO!\n\n\n");
		System.out.print("Scegli la regione in cui abbattere un ovino:\n\n");
		for (Regione x : regioniAdiacenti) {
			mostraPecoreDellaRegione(x);
			if (regionePecoraNera != null && regionePecoraNera == x)
				System.out.print(" La pecora nera si trova qui, nella " + x
						+ "!\n");
			System.out.print("\n");
		}

		regioneScelta = sceltaRegionePecore(regioniAdiacenti);

		return regioneScelta;
	}

	/**
	 * Questo metodo restituisce un intero che rappresenta il numero di regione
	 * scelta dal giocatore per interagire con le pecore
	 * 
	 * @param regioniAdiacenti
	 * @return
	 */
	private int sceltaRegionePecore(Regione[] regioniAdiacenti) {
		int IDRegione1 = regioniAdiacenti[0].getID();
		int IDRegione2 = regioniAdiacenti[1].getID();
		int regioneScelta;
		boolean sceltaValida;
		do {
			sceltaValida = true;
			regioneScelta = inputNumero();
			if (regioneScelta != IDRegione1 && regioneScelta != IDRegione2) {
				sceltaValida = false;
				System.out.print("ATTENZIONE: Scelta NON valida!\n\n");
			} else if (regioneScelta == IDRegione1) {
				if (regioniAdiacenti[0].getNumeroPecore() <= 0) {
					System.out
							.print("ATTENZIONE: Nella regione scelta NON ci sono pecore!\n\n");
					sceltaValida = false;
				}
			} else if (regioneScelta == IDRegione2) {
				if (regioniAdiacenti[1].getNumeroPecore() <= 0) {
					System.out
							.print("ATTENZIONE: Nella regione scelta NON ci sono pecore!\n\n");
					sceltaValida = false;
				}
			}
		} while (!sceltaValida);

		return regioneScelta;
	}

	@Override
	public int sceltaAnimaleDaAbbattere(Regione regioneAbbattimento,
			boolean presenzaPecoraNera, Giocatore giocatore) {
		int animaleScelto;
		System.out.print("Scegli che animale abbattere nella "
				+ regioneAbbattimento + ".\n\n");

		animaleScelto = sceltaTipoPecora(regioneAbbattimento,
				presenzaPecoraNera);

		return animaleScelto;
	}

	/**
	 * Questo metodo riceve in input una regione e un booleano rappresentate la
	 * eventuale presenza della pecora nera. viene chiesto un input numerico al
	 * giocatore che rappresenta il tipo di animale secondo questa regola:
	 * 1.Agnello, 2.Montone, 3.Pecora Femmina, 4.Pecora Nera
	 * 
	 * @param regioneAbbattimento
	 * @param presenzaPecoraNera
	 * @return
	 */
	private int sceltaTipoPecora(Regione regioneAbbattimento,
			boolean presenzaPecoraNera) {
		int animaleScelto;
		boolean sceltaValida;
		System.out.print("Pecore presenti:\n");
		mostraPecoreDellaRegione(regioneAbbattimento);
		if (presenzaPecoraNera)
			System.out.print("La Pecora Nera si trova qui!\n");
		System.out
				.print("\nOpzioni:\n 1.Agnello, 2.Montone, 3.Pecora Femmina, 4.Pecora Nera\n\n");

		do {
			sceltaValida = true;
			animaleScelto = inputNumero();
			animaleScelto -= 1;
			if (animaleScelto < 0 || animaleScelto > 3) {
				sceltaValida = false;
				System.out.print("ATTENZIONE: Scelta NON valida!\n");
			} else if (!presenzaAnimaleScelto(animaleScelto,
					regioneAbbattimento, presenzaPecoraNera)) {
				sceltaValida = false;
				System.out
						.print("ATTENZIONE: La pecora scelta NON è presente!\n");
			}
		} while (!sceltaValida);

		return animaleScelto;

	}

	/**
	 * Questo metodo effettua un controllo al fine di verificare la presenza di
	 * un dato tipo di animale in una data regione. Necessario per realizzare
	 * sceltaTipoPecora, al fine di verificare la effettiva presenza
	 * dell'animale.
	 * 
	 * @param animaleScelto
	 * @param regioneAbbattimento
	 * @param presenzaPecoraNera
	 * @return
	 */
	private boolean presenzaAnimaleScelto(int animaleScelto,
			Regione regioneAbbattimento, boolean presenzaPecoraNera) {
		switch (animaleScelto) {
		case 0:
			if (regioneAbbattimento.getNumeroAgnelli() <= 0)
				return false;
			return true;
		case 1:
			if (regioneAbbattimento.getNumeroMontoni() <= 0)
				return false;
			return true;
		case 2:
			if (regioneAbbattimento.getNumeroPecoreFemmine() <= 0)
				return false;
			return true;
		case 3:
			if (!presenzaPecoraNera)
				return false;
			return true;
		}
		return false;
	}

	/**
	 * Metodo che printa a schermo il numero di tipi di pecore in una regione
	 * 
	 * @param regione
	 */
	private void mostraPecoreDellaRegione(Regione regione) {
		System.out.print(regione + ": " + regione.getNumeroAgnelli()
				+ " agnelli, " + regione.getNumeroMontoni() + " montoni e "
				+ regione.getNumeroPecoreFemmine() + " pecore.\n");
	}

	/**
 * 
 */
	@Override
	public void abbattimento(int animaleDaAbbattere, Regione regioneAbbattimento) {
		System.out.print("Nella " + regioneAbbattimento + " ");
		switch (animaleDaAbbattere) {
		case 0:
			System.out.print("un agnello è stato abbattuto!\n\n");
			break;
		case 1:
			System.out.print("un montone è stato abbattuto!\n\n");
			break;
		case 2:
			System.out.print("una pecora femmina è stata abbattuta!\n\n");
			break;
		case 3:
			System.out.print("la pecora nera è stata abbattuta!!!\n\n");
			break;
		}

	}

	@Override
	public int sceltaRegioneAccoppiamento(Regione[] regioniAccoppiamento,
			Giocatore giocatore) {
		int IDRegione1 = regioniAccoppiamento[0].getID();
		int IDRegione2 = regioniAccoppiamento[1].getID();
		int regioneScelta;
		boolean sceltaValida;
		System.out.print("\n\nACCOPPIAMENTO!\n\n\n");
		System.out
				.print("Scegli in qual regione effettuare l'Accoppiamento:\n\n");
		for (Regione x : regioniAccoppiamento)
			mostraPecoreDellaRegione(x);

		do {
			sceltaValida = true;
			regioneScelta = inputNumero();
			if (regioneScelta != IDRegione1 && regioneScelta != IDRegione2) {
				System.out.print("ATTENZIONE: Scelta NON valida!\n");
				sceltaValida = false;
			} else if (regioneScelta == IDRegione1) {
				if (!regioniAccoppiamento[0].accoppiamentoPossibile()) {
					System.out
							.print("ATTENZIONE: In questa Regione NON puoi effettuare un accoppiamento!\n");
					sceltaValida = false;
				}
			} else if (regioneScelta == IDRegione2) {
				if (!regioniAccoppiamento[1].accoppiamentoPossibile()) {
					System.out
							.print("ATTENZIONE: In questa Regione NON puoi effettuare un accoppiamento!\n");
					sceltaValida = false;
				}
			}
		} while (!sceltaValida);

		return regioneScelta;

	}

	@Override
	public void accoppiamento(Regione regioneAccoppiamento) {
		System.out.print("Nella " + regioneAccoppiamento
				+ ", un montone e una pecora femmina "
				+ "hanno dato vita a un agnellino!\n\n");
	}

	@Override
	public int sceltaRegioneSpostamento(Regione[] regioniAdiacenti,
			Regione regionePecoraNera, Giocatore giocatore) {
		int regioneScelta;
		System.out.print("SPOSTAMENTO PECORA!\n\n\n");
		System.out.print("Scegli la regione da cui prelevare un ovino:\n\n");
		for (Regione x : regioniAdiacenti) {
			mostraPecoreDellaRegione(x);
			if (regionePecoraNera != null && regionePecoraNera == x)
				System.out.print(" La pecora nera si trova qui, nella " + x
						+ "!\n");
			System.out.print("\n");
		}

		regioneScelta = sceltaRegionePecore(regioniAdiacenti);

		return regioneScelta;
	}

	@Override
	public int sceltaPecoraDaSpostare(Regione regioneSpostamento,
			boolean presenzaPecoraNera, Giocatore giocatore) {
		int animaleScelto;
		System.out.print("Scegli che animale spostare dalla "
				+ regioneSpostamento + ".\n\n");

		animaleScelto = sceltaTipoPecora(regioneSpostamento, presenzaPecoraNera);

		return animaleScelto;
	}

	@Override
	public void spostamentoPecora(int pecoraScelta, Regione regioneSpostamento,
			Regione regioneDiDestinazione) {
		switch (pecoraScelta) {
		case 0:
			System.out.print("Un agnello è stato spostato ");
			break;
		case 1:
			System.out.print("Un montone è stato spostato ");
			break;
		case 2:
			System.out.print("Una pecora femmina è stata spostata ");
			break;
		case 3:
			System.out.print("La pecora nera è stata spostata ");
			break;
		}
		System.out.print("dalla " + regioneSpostamento + " alla "
				+ regioneDiDestinazione + "\n\n");
	}

	@Override
	public void mossoLupo(int regioneDestinazione, boolean isMangiata,
			int tipoPecora) {
		if (regioneDestinazione < 0)
			System.out.print("Il lupo non si muove!\n\n");
		else {
			System.out.print("Il lupo si muove nella regione "
					+ regioneDestinazione + "\n");
			if (tipoPecora == -1)
				System.out
						.print("Il lupo non trova nessuna pecora da mangiare!\n\n");
			else
				System.out.print("Il lupo divora " + getNomiPecore(tipoPecora)
						+ "\n\n");
		}
	}

	private String getNomiPecore(int numeroPecora) {
		switch (numeroPecora) {
		case 0:
			return "Agnello";

		case 1:
			return "Pecora Femmina";

		case 2:
			return "Montone";

		case 3:
			return "Pecora Nera";

		}
		return null;
	}

	@Override
	public void refreshTurno(GameBoard gameBoard, int[] carteDisponibili,
			Giocatore[] giocatori, Giocatore giocatore) {
		createAndShowMap(gameBoard, carteDisponibili, giocatori);
		inizioTurno(giocatore);
		mostraDatiGiocatore(giocatore);
	}

	@Override
	public int tiraDado6(Giocatore giocatore) {
		System.out.print(" Premere INVIO per lanciare il dado.\n\n");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int risultatoDado = ((int) (Math.random() * 6) + 1);
		System.out.print("Risultato dado: " + risultatoDado + "\n\n");
		return risultatoDado;
	}

	@Override
	public int tiroDadoGiocatore(Giocatore giocatoreCorrente) {
		System.out.print(giocatoreCorrente
				+ ", premi INVIO per lanciare il dado.\n");
		return tiraDado6(giocatoreCorrente);
	}

	@Override
	public void nonPagaSilenzio(Giocatore giocatore) {
		System.out
				.print("Il giocatore "
						+ giocatore
						+ " NON ha assistito all'abbattimento. Il suo silenzio non viene pagato.\n\n");
	}

	@Override
	public void pagaSilenzio(Giocatore giocatoreCorrente,
			Giocatore giocatoreDaPagare) {
		System.out.print(giocatoreDaPagare + " ha assistito all'uccisione, "
				+ giocatoreCorrente
				+ " deve pagare il suo silenzio con con 2 danari!\n\n");
	}


	@Override
	public Offerta offriMercato(Giocatore giocatoreAttuale) {
		int cartascelta = 0;
		int prezzo = 0;

		cartascelta = offriCarta(giocatoreAttuale);

		if (cartascelta < 0)
			return null;
		prezzo = prezzoCartaOfferta(cartascelta, giocatoreAttuale);
		// creo l'offerta e la ritorno
		return new Offerta(Terreno.getTerreno(cartascelta), giocatoreAttuale,
				prezzo);
	}

	private int prezzoCartaOfferta(int cartascelta, Giocatore giocatore) {
		System.out.print("Hai scelto di vendere un territorio "
				+ Terreno.getTerreno(cartascelta) + ".\n"
				+ "Ora scegli il prezzo di vendita da 1 a 4.\n");
		int prezzo = 0;
		boolean prosegui = false;

		while (!prosegui) {
			prezzo = inputNumero();
			if (prezzo < 1 || prezzo > 4)
				System.out
						.print("Il prezzo deve essere compreso tra 1 e 4 danari! Reinserisci il prezzo.\n");
			else
				prosegui = true;
		}

		return prezzo;
	}

	private int offriCarta(Giocatore giocatoreAttuale) {
		int cartascelta = 0;
		System.out
				.print("\n\n"
						+ giocatoreAttuale
						+ ", è il tuo turno di fare un'offerta al mercato:\n "
						+ "scegli una delle tue propietè da mettere in vendita (numero da 1 a 6),\n"
						+ "oppure premi 0 per non vendere nulla.\n\n");
		mostraCarte(giocatoreAttuale);
		int[] carte = giocatoreAttuale.getCarte();
		boolean prosegui = false;
		// scegli il territorio fino a che non viene scelto uno accettabile
		while (!prosegui) {
			cartascelta = inputNumero() - 1;
			if (cartascelta < -1 || cartascelta > 5)
				System.out
						.print("ATTENZIONE! Scelta NON valida. Riprovare.\n\n");
			else if (cartascelta < 0)
				return cartascelta;
			else if (carte[cartascelta] <= 0)
				System.out
						.print("ATTENZIONE! Non possiedi carte di quel tipo, scegliere un altro territorio!\n\n");
			else
				prosegui = true;
		}
		return cartascelta;

	}

	/**
	 * Metodo che richiede all'utente un numero intero. Viene restituito il
	 * numero dopo aver effettuato controlli sulla stringa
	 * 
	 * @return
	 */

	private int inputNumero() {
		Scanner reader = new Scanner(System.in);
		while (true) {
			try {
				String number = reader.nextLine();
				int numero = Integer.parseInt(number);
				return numero;
			} catch (Exception e) {
				System.out
						.print("ATTENZIONE! Numero non valido. Riprovare!\n\n");
			}
		}
	}

	
	private void creaListaOfferte(ArrayList<Offerta> listaOfferte) {
		if (listaOfferte != null && listaOfferte.size() > 0) {
			System.out.print("Lista offerte disponibili:\n");
			for (int i = 0; i < listaOfferte.size(); i++)
				System.out.println((i + 1) + ". " + listaOfferte.get(i));
			System.out.print("\n");
		} else
			System.out.print("Non ci sono offerte disponibili!\n");
	}

	@Override
	public Offerta accettaOfferta(Giocatore acquirente,
			ArrayList<Offerta> listaOfferte) {
		System.out
				.print(acquirente
						+ ", è il tuo turno per accettare un'offerta al mercato.\n"
						+ "Non puoi accettare la tua offerta. (Per passare premi 0):\n");

		creaListaOfferte(listaOfferte);
		int offertaScelta = 0;
		boolean valida = false;

		while (!valida) {
			offertaScelta = inputNumero() - 1;
			if (offertaScelta < -1 || offertaScelta >= listaOfferte.size())
				System.out.print("ATTENZIONE: Scelta NON valida!\n\n");
			else if (offertaScelta == -1)
				return null;
			else if (listaOfferte.get(offertaScelta).getVenditore()
					.equals(acquirente))
				System.out
						.print("ATTENZIONE: NON puoi acquistare la tua carta!\n\n");
			
			else
				valida = true;
		}

		return listaOfferte.get(offertaScelta);
	}

	

	@Override
	public void inizioMercato() {
		System.out.print("\n\n******* MERCATO *****\n\n");
		System.out.print("Attendi il tuo turno per fare un'offerta...\n\n");
	}

	@Override
	public void crescitaAgnello(Regione regione, int tipoPecora) {
		System.out.print("Nella " + regione
				+ " un agnello è diventato adulto, ora è "
				+ getNomiPecore(tipoPecora) + ".\n");
	}

	@Override
	public void finePartita() {
		System.out.print("\n\n\n\n\n\n\n\n\n\n"
				+ "+++++++++++++++ FINE DEI GIOCHI +++++++++++ \n\n\n\n");
	}

	@Override
	public void annunciaVincitore(Giocatore vincitore, Giocatore[] punteggi) {
		System.out.print("Punteggi:\n");
		for(int i=0; i<punteggi.length; i++)
			System.out.print(punteggi[i]+": "+punteggi[i].getDanari()+"\n");
		
		System.out.print("\n******** VINCITORE ********\n");
		System.out.print("*                         *\n");
		System.out.print("        " + vincitore + "      \n");
		System.out.print("*                         *\n");
		System.out.print("***************************\n");
	}

	@Override
	public void faseFinale() {
		System.out.print("\n\n\n\n ------ FASE FINALE DI GIOCO ------- \n");
		System.out.print("La partita si conluderà alla fine del giro.\n\n");
	}

	@Override
	public int tiroDadoAbbattimento(Pastore pastoreScelto,
			Giocatore giocatoreCorrente) {
		int valoreStrada = pastoreScelto.getStrada().getValore();
		System.out
				.print(giocatoreCorrente
						+ ", devi tirare un dado per effettuare l'abbattimento. Per riuscire ad effettuarlo deve uscirti il numero: "
						+ valoreStrada + ".\n\n");
		if (tiraDado6(giocatoreCorrente) == valoreStrada) {
			System.out.print("Puoi procedere con l'abbattimento!\n\n");
			return 1;
		} else {
			System.out
					.print("Accidenti, non puoi effettuare l'abbattimento!\n\n");
			return 0;
		}

	}

	@Override
	public void inizioAcquistiMercato(ArrayList<Offerta> lista) {
		System.out.print("\n\nINIZIA LA FASE DI ACQUISTO! \n\n");
		creaListaOfferte(lista);
	}

	@Override
	public void disconnessionePersonale() {
		System.out.print("\n\n!!! CONNESIONE PERSA !!!\n\n");
	}

	@Override
	public void freeze(Giocatore giocatore) {
		System.out.print("ATTENZIONE: la partita è sospsesa in attesa di "
				+ giocatore + "...\n");
	}

	@Override
	public void disconnesso(Giocatore giocatore) {
		System.out.print("ATTENZIONE: " + giocatore
				+ " si è disconnesso! La partta prosegue senza di lui.\n");
	}

	@Override
	public void riconnesso(Giocatore giocatore) {
		System.out.print("ATTENZIONE: " + giocatore + " si è riconnesso!");
	}

	@Override
	public void posizionatoPastore(Giocatore giocatore,
			Pastore pastoreDaPosizionare, Strada stradaSuCuiPosizionare) {
		System.out.print("Il " + pastoreDaPosizionare
				+ " è stato posizionato sulla " + stradaSuCuiPosizionare
				+ "\n\n");
	}

	@Override
	public void fineMercato() {
		System.out.print("\n\n****FINE DEL MERCATO****\n\n\n");
	}

	@Override
	public void offertaAccettata(Offerta offerta, Giocatore acquirente) {
		if (offerta == null)
			System.out.println(acquirente + " non ha comprato nessuna carta!");
		else
			System.out.println(acquirente + " ha accettato l'" + offerta);
	}

	@Override
	public void posizionaCancello(Strada strada) {
		System.out.println("Recinto posizionato su " + strada);
	}

	@Override
	public void inserimentoNumeroGiocatori(int num) {
		System.out.println("Partita con " + num+ " giocatore.");
	}

	@Override
	public void indicePersonale(int indice) {
		System.out.println("Sei il giocatore "+(indice+1));		
	}

}
