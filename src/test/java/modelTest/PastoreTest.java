package modelTest;

import static org.junit.Assert.*;
import model.GameBoard;
import model.Pastore;
import org.junit.Before;
import org.junit.Test;

/**
 * Test sul pastore.
 * 
 *
 */
public class PastoreTest {
	private GameBoard gameBoard;
	private Pastore pastore;

	/**
	 * Setuo delle condizioni iniziali
	 */
	@Before
	public void setUp() {
		gameBoard = new GameBoard(3);

	}

/**
 * Viene testato il posizionamento del pastore
 */
	@Test
	public void TestPastore() {
		pastore = gameBoard.getPastore(0);
		pastore.posizionaPastore(gameBoard.getStrada(0));
		assertEquals(pastore.getStrada(), gameBoard.getStrada(0));
		pastore.posizionaPastore(gameBoard.getStrada(8));
		assertEquals(pastore.getStrada(), gameBoard.getStrada(8));
	}
}
