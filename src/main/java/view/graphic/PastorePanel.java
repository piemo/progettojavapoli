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
 * 
 * Classe che estende JPanel e contiene le immagini dei pastori.
 *
 */
public class PastorePanel extends JPanel {
	private static final long serialVersionUID = -8908499507470648342L;
	private static final int L = 30;
	private static final int H = 68;
	private JLabel immagineLabel;
	private Image immaginePastore = null;
	
	/**
	 * Costruttore che carica l'immagine del pastore corrispondente all'indice inserito.
	 * @param id
	 */
	public PastorePanel(int id){
		super();
		setSize(new Dimension(L,H));
		setMaximumSize(getSize());
		setMinimumSize(getSize());
		setBackground(new Color(0,0,0,0));
		setLayout(new BorderLayout());
		try{
			switch (id) {
			case 0: immaginePastore = ImageIO.read(getClass().getResource("/pastoreRosso.png"));
				break;
			case 1: immaginePastore = ImageIO.read(getClass().getResource("/pastoreBlu.png"));
				break;
			case 2: immaginePastore = ImageIO.read(getClass().getResource("/pastoreVerde.png"));
				break;
			case 3: immaginePastore = ImageIO.read(getClass().getResource("/pastoreGiallo.png"));
				break;
			case 4:immaginePastore = ImageIO.read(getClass().getResource("/pastoreRosso2.png"));
				break;
			case 5:immaginePastore = ImageIO.read(getClass().getResource("/pastoreBlu2.png"));
				break;
			default:
				break;
			}
		}catch (IOException e){
			e.printStackTrace();
		}
		immagineLabel = new JLabel(new ImageIcon(immaginePastore));
		add(immagineLabel, BorderLayout.CENTER);
	}
	
	
}
