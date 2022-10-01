package ec.fin.bp.test.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ec.fin.bp.test.util.Constantes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cuenta", schema = Constantes.ESQUEMA_BDD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "CUENTA_ID_GENERATOR", sequenceName = "cuenta_id_cuenta_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUENTA_ID_GENERATOR")
	@Column(name = "id_cuenta")
	private Integer idCuenta;

	@Column(name = "numero_cuenta")
	private String numeroCuenta;

	@Column(name = "tipo")
	private String tipo;

	@Column(name = "saldo_inicial")
	private BigDecimal saldoInicial;

	@Column(name = "estado")
	private Boolean estado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente", nullable = false)
	private Cliente cliente;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cuenta")
	private List<Movimiento> movimientoCuenta;
}
