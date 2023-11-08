package com.sophos.retoSpringBoot.service;

import com.sophos.retoSpringBoot.entity.Movimiento;

import java.util.List;
/**
 * <h1>Interface Movimiento Service</h1>
 *Esta interface define los metodos que se implementaran en la logica de negocio
 *para la clase Movimiento.
 *
 *@author Wilson David Pachon.
 *@version 1.0.0 2022
 *@since 1.0.0
 */
public interface MovimientoService {
    /**
     * metodo para crear un movimiento.
     * @param movimiento movimiento a crear.
     * @return objeto de tipo movimiento.
     */
    Movimiento createMovement(Movimiento movimiento);

    /**
     * metodo para leer un movimiento en especifico
     * @param movimientoId id del movimiento a consultar
     * @return movimiento con el id pasado como parametro.
     */
    Movimiento readMovement(Long movimientoId);

    /**
     * metodo para leer los movimientos asociados a una cuenta.
     * @param cuentaId id de la cuenta a la cual se le va aconsultar los movimientos
     * @return lista de movimientos asociados a la cuenta.
     */
    List<Movimiento> readMovementsByAccount(Long cuentaId);

    /**
     * metodo para actualizar un movimiento.
     * @param movimiento datos del movimiento que se va a actualizar
     * @param movimientoId id del movimiento que se va a actualizar
     * @return movimiento actualizado.
     */
    Movimiento updateMovement(Movimiento movimiento, Long movimientoId);

    /**
     * metodo para eliminar un movimiento
     * @param movimientoId id del movimiento que se va a eliminar
     * @return true si el movimiento se elimino con exito, false sino se pudo eliminar.
     */
    boolean deleteMovement(Long movimientoId);

}
