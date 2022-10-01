package ec.fin.bp.test.model.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResDTO implements  Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer clienteId;
	private String nombreCliente;
	private String identificacion;

}
