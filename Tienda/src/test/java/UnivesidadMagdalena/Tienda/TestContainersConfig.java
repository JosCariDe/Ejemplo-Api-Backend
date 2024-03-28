package UnivesidadMagdalena.Tienda;

import org.testcontainers.containers.PostgreSQLContainer;

//@TestConfiguration(proxyBeanMethods = false)
public class TestContainersConfig {
    //@Bean
    //@ServiceConnection
    PostgreSQLContainer<?> postgreSQLContainer() {
        return new PostgreSQLContainer<>("postgres:15-alpine");
    }
}
