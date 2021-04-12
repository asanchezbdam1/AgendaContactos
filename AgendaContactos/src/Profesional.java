
public class Profesional extends Contacto {
	private String nombreEmpresa;

	public Profesional(String nombre, String apellido, String telefono, String email, String nombreEmpresa) {
		super(nombre, apellido, telefono, email);
		String[] empresa = nombreEmpresa.split(" ");
		for (String palabra : empresa) {
			Character.toUpperCase(palabra.charAt(0));
			this.nombreEmpresa += palabra + " ";
		}

	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public void setFirmaEmail() {
		String[] firmas = { "Atentamente", "Saludos", "Saludos cordiales", "Mis mejores deseos" };
		super.setFirmaEmail(firmas[(int) (Math.random() * 4)]);
	}
}
