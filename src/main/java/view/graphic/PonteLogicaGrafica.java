package view.graphic;

/**
 * Classe che viene interrogata dalla logica della partita quando bisogna
 * attendere un'interazione dell'utente con la grafica.
 * 
 */
public class PonteLogicaGrafica {
	private Integer click = null;

	/**
	 * Metodo che viene usato dalla logica per sapere quale tasto ha premuto
	 * l'utente.
	 * 
	 * @return l'intero corrispondente alla scelta dell'utente.
	 */
	public int whatClicked() {
		synchronized (this) {
			while (click == null)
				try {
					this.wait();
				} catch (Exception e) {
				}
		}
		Integer clickato = click;
		click = null;
		return clickato;
	}

	/**
	 * Metodo usato dalla grafica per notificare il click dell'utente.
	 * 
	 * @param click
	 */
	public void setClick(int click) {
		if (this.click == null) {
			this.click = click;
			synchronized (this) {
				this.notify();
			}
		}
	}

	public void reset() {
		click = null;
	}
}
