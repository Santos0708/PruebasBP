
package ec.fin.bp.test.exceptions;

public class ExceptionEntidad extends Exception {

	private static final long serialVersionUID = 1L;

	public ExceptionEntidad() {
		super();
	}

	public ExceptionEntidad(String mensaje) {
		super(mensaje);
	}

	public ExceptionEntidad(String mensaje, Throwable cause) {
		super(mensaje, cause);
	}

}
