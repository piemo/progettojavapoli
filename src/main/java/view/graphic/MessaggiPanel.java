package view.graphic;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.Giocatore;

/**
 * Classe che estende JPanel. Usato per i messaggi sulla lavagna.
 * 
 *
 */
public class MessaggiPanel extends JPanel{
	private static final long serialVersionUID = -2791295552452240583L;
	private static final int L=180, H=45;
	private JLabel messaggio1, messaggio2;
	private static final Color INDACO = new Color(0,162,232);
	private static final Color VERDE = new Color(34,177,76);
	private static final Color GIALLO = new Color(255,242,0);
	private static final Color ROSSO = new Color(237,28,36);
	private static final Color[] COLORI_GIOCATORI = {ROSSO, INDACO, VERDE, GIALLO};
	private static final int TEMPO = 1000;
	private static final int DIM = 13;
	
	/**
	 * csotruttore che inizializza le scritte sulla lavagna
	 */
	public MessaggiPanel() {
		super();
		this.setOpaque(false);
		setSize(new Dimension(L,H));
		setMaximumSize(getSize());
		setMinimumSize(getSize());
		setBackground(new Color(0,0,0,0));
		setLayout(new GridLayout(2, 1));
		messaggio1 = new JLabel();
		messaggio2 = new JLabel();
		Font font = new Font("Verdana", Font.BOLD, DIM);
		messaggio1.setFont(font); messaggio2.setFont(font);
		add(messaggio1); add(messaggio2);
		settaTesto("Benvenuti a", "SHEEPLAND!");
		settaColori(INDACO, GIALLO);
	}
	
	/**
	 * Metodo che permettte di settare il testo sulle 2 linee della lavagna
	 * @param testo1 Stringa da inserire sulla prima riga grande
	 * 
	 * @param testo2 Stringa da inserire sulla seconda riga piccola.
	 */
	private void settaTesto(String testo1, String testo2){
		messaggio1.setText(testo1);
		messaggio1.setHorizontalAlignment(SwingConstants.CENTER);
		messaggio1.setHorizontalTextPosition(SwingConstants.CENTER);
		repaint(); revalidate();
		messaggio2.setText(testo2);
		messaggio2.setHorizontalAlignment(SwingConstants.CENTER);
		messaggio2.setHorizontalTextPosition(SwingConstants.CENTER);
		repaint(); revalidate();
	}
	
	/**
	 * Metodo che imposta i colori delle righe della lavagna
	 * @param colore1 Colore della prima riga
	 * @param colore2 Colore della seconda riga
	 */
	private void settaColori(Color colore1, Color colore2){
		messaggio1.setForeground(colore1);
		repaint(); revalidate();
		messaggio2.setForeground(colore2);
		repaint(); revalidate();
	}
	
	/**
	 * Metodo che annuncia il turno di un giocatore
	 * @param giocatore
	 */
	public void turno (Giocatore giocatore){
		settaColori(COLORI_GIOCATORI[giocatore.getIndice()], COLORI_GIOCATORI[giocatore.getIndice()]);
		settaTesto("TURNO DI  ", giocatore.toString());
		Util.timer(TEMPO);
	}
	
	/**
	 * Metodo che ricorda al giocatore di posizionare un pastore.
	 * @param giocatore
	 */
	public void posizionamentoPastore(Giocatore giocatore){
		settaColori(COLORI_GIOCATORI[giocatore.getIndice()], COLORI_GIOCATORI[giocatore.getIndice()]);
		settaTesto(giocatore.toString(), "posiziona il pastore");
	}
	
	/**
	 * Metodo che annuncia il blocco di un pastore.
	 */
	public void pastoreBloccato(){
		settaTesto("ATTENZIONE!", "Il pastore è bloccato!");
		Util.timer(TEMPO);
	}


	/**
	 * Metodo che dice all'utente di scegliere una mossa.
	 * @param giocatore
	 */
	public void sceltaMossa(Giocatore giocatore) {
		settaColori(COLORI_GIOCATORI[giocatore.getIndice()], COLORI_GIOCATORI[giocatore.getIndice()]);
		settaTesto(giocatore.toString()+" ", "Scegli una mossa! ->");
	}

