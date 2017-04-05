package startGame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Classe che contiene la grafica presentata all'apertura del gioco.
 * 
 */
public class IntroFrame extends JFrame implements MouseListener {
	private static final long serialVersionUID = -4354411538774009679L;
	private int state;
	private PonteLogicaGraficaStart ponte;
	private JButton[] buttons = new JButton[2];

	public IntroFrame(PonteLogicaGraficaStart ponte) {
		super("Sheepland");
		try {
			super.setIconImage(ImageIO.read(getClass().getResource("/pecora.png")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		this.ponte = ponte;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		state = 0;

		Dimension dimension = new Dimension(1034, 600);
		this.setSize(dimension);
		this.setMaximumSize(dimension);
		this.setMinimumSize(dimension);
		this.setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		BufferedImage myPicture = null;
		JPanel panel = new JPanel();
		buttons[0] = new SelectButton("CONSOLE");
		buttons[1] = new SelectButton("GRAFICA");
		panel.setLayout(null);
		panel.add(buttons[0]);
		panel.add(buttons[1]);
		buttons[0].setLocation(370, 180);
		buttons[1].setLocation(370, 270);
		buttons[0].addMouseListener(this);
		buttons[1].addMouseListener(this);
		try {
			myPicture = ImageIO.read(getClass().getResource("/intro.jpg"));
			JLabel label = new JLabel(new ImageIcon(myPicture));
			label.setSize(dimension);
			panel.setSize(getSize());
			panel.add(label);
		} catch (IOException e) {
			e.printStackTrace();
		}
		panel.validate();
		this.getContentPane().add(panel);
		this.pack();
		setVisible(true);
		for (Component x : getComponents()) {
			x.repaint();
			x.revalidate();
		}
	}

	/**
	 * Non usato.
	 */
	public void mouseClicked(MouseEvent mouseEvent) {
	}

	/**
	 * Non usato.
	 */
	public void mouseEntered(MouseEvent arg0) {
	}

	/**
	 * Non usato.
	 */
	public void mouseExited(MouseEvent arg0) {
	}

	/**
	 * Rileva che bottoni sono stati premuti durante la fase di avvio del gioco.
	 */
	public void mousePressed(MouseEvent mouseEvent) {
		switch (state) {
		case (0):
			if (mouseEvent.getComponent().equals(buttons[0])
					|| (mouseEvent.getComponent().equals(buttons[1]))) {
				state++;
				buttons[0].setText("OFFLINE");
				buttons[1].setText("ONLINE");
			}
			if (mouseEvent.getSource().equals(buttons[0]))
				ponte.setGraphic(false);
			else if (mouseEvent.getSource().equals(buttons[1]))
				ponte.setGraphic(true);
			break;
		case (1):
			if (mouseEvent.getSource().equals(buttons[0])) {
				ponte.setOnline(false);
			} else if (mouseEvent.getSource().equals(buttons[1])) {
				ponte.setOnline(true);
				state++;
				buttons[0].setText("SOCKET");
				buttons[1].setText("RMI");
			}
			break;
		case (2):
			if (mouseEvent.getSource().equals(buttons[0]))
				ponte.setRmi(false);
			else if (mouseEvent.getSource().equals(buttons[1]))
				ponte.setRmi(true);
			break;
		}
	}

	/**
	 * Non usato.
	 */
	public void mouseReleased(MouseEvent arg0) {
	}

	/**
	 * Classe che rappresenta i bottoni per la selezione delle opzioni di gioco.
	 * 
	 */
	public class SelectButton extends JButton {
		private static final long serialVersionUID = -8919397966365334150L;
		private int width = 280;
		private int height = 70;

		public SelectButton(String nome) {
			super(nome);
			Dimension buttonDimension = new Dimension(width, height);
			setSize(buttonDimension);
			setMinimumSize(buttonDimension);
			setMaximumSize(buttonDimension);
			setBackground(Color.LIGHT_GRAY);
			setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5,
					Color.DARK_GRAY));
			setForeground(Color.BLUE);
			repaint();
		}

	}

}
