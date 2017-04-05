package online.RMI;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server per gestire le partite via RMI.
 * 
 */
@SuppressWarnings("unused")
public class ServerRMISheep {

	public final static String SERVER_NAME = "sheepland";
	public final static int SERVER_PORT = 23456;
	private static final long TIME_OUT = 10000;
	private ArrayList<CanaleRMIImpl> listaCanali;
	private ArrayList<GestorePartitaRMI> listaPartite;
	private Integer currentSessionID;

	/**
	 * Metodo per far partire il server, creare il registry ed accogliere
	 * giocatori per creare nuove partite.
	 * 
	 */
	public void startServer() {
		ExecutorService executorService = Executors.newCachedThreadPool();
		listaPartite = new ArrayList<GestorePartitaRMI>();
		currentSessionID = 0;
		try {
			Registry registry = LocateRegistry.createRegistry(SERVER_PORT);
			System.out.println("Server registry created.");
			while (true) {
				listaCanali = new ArrayList<CanaleRMIImpl>();
				LogIn log = new LogInImpl(listaCanali, currentSessionID);
				registry.rebind(SERVER_NAME, log);

				System.out.println("Creating game n°" + currentSessionID);
				while (((LogInImpl) log).getNumeroGiocatori() < 2) {
					long beforeTime = System.currentTimeMillis();
					long elapsedTime = 0;
					while (elapsedTime < 1000)
						elapsedTime = System.currentTimeMillis() - beforeTime;
				}
				long before = System.currentTimeMillis();
				long elapsed = System.currentTimeMillis() - before;
				System.out.println("Starting countdown");
				while (((LogInImpl) log).getNumeroGiocatori() < 4
						&& elapsed < TIME_OUT) {
					long beforeTime = System.currentTimeMillis();
					long elapsedTime = 0;
					while (elapsedTime < 1000)
						elapsedTime = System.currentTimeMillis() - beforeTime;
					elapsed = System.currentTimeMillis() - before;
				}
				System.out.println("Starting game n°" + currentSessionID);
				CanaleRMIImpl[] canali = new CanaleRMIImpl[listaCanali.size()];
				for (int i = 0; i < listaCanali.size(); i++){
					canali[i] = listaCanali.get(i);
				}
				GestorePartitaRMI gestorePartitaRMI = new GestorePartitaRMI(
						canali);
				for (int i = 0; i < listaCanali.size(); i++){
					canali[i].assegnaPartita(gestorePartitaRMI);
				}
				executorService.submit(gestorePartitaRMI);
				currentSessionID++;
			}
		} catch (RemoteException e) {
			System.err.println("Remote exception:");
			e.printStackTrace();
		}
	}

	/*
	 * Metodo main del server. Per avviare il server, cliccare sul tasto verde
	 * di play.
	 */
	public static void main(String[] args) {
		new ServerRMISheep().startServer();
	}
	
	/**
	 * Metodo per chiuedere il server.
	 * @throws RemoteException
	 */
	public void exit() throws RemoteException
	{
	    try{
	        Naming.unbind(SERVER_NAME);

	        UnicastRemoteObject.unexportObject((Remote) this, true);

	        System.out.println("Server exiting.");
	    }
	    catch(Exception e){}
	}
}
