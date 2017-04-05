package model;

/**
 * Interfaccia che rappresenta un animale con movimento libero.
 * Nel nostro caso lupo e pecora nera.
 *
 */
interface AnimaleConMovimentoLibero {

	/**
	 * Metodo per far muovere una pedina in base al risultato del lancio del
	 * dado. Nel caso in cui il movimento non pu� essere effettuato, ritorna -1.
	 * 
	 * @param risultatoDado
	 * @return un intero corrispondente all'ID della regione in cui si muover�
	 *         la pedina.
	 */
	public int movimentoCasuale(int risultatoDado);

}
