package ec.fin.bp.test.util;

public enum EnumData {
	ACTIVO("ACT", "ACTIVO"), 
	INACTIVO("INA", "INACTIVO"), 
	AHORROS("AHO", "AHORROS"), 
	CORRIENTE("COR", "CORRIENTE"),
	CREDITO("CRE", "CREDITO"), 
	DEBITO("DEB", "DEBITO");

	private String codigo;

	private String detalle;

	private EnumData(String codigo, String detalle) {
		this.codigo = codigo;
		this.detalle = detalle;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getDetalle() {
		return detalle;
	}
}
