package ec.fin.bp.test.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ec.fin.bp.test.exceptions.ExceptionEntidad;
import ec.fin.bp.test.jpa.dao.IClienteDao;
import ec.fin.bp.test.jpa.dao.ICuentaDao;
import ec.fin.bp.test.model.dto.CuentaReqDTO;
import ec.fin.bp.test.model.dto.CuentaResDTO;
import ec.fin.bp.test.model.entity.Cliente;
import ec.fin.bp.test.model.entity.Cuenta;
import ec.fin.bp.test.services.ICuentaService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class CuentaServiceImpl implements ICuentaService {

	@Autowired
	private ICuentaDao cuentaDao;

	@Autowired
	private IClienteDao clienteDao;

	@Override
	public CuentaResDTO crearCuenta(CuentaReqDTO cuentaReq) {
		CuentaResDTO respuesta = new CuentaResDTO();
		Cuenta cuenta = new Cuenta();
		cuenta = cuentaReqToCuenta(cuentaReq);
		Cliente cliente = clienteDao.findById(cuentaReq.getIdCliente()).orElse(null);

		if (Objects.nonNull(cliente)) {
			cuenta.setCliente(cliente);
			respuesta = cuentaToRes(cuentaDao.save(cuenta));
		} else
			respuesta = null;

		return respuesta;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cuenta> findAll() {
		return (List<Cuenta>) cuentaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public CuentaResDTO findById(Integer id) {
		return cuentaToRes(cuentaDao.findById(id).orElse(null));
	}

	@Override
	@Transactional
	@Modifying(clearAutomatically = true)
	public Boolean deleteCuenta(Integer id) {
		Cuenta cuenta = cuentaDao.findById(id).orElse(null);
		if (Objects.nonNull(cuenta)) {
			cuentaDao.delete(cuenta);
			return true;
		} else {
			System.err.println("no encontrado");
			return false;
		}
	}

	@Override
	public CuentaResDTO actualizarCuenta(CuentaReqDTO cuentaReq, Integer id) {
		CuentaResDTO respuesta = new CuentaResDTO();
		Cuenta cuenta = cuentaDao.findById(id).orElse(null);
		if (Objects.nonNull(cuenta)) {
			Cuenta tmp = cuentaReqToCuenta(cuentaReq);
			tmp.setIdCuenta(cuenta.getIdCuenta());
			tmp.setCliente(cuenta.getCliente());
			respuesta = cuentaToRes(cuentaDao.save(tmp));
		} else
			respuesta = null;
		return respuesta;
	}

	@Override
	public List<Cuenta> buscarCuentasPorCliente(Integer clienteId) throws ExceptionEntidad {

		List<Cuenta> cuentasCliente = new ArrayList<>();
		Cliente cliente = clienteDao.findById(clienteId).orElse(null);
		if (Objects.nonNull(cliente)) {

			try {
				cuentasCliente = cuentaDao.findByCliente(cliente);
			} catch (ExceptionEntidad e) {
				throw new ExceptionEntidad ("Excepcion cuentas del cliente");
			}
			if (cuentasCliente.size() > 0) {
				return cuentasCliente;
			} else
				return null;
		} else
			return null;

	}

	/***
	 * Parse entidad a DTO Respuesta
	 * 
	 * @param cliente
	 * @return
	 */
	public CuentaResDTO cuentaToRes(Cuenta cuenta) {
		CuentaResDTO cuentaRes = new CuentaResDTO();
		cuentaRes.setIdCuenta(cuenta.getIdCuenta());
		cuentaRes.setEstado(cuenta.getEstado());
		cuentaRes.setNumeroCuenta(cuenta.getNumeroCuenta());
		cuentaRes.setSaldoInicial(cuenta.getSaldoInicial());
		cuentaRes.setTipo(cuenta.getTipo());
		return cuentaRes;
	}

	public Cuenta cuentaReqToCuenta(CuentaReqDTO cuentaReq) {

		Cuenta cuenta = new Cuenta();
		cuenta.setEstado(true);
		cuenta.setNumeroCuenta(cuentaReq.getNumeroCuenta());
		cuenta.setSaldoInicial(cuentaReq.getSaldoInicial());
		cuenta.setTipo(cuentaReq.getTipo());

		return cuenta;
	}
}
