package agenda.modelo;

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

	/**
	 * Constructor de la clase AgendaContactos
	 */
	public AgendaContactos() {
		agenda = new TreeMap<>();
	}
	
	/**
	 * Añade un contacto a la agenda
	 * @param contacto a añadir
	 */
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
	/**
	 * Devuelve los contactos en la letra pasada como parámetro
	 * @param letra por la que empiezan los apellidos de los contactos a devolver
	 * @return conjunto de contactos en la letra pasada como parámetro
	 */
	public Set<Contacto> contactosEnLetra(Character letra) {
		letra = Character.toUpperCase(letra);
		if (!agenda.containsKey(letra)) {
			return null;
		} else {
			return agenda.get(letra);
		}
	}
	/**
	 * 
	 * @return cantidad total de contactos
	 */
	public int totalContactos() {
		int n = 0;
		for (Character c : agenda.keySet()) {
			n += agenda.get(c).size();
		}
		return n;
	}
	/**
	 * Representación textual de la agenda
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("AGENDA DE CONTACTOS\n");
		for (Character c : agenda.keySet()) {
			Set<Contacto> contactos = agenda.get(c);
			sb.append(c + " (" + contactos.size() + " contactos/s)\n\n");
			contactos.forEach(cont -> sb.append(cont.toString() + "\n"));
			sb.append("\n-------------------------\n");
		}
		sb.append("(" + this.totalContactos() + " contactos/s)");
		return sb.toString();
	}

	/**
	 * Devuelve el conjunto de contactos que contienen la cadena pasada como parámetro en nombre o apellidos
	 * @param cadena a buscar
	 * @return conjunto de contactos que contienen la cadena
	 */
	public List<Contacto> buscarContactos(String texto) {
		texto = texto.toUpperCase();
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
	/**
	 * Devuelve los contactos personales en la letra indicada
	 * @param letra por la que empezarán los apellidos
	 * @return conjunto de contactos personales
	 */
	public List<Personal> personalesEnLetra(char letra) {
		letra = Character.toUpperCase(letra);
		List<Personal> personales = new ArrayList<>();
		Set<Contacto> contactos = agenda.get(letra);
		if (contactos == null) {
			return null;
		}
		for (Contacto cont : contactos) {
			if (cont instanceof Personal) {
				personales.add((Personal) cont);
			}
		}
		return personales;
	}
	
	/**
	 * Devuelve el conjunto de contactos cuyo cumpleaños coincide con la fecha del sistema
	 * @return conjunto de contactos que cumplen años
	 */
	public List<Personal> felicitar() {
		List<Personal> contactos = new ArrayList<>();
		Set<Map.Entry<Character, Set<Contacto>>> entradas = agenda.entrySet();
		Iterator<Map.Entry<Character, Set<Contacto>>> it = entradas.iterator();
		while (it.hasNext()) {
			Set<Contacto> entrada = it.next().getValue();
			for (Contacto contacto : entrada) {
				if (contacto instanceof Personal && ((Personal) contacto).esCumpleaños()) {
					contactos.add((Personal) contacto);
				}
			}
		}
		return contactos;
	}

	/**
	 * Devuelve un mapa/diccionario ordenado por relacion, y los nombres de los contactos ordenados por apellido
	 * @return mapa ordenado por relacion
	 */
	public Map<Relacion, List<String>> personalesPorRelacion() {
		Map<Relacion, List<String>> personales = new TreeMap<>();
		Set<Character> claves = agenda.keySet();
		for (char c : claves) {
			List<Personal> contactos = personalesEnLetra(c);
			for (Personal con : contactos) {
				if (personales.get(con.getRelacion()) == null) {
					personales.put(con.getRelacion(), new ArrayList<String>());
				}
				personales.get(con.getRelacion()).add(con.getApellidos() + " " + con.getNombre());
			}
		}
		return personales;
	}

	/**
	 * Devuelve los contactos personales en la letra pasada como parámetro, ordenados por fecha de nacimiento
	 * @param letra de comienzo de los apellidos de los contactos
	 * @return conjunto de contactos personales ordenados
	 */
	public List<Personal> personalesOrdenadosPorFechaNacimiento(char letra) {
		letra = Character.toUpperCase(letra);
		if (!agenda.containsKey(letra)) {
			return null;
		}
		List<Personal> personales = personalesEnLetra(letra);
		Collections.sort(personales,
				(p1, p2) -> (int) p1.getFechaNacimiento().compareTo(p2.getFechaNacimiento()));
		return personales;

	}

}
