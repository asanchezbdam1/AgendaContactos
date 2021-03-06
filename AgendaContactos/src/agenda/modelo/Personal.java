package agenda.modelo;

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

	/**
	 * Constructor de la clase Personal
	 */
	public Personal(String nombre, String apellido, String telefono, String email, String fechaNacimiento,
			Relacion relacion) {
		super(nombre, apellido, telefono, email);
		this.fechaNacimiento = LocalDate.parse(fechaNacimiento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		this.relacion = relacion;
	}
	
	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Relacion getRelacion() {
		return relacion;
	}

	public void setRelacion(Relacion relacion) {
		this.relacion = relacion;
	}

	/**
	 * Establece la firma como "Un abrazo!!"
	 */
	public String getFirmaEmail() {
		return "Un abrazo!!";
	}

	/**
	 * 
	 * @return verdadero si el día actual es el cumpleaños del contacto
	 */
	public boolean esCumpleaños() {
		return fechaNacimiento.getMonth() == LocalDate.now().getMonth()
				&& fechaNacimiento.getDayOfMonth() == LocalDate.now().getDayOfMonth();
	}

	/**
	 * 
	 * @return representación textual del contacto personal
	 */
	@Override
	public String toString() {
		return super.toString() + "Fecha nacimiento: "
				+ fechaNacimiento.format(DateTimeFormatter.ofPattern("dd LLL. yyyy")) + "\nRelación: " + relacion + "\n";
	}

}
