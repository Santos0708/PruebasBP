package ec.fin.bp.test.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteResDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String fecha;
	private String cliente;
	private String numeroCuenta;
	private String tipoCuenta;
	private BigDecimal saldoInicial;
	private Boolean estado;
	private BigDecimal movimiento;
	private BigDecimal saldoDisponible;

}
