package model;

/**
 * Classe che rappresenta la pecora nera. La pecora nera discende da un agnello
 * ed � una pecora che pu� muoversi liberamente all'inizio di ogni turno.
 * Possiede un attributo che ne indica la posizione attuale su una regione.
 */
public class PecoraNera extends Agnello implements AnimaleConMovimentoLibero {

	private Regione regioneAttuale;

	/**
	 * Costruttore. Crea una pecora gi� adulta e la posiziona della regione
	 * passata come parametro.
	 * 
	 * @param regioneDiNascita
	 */
	PecoraNera(Regione regioneDiNascita) {
		creaPecoraAdulta();
		regioneAttuale = regioneDiNascita;
	}

	public int movimentoCasuale(int risultatoDado) {
		Strada stradaDesignata = regioneAttuale.getStradaConValoreX(risultatoDado);
		if (stradaDesignata != null)
			if (!stradaDesignata.isOccupata()) {
				Regione nuovaRegione = stradaDesignata.getRegioneOpposta(regioneAttuale);
				muoviti(nuovaRegione);
				return nuovaRegione.getID();
			}
		return -1;

	}

	/**
	 * Metodo che serve a modificare la posizione della pecora nera in seguito
	 * ad un suo movimento libero.
	 * 
	 * @param nuovaRegione
	 */
	public void muoviti(Regione nuovaRegione) {
		regioneAttuale.prelevaPecoraNera();
		nuovaRegione.posizionaPecora(this);
		regioneAttuale = nuovaRegione;
	}

	public String toString() {
		return "Pecora Nera";
	}

	/**
	 * Metodo per sapere la regione attuale.
	 * @return
	 */
	public Regione getRegioneAttuale() {
		return regioneAttuale;
	}

	public int getTipo() {
		return 3;
	}

}
