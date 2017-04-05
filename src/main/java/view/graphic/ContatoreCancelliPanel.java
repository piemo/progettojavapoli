package view.graphic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Classe che estende JPanel. Viene usato per creare la label del contatore dei cancelli.
 * 
 *
 */
public class ContatoreCancelliPanel extends JPanel {
	private static final long serialVersionUID = 6337933809519460286L;
	private static final int DIM = 51;
	private JLabel testo;
	
	/**
	 * Costruttore che inizalizza dimensione e contenuto.
	 */
	public ContatoreCancelliPanel(){
		super();
		this.setSize(DIM, DIM);
		this.setMaximumSize(this.getSize());
		this.setMinimumSize(this.getSize());
		this.setOpaque(false);
		setLayout(new BorderLayout());
		testo = new JLabel("  0");
		testo.setFont(new Font("Verdana", Font.BOLD, 23));
		testo.setForeground(Color.white);
		this.add(testo,BorderLayout.CENTER);
		setVisible(true);
	}
	
	/**
	 * Metodo che incrementa di uno il numero di cancelli;
	 */
	public void incrementa(){
		String stringa = testo.getText().replaceAll("\\s+", "");
		int valoreAttuale = Integer.parseInt(stringa);
		valoreAttuale++;
		if(valoreAttuale<10)
			testo.setText("  "+valoreAttuale);
		else
			testo.setText(" "+valoreAttuale);
		repaint();revalidate();
	}
	
	/**
	 * Metodo che setta il contatore a un valore fisso
	 * @param numeroCancelli Valore dei cancelli che si vuole mostrare
	 */
	public void settaNum(int numeroCancelli){
		if(numeroCancelli<10)
			testo.setText("  "+numeroCancelli);
		else
			testo.setText(" "+numeroCancelli);
		repaint();revalidate();
	}
}
