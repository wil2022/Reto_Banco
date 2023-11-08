package com.sophos.retoSpringBoot.repository;

import com.sophos.retoSpringBoot.entity.Cuenta;
import com.sophos.retoSpringBoot.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 *<h1>Interfaz Repositorio Movimiento</h1>
 *Es la encargada de resolver el acceso a los datos de la tabla Movimiento.
 *@author Wilson David Pachon.
 *@version 1.0.0 2022
 *@since 1.0.0
 */
@Repository
public interface MovementRepository extends JpaRepository<Movimiento, Long> {
    /**
     * Buscar movimientos por cuenta
     * @param cuenta la cuenta que se quiere consultar.
     * @return lista de movimientos
     */
    List<Movimiento> findByCuenta(Cuenta cuenta);

    /**
     * Buscar movimientos por id
     * @param movimientoId el id del movimiento
     * @return el movimiento asociado al id pasado como parametro.
     */
    Movimiento findByMovimientoId(Long movimientoId);

}

