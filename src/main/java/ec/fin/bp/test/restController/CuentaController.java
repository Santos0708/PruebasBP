package ec.fin.bp.test.restController;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ec.fin.bp.test.model.dto.CuentaReqDTO;
import ec.fin.bp.test.model.dto.CuentaResDTO;
import ec.fin.bp.test.model.dto.InfoRespuesta;
import ec.fin.bp.test.services.ICuentaService;
import ec.fin.bp.test.services.impl.ComunBO;
import ec.fin.bp.test.util.Constantes;
import ec.fin.bp.test.util.EnumRespuestas;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

	private Map<String, Object> response = null;

	private InfoRespuesta infoRespuestaVO;

	private CuentaResDTO cuentaResponse;

	@Autowired
	private ICuentaService cuentaService;

	@Autowired
	private ComunBO comunBO;

	@PostMapping
	@Operation(summary = "Permite crear un registro", responses = {
			@ApiResponse(responseCode = "202", description = "Registro creado") })
	public ResponseEntity<?> crearCuenta(@Valid @RequestBody CuentaReqDTO cuentaReq, BindingResult result) {
		cuentaResponse = new CuentaResDTO();
		response = new HashMap<>();
		infoRespuestaVO = new InfoRespuesta();
		if (result.hasErrors()) {
			infoRespuestaVO = comunBO.llenarInfo(result);
			response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		try {
			cuentaResponse = cuentaService.crearCuenta(cuentaReq);

			if (cuentaResponse == null) {
				infoRespuestaVO.setCodigo(EnumRespuestas.INCORRECTO.getCodigo());
				infoRespuestaVO.setMensaje(EnumRespuestas.INCORRECTO.getMensaje());
				response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
			} else {
				infoRespuestaVO = comunBO.llenarInfo(EnumRespuestas.CUENTA_CREADO);
				response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
				response.put(Constantes.Mensajes.INFO_TRANSACCION, cuentaResponse);
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
	public ResponseEntity<?> buscarCuenta(@Valid @PathVariable Integer id) {

		cuentaResponse = new CuentaResDTO();
		response = new HashMap<>();
		infoRespuestaVO = new InfoRespuesta();

		try {
			cuentaResponse = cuentaService.findById(id);
			if (cuentaResponse == null) {
				infoRespuestaVO.setCodigo(EnumRespuestas.CUENTA_NO_ENCONTRADO.getCodigo());
				infoRespuestaVO.setMensaje(EnumRespuestas.CUENTA_NO_ENCONTRADO.getMensaje());
				response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
			} else {
				infoRespuestaVO = comunBO.llenarInfo(EnumRespuestas.CUENTA_ENCONTRADO);
				response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
				response.put(Constantes.Mensajes.INFO_TRANSACCION, cuentaResponse);
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
	public ResponseEntity<?> updateCliente(@Valid @RequestBody CuentaReqDTO cuentaReq, BindingResult result,
			@PathVariable Integer id) {

		cuentaResponse = new CuentaResDTO();
		response = new HashMap<>();
		infoRespuestaVO = new InfoRespuesta();
		if (result.hasErrors()) {
			infoRespuestaVO = comunBO.llenarInfo(result);
			response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		try {
			cuentaResponse = cuentaService.actualizarCuenta(cuentaReq, id);
			if (cuentaResponse == null) {
				infoRespuestaVO.setCodigo(EnumRespuestas.CUENTA_NO_ENCONTRADO.getCodigo());
				infoRespuestaVO.setMensaje(EnumRespuestas.CUENTA_NO_ENCONTRADO.getMensaje());
				response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
			} else {
				infoRespuestaVO = comunBO.llenarInfo(EnumRespuestas.CUENTA_ACTUALIZADO);
				response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
				response.put(Constantes.Mensajes.INFO_TRANSACCION, cuentaResponse);
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

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> deleteCliente(@Valid @PathVariable("id") Integer id) {
		response = new HashMap<>();
		infoRespuestaVO = new InfoRespuesta();
		try {

			if (cuentaService.deleteCuenta(id)) {
				infoRespuestaVO = comunBO.llenarInfo(EnumRespuestas.CUENTA_ELIMINADO);
				response.put(Constantes.Mensajes.INFO_RESPUESTA, infoRespuestaVO);
//				response.put(Constantes.Mensajes.INFO_TRANSACCION, cuentaResponse);
			} else {
				infoRespuestaVO = comunBO.llenarInfo(EnumRespuestas.CUENTA_NO_ENCONTRADO);
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

}
