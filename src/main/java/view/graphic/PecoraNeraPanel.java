package view.graphic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Classe che estende JPanel. contiene l'immagine della pecora nera.
 * @author lorenzo
 *
 */
public class PecoraNeraPanel extends JPanel{
	private static final long serialVersionUID = 496605824083027649L;
	private static final int DIM = 32;
	private JLabel imgLabel;
	private Image imgPecoraNera;
	
	/**
	 * costruttore che inserisce i componenti all'interno.
	 */
	public PecoraNeraPanel() {
		super();
		setSize(new Dimension(DIM,DIM));
		setMaximumSize(getSize());
		setMinimumSize(getSize());
		setBackground(new Color(0,0,0,0));
		setLayout(new BorderLayout());
		try{
			imgPecoraNera = ImageIO.read(getClass().getResource("/pecoraNera.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		imgLabel = new JLabel(new ImageIcon(imgPecoraNera));
		add(imgLabel,BorderLayout.CENTER);
	}
	
}
