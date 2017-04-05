package model;

/**
 * Classe che rappresenta il lupo. Il lupo � un animale che si muove liberamente
 * dopo ogni giro e, in seguito ad un movimento, pu� mangiare una pecora nella
 * regione in cui si trova.
 */
public class Lupo implements AnimaleConMovimentoLibero {

	private Regione regioneAttuale;

	/**
	 * Costruttore che posiziona il lupo nella regione passata come paramentro.
	 * 
	 * @param regioneIniziale
	 */
	public Lupo(Regione regioneIniziale) {
		regioneAttuale = regioneIniziale;
	}

	public int movimentoCasuale(int risultatoDado) {
		Strada stradaDesignata;
		stradaDesignata = regioneAttuale.getStradaConValoreX(risultatoDado);
		if (stradaDesignata != null)
			if (!stradaDesignata.isRecintata()
					|| regioneAttuale.isTuttaRecintata()) {
				regioneAttuale = stradaDesignata
						.getRegioneOpposta(regioneAttuale);
				return regioneAttuale.getID();
			}
		return -1;
	}

	/**
	 * Metodo per aggiornare la regione del lupo.
	 * @param regioneNuova
	 */
	public void spostaLupo(Regione regioneNuova) {
		regioneAttuale = regioneNuova;
	}

	/**
	 * Metodo che permette al lupo di mangiare una pecora dalla regione in cui
	 * si trova. Questo metodo viene invocato solo a seguito di un movimento del
	 * lupo.
	 * 
	 * @return la pecora mangiata.
	 */
	public Agnello sbrana() {
		if (regioneAttuale.getNumeroPecore()>0)
			return regioneAttuale.mangiaPecora();
		return null;
	}

	/**
	 * Metodo per sapere la regione attuale del lupo.
	 * @return
	 */
	public Regione getRegioneAttuale() {
		return regioneAttuale;
	}

	public String toString() {
		return "Lupo";
	}

}
