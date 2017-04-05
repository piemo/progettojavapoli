package online.RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Oggetto remoto che implementa l'interfaccia remota di LogIn.
 *
 */
public class LogInImpl extends UnicastRemoteObject implements LogIn {
	private static final long serialVersionUID = -946989264286257494L;
	private ArrayList<CanaleRMIImpl> listaCanali;
	private Integer currentSessionID;

	/**
	 * Costruttore per l'oggetto remoto di LogIn.
	 * @param listaCanali la lista di canali relativi alla sesione corrente.
	 * @param sessionID la sessione della partita,
	 * @throws RemoteException
	 */
	public LogInImpl(ArrayList<CanaleRMIImpl> listaCanali, Integer sessionID)
			throws RemoteException {
		this.listaCanali = listaCanali;
		currentSessionID = sessionID;
	}

	public CanaleRMI getConnection() throws RemoteException {
		CanaleRMIImpl canale = new CanaleRMIImpl(currentSessionID,
				listaCanali.size());
		listaCanali.add(canale);
		return (CanaleRMI) canale;
	}

	/**
	 * Metodo per sapere quanti giocatori ci sono nella sessione attuale.
	 * @return il numero di giocatori.
	 */
	public int getNumeroGiocatori() {
		return listaCanali.size();
	}

}