	/**
	 * Metodo che avvisa che un gicoatore salta il turno!
	 * @param giocatore
	 */
	public void saltaTurno(Giocatore giocatore) {
		settaColori(COLORI_GIOCATORI[giocatore.getIndice()], COLORI_GIOCATORI[giocatore.getIndice()]);
		settaTesto(giocatore.toString()+" ", "Salta il Turno!");
		Util.timer(TEMPO);
	}

	/**
	 * Metodo che dice al giocatore di scegliere un pastore.
	 * @param giocatore
	 */
	public void sceltaPastore(Giocatore giocatore) {
		settaColori(COLORI_GIOCATORI[giocatore.getIndice()], COLORI_GIOCATORI[giocatore.getIndice()]);
		settaTesto(giocatore.toString()+" ", "Scegli un pastore! ->");
	}

	/**
	 * Metodo che annuncia la fine del turno di un dato giocatore.
	 * @param giocatore
	 */
	public void fineTurno(Giocatore giocatore) {
		settaColori(COLORI_GIOCATORI[giocatore.getIndice()], COLORI_GIOCATORI[giocatore.getIndice()]);
		settaTesto("FINE TURNO  ", "di "+giocatore.toString());
		Util.timer(TEMPO);
	}

	/**
	 * Metodo che annuncia il movimento della pecora nera.
	 */
	public void movimentoPecoraNera() {
		settaColori(Color.WHITE, Color.WHITE);
		settaTesto("Movimento casuale", "PECORA NERA");
	}

	/**
	 * Metodo che chiede ad un giocatore di scelgiere una strada su cui posizionare un pastore.
	 * @param giocatore
	 */
	public void sceltaStradaPastore(Giocatore giocatore) {
		settaColori(COLORI_GIOCATORI[giocatore.getIndice()], COLORI_GIOCATORI[giocatore.getIndice()]);
		settaTesto(giocatore.toString()+" ", "Scegli una strada!");		
	}

	/**
	 * Metodo che chiede al giocatore di scegliere una carta.
	 * @param giocatore
	 */
	public void sceltaCarta(Giocatore giocatore) {
		settaColori(COLORI_GIOCATORI[giocatore.getIndice()], COLORI_GIOCATORI[giocatore.getIndice()]);
		settaTesto(giocatore.toString()+" ", "Scegli una carta! ->");		
	}


	/**
	 * Metodo che chiede al giocatore di scegliere una regione dove effettuare l'abbattimento.
	 * @param giocatore
	 */
	public void sceltaRegioneAbbattimento(Giocatore giocatore) {
		settaColori(COLORI_GIOCATORI[giocatore.getIndice()], COLORI_GIOCATORI[giocatore.getIndice()]);
		settaTesto("Scegli dove  ", "abbattere una pecora!");	
	}

	/**
	 * Metodo che chiede al giocatore che tipo di animale abnbattare.
	 * @param giocatore
	 */
	public void sceltaAnimaleDaAbbattere(Giocatore giocatore) {
		settaColori(COLORI_GIOCATORI[giocatore.getIndice()], COLORI_GIOCATORI[giocatore.getIndice()]);
		settaTesto("Scegli la  ", "pecora da uccidere! ->");
	}

	/**
	 * Metodo che annuncia l'abbattimento di un animale
	 * @param animaleAbbattuto tipo di animale abbattuto
	 */
	public void abbattimento(int animaleAbbattuto) {
		settaColori(Color.WHITE, Color.WHITE);
		switch (animaleAbbattuto) {
		case(0):
		 settaTesto("Viene ucciso  ", "un agnellino!");
		break;
		case(1):
			 settaTesto("Viene uccisa  ", "una pecora femmina!");
			break;
		case(2):
			 settaTesto("Viene ucciso  ", "un montone!");
			break;
		case(3):
			 settaTesto("Viene uccisa  ", "la pecora nera!");
			break;
		}
		
	}


	/**
	 * Metodo che chiede al giocatore di scegliere una regione.
	 * @param giocatore
	 */
	public void sceltaRegione(Giocatore giocatore) {
		settaColori(COLORI_GIOCATORI[giocatore.getIndice()], COLORI_GIOCATORI[giocatore.getIndice()]);
		settaTesto("Scegli    ", "una regione!");
	}

