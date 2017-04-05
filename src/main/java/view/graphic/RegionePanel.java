package view.graphic;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * Classe che estende JPanel e rappresenta una regione.
 *
 */
public class RegionePanel extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1062463586028639294L;
	private int ID;
	private int WIDTH, HEIGHT;
	private PecorePanel pecore;
	private boolean attiva = false;
	private PonteLogicaGrafica ponte;
	private Point posizionePecoraNera;
	private Point posizioneLupo;
	private Point posizionePecore;
	private Point posizioneAnimazioni;

	/**
	 * Costruttore che inizialziza i componenti e costruisce il pannello.
	 * @param ponte
	 * @param ID Id della regione.
	 * @param X
	 * @param Y
	 * @param L
	 * @param H
	 */
	public RegionePanel(PonteLogicaGrafica ponte, int ID, int X, int Y, int L,
			int H) {
		super();
		this.WIDTH = L;
		this.HEIGHT = H;
		this.ID = ID;
		this.ponte = ponte;
		posizionePecoraNera = new Point(X-2, Y + HEIGHT-30); 
		posizioneLupo = new Point(X + WIDTH-26, Y+HEIGHT-32);
		posizionePecore = new Point (X+(L/2)-16, Y+(H/2)-16);
		posizioneAnimazioni = new Point(X+(L/2)-37, Y+(H/2)-37);
		setSize(new Dimension(L, H));
		setMaximumSize(getSize());
		setMinimumSize(getSize());
		setOpaque(false);
		pecore = new PecorePanel();
		pecore.setBounds(WIDTH / 2 - pecore.getWidth() / 2,
				HEIGHT / 2 - pecore.getHeight() / 2, pecore.getWidth(),
				pecore.getHeight());
		add(pecore);
		for (Component x : Util.getAllComponents(this)) {
			x.repaint();
			x.revalidate();
		}
		repaint(); revalidate();
		addMouseListener(this);
		pecore.addMouseListener(this);
		setVisible(true);
	}

	/**
	 * Riduce di uno un tipo di pecore in una regione.
	 * @param tipo
	 */
	public void prelevaPecora(int tipo) {
		pecore.prelevaPecora(tipo);
	}

	/**
	 * Aumenta di uno un dato tipo di pecora in questa regione.
	 * @param tipo
	 */
	public void posizionaPecora(int tipo) {
		pecore.posizionaPecora(tipo);
	}

	/**
	 * Imposta il numero degli animali nella regione
	 * @param numAgnelli
	 * @param numPecore
	 * @param numMontoni
	 */
	public void setPecore(int numAgnelli, int numPecore, int numMontoni) {
		pecore.setPecore(numAgnelli, numPecore, numMontoni);
	}

	/**
	 * Ritorna le coordinate in cui la pecora nera si trova.
	 * @return
	 */
	public Point getPosizionePecoraNera() {
		return posizionePecoraNera;
	}

	/**
	 * Ritorna le coordinate in cui il lupo si trova.
	 * @return
	 */
	public Point getPosizioneLupo() {
		return posizioneLupo;
	}
	
	/**
	 * Ritorna le coordinate in cui le pecore si trovano.
	 * @return
	 */
	public Point getPosizionePecore(){
		return posizionePecore;
	}
	
	/**
	 * Ritorna le cordinate in cui le animazioni di trovano.
	 * @return
	 */
	public Point getPosizioneAnimazioni(){
		return posizioneAnimazioni;
	}

	
	/**
	 * 
	 * Questa classe estende Jpanel. Contiene le informazioni degli animali in una data regione mostrandone il numero nel tipo 
	 * agnelli/pecore/montoni
	 *
	 */
	public class PecorePanel extends JPanel {
		private static final long serialVersionUID = 9080112575424663636L;
		private int WIDTHPECORE = 32, HEIGHTPECORE = 38;
		private JLabel img;
		private int numPecore = 0/* 1 */, numMontoni = 0/* 2 */,
				numAgnelli = 0 /* 0 */;
		private JLabel pecore;

		/**
		 * Costruttore della classe. Inserisce la dimensione dei componenti e la posizione.
		 */
		public PecorePanel() {
			super();
			setSize(new Dimension(WIDTHPECORE, HEIGHTPECORE));
			setMaximumSize(getSize());
			setMinimumSize(getSize());
			setOpaque(false);
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			Image myPicture = null;
			try {
				myPicture = ImageIO.read(getClass().getResource(
						"/pecora.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			img = new JLabel(new ImageIcon(myPicture));
			Font font = new Font("Verdana", Font.BOLD, 9);
			pecore = new JLabel("" + numAgnelli + "|" + numPecore + "|"
					+ numMontoni);
			pecore.setFont(font);
			pecore.setForeground(Color.black);
			repaint(); revalidate();
		}

		/**
		 * Metodo che diminuisce il contatore di un dato tipo di pecora.
		 * @param tipo
		 */
		public void prelevaPecora(int tipo) {
			switch (tipo) {
			case (0):
				numAgnelli--;
				break;
			case (1):
				numPecore--;
				break;
			case (2):
				numMontoni--;
				break;
			}
			pecore.setText("" + numAgnelli + "|" + numPecore + "|" + numMontoni);
			if (numAgnelli + numMontoni + numPecore == 0) {
				remove(img); remove(pecore); repaint(); revalidate();
			}
		}

		/**
		 * metodo che incrementa il contatore di un dato tipo di pecora.
		 * @param tipo
		 */
		public void posizionaPecora(int tipo) {
			if (numAgnelli + numMontoni + numPecore == 0) {
				add(img); add(pecore); repaint(); revalidate();
			}
			switch (tipo) {
			case (0):
				numAgnelli++;
				break;
			case (1):
				numPecore++;
				break;
			case (2):
				numMontoni++;
				break;
			}
			pecore.setText("" + numAgnelli + "|" + numPecore + "|" + numMontoni);
		}

		/**
		 * Metodo che setta il numero di animali in questa regione.
		 * @param agnelli
		 * @param pecore
		 * @param montoni
		 */
		public void setPecore(int agnelli, int pecore, int montoni) {
			numAgnelli = agnelli;
			numPecore = pecore;
			numMontoni = montoni;
			this.pecore.setText("" + numAgnelli + "|" + numPecore + "|"
					+ numMontoni);
			if (numAgnelli + numMontoni + numPecore > 0){
				add(img); add(this.pecore); repaint(); revalidate();
			}
		}
	}

	/**
	 * Metodo che attiva la possibilità di cliccare la regione.
	 */
	public void attiva() {
		attiva = true;
	}

	/**
	 * Metodo che disattiva la possibilità di cliccare la regione.
	 */
	public void disattiva() {
		attiva = false;
	}

	/**
	 * Metodo che, nel caso la regione sia attiva, rende il puntatore una manina.
	 */
	public void mouseEntered(MouseEvent arg0) {
		if (attiva)
			setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	/**
	 * Metodo che rende il puntatore un puntatore quando si lascia l'area del panel.
	 */
	public void mouseExited(MouseEvent arg0) {
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * se la regione è attiva, viene registrato che questa è stata cliccata nel ponte.
	 */
	public void mousePressed(MouseEvent arg0) {
		if (attiva)
			ponte.setClick(ID);
	}

	/**
	 * Non usato.
	 */
	public void mouseReleased(MouseEvent arg0) {
	}

	/**
	 * Non usato.
	 */
	public void mouseClicked(MouseEvent arg0) {
	}

}
