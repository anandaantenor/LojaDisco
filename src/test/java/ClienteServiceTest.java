import br.com.letscode.excecoes.UsuarioJaExisteException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
/*
@RunWith(MockitoJUnitRunner.class)
public class ClienteServiceTest {

  @InjectMocks
  private ClienteServiceImpl clienteService;

  @Mock
  private ClienteDao clienteDao;




  @Test
  public void should_insert_a_client_succesfully() throws IOException {
      Cliente cliente = new Cliente();
      cliente.setCpf("27091914074");
      cliente.setNome("Nome de teste");
      clienteService.inserir(cliente);
      verify(clienteDao, Mockito.times(1)).inserirNoArquivo(cliente);
      Assert.assertNotNull(cliente.getIdentificador());
  }

  @Test(expected = UsuarioJaExisteException.class)
  public void failed_to_insert_a_cliente_with_same_cpf_identifier() throws IOException{
      //quando
      Cliente clienteExistente = new Cliente();
      clienteExistente.setCpf("27091914074");
      clienteExistente.setNome("Nome ja existente");
      when(clienteDao.findByCpf(eq("27091914074"))).thenReturn(Optional.of(clienteExistente));

      Cliente cliente = new Cliente();
      cliente.setCpf("27091914074");
      cliente.setNome("Nome de teste");
      clienteService.inserir(cliente);

      verify(clienteDao,never()).inserirNoArquivo(any());

  }
}
*/