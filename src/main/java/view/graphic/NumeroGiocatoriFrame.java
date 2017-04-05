package view.graphic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Frame usato per scegliere il numero di giocatori.
 * @author lorenzo
 *
 */
public class NumeroGiocatoriFrame extends JInternalFrame implements MouseListener {
	private static final long serialVersionUID = 9199366804983814130L;
	private PonteLogicaGrafica ponte;
	private JButton[] buttons = new JButton[3];

	/**
	 * Costruttore. Viene inizalizzato il frame e tutti i suoi componenti interni.
	 * @param ponte
	 */
	public NumeroGiocatoriFrame(PonteLogicaGrafica ponte) {
		super("Numero Giocatori     ");
		this.ponte = ponte;
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(new Dimension(200, 130));
		setMaximumSize(getSize());
		setMinimumSize(getSize());
		setPreferredSize(getSize());
		setLayout(null);
		JLabel name = new JLabel("    QUANTI GIOCATORI?");
		name.setFont(new Font("Arial", Font.BOLD, 14));
		name.setBounds(0, 0, 200, 50);

		JPanel bottoniJPanel = new JPanel();
		bottoniJPanel.setBounds(0, 50, 200, 100);
		bottoniJPanel.setLayout(new FlowLayout());
		for (int i = 0; i < 3; i++) {
			buttons[i] = new JButton("" + (i + 2));
			buttons[i].setForeground(Color.BLUE);
			bottoniJPanel.add(buttons[i]);
			buttons[i].addMouseListener(this);
		}
		add(bottoniJPanel, BorderLayout.CENTER);
		add(name);
		pack();
		setVisible(true);
		this.requestFocus();
		for (Component x : Util.getAllComponents(this)) {
			x.repaint();
			x.revalidate();
		}
	}

	/**
	 * Non usato.
	 */
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * Non usato.
	 */
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * Non usato.
	 */
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * Rileva quale pulsante sia stato premuto e lo registra nel ponte.
	 */
	public void mousePressed(MouseEvent event) {
		if (event.getComponent().equals(buttons[0])) {
			ponte.setClick(2);
			return;
		} else if (event.getComponent().equals(buttons[1])) {
			ponte.setClick(3);
			return;
		} else if (event.getComponent().equals(buttons[2])) {
			ponte.setClick(4);
			return;
		}

	}

	/**
	 * Non usato.
	 */
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
