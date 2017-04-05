package partitaConsole;

import modelTest.GameBoardTest;
import modelTest.LupoTest;
import modelTest.PastoreTest;
import modelTest.PecoraNeraTest;
import modelTest.RegioneTest;
import modelTest.StradaTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import partitaGrafica.PartitaGraficaTest;

@RunWith(Suite.class)
@SuiteClasses({ GameBoardTest.class, LupoTest.class, PastoreTest.class,
		PecoraNeraTest.class, RegioneTest.class, StradaTest.class,
		GiocatoreTest.class, PartitaTest.class,
		TestPartitaSocket.class, PartitaGraficaTest.class })
public class AllTest {

}
