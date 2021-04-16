package ut7.agenda.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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

	public Set<Contacto> contactosEnLetra(Character letra) {
		if (!agenda.containsKey(letra)) {
			throw new IllegalArgumentException("No existe esa letra en la agenda");
		} else {
			return agenda.get(letra);
		}
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
		List<Contacto> contactos = new ArrayList<>();
		Set<Map.Entry<Character, Set<Contacto>>> entradas = agenda.entrySet();
		Iterator<Map.Entry<Character, Set<Contacto>>> it = entradas.iterator();
		while (it.hasNext()) {
			Set<Contacto> entrada = it.next().getValue();
			for (Contacto contacto : entrada) {
				if (contacto.getNombre().lastIndexOf(texto) != -1 || contacto.getApellidos().lastIndexOf(texto) != -1) {
					contactos.add(contacto);
				}
			}
		}
		return contactos;

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
		List<Personal> contactos = new ArrayList<>();
		Set<Map.Entry<Character, Set<Contacto>>> entradas = agenda.entrySet();
		Iterator<Map.Entry<Character, Set<Contacto>>> it = entradas.iterator();
		while (it.hasNext()) {
			Set<Contacto> entrada = it.next().getValue();
			for (Contacto contacto : entrada) {
				if (contacto instanceof Personal && ((Personal) contacto).esCumpleaños())
					;
				{
					contactos.add((Personal) contacto);
				}
			}
		}
		return contactos;
	}

	public void personalesPorRelacion() {

	}

	public List<Personal> personalesOrdenadosPorFechaNacimiento(char letra) {

		if (!agenda.containsKey(letra)) {
			throw new IllegalArgumentException("No existe esa letra en la agenda");
		}
		List<Personal> personales = personalesEnLetra(letra);
		Collections.sort(personales,
				(p1, p2) -> (int) Math.signum(p1.getFechaNacimiento().compareTo(p2.getFechaNacimiento())));
		return personales;

	}

}
