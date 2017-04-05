package partitaConsole;

import online.RMI.ServerRMISheep;

import org.junit.Before;
import org.junit.Test;

import view.console.InterfacciaTest;
import view.graphic.Util;
import controller.PartitaRMI;
/**
 * Classe di test per la RMI. Vengono fatti giocare in automatico diverse partite tra server e client.
 *
 */
public class TestPartitaRMI {
	private Thread client1, client2, client3, server;

	/**
	 * Avvio del server
	 */
	@Before
	public void setUp() {
		server = new Thread(){
			public void run() {
			new ServerRMISheep().startServer();
			
		}
	};
	}

	
	/**
	 * Avvio dei thread dei client. la prima partita con 2 giocatori, la seconda con 3
	 */
	@Test
	public void test() {
		server.start();
		Util.timer(2000);
		for (int i = 0; i < 2; i++) {
			client1 = new Thread() {
				public void run() {
					new PartitaRMI(new InterfacciaTest()).startPartita();
					;
				}
			};
			client1.start();
			client2 = new Thread() {
				public void run() {
					new PartitaRMI(new InterfacciaTest()).startPartita();
					;
				}
			};
			client2.start();
			if(i==1){
				client3 = new Thread() {
					public void run() {
						new PartitaRMI(new InterfacciaTest()).startPartita();
						;
					}
				};
				client3.start();
			}
			
			Util.timer(15*1000);
		}
	}
}
