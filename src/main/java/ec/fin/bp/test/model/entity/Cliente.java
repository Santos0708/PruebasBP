package ec.fin.bp.test.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ec.fin.bp.test.util.Constantes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Table(name = "cliente", schema = Constantes.ESQUEMA_BDD)
@PrimaryKeyJoinColumn(name = "id_cliente")
public class Cliente extends Persona {

	private static final long serialVersionUID = 1L;

//	@Id
//	@SequenceGenerator(name = "CLIENTE_ID_GENERATOR", sequenceName = "cliente_id_cliente_seq", allocationSize = 1)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLIENTE_ID_GENERATOR")
//	@Column(name = "id_cliente")
//	private Integer idCliente;

	private String contrasenia;

	private Boolean estado;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_persona")
	private Persona persona;

}
