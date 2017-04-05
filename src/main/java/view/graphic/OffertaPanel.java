package view.graphic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.Giocatore;
import controller.Offerta;

/**
 * Classe che estende JPanel e viene usato nel pannello del mercato al fine di mostrare la offerta.
 *
 *
 */
public class OffertaPanel  extends JPanel implements MouseListener {
	private static final long serialVersionUID = 5227776243582907420L;
	private int id;
	private PonteLogicaGrafica ponte;
	private boolean attiva = false;
	private JLabel offerta;
	private Image[] carte = new Image[6];
	private static final Color INDACO = new Color(0,162,232);
	private static final Color VERDE = new Color(34,177,76);
	private static final Color GIALLO = new Color(255,242,0);
	private static final Color ROSSO = new Color(237,28,36);
	private static final Color[] COLORI_GIOCATORI = {ROSSO, INDACO, VERDE, GIALLO};
	
	/**
	 * Costruttore che inizializza i componenti e le dimenioni.
	 * @param id id dell'offerta
	 * @param larghezza 
	 * @param altezza
	 * @param ponte
	 */
	public OffertaPanel(int id , int larghezza, int altezza, PonteLogicaGrafica ponte) {
		super();
		this.id = id;
		this.ponte = ponte;
		setSize(new Dimension(larghezza, altezza));
		setMaximumSize(getSize());
		setMinimumSize(getSize());
		setOpaque(false);
		setLayout(new BorderLayout());
		addMouseListener(this);
		try{
			carte[0] = ImageIO.read(getClass().getResource("/agricolo.png"));
			carte[1] = ImageIO.read(getClass().getResource("/arido.png"));
			carte[2] = ImageIO.read(getClass().getResource("/fiume.png"));
			carte[3] = ImageIO.read(getClass().getResource("/foresta.png"));
			carte[4] = ImageIO.read(getClass().getResource("/montagna.png"));
			carte[5] = ImageIO.read(getClass().getResource("/prato.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		offerta = new JLabel();
		this.validate();
	}
	
	/**
	 * Metodo che imposta l'offerta all'interno del mercato.
	 * @param nuovaOfferta
	 */
	public void settaOfferta(Offerta nuovaOfferta){
		int prezzo = nuovaOfferta.getPrezzo();
		int tipo = nuovaOfferta.getTipoCarta().ordinal();
		Giocatore venditore = nuovaOfferta.getVenditore();
		offerta = new JLabel("Prezzo: "+prezzo+"   ["+venditore+"]", 
							new ImageIcon(carte[tipo]), SwingConstants.LEADING);
		offerta.setFont(new Font("Verdana", Font.BOLD, 18));
		offerta.setForeground(COLORI_GIOCATORI[venditore.getIndice()]);
		add(offerta,BorderLayout.CENTER);
		repaint(); revalidate();
	}
	
	/**
	 * Metodo che attiva la pressione del pannello.
	 */
	public void attivaPressione(){
		this.attiva =true;
	}
	
	/**
	 * Metodo che disaattiva la pressione del pannello.
	 */
	public void disattivaPressione(){
		this.attiva =false;
	}
	
	/**
	 * metodo che registra la pressione nel ponte quando l'offerta è attiva.
	 */
	public void mousePressed(MouseEvent arg0) {
		if (attiva)
			ponte.setClick(id);
	}

	/**
	 * non usato.
	 */
	public void mouseClicked(MouseEvent arg0) {
	}

	/**
	 * metodo che trasforma il cursore in una manina quando il panello è attivato.
	 */
	public void mouseEntered(MouseEvent arg0) {
		if(attiva)
			setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	/**
	 * Trasforma il cursore da manina a puntatore quando si esce dall'area del pannello
	 */
	public void mouseExited(MouseEvent arg0) {
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * Non usato.
	 */
	public void mouseReleased(MouseEvent arg0) {
	}

	/**
	 * Metodo che rimuove l'offerta.
	 */
	public void togliOfferta() {
		remove(offerta);
		repaint(); revalidate();
	}
}
