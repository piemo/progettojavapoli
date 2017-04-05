package view.graphic;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Classe che eestende JPanel. Questa classe contiene solo un'immagine della pecora.
 * 
 *
 */
public class PecoraPanel extends JPanel{
	private static final long serialVersionUID = -2422055667508756401L;
	private JLabel immaginLabel;
	private Image pecora = null;
	private static final int DIM = 32;
	/**
	 * COstruttore del pannello che crea il pannello.
	 */
	public PecoraPanel(){
		super();
		setSize(new Dimension(DIM,DIM));
		setMaximumSize(getSize());
		setMinimumSize(getSize());
		setOpaque(false);
		setLayout(new BorderLayout());
		try{
			pecora = ImageIO.read(getClass().getResource("/pecora.png"));
		}catch (IOException e){
			e.printStackTrace();
		}
		immaginLabel = new JLabel(new ImageIcon(pecora));
		add(immaginLabel,BorderLayout.CENTER);
	}
}
