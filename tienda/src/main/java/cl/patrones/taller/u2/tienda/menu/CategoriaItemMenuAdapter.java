package cl.patrones.taller.u2.tienda.menu;

import java.util.ArrayList;
import java.util.List;

import cl.patrones.taller.u2.catalogo.domain.Categoria;
import cl.patrones.taller.u2.tienda.menu.util.Slugger;

public class CategoriaItemMenuAdapter implements ItemMenu {

    private final Categoria categoria;
    private final List<ItemMenu> hijos = new ArrayList<>();

    public CategoriaItemMenuAdapter(Categoria categoria) {
        this.categoria = categoria;
    }

    public void agregarHijo(ItemMenu hijo) {
        this.hijos.add(hijo);
    }

    @Override
    public String getTexto() {
        return categoria.getNombre();
    }

    @Override
    public String getSlug() {
        return Slugger.toSlug(categoria.getNombre());
    }

    @Override
    public String getEnlace() {
        return "/categoria/" + categoria.getId() + "/" + getSlug();
    }

    @Override
    public boolean tieneHijos() {
        return !hijos.isEmpty();
    }

    @Override
    public List<ItemMenu> getHijos() {
        return hijos;
    }
}
