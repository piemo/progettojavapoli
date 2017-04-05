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
 * Classe che estende JPAnel e viene usata sopra le mosse per abilitare o meno la scelta.
 * @author lorenzo
 *
 */
public class MossaPanel extends JPanel implements MouseListener {
	private static final long serialVersionUID = 5227776243582907420L;
	private int id;
	private static final int L = 215;
	private static final int H = 70;
	private PonteLogicaGrafica ponte;
	private boolean attiva = false;
	private JLabel copriMossa;
	private Image copertura;

	/**
	 * Costruttore che inizializza i componenti e il listener.
	 * @param id Id della mossa
	 * @param ponte
	 */
	public MossaPanel(int id , PonteLogicaGrafica ponte) {
		super();
		this.id = id;
		this.ponte = ponte;
		setSize(new Dimension(L, H));
		setMaximumSize(getSize());
		setMinimumSize(getSize());
		setOpaque(false);
		setLayout(new BorderLayout());
		addMouseListener(this);
		try{
			copertura = ImageIO.read(getClass().getResource("/copriMossa.png"));
		}catch (IOException e){
			e.printStackTrace();
		}
		copriMossa = new JLabel(new ImageIcon(copertura));
		add(copriMossa, BorderLayout.CENTER);
	}
	
	/**
	 * Metodo che attiva la pressione della mossa
	 */
	public void attivaPressione(){
		attiva = true;
		remove(copriMossa);

	}
	
	/**
	 * Metodo che disattiva la pressione della mossa
	 */
	public void disattivaPressione(){
		add(copriMossa,BorderLayout.CENTER);
		attiva =false;
	}
	
	/**
	 * Metodo che attiva una mossa, rendendola visibile e cliccabile
	 */
	public void attiva() {
		attivaPressione();
		repaint(); revalidate();
	}

	/**
	 * Metodo che disattiva una mossa, rendendola coperta e non cliccabile
	 */
	public void disattiva() {
		disattivaPressione();
		repaint(); revalidate();
	}
	
	/**
	 * MEtodo usato per rilevare il click del mouse quando la mossa è abilitata
	 */
	public void mousePressed(MouseEvent arg0) {
		if (attiva)
			ponte.setClick(id);
	}

	/**
	 * Non usato.
	 */
	public void mouseClicked(MouseEvent arg0) {
	}

	/**
	 * Metodo usato per trasformare il cursore in una manina quando la mossa è abilitata.
	 */
	public void mouseEntered(MouseEvent arg0) {
		if(attiva)
			setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	/**
	 * metodo usato per trasformare il cursore da manina a normale.
	 */
	public void mouseExited(MouseEvent arg0) {
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
	
	/**
	 * Non usato.
	 */
	public void mouseReleased(MouseEvent arg0) {
	}

}
