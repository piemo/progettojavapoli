package partitaConsole;

import static org.junit.Assert.*;
import model.Pastore;
import model.Terreno;

import org.junit.Before;
import org.junit.Test;

import controller.Giocatore;

/**
 * Test sulla classe del giocatore
 *
 */
public class GiocatoreTest {
	private Giocatore giocatoreTest;
	private Pastore pastoreTest;
	private int[] carteTest;

	/**
	 * Setup delle impostazioni del test
	 */
	@Before
	public void setUp() {
		giocatoreTest = new Giocatore("test", 1);
		pastoreTest = new Pastore(1, false);
		giocatoreTest.assegnaPastore(pastoreTest, false);
		giocatoreTest.setDanari(30);
		carteTest = new int[6];
	}

	/**
	 * Vengono testati i danari, gli indizi del giocatore, i pastori e le carte del giocatore. 
	 * Viene testata la funzione di acquisto e viene controllato il conseguente utilizzo di danari.
	 */
	@Test
	public void test() {
		assertEquals(pastoreTest, giocatoreTest.getPastore());
		assertNotEquals(pastoreTest, giocatoreTest.getSecondoPastore());
		assertEquals(30, giocatoreTest.getDanari());
		assertEquals(1, giocatoreTest.getIndice());
		
		for(int i=0; i<carteTest.length; i++){
			giocatoreTest.assegnaCarta(Terreno.getTerreno(i));
			assertEquals(1, giocatoreTest.getCarte()[i]);
			giocatoreTest.togliCarta(Terreno.getTerreno(i));
			assertEquals(0, giocatoreTest.getCarte()[i]);
		}
		
		giocatoreTest.acquistoCarta(Terreno.AGRICOLO, 2);
		assertEquals(28, giocatoreTest.getDanari());
	}

}
