package view.graphic;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 *Questa clase estende JPanel. Viene usata per definire le strade all'interno della grafica.
 *
 */
public class StradaButton extends JPanel implements MouseListener {
	private static final long serialVersionUID = -7101322939284476952L;
	private int ID;
	private PonteLogicaGrafica ponte;
	private boolean attivo = false;
	private static final int L = 30;
	private JLabel attivaVicina, attivaLontana, cancello;

	/**
	 * Costruttore che crea la strada, assegnando ad essa l'id della strada corrispondente nella logica.
	 * @param ponte
	 * @param ID Id della strada.
	 */
	public StradaButton(PonteLogicaGrafica ponte, int ID) {
		this.ponte = ponte;
		this.ID = ID;
		setSize(new Dimension(L, L));
		setMaximumSize(getSize());
		setMinimumSize(getSize());
		setOpaque(false);
		addMouseListener(this);
		setLayout(new BorderLayout());
		Image attivaVicinaImg = null, attivaLontanaImg=null, cancelloImg = null;
		try{
			attivaVicinaImg = ImageIO.read(getClass().getResource("/cerchioBlu.png"));
			attivaLontanaImg = ImageIO.read(getClass().getResource("/cerchioGiallo.png"));
			cancelloImg = ImageIO.read(getClass().getResource("/cancello.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		attivaVicina = new JLabel(new ImageIcon(attivaVicinaImg));
		attivaLontana = new JLabel(new ImageIcon(attivaLontanaImg));
		cancello = new JLabel(new ImageIcon(cancelloImg));
	}

	/**
	 * Metodo che evidenzia la strada di colore blu ed attiva la pressione.
	 */
	public void attivaVicina() {
		attivo = true;
		add(attivaVicina, BorderLayout.CENTER);
		repaint();
		revalidate();
	}

	/**
	 * Metodo che evidenzia la stgrada di colore arancio ed attiva la pressione.
	 */
	public void attivaLontana() {
		attivo = true;
		add(attivaLontana, BorderLayout.CENTER);
		repaint();
		revalidate();
	}

	/**
	 * Metodo che disattiva la pressione e toglie ogni tipo di evidenziazzione
	 */
	public void disattiva() {
		attivo = false;
		remove(attivaLontana); remove(attivaVicina);
		repaint(); revalidate();
	}
	
	/**
	 * Metodo che disattiva la pressione e toglie ogni tipo di evidenziazzione e anche eventuali cacnelli sopra di essa.
	 */
	public void clear(){
		attivo = false;
		remove(attivaLontana); remove(attivaVicina); remove(cancello);
		repaint(); revalidate();
	}

	/**
	 * Metodo che posziona un cancello sopra la strada.
	 */
	public void posizionaCancello() {
		attivo = false;
		add(cancello,BorderLayout.CENTER);
		repaint();
		revalidate();
	}
	
	
	/**
	 * Metodo che restituisce dove il pastore deve posizionarsi.
	 * @return
	 */
	public Point getPosizionePastore(){
		return new Point(getX(), getY()-38);
	}

	/**
	 * se la strada è attiva, il click sul pannello viene registrato nel ponte.
	 */
	public void mousePressed(MouseEvent arg0) {
		if (attivo)
			ponte.setClick(ID);
	}

	/**
	 * se il pannello è  attivo, il mouse viene trasformato in una mano.
	 */
	public void mouseEntered(MouseEvent event) {
		if(attivo)
			setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	/**
	 * il mouse diventas un puntatore quando si lascia l'area del pannello.
	 */
	public void mouseExited(MouseEvent arg0) {
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * Non usato.
	 */
	public void mouseReleased(MouseEvent arg0) {}
	/**
	 * Non usato.
	 */
	public void mouseClicked(MouseEvent arg0) {}
}
