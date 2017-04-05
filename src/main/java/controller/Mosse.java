package controller;

/**
 * Enumerazione che rappresenta l'elenco delle mosse eseguibili durante il turno
 * di ciascun giocatore.
 */
public enum Mosse {
	MOVIMENTO_PASTORE("Movimento pastore."), SPOSTAMENTO_PECORA(
			"Spostamento di una pecora, montone o agnello."), ACQUISTO_CARTA(
			"Acquisto di una carta terreno."), ACCOPPIAMENTO(
			"Accoppiamento tra un montone e una pecora. "), ABBATTIMENTO(
			"Abbattimento di una pecora, montone o agnello. ");

	private String mossa;

	/**
	 * Costruttore.
	 * @param mossa
	 */
	Mosse(String mossa) {
		this.mossa = mossa;
	}

	public String toString() {
		return mossa;
	}

	/**
	 * Restituisce l'elemento dell'enumerazione corrispondente ad un intero in
	 * ingresso.
	 * 
	 * @param i
	 * @return
	 */
	public static Mosse getMossa(int i) {
		switch (i) {
		case 0:
			return MOVIMENTO_PASTORE;
		case 1:
			return SPOSTAMENTO_PECORA;
		case 2:
			return ACQUISTO_CARTA;
		case 3:
			return ACCOPPIAMENTO;
		case 4:
			return ABBATTIMENTO;
		}
		return null;
	}
}
