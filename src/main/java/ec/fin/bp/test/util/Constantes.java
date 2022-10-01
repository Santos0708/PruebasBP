package ec.fin.bp.test.util;

import java.math.BigDecimal;

public class Constantes {

	public static final String ESQUEMA_BDD = "public";
	public static final BigDecimal MONTO_DIARIO = new BigDecimal(1000);

	public static final class Mensajes {
		public static final String INFO_RESPUESTA = "infoRespuesta";
		public static final String INFO_TRANSACCION = "transaccion";
		public static final String TEXTO_MENSAJE = "mensaje";
		public static final String TEXTO_ERROR = "error";
		public static final String ERROR_CONSULTA_BDD = "Error al realizar la consulta en la BDD";
		public static final String NO_EXISTE_RESULTADOS_BDD = "No se encontraron resultados en la BDD.";
		public static final String VALIDACIONES_CORREGIR = "Existen validaciones en la información que corregir.";
		public static final String ERROR_CREAR_REGISTRO = "Error al crear el registro en la BDD";
		public static final String CREA_REGISTRO_EXITOSO = "Se creo el registro en la BDD";
		public static final String ERROR_ACTUALIZAR_REGISTRO = "Error al actualizar el registro en la BDD";
		public static final String ACTUALIZA_REGISTRO_EXITOSO = "Se actualizo el registro en la BDD";
		public static final String CONSULTA_EXITOSA = "Consulta Exitosa a BDD";
		public static final String ERROR_CONSULTA = "Error en la consulta a BDD";
	}
}
