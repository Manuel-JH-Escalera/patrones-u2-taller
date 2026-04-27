package cl.patrones.taller.u2.tienda.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cl.patrones.taller.u2.bodegaje.domain.Producto;
import cl.patrones.taller.u2.bodegaje.domain.Stock;
import cl.patrones.taller.u2.bodegaje.service.BodegajeService;
import cl.patrones.taller.u2.catalogo.domain.Aviso;
import cl.patrones.taller.u2.catalogo.repository.ClasificacionRepository;

@Service
public class ProductoAvisoAdapter {

    private final BodegajeService bodegajeService;
    private final ClasificacionRepository clasificacionRepo;

    public ProductoAvisoAdapter(
            BodegajeService bodegajeService,
            ClasificacionRepository clasificacionRepo) {
        this.bodegajeService = bodegajeService;
        this.clasificacionRepo = clasificacionRepo;
    }

    public List<Aviso> obtenerAvisos() {
        return bodegajeService.getProductos().stream()
            .map(this::adaptarProducto)
            .collect(Collectors.toList());
    }

    public List<Aviso> obtenerAvisosPorCategoria(Long catId) {
        return obtenerAvisos().stream()
            .filter(a -> a.getCategoria() != null
                && a.getCategoria().getId().equals(catId))
            .collect(Collectors.toList());
    }

    private Aviso adaptarProducto(Producto producto) {
        Aviso aviso = new Aviso();
        aviso.setTitulo(producto.getNombre());
        aviso.setSku(producto.getSku());
        aviso.setImagen(producto.getImagen());

        // Precio = costo + 30% utilidad
        double precio = producto.getCosto() * 1.30;
        aviso.setPrecio((long) precio);

        // Stock consolidado de todas las bodegas
        int stockTotal = producto.getStocks().stream()
            .mapToInt(Stock::getCantidad)
            .sum();
        aviso.setStock(stockTotal);

        // Categoría desde clasificación
        clasificacionRepo.findAll().stream()
            .filter(c -> c.getSku().equals(producto.getSku()))
            .findFirst()
            .ifPresent(c -> aviso.setCategoria(
                c.getCategoria()));

        return aviso;
    }
}