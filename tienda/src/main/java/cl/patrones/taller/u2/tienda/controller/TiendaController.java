package cl.patrones.taller.u2.tienda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.patrones.taller.u2.catalogo.domain.Aviso;
import cl.patrones.taller.u2.catalogo.service.CategoriaService;
import cl.patrones.taller.u2.tienda.adapter.ProductoAvisoAdapter;

import java.util.List;

@Controller
public class TiendaController {

    private final ProductoAvisoAdapter adapter;
    private final CategoriaService categoriaService;

    public TiendaController(ProductoAvisoAdapter adapter,
            CategoriaService categoriaService) {
        this.adapter = adapter;
        this.categoriaService = categoriaService;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        List<Aviso> avisos = adapter.obtenerAvisos();
        model.addAttribute("avisos", avisos);
        return "inicio";
    }

    @GetMapping("/categoria/{categoriaId}/{slug}")
    public String categoria(
            @PathVariable("categoriaId") Long categoriaId,
            @PathVariable("slug") String slug,
            Model model) {
        List<Aviso> avisos =
            adapter.obtenerAvisosPorCategoria(categoriaId);
        model.addAttribute("avisos", avisos);
        categoriaService.getCategoriaPorId(categoriaId)
            .ifPresent(c -> model.addAttribute("categoria", c));
        return "categoria";
    }
}