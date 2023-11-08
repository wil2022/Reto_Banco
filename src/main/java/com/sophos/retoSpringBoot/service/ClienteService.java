package com.sophos.retoSpringBoot.service;

import com.sophos.retoSpringBoot.entity.Cliente;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <h1>Interface Cliente Service</h1>
 *Esta interface define los metodos que se implementaran en la logica de negocio 
 *para la clase Cliente.
 *
 *@author Wilson David Pachon.
 *@version 1.0.0 2022
 *@since 1.0.0
 */
@Service
public interface ClienteService {
    /**
     *metodo para crear un cliente
     * @param cliente objeto de tipo Cliente
     * @return un objeto de tipo Cliente.
     */
    Cliente createClient(Cliente cliente);

    /**
     * metodo para leer un cliente
     * @param clienteId id del cliente a consultar.
     * @return el Cliente con el id pasado como parametro.
     */
    Cliente readClient(Long clienteId);

    /**
     * metodo para leer todos los clientes
     * @return lista de todos los clientes.
     */
    List<Cliente> readAllClients();

    /**
     * metodo para actualizar un cliente
     * @param cliente datos del cliente que se va a actualizar
     * @param clienteId id del cliente el cual se va a actualizar.
     * @return el cliente actualizado.
     */
    Cliente updateClient(Cliente cliente, Long clienteId);

    /**
     * metodo para borrar un cliente.
     * @param clienteId id del cliente que se va a eliminar.
     * @return true si se elimino con exito, false sino se elimino el cliente.
     */
    boolean deleteClient(Long clienteId);

}
