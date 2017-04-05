package view.graphic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * Questa classe contiene tutte le animazioni grafiche di "zoom". ad esempio l'animazione del cuore o dell'abbattimento.
 *
 */
public class AnimazionePanel extends JPanel {
	private static final long serialVersionUID = -5640038102936265496L;
	private static final int  DIM = 100;
	private Image cuore;
	private Image teschio;
	private Image carne;
	private Image carta;
	private Image crescita;
	private JLabel label;
	
	public AnimazionePanel(){
		setSize(DIM, DIM);
		setMaximumSize(getSize());
		setMinimumSize(getSize());
		setOpaque(false);
		setLayout(new BorderLayout());
		try{
			cuore = ImageIO.read(getClass().getResource("/cuore.png"));
			teschio = ImageIO.read(getClass().getResource("/teschio.png"));
			carne = ImageIO.read(getClass().getResource("/carne.png"));
			carta = ImageIO.read(getClass().getResource("/carta.png"));
			crescita = ImageIO.read(getClass().getResource("/crescita.png"));

		}catch (IOException e){
			e.printStackTrace();
		}
		setVisible(true);
	}
	
	/**
	 * Metodo che crea genera un'istanzazione dell'animazione del cuore.
	 */
	public void animazioneCuore(){
		int dimLato =1;
		label = new JLabel(new ImageIcon(cuore.getScaledInstance(dimLato,dimLato,Image.SCALE_FAST)));
		label.setBackground(new Color(0,0,0,0));
		this.add(label, BorderLayout.CENTER);
		repaint();
		revalidate();
		while(dimLato<=75){
			label.setIcon((new ImageIcon(cuore.getScaledInstance(dimLato,dimLato,Image.SCALE_FAST))));
			repaint();
			revalidate();
			dimLato++;
			Util.timer(10);
		}

	}
	
	/**
	 * Metodo che crea genera un'istanzazione dell'animazione della divorazione di una pecora
	 */
	public void animazioneCarne(){
		int dimLato =1;
		label = new JLabel(new ImageIcon(carne.getScaledInstance(dimLato,dimLato,Image.SCALE_FAST)));
		label.setBackground(new Color(0,0,0,0));
		this.add(label, BorderLayout.CENTER);
		repaint();
		revalidate();
		while(dimLato<=75){
			label.setIcon((new ImageIcon(carne.getScaledInstance(dimLato,dimLato,Image.SCALE_FAST))));
			repaint();
			revalidate();
			dimLato++;
			Util.timer(10);
		}

	}
	
	/**
	 * Metodo che crea genera un'istanzazione dell'animazione del teschio.
	 */
	public void animazioneTeschio(){
		int dimLato =1;
		label = new JLabel(new ImageIcon(teschio.getScaledInstance(dimLato,dimLato,Image.SCALE_FAST)));
		label.setBackground(new Color(0,0,0,0));
		this.add(label, BorderLayout.CENTER);
		repaint();
		revalidate();
		while(dimLato<=75){
			label.setIcon((new ImageIcon(teschio.getScaledInstance(dimLato,dimLato,Image.SCALE_FAST))));
			repaint();
			revalidate();
			dimLato++;
			Util.timer(10);
		}
		

	}
	
	/**
	 *Metodo che crea genera un'istanzazione dell'animazione dell'acquisto delle carte.
	 */
	public void animazioneCarta(){
		int dimLato =1;
		label = new JLabel(new ImageIcon(carta.getScaledInstance(dimLato,dimLato,Image.SCALE_FAST)));
		label.setBackground(new Color(0,0,0,0));
		this.add(label, BorderLayout.CENTER);
		repaint();
		revalidate();
		while(dimLato<=75){
			label.setIcon((new ImageIcon(carta.getScaledInstance(dimLato,dimLato,Image.SCALE_FAST))));
			repaint();
			revalidate();
			dimLato++;
			Util.timer(10);
		}
	}
	
	/**
	 * Metodo che crea genera un'istanzazione dell'animazione della crescita degli agnellini.
	 */
	public void animazioneCrescita(){
		int dimLato =1;
		label = new JLabel(new ImageIcon(crescita.getScaledInstance(dimLato,dimLato,Image.SCALE_FAST)));
		label.setBackground(new Color(0,0,0,0));
		this.add(label, BorderLayout.CENTER);
		repaint();
		revalidate();
		while(dimLato<=75){
			label.setIcon((new ImageIcon(crescita.getScaledInstance(dimLato,dimLato,Image.SCALE_FAST))));
			repaint();
			revalidate();
			dimLato++;
			Util.timer(8);
		}
	}
}
