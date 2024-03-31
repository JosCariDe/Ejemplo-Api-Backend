package UnivesidadMagdalena.Tienda.repository;

import UnivesidadMagdalena.Tienda.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto,Long> {
    @Query("SELECT p FROM Producto p WHERE p.nombre LIKE %:term%")
    List<Producto> buscarPorTerminoDeBusqueda(@Param("term") String term);


    @Query("SELECT p FROM Producto p WHERE p.stock > 0")
    List<Producto> buscarEnStock();

    @Query("SELECT p FROM Producto p WHERE p.price <= :precioMaximo AND p.stock <= :stockMaximo")
    List<Producto> buscarPorPrecioMaximoYStockMaximo(@Param("precioMaximo") Integer precioMaximo, @Param("stockMaximo") Integer stockMaximo);
}
