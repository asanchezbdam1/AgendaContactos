package ut7.agenda.modelo;
/**
 * 
 * @author Ander Gaona y Asier Sánchez
 *
 */
public class Profesional extends Contacto {
	private String nombreEmpresa;

	/**
	 * Constructor de la clase Profesional
	 */
	public Profesional(String nombre, String apellido, String telefono, String email, String nombreEmpresa) {
		super(nombre, apellido, telefono, email);
		String[] empresa = nombreEmpresa.split(" ");
		this.nombreEmpresa = "";
		for (String palabra : empresa) {
			this.nombreEmpresa += Character.toUpperCase(palabra.charAt(0)) + palabra.substring(1) + " ";
		}
		setFirmaEmail();
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}
	
	/**
	 * Establece la firma del contacto a una al azar de las contenidas en el array
	 */
	public void setFirmaEmail() {
		String[] firmas = { "Atentamente", "Saludos", "Saludos cordiales", "Mis mejores deseos" };
		super.setFirmaEmail(firmas[(int) (Math.random() * 4)]);
	}

	/**
	 * 
	 * @return representación textual del contacto profesional
	 */
	@Override
	public String toString() {
		return super.toString() + "\nEmpresa: " + nombreEmpresa;
	}
}
