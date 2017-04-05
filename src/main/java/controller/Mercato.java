package controller;

import java.util.ArrayList;

import model.Terreno;

/**
 * Classe che rappresenta il mercato. Questa classe viene usato alla fine di
 * ogni giro, durante la fase del mercato e contiene la lista delle offerte
 * fatte.
 */
public class Mercato {

	private ArrayList<Offerta> listaOfferte = new ArrayList<Offerta>();

	/**
	 * Metodo che aggiunge una offerta alle liste delle offerte del mercato.
	 * Usato quando si hanno i 3 campi di una offerta.
	 * 
	 * @param carta
	 *            Tipo del terreno rappresentato da una carta.
	 * @param venditore
	 *            Giocatore che vende la carta.
	 * @param prezzo
	 */
	public void aggiungiOfferta(Terreno carta, Giocatore venditore, int prezzo) {
		listaOfferte.add(new Offerta(carta, venditore, prezzo));
	}

	/**
	 * Metodo che aggiunge una offerta alle liste delle offerte del mercato.
	 * Usato quando si vuole passare l'offerta direttamente.
	 * 
	 * @param offerta
	 */
	public void aggiungiOfferta(Offerta offerta) {
		listaOfferte.add(offerta);
	}

	/**
	 * Metodo che gestisce la vendita di una offerta, con conseguente aggiunta
	 * della carta al compratore, rimozione della carta dal venditore e
	 * rimozione dell'offerta dal mercato
	 * 
	 * @param offertaAccettata
	 *            rappresenta l'offerta che si desidera comprare
	 * @param compratore
	 *            rappresenta il compratore dell'offerta
	 */
	public void accettaOfferta(Offerta offertaAccettata, Giocatore compratore) {
		int prezzo = offertaAccettata.getPrezzo();
		Terreno tipoCarta = offertaAccettata.getTipoCarta();
		Giocatore venditore = offertaAccettata.getVenditore();
		venditore.venditaCarta(tipoCarta, prezzo);
		compratore.acquistoCarta(tipoCarta, prezzo);
		for (Offerta x : listaOfferte)
			if (x.getVenditore().equals(venditore)) {
				listaOfferte.remove(x);
				return;
			}
	}

	/**
	 * Metodo per prendere la lista delle offerte.
	 * @return
	 */
	public ArrayList<Offerta> getListaOfferte() {
		return listaOfferte;
	}

}
