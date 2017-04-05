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
 * Classe che estende JPanel. rappresenta un componente che evidenzia la scelta di un pastore.
 *
 *
 */
public class SceltaPastorePanel extends JPanel implements MouseListener{
	private static final long serialVersionUID = 608554652043359290L;
	private int id;
	private PonteLogicaGrafica ponte;
	private boolean attivo=false;
	private JLabel rect;
	private Image rettangolo = null;
	private static final int L=31, H=62;
	
	/**
	 * costruttore che inserisce i componenti e le dimensioni del pannello.
	 * @param ponte
	 * @param id
	 */
	public SceltaPastorePanel(PonteLogicaGrafica ponte, int id) {
		super();
		this.id = id;
		this.ponte = ponte;
		setSize(new Dimension(L,H));
		setMaximumSize(getSize());
		setMinimumSize(getSize());
		setLayout(new BorderLayout());
		setOpaque(false);
		try{
			rettangolo = ImageIO.read(getClass().getResource("/sceltaPastore.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		rect = new JLabel(new ImageIcon(rettangolo));
		addMouseListener(this);
	}
	
	
	/**
	 * Attiva il click sulla regione aggiungengo lo sfondo.
	 */
	public void attiva(){
		attivo = true;
		add(rect);
		repaint(); revalidate();
	}
	
	/**
	 * Disattiva il click sulla regione togliendo il quadrato di selezione.
	 */
	public void disattiva(){
		attivo = false;
		remove(rect);
		repaint(); revalidate();
	}
	

	/**
	 * Se il pannello è attivo, fa diventare il mouse una manina.
	 */
	public void mouseEntered(MouseEvent arg0) {
		if(attivo)
			setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	/**
	 * Fa diventare il muouse un puntatore quando si lascai l'area del pannello.
	 */
	public void mouseExited(MouseEvent arg0) {
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * Se il pannello è attivo registra il click nel ponte.
	 */
	public void mousePressed(MouseEvent arg0) {
		if(attivo)
			ponte.setClick(id);	
	}

	/**
	 * Non usato.
	 */
	public void mouseReleased(MouseEvent arg0) {}
	/**
	 * non usato.
	 */
	public void mouseClicked(MouseEvent arg0) {}
}
