package ec.fin.bp.test.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import ec.fin.bp.test.model.dto.MovimientoReqDTO;
import ec.fin.bp.test.model.dto.MovimientoResDTO;
import ec.fin.bp.test.model.dto.ReporteResDTO;

public interface IMovimientoService {

	public BigDecimal saldoDisponible(MovimientoReqDTO movimientoReq);

	public MovimientoResDTO crearMovimiento(MovimientoReqDTO movimientoReq);

	public BigDecimal obtenerTotalDiario(Date fecha, Integer idCuenta, String tipoMovimiento);

	public MovimientoResDTO findById(Integer id);

	public Boolean deleteMovimiento(Integer id);

	public MovimientoResDTO actualizarMovimiento(MovimientoReqDTO movimientoReq, Integer id);

	public List<ReporteResDTO> movimientosCuentasCliente(Integer idCliente, Date fechaInicio, Date fechaFin);
}
