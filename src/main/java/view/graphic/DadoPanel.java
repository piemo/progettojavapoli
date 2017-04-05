package view.graphic;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/**
 * 
 * Classe che estende JPanel e usato per gestire l'interazione con il dado. Implementa un MouseListener.
 *
 */
public class DadoPanel extends JPanel implements MouseListener {
	private static final long serialVersionUID = 2595601415444071520L;
	private static final int DIM = 83;
	private PonteLogicaGrafica ponte;
	private boolean attiva = false;

	/**
	 * Costruttore che inizializza le dimensioni, componenti e aggiunge il Listener.
	 * @param ponte
	 */
	public DadoPanel (PonteLogicaGrafica ponte){
		super();
		this.ponte = ponte;
		setSize(new Dimension(DIM, DIM));
		setMaximumSize(getSize());
		setMinimumSize(getSize());
		setOpaque(false);
		addMouseListener(this);
		setVisible(true);
	}
	
	/**
	 * Metodo che attiva la possibilità di cliccare il dado
	 */
	public void attiva() {
		attiva = true;
		grabFocus();
	}

	/**
	 * Metodo che disattiva la possibilità di cliccare il dado
	 */
	public void disattiva() {
		attiva = false;
	}
	
	/**
	 * Non usato
	 */
	public void mouseClicked(MouseEvent arg0) {

	}
	/**
	 * Metodo che gestisce l'hovering del mouse sul pannello. Se la casella è attiva, rende il mouse una manina.
	 */
	public void mouseEntered(MouseEvent arg0) {
		if(attiva)
			setCursor(new Cursor(Cursor.HAND_CURSOR));		
	}
	/**
	 * Metodo che gestisce l'uscita del cursore dall'area del pannello. Fa diventare il cursore un normale puntatore
	 */
	public void mouseExited(MouseEvent arg0) {
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		
	}
	/**
	 * Metodo che gestisce il click del mouse, registrando il valore del dado nel Ponte.
	 */
	public void mousePressed(MouseEvent arg0) {
		if (attiva){
			int numero =(int) (Math.random() * 6) + 1;
			ponte.setClick(numero);
		}
							
	}
	/**
	 * Non usato.
	 */
	public void mouseReleased(MouseEvent arg0) {
		
	}

}
