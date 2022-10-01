package ec.fin.bp.test.services.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ec.fin.bp.test.jpa.dao.ICuentaDao;
import ec.fin.bp.test.jpa.dao.IMovimiento;
import ec.fin.bp.test.model.dto.MovimientoReqDTO;
import ec.fin.bp.test.model.dto.MovimientoResDTO;
import ec.fin.bp.test.model.dto.ReporteResDTO;
import ec.fin.bp.test.model.entity.Cuenta;
import ec.fin.bp.test.model.entity.Movimiento;
import ec.fin.bp.test.services.IMovimientoService;
import ec.fin.bp.test.util.EnumData;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class MovimientoServiceImpl implements IMovimientoService {

	@Autowired
	private IMovimiento movimientoDao;

	@Autowired
	private ICuentaDao cuentaDao;

	@Override
	public BigDecimal obtenerTotalDiario(Date fecha, Integer idCuenta, String tipoMovimiento) {
		Cuenta cuenta = cuentaDao.findById(idCuenta).orElse(null);
		return movimientoDao.obtenerTotalDiario(fecha, cuenta, tipoMovimiento);

	}

	@Override
	@Transactional(readOnly = true)
	public MovimientoResDTO findById(Integer id) {
		return movimientoToRes(movimientoDao.findById(id).orElse(null));
	}

	@Override
	public BigDecimal saldoDisponible(MovimientoReqDTO movimientoReq) {
		// Buscar cuenta
		Cuenta cuenta = cuentaDao.findById(movimientoReq.getIdCuenta()).orElse(null);
		// 1. Busco ultimo movimiento de la cuenta
		Integer movimientoUltimo = movimientoDao.obtieneMaximoMovimiento(movimientoReq.getIdCuenta());
		Movimiento movUltimo = null;
		if (Objects.nonNull(movimientoUltimo)) {
			movUltimo = movimientoDao.findById(movimientoUltimo).orElse(null);
		}
		// Se obtiene el ultimo valor de saldo
		BigDecimal saldoActual = movUltimo == null ? cuenta.getSaldoInicial() : movUltimo.getSaldo();
		return saldoActual;
	}

	@Override
	public MovimientoResDTO crearMovimiento(MovimientoReqDTO movimientoReq) {
		MovimientoResDTO respuesta = new MovimientoResDTO();
		Movimiento movimiento = new Movimiento();
		// Buscar cuenta
		Cuenta cuenta = cuentaDao.findById(movimientoReq.getIdCuenta()).orElse(null);
		// 1. Busco ultimo movimiento de la cuenta
		Integer movimientoUltimo = movimientoDao.obtieneMaximoMovimiento(movimientoReq.getIdCuenta());
		Movimiento movUltimo = null;
		if (Objects.nonNull(movimientoUltimo)) {
			movUltimo = movimientoDao.findById(movimientoUltimo).orElse(null);
		}
		// Se obtiene el ultimo valor de saldo
		BigDecimal saldoActual = movUltimo == null ? cuenta.getSaldoInicial() : movUltimo.getSaldo();
		// Calculo el saldo actual
		BigDecimal saldoDisponible;
		saldoDisponible = saldoActual.add(movimientoReq.getValor());
		movimiento = movimientoReqTOMovimiento(movimientoReq);
		movimiento.setCuenta(cuenta);
		movimiento.setSaldo(saldoDisponible);

		if (Objects.nonNull(cuenta)) {
			respuesta = movimientoToRes(movimientoDao.save(movimiento));
		} else
			respuesta = null;

		return respuesta;
	}

	@Override
	@Transactional
	@Modifying(clearAutomatically = true)
	public Boolean deleteMovimiento(Integer id) {
		Movimiento movimiento = movimientoDao.findById(id).orElse(null);
		if (Objects.nonNull(movimiento)) {
			movimientoDao.delete(movimiento);
			return true;
		} else {
			return false;
		}
	}

	/***
	 * Parse entidad a DTO Respuesta
	 * 
	 * @param cliente
	 * @return
	 */
	public MovimientoResDTO movimientoToRes(Movimiento movimiento) {
		MovimientoResDTO movRes = new MovimientoResDTO();
		if (Objects.nonNull(movimiento)) {
			movRes.setIdMovimiento(movimiento.getIdMovimiento());
			movRes.setFechaMovimiento(fechaFormato(movimiento.getFechaMovimiento()));
			movRes.setSaldo(movimiento.getSaldo());
			movRes.setTipoMovimiento(movimiento.getTipoMovimiento());
			movRes.setValor(movimiento.getValor());
			movRes.setDetalleMovimeinto(movimiento.getTipoMovimiento().equalsIgnoreCase(EnumData.DEBITO.getDetalle())
					? "Retiro de " + movimiento.getValor()
					: "Deposito de " + movimiento.getValor());
		} else
			movRes = null;
		return movRes;
	}

	public Movimiento movimientoReqTOMovimiento(MovimientoReqDTO movimientoReq) {
		Movimiento movimiento = new Movimiento();
		movimiento.setFechaMovimiento(new Date());
		movimiento.setTipoMovimiento(movimientoReq.getTipoMovimiento());
		movimiento.setValor(movimientoReq.getValor());
		return movimiento;
	}

	private String fechaFormato(Date fecha) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(fecha);
		return date;
	}

	@Override
	@Transactional
	public MovimientoResDTO actualizarMovimiento(MovimientoReqDTO movimientoReq, Integer id) {
		MovimientoResDTO respuesta = new MovimientoResDTO();
		Movimiento movimiento = movimientoDao.findById(id).orElse(null);

		if (Objects.nonNull(movimiento)) {
			Movimiento tmp = movimientoReqTOMovimiento(movimientoReq);
			tmp.setIdMovimiento(movimiento.getIdMovimiento());
			respuesta = movimientoToRes(movimientoDao.save(tmp));
		} else
			respuesta = null;
		return respuesta;
	}

	@Override
	public List<ReporteResDTO> movimientosCuentasCliente(Integer idCliente, Date fechaInicio, Date fechaFin) {
		List<ReporteResDTO> movimientos = new ArrayList<ReporteResDTO>();
		List<Movimiento> mov = new ArrayList<Movimiento>();
		mov = movimientoDao.movimientosCuentasCliente(idCliente, fechaInicio, fechaFin);

		if (mov.size() > 0) {
			for (Movimiento movimiento : mov) {
				movimientos.add(movimientoTOReporte(movimiento));
			}
		} else
			movimientos = null;
		return movimientos;
	}

	public ReporteResDTO movimientoTOReporte(Movimiento movimiento) {
		ReporteResDTO reporteItem = new ReporteResDTO();
		reporteItem.setFecha(fechaFormato(movimiento.getFechaMovimiento()));
		reporteItem.setCliente(movimiento.getCuenta().getCliente().getNombre());
		reporteItem.setNumeroCuenta(movimiento.getCuenta().getNumeroCuenta());
		reporteItem.setTipoCuenta(movimiento.getCuenta().getTipo());
		reporteItem.setSaldoInicial(movimiento.getCuenta().getSaldoInicial());
		reporteItem.setEstado(movimiento.getCuenta().getEstado());
		reporteItem.setMovimiento(movimiento.getValor());
		reporteItem.setSaldoDisponible(movimiento.getSaldo());

		return reporteItem;
	}

}
