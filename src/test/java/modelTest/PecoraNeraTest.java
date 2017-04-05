package modelTest;

import static org.junit.Assert.*;
import model.GameBoard;
import model.PecoraNera;
import org.junit.Before;
import org.junit.Test;

/**
 * Test sulla funzionalità della pecora nera
 *
 */
public class PecoraNeraTest {
	private GameBoard gameBoard;
	private PecoraNera pecoraNera;

	/**
	 * Viene eseguito il setup del test. viene creata e posizionata una pecora nera sulla Gameboard.
	 */
	@Before
	public void setUp() {
		gameBoard = new GameBoard(3);
		pecoraNera = gameBoard.getPecoraNera();
	}

	/**
	 * Viene testato il movimento casuale della pecora nera.
	 */
	@Test
	public void testaPecoraNera() {
		assertEquals(gameBoard.getRegione(0), pecoraNera.getRegioneAttuale());

		pecoraNera.muoviti(gameBoard.getRegione(5));
		assertEquals(gameBoard.getRegione(5), pecoraNera.getRegioneAttuale());

		pecoraNera.movimentoCasuale(4);
		assertEquals(gameBoard.getRegione(2), pecoraNera.getRegioneAttuale());

	}
}