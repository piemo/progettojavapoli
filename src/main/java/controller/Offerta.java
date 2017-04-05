package controller;

import model.Terreno;

/**
 * Classe che rappresenta l'offerta del mercato. L'offerta è caratterizzata da
 * un tipo di terreno offerto, il giocatore che lo offre e il relativo prezzo.
 */
public class Offerta {
	private Terreno tipoCarta;
	private Giocatore venditore;
	private int prezzo;

	/**
	 * Costruttore.
	 * 
	 * @param tipoCarta
	 *            Il tipo di terreno corrispondente alla carta messa in vendita
	 * @param venditore
	 *            Il giocatore che mette in vendita la carta
	 * @param prezzo
	 */
	public Offerta(Terreno tipoCarta, Giocatore venditore, int prezzo) {
		this.tipoCarta = tipoCarta;
		this.venditore = venditore;
		this.prezzo = prezzo;
	}

	/**
	 * Metodo per sapere il prezzo dell'offerta
	 * @return
	 */
	public int getPrezzo() {
		return prezzo;
	}

	/**
	 * Metodo per sapere che ha fatto quell'offerta.
	 * @return
	 */
	public Giocatore getVenditore() {
		return venditore;
	}

	/**
	 * Metodo per sapere il tipo di carta offerta.
	 * @return
	 */
	public Terreno getTipoCarta() {
		return tipoCarta;
	}

	public String toString() {
		return ("Offerta di " + venditore + ": " + tipoCarta + " a " + prezzo);
	}
}
