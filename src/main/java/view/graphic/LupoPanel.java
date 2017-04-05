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
 * Classe che estende JPanel. Usato per l'icona del lupo.
 * @author lorenzo
 *
 */
public class LupoPanel extends JPanel{
	private static final long serialVersionUID = 6966159781694410532L;
	private static final int DIM = 32;
	private JLabel imgLabel;
	private Image imgLupo;
	
	/**
	 * Costruttore.
	 */
	public LupoPanel() {
		super();
		setSize(new Dimension(DIM,DIM));
		setMaximumSize(getSize());
		setMinimumSize(getSize());
		setBackground(new Color(0,0,0,0));
		setLayout(new BorderLayout());
		try{
			imgLupo = ImageIO.read(getClass().getResource("/lupo.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		imgLabel = new JLabel(new ImageIcon(imgLupo));
		add(imgLabel,BorderLayout.CENTER);
	}
}
