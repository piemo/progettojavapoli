package model;

/**
 * Classe che rappresenta il tipo primitivo delle pecore.
 */
public class Agnello {
	private int eta;

	/**
	 * Costruttore che crea un nuovo agnello, che avrè quindi eta pari a zero.
	 */
	public Agnello() {
		eta = 0;
	}

	/**
	 * Metodo che serve al costruttore della sottoclasse PecoraAdulta per poter
	 * settare l'eta di partenza a 3.
	 */
	public void creaPecoraAdulta() {
		eta = 3;
	}

	/**
	 * Metodo che incrementa l'eta dell'Agnello. Verifica poi se l'eta è 2 per
	 * determinare se l'Agnello ha raggiunto l'eta per diventare PecoraAdulta.
	 * 
	 * @return true se l'agnello cresce.
	 */
	public boolean crescita() {
		eta++;
		if (eta == 2)
			return true;
		return false;
	}

	/**
	 * Metodo per sapere il tipo di pecora.
	 * @return 0 per l'agnello, 1 per la pecora femmina, 2 per il montone e 3 per la pecora nera.
	 */
	public int getTipo() {
		return 0;
	}

	public String toString() {
		return "Agnello";
	}

}
