package view.graphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Classe che estende JPanel e mostra i dati dei giocatori ( nome e soldi ).
 * 
 *
 */
public class GiocatoriPanel extends JPanel{
	private static final long serialVersionUID = 6878083474704247188L;
	private static final int L=200, H=70;
	private String[] nomi;
	private int[] danari;
	private JLabel[] giocatori;

	private static final Color INDACO = new Color(0,162,232);
	private static final Color VERDE = new Color(34,177,76);
	private static final Color GIALLO = new Color(255,242,0);
	private static final Color ROSSO = new Color(237,28,36);
	
	/**
	 * Costruttore inizializza le dimensioni e i componenti.
	 * @param num Rappresenta Il numero di giocatori
	 */
	public GiocatoriPanel (int num){
		super();
		setSize(new Dimension(L,H));
		setMaximumSize(getSize());
		setMinimumSize(getSize());
		setOpaque(false);
		Color[] colori = {ROSSO, INDACO, VERDE, GIALLO};
		setLayout(new GridLayout(num, 1));
		giocatori = new JLabel[num];
		danari = new int[num];
		nomi = new String[num];
		Font font = new Font("Verdana", Font.BOLD, 13);
		for (int i=0; i<num; i++){
			if(num==2)
				danari[i] = 30;
			else 
				danari[i] = 20;
			nomi[i] = "Giocatore "+(i+1);
			giocatori[i] = new JLabel("    "+nomi[i]+" : "+danari[i]+" danari");
			giocatori[i].setForeground(colori[i]);
			giocatori[i].setFont(font);
			add(giocatori[i]);
			repaint(); revalidate();
		}	
	}
	
	/**
	 * Metodo che setta un valore di denari per un dato giocatore.
	 * @param giocatore L'indice del giocatore
	 * @param danari La cifra di danari da impostare
	 */
	public void settaDanari(int giocatore, int danari){
		this.danari[giocatore] = danari;
		giocatori[giocatore].setText("   "+nomi[giocatore]+" : "+danari+" danari");
	}
	
	/**
	 * Metodo che incrementa i danari di un giocatore di un dato numero.
	 * @param somma La crifra da aumentare.
	 * @param giocatore L'id del giocatore.
	 */
	public void aumentaDanari(int somma, int giocatore){
		this.danari[giocatore] = danari[giocatore] + somma;
		giocatori[giocatore].setText("   "+nomi[giocatore]+" : "+danari[giocatore]+" danari");
	}
	
	/**
	 * Metodo che dimuinsce i danari di un giocatore di un dato numero.
	 * @param somma La cifra da diminuire
	 * @param giocatore L'id del giocatore
	 */
	public void diminuisciDanari(int somma, int giocatore){
		this.danari[giocatore] = danari[giocatore] - somma;
		giocatori[giocatore].setText("   "+nomi[giocatore]+" : "+danari[giocatore]+" danari");
	}
}
