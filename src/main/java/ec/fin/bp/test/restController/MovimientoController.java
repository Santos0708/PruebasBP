package ec.fin.bp.test.restController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ec.fin.bp.test.model.dto.InfoRespuesta;
import ec.fin.bp.test.model.dto.MovimientoReqDTO;
import ec.fin.bp.test.model.dto.MovimientoResDTO;
import ec.fin.bp.test.model.dto.ReporteResDTO;
import ec.fin.bp.test.services.IMovimientoService;
import ec.fin.bp.test.services.impl.ComunBO;
import ec.fin.bp.test.util.Constantes;
import ec.fin.bp.test.util.EnumData;
import ec.fin.bp.test.util.EnumRespuestas;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

	private Map<String, Object> response = null;

	private InfoRespuesta infoRespuestaVO;

	private MovimientoResDTO movimientoResponse;

	@Autowired
	private IMovimientoService movimientoService;

	@Autowired
	private ComunBO comunBO;

	@PostMapping
	@Operation(summary = "Permite crear un registro", responses = {
			@ApiResponse(responseCode = "202", description = "Registro creado") })
	public ResponseEntity<?> crearMovimiento(@Valid @RequestBody MovimientoReqDTO movimientoReq, BindingResult result) {
		movimientoResponse = new MovimientoResDTO();
		response = new HashMap<>();
		infoRespuestaVO = new InfoRespuesta();
		BigDecimal saldoDisponible;
		BigDecimal montoDiarioRetiro;
		BigDecimal valorMovimiento = null;

		if (result.hasErrors()) {
			infoRespuestaVO = comunBO.llenarInfo(result);
			response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		try {
			saldoDisponible = movimientoService.saldoDisponible(movimientoReq);
			montoDiarioRetiro = movimientoService.obtenerTotalDiario(new Date(), movimientoReq.getIdCuenta(),
					EnumData.DEBITO.getDetalle());
			if (movimientoReq.getTipoMovimiento().equalsIgnoreCase(EnumData.DEBITO.getDetalle())) {
				if (saldoDisponible.doubleValue() <= 0
						|| saldoDisponible.doubleValue() < movimientoReq.getValor().doubleValue()) {
					infoRespuestaVO.setCodigo(EnumRespuestas.SALDO_NO_DISPONIBLE.getCodigo());
					infoRespuestaVO.setMensaje(EnumRespuestas.SALDO_NO_DISPONIBLE.getMensaje());
					response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
					return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
				} else if (montoDiarioRetiro != null
						&& montoDiarioRetiro.doubleValue() >= Constantes.MONTO_DIARIO.doubleValue()) {
					infoRespuestaVO.setCodigo(EnumRespuestas.CUPO_DIARIO_EXCEDIDO.getCodigo());
					infoRespuestaVO.setMensaje(EnumRespuestas.CUPO_DIARIO_EXCEDIDO.getMensaje());
					response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
					return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
				} else {
					// Crea movimiento
					if (!movimientoReq.getValor().toString().trim().isEmpty()) {
						if (movimientoReq.getValor().doubleValue() > 0) {
							valorMovimiento = movimientoReq.getValor().multiply(new BigDecimal(-1));
							movimientoReq.setValor(valorMovimiento);
						}
					}
					movimientoResponse = movimientoService.crearMovimiento(movimientoReq);
				}
			} else if (movimientoReq.getTipoMovimiento().equalsIgnoreCase(EnumData.CREDITO.getDetalle())) {
				if (!movimientoReq.getValor().toString().trim().isEmpty()) {
					if (movimientoReq.getValor().doubleValue() < 0) {
						valorMovimiento = movimientoReq.getValor().multiply(new BigDecimal(-1));
						movimientoReq.setValor(valorMovimiento);
					}
				}
				// Crea movimiento
				movimientoResponse = movimientoService.crearMovimiento(movimientoReq);
			} else {
				infoRespuestaVO.setCodigo(EnumRespuestas.MOVIMIENTO_NO_SOPORTADO.getCodigo());
				infoRespuestaVO.setMensaje(EnumRespuestas.MOVIMIENTO_NO_SOPORTADO.getMensaje());
				response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
			}

			if (movimientoResponse == null) {
				infoRespuestaVO.setCodigo(EnumRespuestas.MOVIMIENTO_ERROR.getCodigo());
				infoRespuestaVO.setMensaje(EnumRespuestas.MOVIMIENTO_ERROR.getMensaje());
				response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
			} else {
				infoRespuestaVO = comunBO.llenarInfo(EnumRespuestas.MOVIMIENTO_CREADO);
				response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
				response.put(Constantes.Mensajes.INFO_TRANSACCION, movimientoResponse);
			}
		} catch (

		DataAccessException e) {
			infoRespuestaVO.setCodigo(EnumRespuestas.BDD_CONEXION.getCodigo());
			infoRespuestaVO.setMensaje(EnumRespuestas.BDD_CONEXION.getMensaje());
			response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception ex) {
			System.err.println(ex);
			infoRespuestaVO.setCodigo(EnumRespuestas.GENERAL_ERROR.getCodigo());
			infoRespuestaVO.setMensaje(EnumRespuestas.GENERAL_ERROR.getMensaje());
			response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCliente(@Valid @PathVariable("id") Integer id) {
		response = new HashMap<>();
		infoRespuestaVO = new InfoRespuesta();
		try {
			if (movimientoService.deleteMovimiento(id)) {
				infoRespuestaVO = comunBO.llenarInfo(EnumRespuestas.MOVIMIENTO_ELIMINADO);
				response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
			} else {
				infoRespuestaVO = comunBO.llenarInfo(EnumRespuestas.MOVIMIENTO_NO_ENCONTRADO);
				response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
			}

		} catch (DataAccessException e) {
			infoRespuestaVO.setCodigo(EnumRespuestas.BDD_CONEXION.getCodigo());
			infoRespuestaVO.setMensaje(EnumRespuestas.BDD_CONEXION.getMensaje());
			response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception ex) {
			System.err.println(ex);
			infoRespuestaVO.setCodigo(EnumRespuestas.GENERAL_ERROR.getCodigo());
			infoRespuestaVO.setMensaje(EnumRespuestas.GENERAL_ERROR.getMensaje());
			response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscarCliente(@Valid @PathVariable Integer id) {
		movimientoResponse = new MovimientoResDTO();
		response = new HashMap<>();
		infoRespuestaVO = new InfoRespuesta();
		try {
			movimientoResponse = movimientoService.findById(id);
			if (movimientoResponse == null) {
				infoRespuestaVO.setCodigo(EnumRespuestas.MOVIMIENTO_NO_ENCONTRADO.getCodigo());
				infoRespuestaVO.setMensaje(EnumRespuestas.MOVIMIENTO_NO_ENCONTRADO.getMensaje());
				response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
			} else {
				infoRespuestaVO = comunBO.llenarInfo(EnumRespuestas.MOVIMIENTO_ENCONTRADO);
				response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
				response.put(Constantes.Mensajes.INFO_TRANSACCION, movimientoResponse);
			}
		} catch (DataAccessException e) {
			infoRespuestaVO.setCodigo(EnumRespuestas.BDD_CONEXION.getCodigo());
			infoRespuestaVO.setMensaje(EnumRespuestas.BDD_CONEXION.getMensaje());
			response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception ex) {
			System.err.println(ex);
			infoRespuestaVO.setCodigo(EnumRespuestas.GENERAL_ERROR.getCodigo());
			infoRespuestaVO.setMensaje(EnumRespuestas.GENERAL_ERROR.getMensaje());
			response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateCliente(@Valid @RequestBody MovimientoReqDTO movimientoReq, BindingResult result,
			@PathVariable Integer id) {

		movimientoResponse = new MovimientoResDTO();
		response = new HashMap<>();
		infoRespuestaVO = new InfoRespuesta();
		BigDecimal saldoDisponible;
		BigDecimal montoDiarioRetiro;
		BigDecimal valorMovimiento = null;

		if (result.hasErrors()) {
			infoRespuestaVO = comunBO.llenarInfo(result);
			response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		try {
			saldoDisponible = movimientoService.saldoDisponible(movimientoReq);
			montoDiarioRetiro = movimientoService.obtenerTotalDiario(new Date(), movimientoReq.getIdCuenta(),
					EnumData.DEBITO.getDetalle());
			if (movimientoReq.getTipoMovimiento().equalsIgnoreCase(EnumData.DEBITO.getDetalle())) {
				if (saldoDisponible.doubleValue() <= 0
						|| saldoDisponible.doubleValue() < movimientoReq.getValor().doubleValue()) {
					infoRespuestaVO.setCodigo(EnumRespuestas.SALDO_NO_DISPONIBLE.getCodigo());
					infoRespuestaVO.setMensaje(EnumRespuestas.SALDO_NO_DISPONIBLE.getMensaje());
					response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
					return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
				} else if (montoDiarioRetiro != null
						&& montoDiarioRetiro.doubleValue() >= Constantes.MONTO_DIARIO.doubleValue()) {
					infoRespuestaVO.setCodigo(EnumRespuestas.CUPO_DIARIO_EXCEDIDO.getCodigo());
					infoRespuestaVO.setMensaje(EnumRespuestas.CUPO_DIARIO_EXCEDIDO.getMensaje());
					response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
					return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
				} else {
					if (!movimientoReq.getValor().toString().trim().isEmpty()) {
						if (movimientoReq.getValor().doubleValue() > 0) {
							valorMovimiento = movimientoReq.getValor().multiply(new BigDecimal(-1));
							movimientoReq.setValor(valorMovimiento);
						}
					}
					movimientoResponse = movimientoService.actualizarMovimiento(movimientoReq, id);
				}
			} else if (movimientoReq.getTipoMovimiento().equalsIgnoreCase(EnumData.CREDITO.getDetalle())) {
				if (!movimientoReq.getValor().toString().trim().isEmpty()) {
					if (movimientoReq.getValor().doubleValue() < 0) {
						valorMovimiento = movimientoReq.getValor().multiply(new BigDecimal(-1));
						movimientoReq.setValor(valorMovimiento);
					}
				}
				movimientoResponse = movimientoService.actualizarMovimiento(movimientoReq, id);
			} else {
				infoRespuestaVO.setCodigo(EnumRespuestas.MOVIMIENTO_NO_SOPORTADO.getCodigo());
				infoRespuestaVO.setMensaje(EnumRespuestas.MOVIMIENTO_NO_SOPORTADO.getMensaje());
				response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
			}

			if (movimientoResponse == null) {
				infoRespuestaVO.setCodigo(EnumRespuestas.MOVIMIENTO_ERROR.getCodigo());
				infoRespuestaVO.setMensaje(EnumRespuestas.MOVIMIENTO_ERROR.getMensaje());
				response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
			} else {
				infoRespuestaVO = comunBO.llenarInfo(EnumRespuestas.MOVIMIENTO_ACTUALIZADO);
				response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
				response.put(Constantes.Mensajes.INFO_TRANSACCION, movimientoResponse);
			}
		} catch (DataAccessException e) {
			infoRespuestaVO.setCodigo(EnumRespuestas.BDD_CONEXION.getCodigo());
			infoRespuestaVO.setMensaje(EnumRespuestas.BDD_CONEXION.getMensaje());
			response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception ex) {
			System.err.println(ex);
			infoRespuestaVO.setCodigo(EnumRespuestas.GENERAL_ERROR.getCodigo());
			infoRespuestaVO.setMensaje(EnumRespuestas.GENERAL_ERROR.getMensaje());
			response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/reportes")
	ResponseEntity<?> consultaMovimientos(@RequestParam(value = "fecha_desde", required = true) String fechaDesde,
			@RequestParam(value = "fecha_fin", required = true) String fechaFin,
			@RequestParam(value = "id_cliente", required = true) Integer idCliente) {
		List<ReporteResDTO> reporteRes = new ArrayList<>();
		response = new HashMap<>();
		infoRespuestaVO = new InfoRespuesta();

		try {
			Date fechaD = new SimpleDateFormat("yyyy-MM-dd").parse(fechaDesde);
			Date fechaF = new SimpleDateFormat("yyyy-MM-dd").parse(fechaFin);
			reporteRes = movimientoService.movimientosCuentasCliente(idCliente, fechaD, fechaF);
			if (fechaD.after(fechaF)) {
				infoRespuestaVO.setCodigo(EnumRespuestas.FECHAS_INCORRECTAS.getCodigo());
				infoRespuestaVO.setMensaje(EnumRespuestas.FECHAS_INCORRECTAS.getMensaje());
				response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				if (reporteRes == null) {
					infoRespuestaVO.setCodigo(EnumRespuestas.CONSULTA_INCORRECTA.getCodigo());
					infoRespuestaVO.setMensaje(EnumRespuestas.CONSULTA_INCORRECTA.getMensaje());
					response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
				} else {
					infoRespuestaVO = comunBO.llenarInfo(EnumRespuestas.MOVIMIENTOS_CLIENTE);
					response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
					response.put(Constantes.Mensajes.INFO_TRANSACCION, reporteRes);
				}
			}
		} catch (DataAccessException e) {
			infoRespuestaVO.setCodigo(EnumRespuestas.BDD_CONEXION.getCodigo());
			infoRespuestaVO.setMensaje(EnumRespuestas.BDD_CONEXION.getMensaje());
			response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception ex) {
			System.err.println(ex);
			infoRespuestaVO.setCodigo(EnumRespuestas.GENERAL_ERROR.getCodigo());
			infoRespuestaVO.setMensaje(EnumRespuestas.GENERAL_ERROR.getMensaje());
			response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);

	}
}
