package ut7.agenda.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import ut7.agenda.modelo.AgendaContactos;
import ut7.agenda.modelo.Contacto;
import ut7.agenda.modelo.Personal;
import ut7.agenda.modelo.Profesional;
import ut7.agenda.modelo.Relacion;

/**
 * 
 * @author Ander Gaona y Asier Sánchez
 *
 *         Utilidades para cargar la agenda
 */
public class AgendaIO {

	/**
	 * Importa a una agenda dada un String[] que contiene las lineas con las
	 * personas que se quiere agregar a ella
	 * 
	 * @param La agenda a la que se quiere añadir los contactos
	 */
	public static void importar(AgendaContactos agenda) {
		String[] contactos = obtenerLineasDatos();
		for (String contacto : contactos) {
			agenda.añadirContacto(parsearLinea(contacto));
		}
	}

	/**
	 * Parsea una linea y la convierte en un objeto Contacto
	 * 
	 * @param La linea a parsear
	 * @return Un objeto Contacto
	 */
	private static Contacto parsearLinea(String linea) throws DateTimeParseException, ArrayIndexOutOfBoundsException, EnumConstantNotPresentException {
		String[] datos = linea.split(",");
		for (int i = 0; i < datos.length; i++) {
			datos[i] = datos[i].trim();
		}
		datos[1] = datos[1].toUpperCase();
		datos[2] = datos[2].toUpperCase();
		Contacto c = null;
		if (datos[0].equals("1")) {
			c = new Profesional(datos[1], datos[2], datos[3], datos[4], datos[5]);
		} else if (datos[0].equals("2")) {
			c = new Personal(datos[1], datos[2], datos[3], datos[4], datos[5],
					Relacion.valueOf(datos[6].toUpperCase()));
		}
		return c;
	}
	
	public static void exportarPersonales(AgendaContactos agenda, String fichero) throws IOException {
		PrintWriter salida = new PrintWriter(new BufferedWriter(new FileWriter(new File(fichero))));
		Map<Relacion, List<String>> personales = agenda.personalesPorRelacion();
		for (Map.Entry<Relacion, List<String>> entrada : personales.entrySet()) {
			salida.println(entrada.getKey());
			salida.println("\t" + entrada.getValue().toString());
		}
		salida.close();
	}

}
