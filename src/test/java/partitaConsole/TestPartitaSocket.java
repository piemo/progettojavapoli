package partitaConsole;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import controller.PartitaSocket;
import online.socket.ServerSocketSheep;
import view.console.InterfacciaTest;
import view.graphic.Util;
/**
 * Classe di test per la partita via socket. Vengono eseguiti diversi thread, di cui uno è il client, per fare giocare il computer automaticamente
 * @author lorenzo
 *
 */
public class TestPartitaSocket {
	private Thread client1, client2, client3, server;

	/**
	 * Avvio del server
	 */
	@Before
	public void setUp() {

		server = new Thread() {
			public void run() {
				try {
					new ServerSocketSheep().startServer();
				} catch (IOException e) {
				}
			}
		};

	}

	/**
	 * Avvio dei thread dei client. la prima partita con 2 giocatori, la seconda con 3
	 */
	@Test
	public void test() {
		server.start();
		for (int i = 0; i < 2; i++) {
			client1 = new Thread() {
				public void run() {
					new PartitaSocket(new InterfacciaTest()).startPartita();
					;
				}
			};
			client1.start();
			client2 = new Thread() {
				public void run() {
					new PartitaSocket(new InterfacciaTest()).startPartita();
					;
				}
			};
			client2.start();
			if(i==1){
				client3 = new Thread() {
					public void run() {
						new PartitaSocket(new InterfacciaTest()).startPartita();
						;
					}
				};
				client3.start();
			}
			Util.timer(15*1000);
		}
	}
}
