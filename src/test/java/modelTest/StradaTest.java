package modelTest;

import static org.junit.Assert.*;
import model.GameBoard;
import model.Regione;
import model.Strada;

import org.junit.Before;
import org.junit.Test;

/**
 * Classe di test per le funzioni delle strade
 * @author lorenzo
 *
 */
public class StradaTest {
	private Strada strada;
	private GameBoard gameBoard;

	/**
	 * Setup iniziale delle strade
	 */
	@Before
	public void setUp() {
		gameBoard = new GameBoard(3);
		strada = gameBoard.getStrada(1);
	}

	/**
	 * VEngono testate le regioni adiacenti.
	 */
	@Test
	public void testaStrada() {
		Regione[] regioniAdiacenti = strada.getRegioniAdicaenti();
		Regione expected1 = gameBoard.getRegione(2);
		Regione expected2 = gameBoard.getRegione(3);
		Regione regioneOppostaAlla1 = strada
				.getRegioneOpposta(regioniAdiacenti[0]);
		strada.setOccupata();

		assertEquals(expected1, regioniAdiacenti[0]);
		assertEquals(expected2, regioniAdiacenti[1]);
		assertEquals(expected2, regioneOppostaAlla1);
		assertFalse(strada.isRecintata());
		assertTrue(strada.isOccupata());
	}
}
