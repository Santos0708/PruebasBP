package ec.fin.bp.test.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructors
@NoArgsConstructor
public class ClienteReqDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "es obligatorio")
	private String contrasenia;

	private Boolean estado;

	@NotNull(message = "es obligatorio")
	private String nombre;

	@NotNull(message = "es obligatorio")
	private String genero;

	@NotNull(message = "es obligatorio")
	private Integer edad;

	@NotNull(message = "es obligatorio")
	private String identificacion;

	@NotNull(message = "es obligatorio")
	private String direccion;

	@NotNull(message = "es obligatorio")
	private String telefono;

}
