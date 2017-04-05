package view.graphic;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Classe che estende JPanel e rappresta il pannello mostrato quando si deve offrire qualcosa al mercato.
 *
 */
public class OffriPanel extends JPanel {
	private static final long serialVersionUID = 6515093504121737046L;
	private PonteLogicaGrafica ponte;
	private static final int NUM_CARTE = 6;
	private CartaPanel[] carte = new CartaPanel[NUM_CARTE];
	private MossaPanel cancellazioneMossaPanel;
	private JLabel sfondo;
	private PrezzoFrame prezzoFrame;
	
	/*
	 * Costruttore che inizalizza i componenti e le dimensioni.
	 */
	public OffriPanel (int larghezza, int altezza, PonteLogicaGrafica ponte){
		this.ponte = ponte;
		this.setSize(larghezza, altezza);
		this.setMinimumSize(getSize());
		this.setMaximumSize(getSize());
		this.setOpaque(false);
		this.setLayout(null);
		setLayout(null);
		aggiuntaCarte();
		aggiungiCancellazione();
		aggiungiSfondo();
		nascondi();
		prezzoFrame = new PrezzoFrame(ponte);
		add(prezzoFrame);
		prezzoFrame.setVisible(false);
		validate();
	}
	
	/** 
	 * Metodo che aggiunge un pannello  che permette di annullare la fase di offerta.
	 */
	private void aggiungiCancellazione(){
		cancellazioneMossaPanel = new MossaPanel(-1, ponte);
		cancellazioneMossaPanel.setLocation(338, 520);
		this.add(cancellazioneMossaPanel);
	}
	
	/**
	 * MEtodo che carica lo sfondo e lo aggiunge al pannello.
	 */
	private void aggiungiSfondo(){
		Image sfondoImg =null;
		try {
			 sfondoImg= ImageIO.read(getClass().getResource("/offertamercato.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sfondo = new JLabel(new ImageIcon(sfondoImg));
		sfondo.setSize(this.getSize().width, this.getSize().height);
		sfondo.setBackground(new Color(0,0,0,0));

		this.add(sfondo);
	}
	
	/**
	 * Metodo che aggiunge i pannelli delle carte.
	 */
	private void aggiuntaCarte() {
		final int X1=341,X2=480, Y=217;
		int i=0;
		int primaOrSeconda =1;
		for (; i<NUM_CARTE; i++){
			if(primaOrSeconda==1){
				carte[i] = new CartaPanel(ponte, i);
				this.add(carte[i]);
				carte[i].setLocation(X1,Y+((i/2)*(100)));
				primaOrSeconda=2;
			}

			else {
				carte[i] = new CartaPanel(ponte, i);
				this.add(carte[i]);
				carte[i].setLocation(X2,Y+((i/2)*(100)));
				primaOrSeconda=1;

			}
		}

	}
	
	/**
	 * Metodo che riceve le carte disponibili, attiva quelle che si possono acquistare e disattiva le rimanenti. Successivamente mostra il pannello.
	 * @param carteDisponibili array di interi che rappresenta la quantitò di carte possedute
	 */
	public void visualizza(int[] carteDisponibili){
		aggiuntaCarte();
		aggiungiSfondo();
		repaint();revalidate();
		for (int i =0; i<carteDisponibili.length;i++) {
			carte[i].settaCarte(carteDisponibili[i]);
			if(carteDisponibili[i]>0)
				carte[i].attivaPressione();
			else {
				carte[i].disattivaPressione();
			}
		}
		cancellazioneMossaPanel.attivaPressione();
		this.setVisible(true);
		repaint();revalidate();
	}
	
	/**
	 * Metodo che nasconde il pannello.
	 */
	public void nascondi(){
		for(int i=0; i<NUM_CARTE;i++){
			remove(carte[i]);
		}
		this.remove(sfondo);
		cancellazioneMossaPanel.disattivaPressione();
		this.setVisible(false);
		repaint();revalidate();
	}
	
	/**
	 * metodo che apre un frame nel quale inserire il prezzo dell'offerta.
	 * @param idCarta
	 * @return
	 */
	public int getPrezzoCarta(int idCarta){
		prezzoFrame.settaCarta(idCarta);
		prezzoFrame.setVisible(true);
		prezzoFrame.grabFocus();
		repaint(); revalidate();
		ponte.reset();
		int scelta= ponte.whatClicked();
		prezzoFrame.setVisible(false);
		repaint(); revalidate();
		return scelta;
	}
	
	/**
	 *metodo che disattiva tutte le carte. 
	 */
	public void disattiva(){
		for(int i=0; i<NUM_CARTE ; i++){
			carte[i].disattivaPressione();
		}
	}
}
