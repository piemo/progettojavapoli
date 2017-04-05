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

/**
 * Classe che estende JPanel. Usato sulle carte da comprare per mostrarne il prezzo, disponibilità.
 *
 */
public class CartaPanel extends JPanel implements MouseListener {
	private static final long serialVersionUID = 5871380166135240080L;
	private int id;
	private static final int DIM = 83;
	private JLabel disponibili, esaurite;
	private Image croce = null;
	private PonteLogicaGrafica ponte;
	private boolean attiva = false;
	private int numDisponibili;
	private JLabel attivazione;
	private JLabel prezzo;
	private JLabel sopra;
	private Image cerchio;

	/**
	 * Costruttore. 
	 * @param ponte 
	 * @param ID Rappresenta l'id della carta, ovvero il tipo di terreno.
	 */
	public CartaPanel(PonteLogicaGrafica ponte, int ID) {
		super();
		id = ID;
		this.ponte = ponte;
		numDisponibili = 5;
		setSize(new Dimension(DIM, DIM));
		setMaximumSize(getSize());
		setMinimumSize(getSize());
		setOpaque(false);
		setLayout(new BorderLayout());
		try{
			croce = ImageIO.read(getClass().getResource("/croce.png"));
			cerchio = ImageIO.read(getClass().getResource("/cerchioVerde.png"));
		}catch (IOException e){
			e.printStackTrace();
		}
		Font font = new Font("Verdana", Font.BOLD, 20);
		
		///esaurite
		esaurite = new JLabel(new ImageIcon(croce));
		esaurite.setBackground(new Color(0,0,0,0));
		
		//dsponibili
		disponibili = new JLabel("     " + numDisponibili);
		disponibili.setFont(font);
		disponibili.setForeground(Color.WHITE);
		
		//attivazione
		attivazione = new JLabel(new ImageIcon(cerchio));
		prezzo = new JLabel("       "+(5-numDisponibili));
		prezzo.setFont(new Font("Verdana", Font.BOLD, 23));
		prezzo.setForeground(Color.YELLOW);
		sopra = new JLabel(" ");
		sopra.setFont(new Font("Verdana", Font.BOLD, 23));
		addMouseListener(this);
		this.add(disponibili, BorderLayout.CENTER);
		setVisible(true);
	}

	/**
	 * Metodo che setta la disponibilità di un determinato tipo di carta.
	 * @param num numero di carte disponibili.
	 */
	public void settaCarte(int num) {
		numDisponibili = num;
		disponibili.setText("     " + numDisponibili);
		prezzo.setText("       "+(5-numDisponibili));
		repaint(); revalidate();
		if (numDisponibili <= 0) {
			remove(disponibili);
			add(esaurite,BorderLayout.CENTER);
			repaint(); revalidate();
		}
	}

	/**
	 * Metodo che rimuove una carta dal numero di quelle disponibili.
	 */
	public void togliCarta() {
		if(numDisponibili>0){
			numDisponibili--;
			disponibili.setText("     " + numDisponibili);
			prezzo.setText("       "+(5-numDisponibili));
			repaint(); revalidate();
			if (numDisponibili <= 0) {
				remove(disponibili);
				add(esaurite,BorderLayout.CENTER);
				repaint(); revalidate();
			}
		}
	}
	
	/**
	 * Metodo che attiva la possibilità di premere il pannello.
	 */
	public void attivaPressione(){
		attiva = true;
	}
	
	/**
	 * Metodo che disattiva la possibilità di cliccare il pannello;
	 */
	public void disattivaPressione(){
		attiva = false;
	}
	
	/**
	 * metodo che abilita la vista avanzata per l'acquisto, mostrando il prezzo, attivando la pressione.
	 */
	public void attiva() {
		attivaPressione();
		add(sopra,BorderLayout.NORTH);
		add(attivazione,BorderLayout.CENTER);
		add(prezzo, BorderLayout.SOUTH);
		repaint(); revalidate();
	}

	/**
	 * Disattiva la vista avanzata di acquisto.
	 */
	public void disattiva() {
		disattivaPressione();
		remove(sopra);
		remove(attivazione);
		remove(prezzo);
		repaint(); revalidate();
	}

	/**
	 * Metodo che gestisce il click del mouse quando è possibile farlo.
	 */
	public void mousePressed(MouseEvent arg0) {
		if (attiva)
			ponte.setClick(id);
	}

	/**
	 * Non utilizzato.
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
	 * Mtodo che gestisce l'uscita del cursore dall'area del pannello. Fa diventare il cursore un normale puntatore
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
