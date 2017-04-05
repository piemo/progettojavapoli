package view.graphic;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Questa classe estende JFrame. è il frame principale che contiene gran parte degli elementi grafici del gioco.
 * @author lorenzo
 *
 */
public class MainFrame extends JFrame {
	private static final long serialVersionUID = 3314033773811764246L;
	private static final int LARGEZZA = 900;
	private static final int ALTEZZA = 700;
	private JPanel panelloBackground;
	private PanelGameboard pannelloElementi;
	
	/**
	 * Metodo di test usato per visualizzare il funzionamento degli spostamenti e delle animazioni. Non più usato.
	 */
	@Deprecated
	public void spostaTest(){
		pannelloElementi.spostaPastore(0, 30);
		pannelloElementi.animazioneCuore(5);
		pannelloElementi.animazioneTeschio(6);
		pannelloElementi.spostaPastore(3, (int)(Math.random()*42));
		
	}
	/**
	 * Costruttore del frame. Inizializza tutti i componenti.
	 * @param ponte 
	 */
	MainFrame(PonteLogicaGrafica ponte) {
		super("Sheepland");
		try {
			super.setIconImage(ImageIO.read(getClass().getResource("/pecora.png")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Dimension dimension = new Dimension(LARGEZZA, ALTEZZA);
		this.setSize(dimension);
		this.setMaximumSize(dimension);
		this.setMinimumSize(dimension);
		this.setResizable(false);
		setLocationRelativeTo(null);
		BufferedImage myPicture;
		panelloBackground = new JPanel();
		panelloBackground.setSize(new Dimension(LARGEZZA, ALTEZZA));
		panelloBackground.setOpaque(false);
		panelloBackground.setLayout(null);
		pannelloElementi = new PanelGameboard(LARGEZZA, ALTEZZA, ponte);
		panelloBackground.add(pannelloElementi);
		pannelloElementi.setBounds(0, 0, LARGEZZA, ALTEZZA);
		try {
			myPicture = ImageIO.read(getClass().getResource("/gameboard.png"));
			JLabel label = new JLabel(new ImageIcon(myPicture));
			label.setSize(dimension);
			panelloBackground.add(label);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.getContentPane().add(panelloBackground);
		for (Component x : Util.getAllComponents(this))
			x.repaint();
		this.pack();
		pannelloElementi.setVisible(true);
		this.setVisible(true);
		ponte.setClick(1);
	}


	

	
	public PanelGameboard getElementPanel() {
		return pannelloElementi;
		
	}

}
