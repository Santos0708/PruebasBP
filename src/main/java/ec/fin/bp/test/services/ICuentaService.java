package ec.fin.bp.test.services;

import java.util.List;

import ec.fin.bp.test.exceptions.ExceptionEntidad;
import ec.fin.bp.test.model.dto.CuentaReqDTO;
import ec.fin.bp.test.model.dto.CuentaResDTO;
import ec.fin.bp.test.model.entity.Cuenta;

public interface ICuentaService {

	public CuentaResDTO crearCuenta(CuentaReqDTO cuenta);

	public List<Cuenta> findAll();

	public CuentaResDTO findById(Integer id);

	public Boolean deleteCuenta(Integer id);

	public CuentaResDTO actualizarCuenta(CuentaReqDTO cuentaReq, Integer id);

	public List<Cuenta> buscarCuentasPorCliente(Integer clienteId) throws ExceptionEntidad;
}
