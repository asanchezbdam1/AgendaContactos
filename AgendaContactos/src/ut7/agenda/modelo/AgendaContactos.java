package ut7.agenda.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * 
 * @author Ander Gaona y Asier Sánchez
 *
 */
public class AgendaContactos {
	private Map<Character, Set<Contacto>> agenda;

	public AgendaContactos() {
		agenda = new TreeMap<>();
	}

	public void añadirContacto(Contacto contacto) {
		Character letra = contacto.getPrimeraLetra();
		if (agenda.containsKey(letra)) {
			Set<Contacto> contactos = agenda.get(letra);
			contactos.add(contacto);
		} else {
			Set<Contacto> contactos = new TreeSet<>();
			contactos.add(contacto);
			agenda.put(letra, contactos);
		}
	}

	public void contactosEnLetra() {

	}

	public int totalContactos() {
		int n = 0;
		for (Character c : agenda.keySet()) {
			n += agenda.get(c).size();
		}
		return n;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Character c : agenda.keySet()) {
			agenda.get(c).forEach(cont -> sb.append(cont.toString()));
		}
		return sb.toString();
	}

	public List<Contacto> buscarContactos(String texto) {

		return null;

	}

	public List<Personal> personalesEnLetra(char letra) {
		List<Personal> personales = new ArrayList();
		for (Contacto cont : agenda.get(letra)) {
			if (cont instanceof Personal) {
				personales.add((Personal) cont);
			}
		}
		return personales;
	}

	public List<Personal> felicitar() {

		return null;
	}

	public Map<Relacion, ArrayList<Personal>> personalesPorRelacion() {
		Map<Relacion, ArrayList<Personal>> personales = new TreeMap<>();
		Set<Character> claves = agenda.keySet();
		for (char c : claves) {
			List<Personal> contactos = personalesEnLetra(c);
			for (Personal con : contactos) {
				List<Personal> arlc = personales.get(con.getRelacion());
				if (arlc == null) {
					personales.put(con.getRelacion(), new ArrayList<Personal>());
					personales.get(con.getRelacion()).add(con);
				}
				else {
					arlc.add(con);
				}
			}
		}
		for (Relacion r : personales.keySet()) {
			Collections.sort(personales.get(r));
		}
		return personales;
	}

	public List<Personal> personalesOrdenadosPorFechaNacimiento(char letra) {

		return null;

	}

}
