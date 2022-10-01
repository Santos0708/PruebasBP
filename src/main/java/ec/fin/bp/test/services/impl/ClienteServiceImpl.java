package ec.fin.bp.test.services.impl;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ec.fin.bp.test.jpa.dao.IClienteDao;
import ec.fin.bp.test.model.dto.ClienteReqDTO;
import ec.fin.bp.test.model.dto.ClienteResDTO;
import ec.fin.bp.test.model.entity.Cliente;
import ec.fin.bp.test.services.IClienteService;
import ec.fin.bp.test.util.EncriptacionAES;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class ClienteServiceImpl implements IClienteService {

	private static final String LLAVE_AES = "BANCOBP";
	private EncriptacionAES encriptacionAES = new EncriptacionAES();

	@Autowired
	private IClienteDao clienteDao;

	@Override
	public ClienteResDTO crearCliente(ClienteReqDTO clienteReq)
			throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		ClienteResDTO respuesta = new ClienteResDTO();
		Cliente cliente = new Cliente();
		cliente = clienteReqTOCliente(clienteReq);
		if (Objects.nonNull(cliente)) {
			respuesta = clienteToRes(clienteDao.save(cliente));
		}
		return respuesta;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public ClienteResDTO findById(Integer id) {
		return clienteToRes(clienteDao.findById(id).orElse(null));
	}

	@Override
	@Transactional
	@Modifying(clearAutomatically = true)
	public Boolean deleteCliente(Integer id) {
		Cliente cliente = clienteDao.findById(id).orElse(null);
		if (Objects.nonNull(cliente)) {
			clienteDao.delete(cliente);
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public ClienteResDTO actualizarCliente(ClienteReqDTO clienteReq, Integer id)
			throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		ClienteResDTO respuesta = new ClienteResDTO();
		Cliente cliente = clienteDao.findById(id).orElse(null);

		if (Objects.nonNull(cliente)) {
			Cliente tmp = clienteReqTOCliente(clienteReq);
			tmp.setIdPersona(cliente.getIdPersona());
			respuesta = clienteToRes(clienteDao.save(tmp));
		} else
			respuesta = null;
		return respuesta;
	}

	/***
	 * Parse entidad a DTO Respuesta
	 * 
	 * @param cliente
	 * @return
	 */
	public ClienteResDTO clienteToRes(Cliente cliente) {
		ClienteResDTO clienteRes = new ClienteResDTO();
		if (Objects.nonNull(cliente)) {
			clienteRes.setClienteId(cliente.getIdPersona());
			clienteRes.setIdentificacion(cliente.getIdentificacion());
			clienteRes.setNombreCliente(cliente.getNombre());
		} else
			clienteRes = null;
		return clienteRes;
	}

	public Cliente clienteReqTOCliente(ClienteReqDTO clienteReq)
			throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		Cliente cliente = new Cliente();
		cliente.setContrasenia(encriptacionAES.encriptar(clienteReq.getContrasenia(), LLAVE_AES));
		cliente.setEstado(true);
		cliente.setNombre(clienteReq.getNombre());
		cliente.setGenero(clienteReq.getGenero());
		cliente.setEdad(clienteReq.getEdad());
		cliente.setIdentificacion(clienteReq.getIdentificacion());
		cliente.setDireccion(clienteReq.getDireccion());
		cliente.setTelefono(clienteReq.getTelefono());

		return cliente;

	}
}
