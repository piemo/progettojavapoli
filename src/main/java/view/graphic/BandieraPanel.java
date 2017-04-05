package view.graphic;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * classe che estende JPanel e utilizzata per mostrare le bandiere colorate per il singolo giocatore;
 * 
 *
 */
public class BandieraPanel extends JPanel {
	private static final long serialVersionUID = -4879594104310691027L;

	/**
	 * costruttore
	 * @param indiceGiocatore l'indice del giocatore di cui si vuole mostrare il colore.
	 */
	public BandieraPanel(int indiceGiocatore){
		super();
		setSize(new Dimension(216, 28));
		setMaximumSize(getSize());
		setMinimumSize(getSize());
		setLayout(new BorderLayout());
		setOpaque(false);

		Image bandiera = null;
		try {
			switch (indiceGiocatore) {
			case 0:
				bandiera = ImageIO.read(getClass().getResource("/bannerRosso.png"));

				break;
			case 1:
				bandiera = ImageIO.read(getClass().getResource("/bannerBlu.png"));

				break;
			case 2:
				bandiera = ImageIO.read(getClass().getResource("/bannerVerde.png"));

				break;
			case 3:
				bandiera = ImageIO.read(getClass().getResource("/bannerGiallo.png"));

				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JLabel label = new JLabel(new ImageIcon(bandiera));
		this.add(label);
		setVisible(true);

	}
}
