package ec.fin.bp.test.jpa.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ec.fin.bp.test.model.entity.Cuenta;
import ec.fin.bp.test.model.entity.Movimiento;

@Repository
public interface IMovimiento extends JpaRepository<Movimiento, Integer> {

	/***
	 * Busqueda historial de movimientos por cuenta
	 * 
	 * @param cuenta
	 * @return
	 */
	List<Movimiento> findByCuenta(Cuenta cuenta);

	/**
	 * Consulta obtiene el valor diario
	 * 
	 * @param fecha
	 * @param cuenta
	 * @param tipoMovimiento
	 * 
	 * @return
	 */
	@Query("Select SUM(abs(m.valor)) From Movimiento m Where m.fechaMovimiento  = ?1 and m.cuenta = ?2 and m.tipoMovimiento = ?3")
	BigDecimal obtenerTotalDiario(Date fecha, Cuenta cuenta, String tipoMovimiento);

	/***
	 * Obtener movimientos de las cuentas del cliente
	 * 
	 * @param cliente
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */

	@Query(value = "select DISTINCT  m from Movimiento m, Cuenta cu, Cliente cl where m.cuenta.idCuenta = cu.idCuenta "
			+ "and cu.cliente.id = ?1  and m.fechaMovimiento >= ?2  and m.fechaMovimiento <= ?3 Order by m.idMovimiento ASC")
	List<Movimiento> movimientosCuentasCliente(Integer idCliente, Date fechaInicio, Date fechaFin);

	@Query(value = "select max(id_movimiento) from movimiento where  id_cuenta = ?1", nativeQuery = true)
	Integer obtieneMaximoMovimiento(Integer idCuenta);

}
