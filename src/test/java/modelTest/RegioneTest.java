package modelTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import view.InterfacciaPartitaUtente;
import view.console.ConsoleView;
import model.*;

/**
 * Metodo di test sulle funzioni delle regioni
 *
 */
public class RegioneTest {
	private Regione regioneTest1;
	private Regione regioneTest2;
	private GameBoard gameBoardTest;

	/**
	 * Setup delle regioni. viene creata una gameboard e prese 2 regioni a caso.
	 */
	@Before
	public void setUp() {
		gameBoardTest = new GameBoard(3);
		gameBoardTest.setUpPecore();
		regioneTest1 = gameBoardTest.getRegione(9);
		regioneTest2 = gameBoardTest.getRegione(13);
	}

	/**
	 * esecuzioni del test.
	 * in ordine vengono eseguiti:
	 * -test sul controllo di non occupazione della regione.
	 * -Controllo del numero di pecore iniziali della regione.
	 * -Controllo del tipo di terreno della regione.
	 * -COntrollo dell'id della regione.
	 * -Controllo delle strade confinanti della regione.
	 * -Spostamento di un agnello da una regione all'altra.
	 * -Spostamento di un montone da una regione all'altra.
	 * -Spostamento di una pecora da una regione all'altra.

	 * 	 */
	@Test
	public void testaRegioni() {
		assertFalse(regioneTest1.isTuttaOccupata()
				|| regioneTest1.isTuttaRecintata());
		assertFalse(regioneTest2.isTuttaRecintata()
				|| regioneTest2.isTuttaOccupata());
		assertFalse(regioneTest1.accoppiamentoPossibile()
				|| regioneTest2.accoppiamentoPossibile());

		int numeroPecoreExpected = 1;
		assertEquals(numeroPecoreExpected, regioneTest1.getNumeroPecore());
		assertEquals(numeroPecoreExpected, regioneTest2.getNumeroPecore());

		int numAgnelli1 = regioneTest1.getNumeroAgnelli();
		int numMontoni1 = regioneTest1.getNumeroMontoni();
		int numPecoreFemmine1 = regioneTest1.getNumeroPecoreFemmine();
		assertEquals(numeroPecoreExpected, numAgnelli1 + numMontoni1
				+ numPecoreFemmine1);

		Terreno terrenoExpected1 = Terreno.PRATO;
		assertEquals(terrenoExpected1, regioneTest1.getTerreno());

		int IDexpected2 = 13;
		assertEquals(IDexpected2, regioneTest2.getID());

		Strada stradaExpected = gameBoardTest.getStrada(25);
		assertEquals(stradaExpected, regioneTest1.getStradaConValoreX(6));
		assertEquals(stradaExpected, regioneTest2.getStradaConValoreX(6));

		Agnello pecoraTest = regioneTest2.prelevaAgnello();
		if (pecoraTest != null) {
			regioneTest1.posizionaPecora(pecoraTest);
			numAgnelli1++;
			assertEquals(regioneTest1.getNumeroAgnelli(), numAgnelli1);
		} else {
			assertEquals(0, regioneTest2.getNumeroAgnelli());
		}

		pecoraTest = regioneTest2.prelevaMontone();
		if (pecoraTest != null) {
			regioneTest1.posizionaPecora(pecoraTest);
			numMontoni1++;
			assertEquals(regioneTest1.getNumeroMontoni(), numMontoni1);

		} else {
			assertEquals(0, regioneTest2.getNumeroMontoni());
		}

		pecoraTest = regioneTest2.prelevaPecoraFemmina();
		if (pecoraTest != null) {
			regioneTest1.posizionaPecora(pecoraTest);
			numPecoreFemmine1++;
			assertEquals(regioneTest1.getNumeroPecoreFemmine(),
					numPecoreFemmine1);

		} else {
			assertEquals(0, regioneTest2.getNumeroPecoreFemmine());
		}

		InterfacciaPartitaUtente interfaccia = new ConsoleView();
		regioneTest2.nascitaAgnello();
		regioneTest2.crescitaPecorelle(interfaccia);
		regioneTest2.crescitaPecorelle(interfaccia);
		assertEquals(0, regioneTest2.getNumeroAgnelli());
		assertEquals(
				1,
				regioneTest2.getNumeroMontoni()
						+ regioneTest2.getNumeroPecoreFemmine());
	}
}
