package online.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaccia remota. Contiene i metodi per la comunicazione tra client e
 * server.
 * 
 */
public interface CanaleRMI extends Remote {

	/**
	 * Metodo usato dal server per leggere una stringa dal client.
	 * 
	 * @return la stringa scritta dal client.
	 * @throws RemoteException
	 */
	public String readLineServer() throws RemoteException;

	/**
	 * Metodo usato dal server per leggere un intero dal client.
	 * 
	 * @return l'intero scritto dal client.
	 * @throws RemoteException
	 */
	public Integer readIntServer() throws RemoteException;

	/**
	 * Metodo usato dal server per scrivere una stringa che verrà letta dal
	 * client.
	 * 
	 * @param nuovaStringa
	 * @throws RemoteException
	 */
	public void writeServer(String nuovaStringa) throws RemoteException;

	/**
	 * Metodo usato dal server per scrivere un intero che verrà letto dal
	 * client.
	 * 
	 * @param nuovoNum
	 * @throws RemoteException
	 */
	public void writeServer(Integer nuovoNum) throws RemoteException;

	/**
	 * Metodo usato dal client per leggere una stringa inviata dal server.
	 * 
	 * @return la stringa scritta dal server.
	 * @throws RemoteException
	 */
	public String readLineClient() throws RemoteException;

	/**
	 * Metodo usato dal client per leggere un intero scritto dal server.
	 * 
	 * @return l'intero scritto dal server.
	 * @throws RemoteException
	 */
	public Integer readIntClient() throws RemoteException;

	/**
	 * Metodo usato dal client per scrivere una stringa al server.
	 * 
	 * @param nuovaStringa
	 * @throws RemoteException
	 */
	public void writeClient(String nuovaStringa) throws RemoteException;

	/**
	 * Metodo usato dal client per scrivere un intero al server.
	 * 
	 * @param nuovoNum
	 * @throws RemoteException
	 */
	public void writeClient(Integer nuovoNum) throws RemoteException;

	/**
	 * Metodo per ottenre il session ID del canale, ovvero l'indice della
	 * partita a cui è collegato.
	 * 
	 * @return sessionID.
	 * @throws RemoteException
	 */
	public int getSessionID() throws RemoteException;

	/**
	 * Metodo per ottenere l'indice del giocatore a cui appartiene il canale
	 * all'interno di una partita.
	 * 
	 * @return l'indice personale.
	 * @throws RemoteException
	 */
	public int getIndicePersonale() throws RemoteException;

	/**
	 * Metodo che permette al client di riconnettersi alla partita.
	 * @param indicePersonale
	 * @throws RemoteException
	 */
	public void reconnect(int indicePersonale) throws RemoteException;

	/**
	 * Metodo che ritorna l'oggetto remoto per efettuare i ping.
	 * @return
	 * @throws RemoteException
	 */
	public PingPong getPingPong() throws RemoteException;
		
	}
	
