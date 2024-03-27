package UnivesidadMagdalena.Tienda.repository;

import UnivesidadMagdalena.Tienda.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByEmail(String email);
    List<Cliente> findByDireccion(String direccion);
    @Query("SELECT c FROM Cliente p WHERE c.nombre LIKE :term%")
    List<Cliente> buscarPorComienzoNombreCon(@Param("term") String nombre);
}
