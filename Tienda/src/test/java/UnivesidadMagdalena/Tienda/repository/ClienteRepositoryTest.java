package UnivesidadMagdalena.Tienda.repository;

import UnivesidadMagdalena.Tienda.entities.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class ClienteRepositoryTest extends AbstractIntegrationDBTest{

    ClienteRepository clienteRepository;

    @Autowired
    public ClienteRepositoryTest(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @BeforeEach
    void setUp() {

        clienteRepository.deleteAll();
        initMockClientes();

    }

    void initMockClientes() {
        Cliente cliente = Cliente.builder()
                .nombre("Jose")
                .email("jose@gmail.com")
                .direccion("Calle 13")
                .build();
        clienteRepository.save(cliente);

        Cliente cliente1 = Cliente.builder()
                .nombre("Maria")
                .email("maria@gmail.com")
                .direccion("Santa fe calle 24")
                .build();
        clienteRepository.save(cliente1);

        Cliente cLiente2 = Cliente.builder()
                .nombre("Juan")
                .email("juan@gmail.com")
                .direccion("Nogales carrera 12")
                .build();
        clienteRepository.save(cLiente2);

        Cliente cliente3 = Cliente.builder()
                .nombre("Marcos")
                .email("marcos@gmail.com")
                .direccion("Quinta San Pedro")
                .build();
        clienteRepository.save(cliente3);

        clienteRepository.flush();


    }

    @Test
    void givenAnCliente_whenSave_thenClienteWithId() {
        //given
        Cliente cliente = Cliente.builder()
                .nombre("David")
                .email("david@gmail.com")
                .direccion("olivos 12 con 15")
                .build();
        //when
        clienteRepository.save(cliente);
        //then
        assertThat(clienteRepository.findById(cliente.getId())).isNotNull();
    }

    @Test
    void findByEmailTest() {
        List<Cliente> clientesSave = clienteRepository.findByEmail("david@gmail.com");
        List<Cliente> clientesSave2 = clienteRepository.findByEmail("juan@gmail.com");

        assertThat(clientesSave.isEmpty()).isTrue();
        assertThat(clientesSave2.isEmpty()).isFalse();
    }

    @Test
    void findByDireccion() {
        List<Cliente> clientesSave = clienteRepository.findByDireccion("olivos con 12 con 15");
        List<Cliente> clientesSave2 = clienteRepository.findByDireccion("Calle 13");

        assertThat(clientesSave.isEmpty()).isTrue();
        assertThat(clientesSave2.isEmpty()).isFalse();
    }

    @Test
    void buscarPorComienzoNombreCon() {
        List<Cliente> clientesSave = clienteRepository.buscarPorComienzoNombreCon("Da");
        List<Cliente> clientesSave2 = clienteRepository.buscarPorComienzoNombreCon("Ma");

        assertThat(clientesSave.isEmpty()).isTrue();
        assertThat(clientesSave2.size() == 2).isTrue();
    }
}