package ec.fin.bp.test.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuentaResDTO implements  Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer idCuenta;

	private String numeroCuenta;

	private String tipo;

	private BigDecimal saldoInicial;

	private Boolean estado;

}
