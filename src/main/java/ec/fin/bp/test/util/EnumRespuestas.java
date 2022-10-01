package ec.fin.bp.test.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EnumRespuestas {

	// 2XX
	// Respuestas satisfactorias (200–299)
	AUTH_CORRECTA("220", "Autenticación correcta."), 
	CLIENTE_CREADO("221", "Cliente creado correctamente."),
	CLIENTE_ELIMINADO("222", "Cliente eliminado correctamente."),
	CLIENTE_ENCONTRADO("223", "Cliente encontrado correctamente."),
	CLIENTE_ACTUALIZADO("224", "Cliente actualizado correctamente."),
	CUENTA_CREADO("221", "Cuenta creado correctamente."), 
	CUENTA_ELIMINADO("222", "Cuenta eliminado correctamente."),
	CUENTA_ENCONTRADO("223", "Cuenta encontrado correctamente."),
	CUENTA_ACTUALIZADO("224", "Cuenta actualizado correctamente."),
	MOVIMIENTO_CREADO("221", "Movimiento creado correctamente."),
	MOVIMIENTO_ELIMINADO("222", "Movimiento eliminado correctamente."),
	MOVIMIENTO_ENCONTRADO("223", "Movimiento encontrado correctamente."),
	MOVIMIENTO_ACTUALIZADO("224", "Movimiento actualizado correctamente."),
	MOVIMIENTOS_CLIENTE("224", "Consulta de movimientos del cliente."),
	// 4XX
	// Errores de los clientes (400–499)
	CLIENTE_NO_ENCONTRADO("420", "Cliente no encontrado"), 
	CUENTA_NO_ENCONTRADO("420", "Cuenta no encontrada"),
	MOVIMIENTO_NO_ENCONTRADO("420", "Movimiento no encontrada"),
	SALDO_NO_DISPONIBLE("420", "Saldo no disponible"), 
	CUPO_DIARIO_EXCEDIDO("420", "Cupo diario Excedido"),
	MOVIMIENTO_NO_SOPORTADO("420", "Movimiento no soportado"), 
	MOVIMIENTO_ERROR("420", "Movimiento no pudo ser generado"), 
	INCORRECTO("421", "Error al crear"),
	GENERAL_ERROR("422", "Error general"), 
	CONSULTA_INCORRECTA("425", "Consulta incorrecta"),
	FECHAS_INCORRECTAS("425", "Parametros de fechas incorrecto"),
	VALIDACIONES_CORREGIR("463","Los datos ingresados no cumplen la validación, Revisar:"),
	// 5XX
	// Errores de los servidores (500–599).
	SERVIDOR_APP("520", "Error servidor aplicaciones"), 
	BDD_CONEXION("521", "Error en conexion a base de datos");

	@Getter
	private final String codigo;
	@Getter
	private final String mensaje;

}
