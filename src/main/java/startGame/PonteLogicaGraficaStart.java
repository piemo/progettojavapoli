package startGame;

/**
 * Classe che mette in comunicazione Start e IntroFrame.
 * 
 */
public class PonteLogicaGraficaStart {
	private Boolean grafica = null;
	private Boolean online = null;
	private Boolean rmi = null;

	/**
	 * Metodo per sapere se l'utente ha scelto di giocare con la grafica o con
	 * la console.
	 * 
	 * @return
	 */
	public boolean isGraphic() {
		synchronized (this) {
			while (grafica == null)
				try {
					this.wait();
				} catch (Exception e) {
				}
		}
		return grafica;
	}

	/**
	 * Metodo per saper ese l'utente ha scelto di giocare online opuure offline.
	 * 
	 * @return
	 */
	public boolean isOnline() {
		synchronized (this) {
			while (online == null)
				try {
					this.wait();
				} catch (Exception e) {
				}
		}
		return online;
	}

	/**
	 * Metodo per saper ese l'utente ha scelto di usrae RMI o Socket.
	 * 
	 * @return
	 */
	public boolean isRmi() {
		synchronized (this) {
			while (rmi == null)
				try {
					this.wait();
				} catch (Exception e) {
				}
		}
		return rmi;
	}

	/**
	 * Metodo per notificare la scleta dell'utente rigurado alla grafica.
	 * 
	 * @param grafica
	 */
	public void setGraphic(boolean grafica) {
		this.grafica = grafica;
		synchronized (this) {
			this.notify();
		}
	}

	/**
	 * Metodo per notificare la scelta dell'utente riguardo al gioco online
	 * oppure offline.
	 * 
	 * @param online
	 */
	public void setOnline(boolean online) {
		this.online = online;
		synchronized (this) {
			this.notify();
		}
	}

	/**
	 * Metodo per notificare la scelta dell'utente riguuardo al tipo di
	 * connessione tra RMI o socket.
	 * 
	 * @param rmi
	 */
	public void setRmi(boolean rmi) {
		this.rmi = rmi;
		synchronized (this) {
			this.notify();
		}
	}

}
