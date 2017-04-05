package online.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaccia remota attraverso cui passano i client per ottenere la
 * connessione ad una partita.
 * 
 */
public interface LogIn extends Remote {

	/**
	 * Metodo usato dal client per ottenere la connessione a una partita.
	 * 
	 * @return il canale collegato alla partita.
	 * @throws RemoteException
	 */
	public CanaleRMI getConnection() throws RemoteException;

}
