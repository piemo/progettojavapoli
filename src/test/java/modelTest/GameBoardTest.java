package modelTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import model.GameBoard;
import model.Lupo;
import model.Pastore;
import model.PecoraNera;
import model.Regione;
import model.Strada;
import model.Terreno;

/**
 * Classe di test sulle funzionalità delle gameboard.
 * 
 * 
 *
 */
public class GameBoardTest {
	private GameBoard gameBoardTest;
	private Regione[] regioniTest;
	private ArrayList<Strada> stradeTest;
	private ArrayList<Pastore> pastoriTest;

	/**
	 * Setup delle condizioni iniziali della gameboard prima dell'inizio del test.
	 */
	@Before
	public void setUp() {
		gameBoardTest = new GameBoard(3);
		gameBoardTest.setUpPecore();
		regioniTest = gameBoardTest.getRegioni();
		stradeTest = gameBoardTest.getStradeLibere();
		pastoriTest = gameBoardTest.getPastori();
	}

	/**
	 * Esecuzione del vari test della gameboard. vegono testate le regioni, i terreni, i vari posizionamenti, le strade, le funzioni
	 * della pecora nera ed il lupo.
	 */
	@Test
	public void testGameBoard() {
		// ID REGIONI
		for (int i = 0; i < regioniTest.length; i++)
			assertEquals(i, regioniTest[i].getID());

		// TERRENI
		assertTrue(regioniTest[1].getTerreno() == regioniTest[4].getTerreno()
				&& regioniTest[4].getTerreno() == regioniTest[9].getTerreno());
		assertTrue(regioniTest[2].getTerreno() == regioniTest[3].getTerreno()
				&& regioniTest[3].getTerreno() == regioniTest[5].getTerreno());
		assertEquals(Terreno.SHEEPSBURG, regioniTest[0].getTerreno());

		// STRADE
		assertEquals(1, stradeTest.get(1).getID());
		assertEquals(42, stradeTest.size());
		assertTrue(gameBoardTest.getStradeRecintate().size() == 0);

		// POSIZIONIAMO I PASTORI
		Pastore pastore1 = pastoriTest.get(0);
		pastore1.posizionaPastore(stradeTest.get(0));
		pastoriTest.get(1).posizionaPastore(stradeTest.get(3));
		pastoriTest.get(2).posizionaPastore(stradeTest.get(13));
		stradeTest = gameBoardTest.getStradeLibere(); // DA QUI
														// stradeTest!=stradeDellaGameboard
		assertTrue(gameBoardTest.getStrada(0).isOccupata()
				&& gameBoardTest.getStrada(3).isOccupata()
				&& gameBoardTest.getStrada(13).isOccupata());
		assertFalse(gameBoardTest.getStrada(0).isRecintata()
				|| gameBoardTest.getStrada(3).isRecintata()
				|| gameBoardTest.getStrada(13).isRecintata());
		assertEquals(gameBoardTest.getStrada(4), pastore1.getStrada()
				.getStradeVicineLibere().get(0));
		assertEquals(gameBoardTest.getPastore(2).getStrada().getStradeVicine(),
				gameBoardTest.getPastore(2).getStrada().getStradeVicineLibere());

		// SPOSTIAMO UN PASTORE
		pastore1.spostaPastore(gameBoardTest.getStrada(20));
		assertTrue(gameBoardTest.getStrada(0).isRecintata());
		assertTrue(pastoriTest.get(2).getStrada().getStradeVicineLibere()
				.size() == 3);

		// MUOVIAMO LA PECORA NERA E UCCIDIAMOLA
		PecoraNera pecoraNeraTest = gameBoardTest.getPecoraNera();
		assertEquals(regioniTest[0], pecoraNeraTest.getRegioneAttuale());
		pecoraNeraTest.movimentoCasuale(1);
		assertEquals(regioniTest[6], pecoraNeraTest.getRegioneAttuale());
		assertEquals(0, regioniTest[0].getNumeroPecore());
		assertEquals(2, regioniTest[6].getNumeroPecore());
		pecoraNeraTest.muoviti(regioniTest[3]);
		pecoraNeraTest.movimentoCasuale(6);
		assertEquals(regioniTest[3], pecoraNeraTest.getRegioneAttuale());
		gameBoardTest.eliminaPecoraNera();
		pecoraNeraTest = gameBoardTest.getPecoraNera();
		assertNull(pecoraNeraTest);

		// MUOVIAMO IL LUPO
		Lupo lupoTest = gameBoardTest.getLupo();
		assertEquals(regioniTest[0], lupoTest.getRegioneAttuale());
		assertNull(lupoTest.sbrana());
		lupoTest.movimentoCasuale(2);
		assertEquals(regioniTest[10], lupoTest.getRegioneAttuale());
		lupoTest.sbrana();
		assertEquals(0, regioniTest[10].getNumeroPecore());

	}
}
