package view.graphic;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * 
 * Queesto frame viene creato quando si vuole ottenere il prezzo di una offerta. Il frame mostra la carta corrispondete all'offerta
 * e mostra dei bottoni rappresentati il prezzo.
 *
 */
public class PrezzoFrame extends JInternalFrame implements ActionListener{
	private static final long serialVersionUID = -8068682200076289191L;
	private JLabel carta;
	private JLabel testo;
	private Image[] carte = new Image[6];
	private PonteLogicaGrafica ponte;
	private JPanel buttons = new JPanel();
	private JButton[] prezzi = new JButton[4];
	
	/**
	 * Costruttore che inizializza i componenti del frame.
	 * @param ponte
	 */
	public PrezzoFrame(PonteLogicaGrafica ponte) {
		super("Prezzo offerta");
		this.ponte = ponte;
		setSize(new Dimension(390,176));
		setMaximumSize(getSize());
		setMinimumSize(getSize());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocation(255, 262);
		setLayout(new BorderLayout());
		try{
			carte[0] = ImageIO.read(getClass().getResource("/agricolo.png"));
			carte[1] = ImageIO.read(getClass().getResource("/arido.png"));
			carte[2] = ImageIO.read(getClass().getResource("/fiume.png"));
			carte[3] = ImageIO.read(getClass().getResource("/foresta.png"));
			carte[4] = ImageIO.read(getClass().getResource("/montagna.png"));
			carte[5] = ImageIO.read(getClass().getResource("/prato.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		carta = new JLabel(new ImageIcon(carte[0]));
		add(carta,BorderLayout.CENTER);
		testo = new JLabel("A quanto vuoi venderla?",SwingConstants.CENTER);
		testo.setFont(new Font("Verdana", Font.BOLD, 15));
		buttons.setLayout(new FlowLayout());
		for (int i=0; i<4; i++){
			prezzi[i] = new JButton((i+1)+" Danari");
			prezzi[i].addActionListener(this);
			buttons.add(prezzi[i],BorderLayout.SOUTH);
		}
		add(testo,BorderLayout.NORTH);
		add(carta, BorderLayout.CENTER);
		add(buttons,BorderLayout.SOUTH);
		pack();
		
	}
	
	/**
	 * Imposta l'immagine del tipo di carta.
	 * @param idCarta
	 */
	public void settaCarta(int idCarta){
		carta.setIcon(new ImageIcon(carte[idCarta]));
		repaint(); revalidate();
	}
	
	/**
	 * Imposta il valore del bottone cliccato sul ponte.
	 */
	public void actionPerformed(ActionEvent event) {
		if(event.getSource().equals(prezzi[0]))
				ponte.setClick(1);
		else if(event.getSource().equals(prezzi[1]))
			ponte.setClick(2);
		else if(event.getSource().equals(prezzi[2]))
			ponte.setClick(3);
		else if(event.getSource().equals(prezzi[3]))
			ponte.setClick(4);
		
	}

}