	/**
	 * metodo che chiede a un giocatore di scegliere un tipo di pecora da spostare.
	 * @param giocatore
	 */
	public void sceltaPecoraDaSpostare(Giocatore giocatore) {
		settaColori(COLORI_GIOCATORI[giocatore.getIndice()], COLORI_GIOCATORI[giocatore.getIndice()]);
		settaTesto("Scegli la  ", "pecora da spostare ->");
	}

	/**
	 * Metodo che annuncia lo spostamento di un animale
	 * @param pecoraScelta Id del tipo di animale
	 */
	public void spostamentoPecora(int pecoraScelta) {
		settaColori(Color.WHITE, Color.WHITE);
		switch (pecoraScelta) {
		case(0):
		 settaTesto("Viene spostato  ", "un agnellino!");
		break;
		case(1):
			 settaTesto("Viene spostata  ", "una pecora femmina!");
			break;
		case(2):
			 settaTesto("Viene spostato  ", "un montone!");
			break;
		case(3):
			 settaTesto("Viene spostata  ", "la pecora nera!");
			break;
		}
		
	}

	/**
	 * Metodo che annuncia il movimento del lupo	
	 */
	public void movimentoLupo() {
		settaColori(Color.WHITE, Color.WHITE);
		settaTesto("Attenzione!", "Il LUPO si muove!");
	}

	/**
	 * Metodo che annuncia il pagamento del silenzio da parte di un giocatore ad un altro.
	 * @param giocatore
	 * @param giocatoreDaPagare
	 */
	public void pagaSilenzio(Giocatore giocatore,Giocatore giocatoreDaPagare) {
		settaColori(COLORI_GIOCATORI[giocatore.getIndice()], COLORI_GIOCATORI[giocatore.getIndice()]);
		settaTesto(giocatore.toString()+" ", "paga "+giocatoreDaPagare);
		Util.timer(TEMPO/3);
	}

	/**
	 * Metodo che annuncia la vittoria di un dato giocatore.
	 * @param vincitore
	 */
	public void annunciaVincitore(Giocatore vincitore) {
		settaColori(COLORI_GIOCATORI[vincitore.getIndice()], COLORI_GIOCATORI[vincitore.getIndice()]);
		settaTesto("** "+vincitore.toString()+" ** ", "VINCE!");
		Util.timer(10*1000);
	}

	/**
	 * Metodo che avvisa l'inizio della fase finale del gioco.
	 */
	public void faseFinale() {
		settaColori(Color.WHITE, Color.WHITE);
		settaTesto("Attenzione!  ", "Inizia la fase finale!");
		Util.timer(TEMPO/2);
	}


	/**
	 * Metodo che chiede a un gicoatore di tirare un dado.
	 * @param giocatore
	 */
	public void tiraDado (Giocatore giocatore) {
		settaColori(COLORI_GIOCATORI[giocatore.getIndice()], COLORI_GIOCATORI[giocatore.getIndice()]);
		settaTesto(giocatore.toString()+" ", "Tira il dado!!");	
	}

	/**
	 * metodo che annuncia il risultato del tiro del dado.
	 * @param valoreDado
	 */
	public void risultatoDado (int valoreDado) {
		settaColori(Color.WHITE, Color.WHITE);
		settaTesto("Risultato dado: ", "      "+valoreDado);
		Util.timer((int) (TEMPO*1.5));
	}

	/**
	 * Metodo che annuncia la fine della partita.
	 */
	public void finePartita() {
		settaColori(Color.WHITE, Color.WHITE);
		settaTesto("FINE", "PARTITA");
		Util.timer((int) (TEMPO*1.5));
	}

	/**
	 * Metodo che dice al giocatore di tirare il dado, ricordandogli che valore deve uscire.
	 * @param giocatore
	 * @param risultatoAtteso
	 */
	public void tiraDadoAbbattimento(Giocatore giocatore, int risultatoAtteso) {
		tiraDado(giocatore);
		Util.timer(TEMPO);
		settaTesto("Deve uscire ", "   "+risultatoAtteso);	
	}

