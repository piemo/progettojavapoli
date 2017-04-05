package view.graphic;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * Classe che estende JPanel. Serve a creare un quadrato per evidenziare la scelta delle pecore.
 *
 */
public class SceltaTipoPecoraPanel extends JPanel implements MouseListener{
	private static final long serialVersionUID = -4578133849817546785L;
	private int ID;
	private boolean attiva=false;
	private PonteLogicaGrafica ponte;
	private static final int DIM = 52;
	private JLabel attivaLabel;
	private Image quadrato = null; 
	
	/**
	 * Costruttore che inserisce tutti i componenti dentro il pannello e lo dimensiona.
	 * @param ponte
	 * @param id
	 */
	public SceltaTipoPecoraPanel(PonteLogicaGrafica ponte, int id) {
		super();
		ID=id;
		this.ponte = ponte;
		setSize(new Dimension(DIM,DIM));
		setMaximumSize(getSize());
		setMinimumSize(getSize());
		setLayout(new BorderLayout());
		setOpaque(false);
		try{
			quadrato = ImageIO.read(getClass().getResource("/sceltaPecora.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		attivaLabel = new JLabel(new ImageIcon(quadrato));
		addMouseListener(this);
	}
	
	/**
	 * Attiva la pressione del pannello e mostra il quadrato.
	 */
	public void attiva() {
		attiva = true;
		add(attivaLabel,BorderLayout.CENTER);
		repaint(); revalidate();
	}

	/**
	 * Disattiva la pressione del pannello e nasconde il quadrato.
	 */
	public void disattiva() {
		attiva = false;
		remove(attivaLabel);
		repaint(); revalidate();
	}

	/**
	 * Se il pannello è attivo, fa diventare il mouse una manina.
	 */
	public void mouseEntered(MouseEvent arg0) {
		if (attiva)
			setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	/**
	 * Quando il mouse esce dall'area del panel, il cursore diventa un puntatore.
	 */
	public void mouseExited(MouseEvent arg0) {
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * Se il pannello è attivo, il click viene registrato nel ponte.
	 */
	public void mousePressed(MouseEvent arg0) {
		if (attiva)
			ponte.setClick(ID);
	}

	/**
	 * non usato.
	 */
	public void mouseReleased(MouseEvent arg0) {}
	/**
	 * Non usato.
	 */
	public void mouseClicked(MouseEvent arg0) {}
	
}
