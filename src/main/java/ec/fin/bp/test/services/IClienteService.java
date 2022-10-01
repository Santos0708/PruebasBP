                                                           package ec.fin.bp.test.services;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import ec.fin.bp.test.model.dto.ClienteReqDTO;
import ec.fin.bp.test.model.dto.ClienteResDTO;
import ec.fin.bp.test.model.entity.Cliente;

public interface IClienteService {

	public ClienteResDTO crearCliente(ClienteReqDTO cliente) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException;

	public List<Cliente> findAll();

	public ClienteResDTO findById(Integer id);

	public Boolean deleteCliente(Integer id);
	
	public ClienteResDTO actualizarCliente(ClienteReqDTO clienteReq,Integer id) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException;

}
