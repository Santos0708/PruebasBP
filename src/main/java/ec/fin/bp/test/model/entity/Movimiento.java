package ec.fin.bp.test.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ec.fin.bp.test.util.Constantes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movimiento", schema = Constantes.ESQUEMA_BDD)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movimiento implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "MOVIMIENTO_ID_GENERATOR", sequenceName = "movimiento_id_movimiento_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOVIMIENTO_ID_GENERATOR")
	
	@Column(name = "id_movimiento")
	private Integer idMovimiento;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha")
	private Date fechaMovimiento;

	@Column(name = "tipo")
	private String tipoMovimiento;

	@Column(name = "valor")
	private BigDecimal valor;

	@Column(name = "saldo")
	private BigDecimal saldo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cuenta", referencedColumnName = "id_cuenta", nullable = false)
	private Cuenta cuenta;

}
