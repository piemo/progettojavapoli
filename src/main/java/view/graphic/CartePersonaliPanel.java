package view.graphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Classe che estende JPanel usato per mostrare la carte personali possedute.
 * @author lorenzo
 *
 */
public class CartePersonaliPanel extends JPanel{
	private static final long serialVersionUID = -3836833366624595272L;
	private static final int L=40, H=648;
	private static final int NUM_CARTE = 6;
	private JLabel[] carte = new JLabel[NUM_CARTE];
	private int[] numCarte = new int[NUM_CARTE];
	
	/**
	 * Costruttore. Inizializza il pannello con dimensioni e contenuti.
	 */
	public CartePersonaliPanel() {
		super();
		setSize(new Dimension(L,H));
		setMaximumSize(getSize());
		setMinimumSize(getSize());
		setOpaque(false);
		setLayout(new GridLayout(NUM_CARTE, 1));
		Font font = new Font("Verdana", Font.BOLD, 19);
		for (int i=0; i<NUM_CARTE; i++){
			carte[i]= new JLabel("  "+numCarte[i]);
			carte[i].setFont(font);
			carte[i].setForeground(Color.WHITE);
			this.add(carte[i]);
		}
	}
	
	/**
	 * Metodo che setta il valore iniziale per tutte le carte possedute.
	 * @param cartPersonali array di interi rappresentate quante carte di un tipo si hanno.
	 */
	public void settaCarte(int[] cartPersonali){
		for(int i=0; i<NUM_CARTE; i++){
			numCarte[i]=cartPersonali[i];
			carte[i].setText("  "+numCarte[i]);
			repaint(); revalidate();
		}
	}
	
	/**
	 * Metodo che rimuove una carta di un dato tipo
	 * @param tipo
	 */
	public void aggiungiCarta(int tipo){
		numCarte[tipo]++;
		carte[tipo].setText("  "+numCarte[tipo]);
		repaint(); revalidate();
	}
	
	/**
	 * Metodo che aggiunge una carta di un dato tipo.
	 * @param tipo
	 */
	public void rimuoviCarta(int tipo){
		numCarte[tipo]--;
		carte[tipo].setText("  "+numCarte[tipo]);
		repaint(); revalidate();
	}
	
}
