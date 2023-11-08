package com.sophos.retoSpringBoot.service;

import com.sophos.retoSpringBoot.entity.Cuenta;
import java.util.List;

/**
 * <h1>Interface Cuenta Service</h1>
 *Esta interface define los metodos que se implementaran en la logica de negocio
 *para la clase Cuenta.
 *
 *@author Wilson David Pachon.
 *@version 1.0.0 2022
 *@since 1.0.0
 */
public interface CuentaService {
    /**
     *metodo para crear una cuenta.
     * @param cuenta cuenta a crear.
     * @return objeto de tipo cuenta.
     */
    Cuenta createAccount(Cuenta cuenta);

    /**
     * metodo para leer una cuenta especifica
     * @param cuentaId id de la cuenta a consultar
     * @return la cuenta con id pasado como parametro.
     */
    Cuenta readAccount(Long cuentaId);

    /**
     * metodo para leer las cuentas asociadas a un cliente.
     * @param clienteId id del cliente a consultar.
     * @return lista de cuentas asociada a un cliente.
     */

    List<Cuenta> readAccountsByClient(Long clienteId);

    /**
     * metodo para actualizar una cuenta.
     * @param cuenta datos de la cuenta que se va a actualizar.
     * @param cuentaId id de la cuenta a actualizar
     * @return cuenta actualizada.
     */
    Cuenta updateAccount(Cuenta cuenta, Long cuentaId);

    /**
     * metodo para eliminar una cuenta.
     * @param cuentaId id de la cuenta a eliminar.
     * @return true si la cuenta se elimino con exito, false si no se pudo eliminar la cuenta.
     */
    boolean deleteAccount(Long cuentaId);
}
