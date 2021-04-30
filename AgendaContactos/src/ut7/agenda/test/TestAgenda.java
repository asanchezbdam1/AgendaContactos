package ut7.agenda.test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import ut7.agenda.io.AgendaIO;
import ut7.agenda.modelo.AgendaContactos;
import ut7.agenda.modelo.Contacto;
import ut7.agenda.modelo.Personal;

/**
 * 
 * @author Ander Gaona y Asier Sánchez
 *
 */
public class TestAgenda {

	public static void main(String[] args) {
		AgendaContactos agenda = new AgendaContactos();
		System.out.println(AgendaIO.importar(agenda, "agenda.csv") + " lineas erroneas");
		System.out.println(agenda);
		separador();

		buscarContactos(agenda, "acos");
		separador();

		buscarContactos(agenda, "don");
		separador();

		felicitar(agenda);
		separador();

		personalesOrdenadosPorFecha(agenda, 'm');
		separador();
		personalesOrdenadosPorFecha(agenda, 'e');
		separador();
		personalesOrdenadosPorFecha(agenda, 'w');
		separador();
		exportarPersonales(agenda);
		System.out.println("Exportados personales agrupados por relación");
		separador();

	}

	private static void buscarContactos(AgendaContactos agenda, String texto) {
		List<Contacto> resultado = agenda.buscarContactos(texto);
		System.out.println("Contactos que contienen \"" + texto + "\"");
		if (resultado.isEmpty()) {
			System.out.println("No hay contactos coincidentes");
		} else {
			resultado.forEach(contacto -> System.out.println(contacto));
		}

	}

	private static void felicitar(AgendaContactos agenda) {
		System.out.println("Fecha actual: " + LocalDate.now());
		List<Personal> resultado = agenda.felicitar();
		if (resultado.isEmpty()) {
			System.out.println("Hoy no cumple nadie");
		} else {
			System.out.println("Hoy hay que felicitar a ");
			resultado.forEach(contacto -> System.out.println(contacto));
		}

	}

	private static void personalesOrdenadosPorFecha(AgendaContactos agenda, char letra) {
		System.out.println("Personales en letra " + letra + " ordenados de < a > fecha de nacimiento");
		List<Personal> personales = agenda.personalesEnLetra(letra);
		if (personales == null) {
			System.out.println(letra + " no está en la agenda");
		} else {
			agenda.personalesOrdenadosPorFechaNacimiento(letra).forEach(contacto -> System.out.println(contacto));
		}

	}

	private static void separador() {
		System.out.println("------------------------------------------------------------");
	}

	private static void exportarPersonales(AgendaContactos agenda) {
		try {
			AgendaIO.exportarPersonales(agenda, "personales-relacion.txt");
		} catch (IOException e) {
			System.out.println("Error al exportar personales");
		}

	}

}
