package model;

/**
 * Classe che rappresenta montoni e pecore femmine. Possiede un booleano che
 * rappresenta il sesso (true=maschio, false=femmina).
 */
public class PecoraAdulta extends Agnello {
	private boolean maschio;

	/**
	 * Costruttore che crea una pecora adulta di sesso casuale (50% M e 50% F).
	 */
	public PecoraAdulta() {
		creaPecoraAdulta();
		if ((int) (Math.random() * 2) == 1)
			maschio = true;
		else
			maschio = false;
	}

	/**
	 * Costruttore per una pecora di cui si conosce il sesso.
	 * @param isMaschio
	 */
	public PecoraAdulta(boolean isMaschio) {
		creaPecoraAdulta();
		if (isMaschio)
			maschio = true;
		else
			maschio = false;
	}

	/**
	 * Metodo per sapere il sesso della pecora.
	 * @return
	 */
	public boolean isMaschio() {
		return maschio;
	}

	public String toString() {
		if (maschio)
			return "Montone";
		return "Pecora femmina";
	}

	public int getTipo() {
		if (maschio)
			return 2;
		else {
			return 1;
		}
	}

}
