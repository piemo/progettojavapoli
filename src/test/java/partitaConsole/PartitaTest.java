package partitaConsole;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controller.Partita;
/**
 * Classe di test della partita. Vengono eseguite 100 partite di seguito.
 *
 *
 */
public class PartitaTest {
	private Partita partitaTest;

	/**
	 * Vengono eseguite qui le partite senza test, per controllare che il gioco non crashi.
	 */
	@Before
	public void setUp() {
		for (int i = 0; i < 10; i++)
			new Partita("test").startPartita();

		partitaTest = new Partita("test");
		partitaTest.setUp();
	}

	/**
	 * test fittizzi.
	 */
	@Test
	public void test() {
		int[] terreni = partitaTest.valutazioneTerreni();
		assertEquals(3, terreni[0]);
		assertEquals(3, terreni[1]);
		assertEquals(3, terreni[2]);
		assertEquals(3, terreni[3]);
		assertEquals(3, terreni[4]);
		assertEquals(3, terreni[5]);

	}

}
