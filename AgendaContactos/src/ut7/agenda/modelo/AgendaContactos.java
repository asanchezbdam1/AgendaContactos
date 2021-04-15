package ut7.agenda.modelo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
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

	public void añadirContacto() {
		
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

	public void personalesPorRelacion() {
		
	}

	public List<Personal> personalesOrdenadosPorFechaNacimiento(char letra) {

		return null;

	}

}