	/**
	 * Metodo che ricorda che è stato scelto di effettuare un accoppiamento.
	 */
	public void accoppiamento() {
		settaColori(Color.WHITE, Color.WHITE);
		settaTesto("", "Accoppiamento!");
		
	}

	/**
	 * Metodo che ricorda che è stato scelto di effettuare un acquisto di una carta.
	 * 	 
	 */
	public void acquistoCarta(Giocatore giocatore) {
		settaColori(COLORI_GIOCATORI[giocatore.getIndice()], COLORI_GIOCATORI[giocatore.getIndice()]);
		settaTesto("Acquisto", "carta! ->");
	}

	/**
	 * Metodo che ricorda che è stato scelto di effettuare un movimento di un pastore.	 
	 * @param giocatore
	 */
	public void movimentoPastore(int giocatore) {
		settaColori(COLORI_GIOCATORI[giocatore], COLORI_GIOCATORI[giocatore]);
		settaTesto("Movimento", "pastore!");
	}

	/**
	 * Metodo che annucnia l'inizio del mercato
	 */
	public void inizioMercato() {
		settaTesto("INIZIO", "MERCATO!");
		settaColori(INDACO, VERDE);
		Util.timer(TEMPO/4);
		settaColori(VERDE, GIALLO);
		Util.timer(TEMPO/4);
		settaColori(GIALLO, ROSSO);
		Util.timer(TEMPO/4);
		settaColori(ROSSO, INDACO);
		Util.timer(TEMPO/4);
		settaColori(INDACO, VERDE);
		Util.timer(TEMPO/4);
		settaColori(VERDE, GIALLO);
		Util.timer(TEMPO/4);
		settaColori(GIALLO, ROSSO);
		Util.timer(TEMPO/4);
		settaColori(ROSSO, INDACO);
		Util.timer(TEMPO/4);
		settaTesto("Attendi il", "tuo turno...");
		settaColori(Color.WHITE, Color.WHITE);
	}

	/**
	 * Metodo che annuncia l'inizio degli acquisti del mercato
	 */
	public void inizioAcquistiMercato() {
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		settaTesto("Inizio", "acquisti");
		settaColori(INDACO, VERDE);
		Util.timer(TEMPO/4);
		settaColori(VERDE, GIALLO);
		Util.timer(TEMPO/4);
		settaColori(GIALLO, ROSSO);
		Util.timer(TEMPO/4);
		settaColori(ROSSO, INDACO);
		Util.timer(TEMPO/4);
		settaColori(Color.WHITE, Color.WHITE);
		settaTesto("Attendi", "il tuo turno...");
	}

	/**
	 * Metodo che annuncia la fine della fase del mercato.
	 */
	public void fineMercato() {
		settaTesto("FINE", "MERCATO!");
		settaColori(INDACO, VERDE);
		Util.timer(TEMPO/4);
		settaColori(VERDE, GIALLO);
		Util.timer(TEMPO/4);
		settaColori(GIALLO, ROSSO);
		Util.timer(TEMPO/4);
		settaColori(ROSSO, INDACO);
		Util.timer(TEMPO/4);
		settaColori(INDACO, VERDE);
		Util.timer(TEMPO/4);
		settaColori(VERDE, GIALLO);
		Util.timer(TEMPO/4);
		settaColori(GIALLO, ROSSO);
		Util.timer(TEMPO/4);
		settaColori(ROSSO, INDACO);
		Util.timer(TEMPO/4);
		
	}

	/**
	 * Metodo che avvisa che è arrivato il turno di partecipare nel mercato
	 * @param giocatore
	 */
	public void turnoMercato(Giocatore giocatore) {
		settaColori(COLORI_GIOCATORI[giocatore.getIndice()], COLORI_GIOCATORI[giocatore.getIndice()]);
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		settaTesto(""+giocatore, "tocca a te!");
		Util.timer((int) (TEMPO*1.5));
	}

	/**
	 * @param giocatore
	 */
	public void offertaFatta(Giocatore giocatore) {
		settaColori(Color.WHITE, Color.WHITE);
		settaTesto("Attendi", "gli altri");
	}

}
