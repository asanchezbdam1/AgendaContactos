public abstract class Contacto implements Comparable <Contacto> {
	private String nombre;
	private String apellidos;
	private String telefono;
	private String email;
	private String firmaEmail;

	public Contacto(String nombre, String apellidos, String telefono,
			String email) {
		this.nombre = nombre.toUpperCase();
		this.apellidos = apellidos.toUpperCase();
		this.telefono = telefono;
		this.email = email.toLowerCase();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		return email.hashCode();

	}
	
	public char getPrimeraLetra() {
		return apellidos.charAt(0);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o == this) {
			return true;
		}
		if (this.getClass() != o.getClass()) {
			return false;
		}
		Contacto c = (Contacto) o;
		if (this.apellidos.compareTo(c.getApellidos()) != 0) {
			return false;
		}
		if (this.nombre.compareTo(c.getNombre()) != 0) {
			return false;
		}
		if (this.email.compareTo(c.getEmail()) != 0) {
			return false;
		}
		return true;
	}
	
	@Override
	public int compareTo(Contacto c) {
		int n = this.apellidos.compareTo(c.getApellidos());
		if (n == 0) {
			n = this.nombre.compareTo(c.getNombre());
		}
		return n;
	}

	public String getFirmaEmail() {
		return firmaEmail;
	}

	public void setFirmaEmail(String firmaEmail) {
		this.firmaEmail = firmaEmail;
	}

}
