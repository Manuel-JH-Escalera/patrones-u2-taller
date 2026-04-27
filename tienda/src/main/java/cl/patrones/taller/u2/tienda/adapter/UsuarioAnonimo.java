package cl.patrones.taller.u2.tienda.adapter;

import cl.patrones.taller.u2.clientes.Cliente;

public class UsuarioAnonimo extends Usuario {

    public UsuarioAnonimo() {
        super(crearClienteAnonimo());
    }

    private static Cliente crearClienteAnonimo() {
        Cliente cliente = new Cliente();
        cliente.setNombre("Invitado");
        cliente.setEmail("");
        cliente.setComuna("");
        cliente.setDireccion("");
        return cliente;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "anonimo";
    }
}