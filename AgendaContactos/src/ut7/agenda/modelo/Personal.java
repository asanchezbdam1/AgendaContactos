package ut7.agenda.modelo;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * 
 * @author Ander Gaona y Asier Sánchez
 *
 */
public class Personal extends Contacto {
	private LocalDate fechaNacimiento;
	private Relacion relacion;

	public Personal(String nombre, String apellido, String telefono, String email, String fechaNacimiento,
			Relacion relacion) {
		super(nombre, apellido, telefono, email);
		this.fechaNacimiento = LocalDate.parse(fechaNacimiento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		this.relacion = relacion;
		setFirmaEmail();
	}

	public void setFirmaEmail() {
		super.setFirmaEmail("Un abrazo!!");
	}

	public boolean esCumpleaños() {
		return fechaNacimiento.isEqual(LocalDate.now());
	}
	
	@Override
	public String toString() {
		return super.toString() + "\nFecha nacimiento: " + fechaNacimiento.format(DateTimeFormatter.ofPattern("dd LLL. yyyy")) + "\nRelación: " + relacion;
	}

}
