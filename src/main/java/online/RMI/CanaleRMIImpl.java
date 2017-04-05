package online.RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Classe che implementa l'interfaccia remota CanaleRMI.
 * 
 */
public class CanaleRMIImpl extends UnicastRemoteObject implements CanaleRMI {

	private static final long serialVersionUID = 3557388799834075772L;
	private int sessionID;
	private int indicePersonale;
	private GestorePartitaRMI partita;
	private String stringaServer;
	private String stringaClient;
	private PingPongImpl pingPong = new PingPongImpl();
	private boolean attivo = true;

	/**
	 * Costruttore.
	 * 
	 * @param sessionID
	 * @param indicePersonale
	 * @throws RemoteException
	 */
	public CanaleRMIImpl(int sessionID, int indicePersonale)
			throws RemoteException {
		this.indicePersonale = indicePersonale;
		this.sessionID = sessionID;
		stringaServer = null;
		stringaClient = null;
	}

	public String readLineServer() throws RemoteException {
		synchronized (this) {
			while (stringaClient == null) {
				try {
					this.wait(2000);
					if(stringaClient == null)
						return null;
				} catch (Exception e) {
					throw new RemoteException();
				}
			}
			String stringaLetta = stringaClient;
			stringaClient = null;
			notify();
			return stringaLetta;
		}

	}

	public Integer readIntServer() throws RemoteException {
		synchronized (this) {
			while (stringaClient == null) {
				try {
					this.wait(2000);
					if(stringaClient == null)
						return null;
				} catch (Exception e) {
					throw new RemoteException();
				}
			}
			Integer intLetto = Integer.parseInt(stringaClient);
			stringaClient = null;
			notify();
			return intLetto;
		}
	}

	public void writeServer(String nuovaStringa) throws RemoteException {
		synchronized (this) {
			while (stringaServer != null) {
				try {
					this.wait(8000);
				} catch (Exception e) {
				}
			}
			stringaServer = nuovaStringa;
			notify();
		}

	}

	public void writeServer(Integer nuovoNum) throws RemoteException {
		synchronized (this) {
			while (stringaServer != null) {
				try {
					this.wait(8000);
				} catch (Exception e) {
				}
			}
			stringaServer = nuovoNum.toString();
			notify();
		}
	}

	public String readLineClient() throws RemoteException {
		synchronized (this) {
			while (stringaServer == null) {
				try {
					this.wait();
				} catch (Exception e) {
				}
			}
			String stringaLetta = stringaServer;
			stringaServer = null;
			notify();
			return stringaLetta;
		}

	}

	public Integer readIntClient() throws RemoteException {
		synchronized (this) {
			while (stringaServer == null) {
				try {
					this.wait();
				} catch (Exception e) {
				}
			}
			Integer intLetto = Integer.parseInt(stringaServer);
			stringaServer = null;
			notify();
			return intLetto;
		}
	}

	public void writeClient(String nuovaStringa) throws RemoteException {
		synchronized (this) {
			while (stringaServer != null) {
				try {
					this.wait();
				} catch (Exception e) {
				}
			}
			stringaClient = nuovaStringa;
			notify();
		}

	}

	public void writeClient(Integer nuovoNum) throws RemoteException {
		synchronized (this) {
			while (stringaServer != null) {
				try {
					this.wait();
				} catch (Exception e) {
				}
			}
			stringaClient = nuovoNum.toString();
			notify();
		}
	}

	public int getSessionID() throws RemoteException {
		return sessionID;
	}

	public int getIndicePersonale() throws RemoteException {
		return indicePersonale;
	}
	
	
	public void assegnaPartita(GestorePartitaRMI gestorePartitaRMI) {
		this.partita = gestorePartitaRMI;	
	}

	public void reconnect(int indicePersonale) throws RemoteException {
		partita.riconnetti(indicePersonale);
	}

	public PingPong getPingPong() throws RemoteException {
		return pingPong;
	}

	/**
	 * Metodo per sapere se il canale è attivo.
	 * @return
	 */
	public boolean isAttivo() {
		return attivo ;
	}

	/**
	 * MEtodo per disattivare il canale.
	 */
	public void disattiva() {
		attivo = false;
	}

	/**
	 * Metodo per attivare il canale.
	 * 
	 */
	public void attiva() {
		attivo = true;
	}

}
