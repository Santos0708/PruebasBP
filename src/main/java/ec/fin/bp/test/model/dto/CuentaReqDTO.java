package ec.fin.bp.test.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuentaReqDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String numeroCuenta;

	private String tipo;

	private BigDecimal saldoInicial;

	private Integer idCliente;

}
