package cl.patrones.taller.u2.tienda.menu;

import java.util.List;

public class EnlaceItemMenu implements ItemMenu {

    private final String texto;
    private final String enlace;

    public EnlaceItemMenu(String texto, String enlace) {
        this.texto = texto;
        this.enlace = enlace;
    }

    @Override
    public String getTexto() { return texto; }

    @Override
    public String getSlug() { return enlace; }

    @Override
    public String getEnlace() { return enlace; }

    @Override
    public boolean tieneHijos() { return false; }

    @Override
    public List<ItemMenu> getHijos() { return List.of(); }
}