package com.sophos.retoSpringBoot.repository;

import com.sophos.retoSpringBoot.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 *<h1>Interfaz Repositorio Cliente</h1>
 *Es la encargada de resolver el acceso a los datos de la tabla Cliente.
 *@author Wilson David Pachon.
 *@version 1.0.0 2022
 *@since 1.0.0
 */
@Repository
public interface ClientRepository extends JpaRepository<Cliente, Long> {
    /**
     * Buscar cliente por id.
     * @param clienteId el id del cliente que se quiere buscar,
     * @return el cliente asociado al id pasado como parametro.
     */
    Cliente findByClienteId(Long clienteId);

}
