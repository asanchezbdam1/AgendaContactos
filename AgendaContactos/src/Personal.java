import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Personal extends Contacto {
	private LocalDate fechaNacimiento;
	private Relacion relacion;

	public Personal(String nombre, String apellido, String telefono, String email, String fechaNacimiento,
			Relacion relacion) {
		super(nombre, apellido, telefono, email);
		this.fechaNacimiento = LocalDate.parse(fechaNacimiento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		this.relacion = relacion;
	}

	public void setFirmaEmail() {
		super.setFirmaEmail("Un abrazo!!");
	}

	public boolean esCumpleaños() {
		return fechaNacimiento.isEqual(LocalDate.now());
	}

}
