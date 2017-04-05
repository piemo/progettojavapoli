package view.graphic;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Offerta;

/**
 * Classe che estende Jpanel. Usato per mostrare la fase di acquisto del mercato.
 * @author lorenzo
 *
 */
public class MercatoPanel extends JPanel {
	private static final long serialVersionUID = -8357459472731701007L;
	private PonteLogicaGrafica ponte;
	private MossaPanel cancellazioneMossaPanel;
	private static final int MAX_OFFERTE =4;
	private OffertaPanel[] offerte;
	
	/**
	 * Costruttore che inizializza gli elementi del pannello
	 * @param larghezza
	 * @param altezza
	 * @param ponte
	 */
	public MercatoPanel (int larghezza, int altezza, PonteLogicaGrafica ponte){
		this.ponte = ponte;
		this.setSize(larghezza, altezza);
		this.setMinimumSize(getSize());
		this.setMaximumSize(getSize());
		this.setOpaque(false);
		this.setLayout(null);
		setLayout(null);
		aggiungiofferte();
		aggiungiCancellazione();
		aggiungiSfondo();
		nascondi();
		validate();}
	

	/**
	 * metodo che carica lo sfondo da file e lo inserisce nel pannello principale
	 */
	private void aggiungiSfondo(){
		Image sfondoImg =null;
		try {
			 sfondoImg= ImageIO.read(getClass().getResource("/acquistoOfferta.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JLabel sfondo = new JLabel(new ImageIcon(sfondoImg));
		sfondo.setSize(this.getSize().width, this.getSize().height);
		sfondo.setBackground(new Color(0,0,0,0));

		this.add(sfondo);
	}
	
	/**
	 * Metodo che aggiunge il bottone di annullamento dell'acquisto nel mercato.
	 */
	private void aggiungiCancellazione(){
		cancellazioneMossaPanel = new MossaPanel(-1, ponte);
		cancellazioneMossaPanel.setLocation(400, 550);
		this.add(cancellazioneMossaPanel);
	}
	
	/**
	 * Metodo che inizializza i 4 pannelli delle offerte nel mercato
	 */
	private void aggiungiofferte() {
		offerte=new OffertaPanel[MAX_OFFERTE];
		for (int i =0; i<MAX_OFFERTE;i++) {
			offerte[i] =new OffertaPanel(i,400,75,ponte);
			offerte[i].setLocation(303,173+i*(92));
			this.add(offerte[i]);
		}
	}
	
	/**
	 * Metodo che inserisce le offerte effettive dentro i 4 pannelli di offerta e ne abilita la pressione dove possibile
	 * @param offerte
	 */
	public void visualizza(ArrayList<Offerta> offerte){
		for (int i =0; i<offerte.size();i++) {
			this.offerte[i].settaOfferta(offerte.get(i));
			this.offerte[i].attivaPressione();
			this.offerte[i].setVisible(true);
		}
		cancellazioneMossaPanel.attivaPressione();
		this.setVisible(true);
		repaint();revalidate();
	}
	
	/**
	 * Metodo che nasconde il pannello del mercato.
	 */
	public void nascondi(){
		for (int i =0; i<MAX_OFFERTE;i++) {
			this.offerte[i].togliOfferta();
			this.offerte[i].disattivaPressione();
			this.offerte[i].setVisible(false);
		}
		cancellazioneMossaPanel.disattivaPressione();
		repaint();revalidate();
		this.setVisible(false);
	}
}
