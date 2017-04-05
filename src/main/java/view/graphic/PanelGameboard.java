package view.graphic;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JPanel;

import controller.Giocatore;
import controller.Offerta;
import model.Regione;

/**
 * Pannello principale che contiene gran parte dei componenti della grafica.
 * @author lorenzo
 *
 */
public class PanelGameboard extends JPanel {
	private static final long serialVersionUID = -590777680145456557L;
	private static final int NUM_STRADE = 42;
	private static final int NUM_REGIONI = 19;
	private static final int NUM_CARTE = 6;
	private static final int NUM_MOSSE = 5;
	private PonteLogicaGrafica ponte;
	private StradaButton[] strade = new StradaButton[NUM_STRADE];
	private RegionePanel[] regioni = new RegionePanel[NUM_REGIONI];
	private MossaPanel[] mosse = new MossaPanel[NUM_MOSSE];
	private CartaPanel[] carteDisponibili = new CartaPanel[NUM_CARTE];
	private GiocatoriPanel giocatori;
	private SceltaPastorePanel[] sceltaPastore = new SceltaPastorePanel[2];
	private SceltaTipoPecoraPanel[] sceltaPecora = new SceltaTipoPecoraPanel[4];
	private PastorePanel[] pastori = new PastorePanel[6];
	private CartePersonaliPanel cartePersonali;
	private LupoPanel lupo;
	private PecoraNeraPanel pecoraNera;
	private PecoraPanel pecoraAnimazione;
	private MessaggiPanel lavagna;
	private AnimazionePanel animazioneGif;
	private BandieraPanel[] bandiere = new BandieraPanel[4];
	private DadoPanel dado;
	private ContatoreCancelliPanel contatoreCancelli;
	private OffriPanel offriPanel;
	private MercatoPanel mercato;
	
	/**
	 * Costruttore. Inizialziza i componenti e le dimensioni.
	 * @param larghezza
	 * @param altezza
	 * @param ponte
	 */
	PanelGameboard(int larghezza, int altezza, PonteLogicaGrafica ponte) {
		this.ponte = ponte;
		this.setSize(larghezza, altezza);
		this.setMinimumSize(getSize());
		this.setMaximumSize(getSize());
		this.setOpaque(false);
		this.setLayout(null);

		aggiungiMercato();
		aggiungiOffertaPanel();
		aggiungiContatoreCancelli();
		aggiungiDado();
		aggiuntaRegioni();
		aggiuntaStrade();
		aggiuntaCarte();
		aggiuntaMosse();
		aggiuntaSceltaPecore();
		aggiuntaSceltaPastori();
		aggiuntaCartePersonali();
		aggiuntaLupo();
		aggiuntaPecoraNera();
		aggiuntaPastori();
		aggiungiLavagna();
		preparaBandiere();
		pecoraAnimazione = new PecoraPanel();
		this.validate();
	}


	/**
	 * Metodo che aggiunge il pannello dell'offerta.
	 */
	private void aggiungiOffertaPanel() {
		offriPanel = new OffriPanel(this.getSize().width, this.getSize().height, ponte);
		this.add(offriPanel);
	}
	
	/**
	 * Metodo che aggiunge il pannello del mercato.
	 */
	private void aggiungiMercato() {
		mercato = new MercatoPanel(this.getSize().width, this.getSize().height, ponte);
		this.add(mercato);
	}


	/**
	 * Metodo che restituisce la regione ( intesa come pannello ) corrispettiva ad un indice dato.
	 * @param indice
	 * @return
	 */
	public RegionePanel getRegione(int indice) {
		return this.regioni[indice];
	}

	/**
	 * metodo che restituisce una strada ( intesa come pannello ) corrispettiva ad un indice dato .
	 * @param indice
	 * @return
	 */
	public StradaButton getStrada(int indice) {
		return this.strade[indice];
	}

	/**
	 * metodo che ottiene la lista di tutte le strade ( intese come pannelli ).
	 * @return
	 */
	public StradaButton[] getStrade() {
		return this.strade;
	}
	
	/**
	 * Metodo che ottiene la lista di tutte le mosse ( intese come pannelli ).
	 * @return
	 */
	public MossaPanel[] getMosse(){
		return mosse;
	}
	
