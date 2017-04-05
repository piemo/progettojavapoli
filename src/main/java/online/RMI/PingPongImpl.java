package online.RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Oggetto remoto che implementa l'interfaccia remota per effetuare i ping. 
 *
 */
public class PingPongImpl extends UnicastRemoteObject implements PingPong{
	private static final long serialVersionUID = 2584290829981715606L;
	private String ping;
	
	
	/**
	 * @throws RemoteException
	 */
	public PingPongImpl() throws RemoteException {
		super();
	}

	public void pinga() throws RemoteException{
		synchronized(this){
			ping=null;
			while(ping==null){
				try{
					this.wait(8000);
					if(ping==null)
						throw new Exception();
				}catch(Exception e){
					throw new RemoteException();
				}
			}
		}
	}
	
	
	public void ponga(){
		ping = "pong";
		synchronized(this){
			this.notify();
		}
	}
}
