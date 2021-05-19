package agenda.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import agenda.modelo.AgendaContactos;
import agenda.modelo.Contacto;
import agenda.modelo.Personal;
import agenda.modelo.Profesional;
import agenda.modelo.Relacion;

/**
 * 
 * @author Ander Gaona y Asier Sánchez
 *
 *         Utilidades para cargar la agenda
 */
public class AgendaIO {

	/**
	 * Importa a una agenda dado el nombre de un fichero
	 * 
	 * @param La agenda a la que se quiere añadir los contactos
	 * @param Nombre del fichero del que se extraen los datos
	 * @return Número de líneas erroneas en la importación
	 */
	public static int importar(AgendaContactos agenda, String nombre) {
		File f = new File(nombre);
		int errores = 0;
		BufferedReader entrada = null;
		try {
			InputStream input = AgendaIO.class.getClassLoader()
					 .getResourceAsStream(nombre);
			entrada = new BufferedReader(new InputStreamReader(input));
			String linea = entrada.readLine();
			while (linea != null) {
				try {
					agenda.añadirContacto(parsearLinea(linea));
				} catch (DateTimeParseException e) {
					System.out.println("Error al parsear fecha " + e.getMessage());
					errores++;
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Error al leer la linea " + e.getMessage());
					errores++;
				} catch (EnumConstantNotPresentException e) {
					System.out.println("Error al encontrar la relacion " + e.getMessage());
					errores++;
				} catch (IllegalArgumentException e) {
					System.out.println("Dato errorneo " + e.getMessage());
					errores++;
				}
				linea = entrada.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Archivo no encontrado " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error en lectura " + e.getMessage());
		} finally {
			try {
				entrada.close();
			} catch (NullPointerException | IOException e) {
				System.out.println("Error al crear el lector " + e.getMessage());
			}
		}

		return errores;
	}

	/**
	 * Parsea una linea y la convierte en un objeto Contacto
	 * 
	 * @param La linea a parsear
	 * @return Un objeto Contacto
	 */
	private static Contacto parsearLinea(String linea)
			throws DateTimeParseException, ArrayIndexOutOfBoundsException, EnumConstantNotPresentException {
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

	/**
	 * Recibe una agenda y nombre de un fichero y
	 * exporta los contactos personales ordenados
	 * por relación
	 * @param Agenda con los contactos a exportar
	 * @param Fichero al que se exportan los contactos
	 * @throws IOException
	 */
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
