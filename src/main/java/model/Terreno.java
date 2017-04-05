package model;

/**
 * Enumerazione che rappresenta i tipi di terreno presenti nel gioco. Questi
 * sono messi per ordine alfabetico. Il terreno Sheepsburg viene messo in fondo
 * perchè si distingue dagli alti, infatti non è possibile acquistare carte di
 * quel tipo.
 */
public enum Terreno {
	AGRICOLO("AGRICOLO"), ARIDO("ARIDO"), FIUME("FIUME"), FORESTA("FORESTA"), MONTAGNA(
			"MONTAGNA"), PRATO("PRATO"),

	SHEEPSBURG("SHEEPSBURG");

	private String tipo;

	Terreno(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Metodo che, presa una stringa, restituisce il terreno corrispondente.
	 * Questo metodo viene usato per inizializzare le regioni.
	 * 
	 * @param nome
	 *            tipo del terreno cercato.
	 * @return il terrneo cercato.
	 */
	public static Terreno getTerreno(String nome) {
		if (nome.equals("AGRICOLO"))
			return Terreno.AGRICOLO;
		else if (nome.equals("PRATO"))
			return Terreno.PRATO;
		else if (nome.equals("MONTAGNA"))
			return Terreno.MONTAGNA;
		else if (nome.equals("ARIDO"))
			return Terreno.ARIDO;
		else if (nome.equals("FORESTA"))
			return Terreno.FORESTA;
		else if (nome.equals("FIUME"))
			return Terreno.FIUME;
		else
			return null;

	}

	/**
	 * Metodo che ritorna un terreno ad un determinato indice.
	 * @param indice
	 * @return
	 */
	public static Terreno getTerreno(int indice) {
		switch (indice) {
		case 0:
			return Terreno.AGRICOLO;
		case 1:
			return Terreno.ARIDO;
		case 2:
			return Terreno.FIUME;
		case 3:
			return Terreno.FORESTA;
		case 4:
			return Terreno.MONTAGNA;
		case 5:
			return Terreno.PRATO;
		default:
			return null;
		}
	}

	public String toString() {
		return tipo;
	}

}
