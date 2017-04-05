package online.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaccia remota che permette al server i pingare i client e ai client di rispondere ai ping.
 *
 */
public interface PingPong extends Remote {
	
	/**
	 * Metodo usato dal server per pingare il client.
	 * @throws RemoteException
	 */
	public void pinga() throws RemoteException;
	
	/**
	 * Metodo usato dal client per rispondere ai ping del server.
	 * @throws RemoteException
	 */
	public void ponga() throws RemoteException;
}
