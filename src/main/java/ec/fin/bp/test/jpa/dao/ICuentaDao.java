package ec.fin.bp.test.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ec.fin.bp.test.exceptions.ExceptionEntidad;
import ec.fin.bp.test.model.entity.Cliente;
import ec.fin.bp.test.model.entity.Cuenta;

@Repository
public interface ICuentaDao extends JpaRepository<Cuenta, Integer> {

	/**
	 * Busqueda de cuentas por cliente
	 * 
	 * @param cliente
	 * @return
	 */
	List<Cuenta> findByCliente(Cliente cliente) throws ExceptionEntidad;

	/***
	 * Busquedad por numero de cuenta
	 * 
	 * @param numeroCuenta
	 * @return
	 */
	Cuenta findByNumeroCuenta(String numeroCuenta) throws ExceptionEntidad;

	@Query("select q " + "from Cuenta q, Cliente c " + "where q.cliente.id = c.id " + "and c.id = ?1")
	Iterable<Cuenta> findByCliente(Integer clienteId);
}
