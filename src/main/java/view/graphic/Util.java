package view.graphic;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;

/**
 * Classe di aiuto per la sincronizzazione degli eventi e la gestione dei componenti
 * 
 *
 */
public class Util {
	/**
	 * metodo che restituisoce una lista di componenti da un cotainer.
	 * @param c
	 * @return
	 */
	public static ArrayList<Component> getAllComponents(final Container c) {
		Component[] comps = c.getComponents();
		ArrayList<Component> compList = new ArrayList<Component>();
		for (Component comp : comps) {
			compList.add(comp);
			if (comp instanceof Container)
				compList.addAll(getAllComponents((Container) comp));
		}
		return compList;
	}

	/**
	 * Metodo che permette di bloccare l'esecuzione del codice per tot tempo.
	 * @param milliseconds
	 */
	public static void timer(int milliseconds) {
		long beforeTime = System.currentTimeMillis();
		long elapsedTime = 0;
		long timer = (long) milliseconds;
		while (elapsedTime < timer)
			elapsedTime = System.currentTimeMillis() - beforeTime;
	}
}
