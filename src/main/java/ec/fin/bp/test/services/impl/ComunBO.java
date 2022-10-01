package ec.fin.bp.test.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import ec.fin.bp.test.model.dto.InfoRespuesta;
import ec.fin.bp.test.util.EnumRespuestas;

@Service
public class ComunBO {

	public InfoRespuesta llenarInfo(EnumRespuestas enumRespuestas) {
		InfoRespuesta infoRespuesta = new InfoRespuesta();
		infoRespuesta.setCodigo(enumRespuestas.getCodigo());
		infoRespuesta.setMensaje(enumRespuestas.getMensaje());
		return infoRespuesta;
	}

	public InfoRespuesta llenarInfo(String codigo, String mensaje) {
		InfoRespuesta infoRespuesta = new InfoRespuesta();
		infoRespuesta.setCodigo(Optional.ofNullable(codigo).orElse(""));
		infoRespuesta.setMensaje(Optional.ofNullable(mensaje).orElse(""));
		return infoRespuesta;
	}

	public InfoRespuesta llenarInfo(BindingResult result) {
		InfoRespuesta infoRespuesta = new InfoRespuesta();
		StringBuilder sb = new StringBuilder();
		sb.append(EnumRespuestas.VALIDACIONES_CORREGIR.getMensaje());
		sb.append(" ");

		List<String> errores = result.getFieldErrors().stream()
				.map(error -> "El campo '" + error.getField() + "' " + error.getDefaultMessage())
				.collect(Collectors.toList());
		sb.append(errores);

		infoRespuesta.setCodigo(EnumRespuestas.INCORRECTO.getCodigo());
		infoRespuesta.setMensaje(sb.toString());
		return infoRespuesta;
	}
}
