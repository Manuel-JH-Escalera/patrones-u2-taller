package cl.patrones.taller.u2.tienda.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import cl.patrones.taller.u2.catalogo.domain.Categoria;
import cl.patrones.taller.u2.catalogo.service.CategoriaService;
import cl.patrones.taller.u2.tienda.menu.CategoriaItemMenuAdapter;
import cl.patrones.taller.u2.tienda.menu.EnlaceItemMenu;
import cl.patrones.taller.u2.tienda.menu.ItemMenu;

@ControllerAdvice
public class MenuControllerAdvice {

    private final CategoriaService categoriaService;

    public MenuControllerAdvice(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @ModelAttribute("menu")
    public List<ItemMenu> menu() {
        List<ItemMenu> menu = new ArrayList<>();
        menu.add(new EnlaceItemMenu("Inicio", "/"));
        menu.add(construirMenuCategorias());
        menu.add(new EnlaceItemMenu("Ubicación", "/ubicacion"));
        menu.add(new EnlaceItemMenu("Contacto", "/contacto"));
        return menu;
    }

    private ItemMenu construirMenuCategorias() {
        List<Categoria> todas = categoriaService.getCategorias();

        Map<Long, CategoriaItemMenuAdapter> adaptadores = new HashMap<>();
        for (Categoria cat : todas) {
            adaptadores.put(cat.getId(),
                new CategoriaItemMenuAdapter(cat));
        }

        List<CategoriaItemMenuAdapter> raices = new ArrayList<>();
        for (Categoria cat : todas) {
            CategoriaItemMenuAdapter adaptador =
                adaptadores.get(cat.getId());
            if (cat.getPadre() == null) {
                raices.add(adaptador);
            } else {
                CategoriaItemMenuAdapter padre =
                    adaptadores.get(cat.getPadre().getId());
                if (padre != null) {
                    padre.agregarHijo(adaptador);
                }
            }
        }

        // Nodo contenedor Categorías
        EnlaceItemMenu nodo = new EnlaceItemMenu("Categorías", "/categoria") {
            @Override
            public boolean tieneHijos() { return true; }
            @Override
            public List<ItemMenu> getHijos() {
                return new ArrayList<>(raices);
            }
            @Override
            public String getSlug() { return "categorias"; }
        };
        return nodo;
    }
}