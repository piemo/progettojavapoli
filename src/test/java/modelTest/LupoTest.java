package modelTest;

import static org.junit.Assert.*;
import model.GameBoard;
import model.Lupo;
import model.Regione;

import org.junit.Before;
import org.junit.Test;

/**
 * Classe di test sulle funzioni del lupo.
 *
 *
 */
public class LupoTest {
	private GameBoard gameBoard;
	private Lupo lupo;

	/**
	 * Setup delle precondizioni del test. Il lupo viene posizionato su una regionee e viene aggiunta una pecora.
	 */
	@Before
	public void setUp() {
		gameBoard = new GameBoard(3);
		lupo = gameBoard.getLupo();
		gameBoard.getRegione(5).aggiungiPecoraCasualmente();
	}

	/**
	 * Viene testato il movimento del lupo e la conseguente uccisione della pecora.
	 */
	@Test
	public void testalupo() {
		Regione regioneDiArrivo = gameBoard.getRegione(5);
		lupo.movimentoCasuale(6);
		assertEquals(regioneDiArrivo, lupo.getRegioneAttuale());

		int numPecore = regioneDiArrivo.getNumeroPecore();
		lupo.sbrana();
		assertEquals(numPecore - 1, regioneDiArrivo.getNumeroPecore());
	}
}
