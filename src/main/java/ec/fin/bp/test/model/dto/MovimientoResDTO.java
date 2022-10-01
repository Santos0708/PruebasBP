package ec.fin.bp.test.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoResDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idMovimiento;

	private String fechaMovimiento;

	private String tipoMovimiento;

	private BigDecimal valor;

	private BigDecimal saldo;

	private String detalleMovimeinto;
}
