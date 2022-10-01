package ec.fin.bp.test;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ec.fin.bp.test.jpa.dao.IClienteDao;
import ec.fin.bp.test.model.entity.Cliente;
import ec.fin.bp.test.services.IClienteService;

public class ClienteServiceTest {

	@Mock
	private IClienteDao clienteDao;

	@InjectMocks
	private IClienteService clienteService;

	private List<Cliente> clientes;

	private Cliente cliente;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(getClass());
		cliente = new Cliente();
		cliente.setContrasenia("1234");
		cliente.setNombre("Jose Lema");
		cliente.setDireccion("Otavalo sn y principal");
		cliente.setTelefono("098254785");
		cliente.setEstado(true);
		cliente.setGenero("MASCULINO");
		cliente.setEdad(38);

	}

//	@Test
//	void findAll() {
//		when(clienteService.findAll()).thenReturn(Arrays.asList(cliente));
//		assertNotNull(clienteService.findAll());
//	}
}
