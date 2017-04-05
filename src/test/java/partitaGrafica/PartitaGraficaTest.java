package partitaGrafica;

import org.junit.Test;

import view.graphic.Util;
import controller.Partita;

/**
 * Test della partita con grafica. Viene eseguito il test di una partita in metodo casuale con grafica per controllar che non ci siano 
 * errori o crash.
 * @author lorenzo
 *
 */
public class PartitaGraficaTest {
	
	/**
	 * Viene fatta partite una partita grafica con interfaccia da test di grafica e viene dato un timeout di 120 sec.
	 */
	@Test public void test(){
		Thread grafica = new Thread(new Runnable() {
			
			public void run() {
				new Partita("test", true).startPartita();
			}
		});
		
		grafica.start();
		
		Util.timer(120*1000);
		
		grafica.interrupt();
	}
}
