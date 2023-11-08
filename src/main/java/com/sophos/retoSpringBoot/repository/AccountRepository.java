package com.sophos.retoSpringBoot.repository;

import com.sophos.retoSpringBoot.entity.Cliente;
import com.sophos.retoSpringBoot.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 *<h1>Interfaz Repositorio Cuenta</h1>
 *Es la encargada de resolver el acceso a los datos de la tabla Cuenta.
 *@author Wilson David Pachon.
 *@version 1.0.0 2022
 *@since 1.0.0
 */
@Repository
public interface AccountRepository extends JpaRepository<Cuenta, Long> {
    /**
     *Buscar cuentas por cliente
     * @param cliente el cliente que se quiere consultar.
     * @return lista de cuentas.
     */
	List<Cuenta> findByCliente(Cliente cliente);

    /**
     *Buscar cuenta por id.
     * @param cuentaId id de la cuenta que se quiere buscar.
     * @return la cuenta asociada al id pasado como parametro.
     */
    Cuenta findByCuentaId(Long cuentaId);


}

