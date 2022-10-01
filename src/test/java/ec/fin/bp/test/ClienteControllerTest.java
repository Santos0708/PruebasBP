package ec.fin.bp.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import ec.fin.bp.test.model.dto.ClienteReqDTO;
import ec.fin.bp.test.model.dto.InfoRespuesta;
import ec.fin.bp.test.util.EnumRespuestas;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(ClienteControllerTest.class)
public class ClienteControllerTest {

	@Autowired
	private MockMvc mockMvvc;

	@Autowired
	private JacksonTester<ClienteReqDTO> clienteRequest;

	@Test
	public void testCliente() throws Exception {
		ClienteReqDTO clienteReq = new ClienteReqDTO();
		clienteReq.setContrasenia("1234");
		clienteReq.setEstado(true);
		clienteReq.setNombre("Jose Lema");
		clienteReq.setGenero("MASCULINO");
		clienteReq.setDireccion("AV. AMERICA ");
		clienteReq.setEdad(38);
		clienteReq.setIdentificacion("123456790");
		clienteReq.setTelefono("555555555");

		InfoRespuesta infoRespuesta = new InfoRespuesta();
		infoRespuesta.setCodigo(EnumRespuestas.CLIENTE_CREADO.getCodigo());
		infoRespuesta.setMensaje(EnumRespuestas.CLIENTE_CREADO.getMensaje());

		MockHttpServletResponse response = mockMvvc.perform(MockMvcRequestBuilders.post("/clientes")
				.contentType(MediaType.APPLICATION_JSON).content(clienteRequest.write(clienteReq).getJson()))
				.andReturn().getResponse();
		BDDMockito.then(response.getStatus()).equals(HttpStatus.OK.value());

	}
}