	/**
	 * Metodo che aggiunge tutti i pannelli delle regioni.
	 */
	private void aggiuntaRegioni() {
		try {
			InputStream fin = getClass().getResourceAsStream("/documents/regioniGrafica.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fin));
			String rigaLetta;
			int contatore = 0;
			while ((rigaLetta = br.readLine()) != null) {
				rigaLetta = rigaLetta.replaceAll("\\s+", "");
				int x = Integer.parseInt(rigaLetta.substring(0, 3));
				int y = Integer.parseInt(rigaLetta.substring(4, 7));
				int dimX = Integer.parseInt(rigaLetta.substring(8, 11));
				int dimY = Integer.parseInt(rigaLetta.substring(12, 15));
				RegionePanel regione = new RegionePanel(ponte, contatore, x, y,
						dimX, dimY);

				regioni[contatore] = regione;
				this.add(regione);
				regione.setLocation(x, y);
				contatore++;
			}
			br.close();
			fin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo che aggiunge i pannelli delle strade
	 */
	private void aggiuntaStrade() {
		try {
			InputStream fin = getClass().getResourceAsStream("/documents/stradeCoord.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fin));
			String rigaLetta;
			int contatore = 0;
			while ((rigaLetta = br.readLine()) != null) {
				rigaLetta = rigaLetta.replaceAll("\\s+", "");
				int x = Integer.parseInt(rigaLetta.substring(0, 3));
				int y = Integer.parseInt(rigaLetta.substring(4, 7));
				StradaButton strada = new StradaButton(ponte, contatore);

				strade[contatore] = strada;
				this.add(strada);
				strada.setLocation(x - strada.getWidth() / 2,
						y - strada.getHeight() / 2);

				contatore++;
			}
			br.close();
			fin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo che aggiunge i pannelli delle carte.
	 */
	private void aggiuntaCarte() {
		final int X=520, Y=40, DIM = 83;
		int i=0;
		for (; i<2; i++){
			carteDisponibili[i] = new CartaPanel(ponte, i);
			this.add(carteDisponibili[i]);
			carteDisponibili[i].setLocation(X,Y+(i*(DIM+25)));
		}
		for (; i<NUM_CARTE; i++){
			carteDisponibili[i] = new CartaPanel(ponte, i);
			this.add(carteDisponibili[i]);
			carteDisponibili[i].setLocation(X,Y+(i*(DIM+25))-3);
		}
	}
	
	/**
	 * Metodo che aggiunge i pannelli delle mosse.
	 */
	private void aggiuntaMosse(){
		final int X=675, Y=180, H=70, GAP=10;
		int i=0;
		for(; i<NUM_MOSSE; i++){
			mosse[i]= new MossaPanel(i, ponte);
			this.add(mosse[i]);
			mosse[i].setLocation(X, Y+(i*(H+GAP)));
		}
			
	}
	/**
	 * Metodo che aggiunge i pannelli di scelta degli animali.
	 */
	private void aggiuntaSceltaPecore(){
		final int X=673, Y=613, DIM = 52;
		int i=0;
		for(; i<4; i++){
			sceltaPecora[i]= new SceltaTipoPecoraPanel(ponte, i);
			this.add(sceltaPecora[i]);
			sceltaPecora[i].setLocation(X+(i*(DIM)), Y);
		}
	}
	/**
	 * Metodo che aggiunge il pannello della scelta dei pastori.
	 */
	private void aggiuntaSceltaPastori(){
		final int X=720, Y=10, GAP=100;
		for(int i=0; i<2; i++){
			sceltaPastore[i] = new SceltaPastorePanel(ponte, i);
			this.add(sceltaPastore[i]);
			sceltaPastore[i].setLocation(X+(i*GAP),Y);
		}
	}
	/**
	 * Metodo che aggiunge il pannello delle carte personali.
	 */
	private void aggiuntaCartePersonali(){
		final int X=612, Y=25;
		cartePersonali = new CartePersonaliPanel();
		add(cartePersonali);
		cartePersonali.setLocation(X,Y);
	}

	/**
	 * Metodo che aggiunge il pannello rappsentate il lupo.
	 */
	private void aggiuntaLupo(){
		lupo = new LupoPanel();
		Point pos = regioni[0].getPosizioneLupo();
		add(lupo);
		lupo.setLocation(pos);
	}
	
	/**
	 * Metodo che aggiunge il pannello rappresentate la pecora nera.
	 */
	private void aggiuntaPecoraNera(){
		pecoraNera = new PecoraNeraPanel();
		Point pos = regioni[0].getPosizionePecoraNera();
		add(pecoraNera);
		pecoraNera.setLocation(pos);
	}
	
	/**
	 * Metodo che aggiunge i pannelli rappresentanti i pastori.
	 */
	private void aggiuntaPastori(){
		for(int i=0; i<pastori.length; i++){
			pastori[i] = new PastorePanel(i);
			add(pastori[i]);
			pastori[i].setLocation(100,900);
		}
	}
	
	/**
	 * Metodo Che gestisce lo spostamento del pastore da una strada ad un'altra
	 * @param idPastore 
	 * @param strada La nuova strada su cui si desidera spostare il pastore.
	 */
	public void spostaPastore(int idPastore, int strada){
		PastorePanel pastore = pastori[idPastore];
		Point arrivo = strade[strada].getPosizionePastore();
		animazione(pastore, pastore.getLocation(), arrivo);
	}
	
	/**
	 * Metodo che gestisce l'animazione dello spostamento.
	 * @param oggetto Oggetto da muovere
	 * @param partenza Posizione di partenza
	 * @param arrivo Posizione di arrivo.
	 */
	private void animazione(JPanel oggetto, Point partenza, Point arrivo){ 
		int T = 600;
		int millis = 15;
		int N_FRAME = T/millis;
		double Xf = arrivo.getX(), Yf = arrivo.getY();
		double X = partenza.getX(), Y = partenza.getY();
		double distanceX;
		double distanceY;
		int deltaX;
		int deltaY;
		Point now = new Point((int)X,(int)Y);
		int i=0;
		while(now.distance(arrivo)>5){
			distanceX = Xf-now.getX();
			distanceY = Yf-now.getY();
			deltaX =  (int) (distanceX/(N_FRAME-i)); if(deltaX == 0) deltaX = 1;
			deltaY = (int) (distanceY/(N_FRAME-i)); if(deltaY == 0) deltaY = 1;
			remove(oggetto);
			X = X+deltaX;
			Y = Y+deltaY;
			now = new Point((int)X,(int)Y);
			oggetto.setLocation(now);
			add(oggetto);
			repaint(); revalidate();
			Util.timer(millis);
			i++;
		}
		remove(oggetto);
		oggetto.setLocation(arrivo);
		add(oggetto);
		repaint(); revalidate();
	}
	
	/**
	 * Metodo che aggiunge il pannello dei dati dei giocatori.
	 * @param num
	 */
	public void aggiungiGiocatori(int num){
		final int X=685, Y=100;
		giocatori = new GiocatoriPanel(num);
		this.add(giocatori);
		giocatori.setLocation(X, Y);
	}
	
	/**
	 * Metodo che aggiunge il pannello della lavagna.
	 */
	public void aggiungiLavagna(){
		final int X=66, Y=6;
		lavagna = new MessaggiPanel();
		this.add(lavagna);
		lavagna.setLocation(X, Y);
	}
	
	/**
	 * Metodo che genera l'animazione di un cuore su una data regione
	 * @param idRegione
	 */
	public void animazioneCuore(int idRegione) {
		animazioneGif = new AnimazionePanel();
		animazioneGif.setLocation(regioni[idRegione].getPosizioneAnimazioni());
		this.add(animazioneGif);
		animazioneGif.animazioneCuore();
		this.remove(animazioneGif);
		repaint(); revalidate();
	}
	
	/**
	 * Metodo che genera l'animazione di crescita degli agnelli su una data regione.
	 * @param idRegione
	 */
	public void animazioneCrescita(int idRegione) {
		animazioneGif = new AnimazionePanel();
		animazioneGif.setLocation(regioni[idRegione].getPosizioneAnimazioni());
		this.add(animazioneGif);
		animazioneGif.animazioneCrescita();
		this.remove(animazioneGif);
		repaint(); revalidate();
	}

	/**
	 * Metodo che genera l'animazione di abbattimento di un animale su una data regione.
	 * @param idRegione
	 */
	public void animazioneTeschio(int idRegione) {
		animazioneGif = new AnimazionePanel();
		animazioneGif.setLocation(regioni[idRegione].getPosizioneAnimazioni());
		this.add(animazioneGif);
		animazioneGif.animazioneTeschio();
		this.remove(animazioneGif);
		repaint(); revalidate();
	}
	
	/**
	 * Metodo che genera l'animazione di sbranamento in una data regione.
	 * @param idRegione
	 */
	public void animazioneCarne(int idRegione) {
		animazioneGif = new AnimazionePanel();
		animazioneGif.setLocation(regioni[idRegione].getPosizioneAnimazioni());
		this.add(animazioneGif);
		animazioneGif.animazioneCarne();
		this.remove(animazioneGif);
		repaint(); revalidate();
	}
	
	/**
	 * Metodo che genera l'animazione di acquisto carta sopra una carta.
	 * @param idCarta
	 */
	public void animazioneCarta(int idCarta) {
		animazioneGif = new AnimazionePanel();
		animazioneGif.setLocation(carteDisponibili[idCarta].getX()-50, carteDisponibili[idCarta].getY());
		this.add(animazioneGif);
		animazioneGif.animazioneCarta();
		this.remove(animazioneGif);
		repaint(); revalidate();
	}

	/**
	 * Metodo che illumina di color blu la strada vicina ad un pastore quando deve effettuare uno spostamento.
	 * @param id
	 */
	public void abilitaStradaVicina(int id) {
		strade[id].attivaVicina();
	}

	/**
	 * Metodo che disabilita l'evidenziazzione delle strade;
	 */
	public void disabilitaStrade() {
		for(StradaButton x: strade)
			x.disattiva();
	}

	/**
	 * Metodo che viene chiamato durante lo spostamento della pecora nera.
	 * Effettua l'animazione di spostamento.
	 */
	public void spostaPecoraNera (Regione destinazione){
		if(destinazione==null){
			remove(pecoraNera);
			repaint(); revalidate();
		}else{
			int idRegione = destinazione.getID();
			Point destinazionePoint = regioni[idRegione].getPosizionePecoraNera();
			animazione(pecoraNera, pecoraNera.getLocation(), destinazionePoint);
		}
	}

	/**
	 * Metodo che viene chiamato durante lo spostamento del lupo.
	 * Effettua l'animazione di spostamento.
	 * @param regioneDestinazione
	 */
	public void spostaLupo (int regioneDestinazione) {
		if(regioneDestinazione>=0){
			Point destinazione = regioni[regioneDestinazione].getPosizioneLupo();
			animazione(lupo, lupo.getLocation(), destinazione);
		}
	}

	/**
	 * Metodo che aggiorna le label delle carte per mostrare i nuovi valori.
	 * @param carteDisponibiliOra
	 */
	public void settaCarte(int[] carteDisponibiliOra) {
		for(int i=0; i<NUM_CARTE; i++)
			carteDisponibili[i].settaCarte(carteDisponibiliOra[i]);
	}

	/**
	 * Metodo che aggiorna il valore dei danari nella label dei giocatori.
	 * @param giocatoriAttuali
	 */
	public void settaDanari(Giocatore[] giocatoriAttuali) {
		for(int i=0; i<giocatoriAttuali.length; i++)
			giocatori.settaDanari(i, giocatoriAttuali[i].getDanari());
	}

	/**
	 * Metodo che aggiorna le label delle carte possedute per mostrare i nuovi valori.
	 * @param cartePersonaliNow
	 */
	public void settaCartePersonali(int[] cartePersonaliNow) {
		cartePersonali.settaCarte(cartePersonaliNow);
	}

	/**
	 * Metodo che disabilita tutte le mosse.
	 */
	public void disabilitaMosse() {
		for(MossaPanel x: mosse)
			x.disattiva();
	}

	/**
	 * Metodo che abilita la scelta del primo o del secondo pastore.
	 * @param secondoPastore
	 */
	public void abilitaPastore(boolean secondoPastore) {
		if(secondoPastore)
			sceltaPastore[1].attiva();
		else 
			sceltaPastore[0].attiva();
	}

	/**
	 * Metodo che disaabilita la scelta del primo o del secondo pastore.
	 */
	public void disabilitaSceltaPastori() {
		sceltaPastore[0].disattiva();
		sceltaPastore[1].disattiva();
	}

	/**
	 * Metodo che colora le strade lontane raggiungibili di arancio
	 * @param id
	 */
	public void abilitaStradaLontana(int id) {
		strade[id].attivaLontana();
		
	}

	/**
	 * Metodo che dimunuisce i soldi di un giocatore
	 * @param giocatore
	 * @param somma
	 */
	public void pagamento(int giocatore, int somma) {
		giocatori.diminuisciDanari(somma, giocatore);
	}
	
	/**
	 * Metodo che aumenta i soldi di un gicoatore
	 * @param giocatore
	 * @param somma
	 */
	public void incassa(int giocatore, int somma){
		giocatori.aumentaDanari(somma, giocatore);
	}

	/*
	 * Metodo che disatitva la scelta di tutti i tipi di pecore
	 */
	public void disabilitaPecore() {
		for(SceltaTipoPecoraPanel x: sceltaPecora)
			x.disattiva();
	}

	/**
	 * metodo che disattiva la pressione di tutte le regioni
	 */
	public void disabilitaRegioni() {
		for(RegionePanel x: regioni)
			x.disattiva();
	}

	/**
	 * Metodo che attiva scelta del tipo di pecora.
	 * @param tipo
	 */
	public void abilitaPecora(int tipo){
		sceltaPecora[tipo].attiva();
	}

	
	/**
	 * Metodo che disattiva tutte le carte
	 */
	public void disabilitaCarte() {
		for (CartaPanel x : carteDisponibili) {
			x.disattiva();
		}
		
	}
	
	/**
	 * Metodo che rimuove la pecora nera dalla grafica
	 */
	public void eliminaPecoraNera(){
		remove(pecoraNera);
	}
	
	/**
	 * Metodo che abilita una singola carta	
	 * @param indice indice del tipo di carta
	 */
	public void abilitaCarta(int indice) {
		carteDisponibili[indice].attiva();
	}

	/**
	 * Metodo che preposiziona tutte le bandiere dei giocatori.
	 */
	private void preparaBandiere(){
		final int X=677, Y=72;
		for(int i=0; i<bandiere.length; i++){
			bandiere[i] = new BandieraPanel(i);
			bandiere[i].setLocation(X,Y);
		}
	}
	
	/**
	 * metodo che mostra un solo tipo di bandiera di un dato giocatore
	 * @param indice
	 */
	public void mostraBandiera(int indice) {
		for(int i=0; i<bandiere.length; i++)
			remove(bandiere[i]);
		add(bandiere[indice]);
		repaint(); revalidate();
	}
	

	/**
	 * Metodo che effettua l'animazione di spostamento di una normale pecora.
	 * @param idPartenza
	 * @param idArrivo
	 */
	public void spostaPecora(int idPartenza, int idArrivo) {
		Point partenza = regioni[idPartenza].getPosizionePecore();
		Point arrivo = regioni[idArrivo].getPosizionePecore();
		pecoraAnimazione.setLocation(partenza);
		add(pecoraAnimazione); repaint(); revalidate();
		animazione(pecoraAnimazione, partenza, arrivo);
		remove(pecoraAnimazione);
		repaint(); revalidate();
	}

	/**
	 * Metodo che aggiunge il pannello del dado.
	 */
	public void aggiungiDado() {
		dado = new DadoPanel(ponte);
		dado.setLocation(387, 599);
		this.add(dado);
		
	}
	/**
	 * Metodo che aggiunge il contatore dei cancelli.
	 */
	private void aggiungiContatoreCancelli() {
		contatoreCancelli = new ContatoreCancelliPanel();
		contatoreCancelli.setLocation(420,520);
		this.add(contatoreCancelli);
	}
	
	/**
	 * metodo che incremementa il conteggio dei cancelli.
	 */
	public void aumentaContatoreCancelli(){
		contatoreCancelli.incrementa();
	}
	
	/**
	 * Metodo che abilita la pressione del dado.
	 */
	public void abilitaDado(){
		dado.attiva();
	}
	
	/**
	 * Metodo che disabilita la pressione del dado.
	 */
	public void disabilitaDado(){
		dado.disattiva();
	}

	/**
	 * Metodo che restituisce la lavagna.
	 */
	public MessaggiPanel getLavagna() {
		return lavagna;
	}

	/**
	 * metodo che restituisce una carta del pannello in base ad un indice dato.
	 * @param ordinal
	 * @return
	 */
	public CartaPanel getCarta(int ordinal) {
		return carteDisponibili[ordinal];		
	}
	
	/**
	 * Metodo che setta un valore intero nel contatore dei cancelli.
	 * @param num
	 */
	public void settaNumeroCancelli(int num){
		contatoreCancelli.settaNum(num);
	}
	
	/**
	 * Metodo che permette la visualizzazzione del mercato.
	 * @param offerteMercato
	 */
	public void visualizzaMercato(ArrayList<Offerta> offerteMercato){
		mercato.visualizza(offerteMercato);
	}
	
	/**
	 * metodo che nasconde il mercato.
	 */
	public void nascondiMercato(){
		mercato.nascondi();
	}

	/**
	 * Metodo che mostra il pannello delle offerte.
	 * @return
	 */
	public OffriPanel getOffriPanel(){
		return offriPanel;
	}

	/**
	 * Metodo che restistuisce tutte le carte personali ( intese come pannelli ).
	 * @return
	 */
	public CartePersonaliPanel getCartePersonali() {
		return cartePersonali;
	}
	
	

}
