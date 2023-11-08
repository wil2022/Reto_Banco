package com.sophos.retoSpringBoot.service.implementations;

import com.sophos.retoSpringBoot.entity.Movimiento;
import com.sophos.retoSpringBoot.exceptions.AccountNotFoundException;
import com.sophos.retoSpringBoot.exceptions.MovementActiveStateException;
import com.sophos.retoSpringBoot.exceptions.MovementNotFoundException;
import com.sophos.retoSpringBoot.repository.AccountRepository;
import com.sophos.retoSpringBoot.repository.MovementRepository;
import com.sophos.retoSpringBoot.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 *<h1>Implementacion Servicio Movimiento</h1>
 *En esta clase se implementa la logica de negocio de la entidad Movimiento.
 *
 *@author Wilson David Pachon.
 *@version 1.0.0 2022
 *@since 1.0.0
 */


@Service 
public class MovimientoServiceImp implements MovimientoService {
    /**
     * inyeccion de la dependencia MovementRepository
     */
    @Autowired
    private MovementRepository movementRepository;
    /**
     * inyeccion de la dependencia AccountRepository
     */
    @Autowired
    private AccountRepository accountRepository;

    /**
     * <h2>Metodo para crear un movimiento</h2>
     * Para crear el movimiento se obtiene una cuenta por id, a la
     * cual se va asociar dicho movimiento.
     * @param movimiento movimiento a crear.
     * @return objeto de tipo Movimiento
     */
    @Override
    public Movimiento createMovement(Movimiento movimiento) {

        LocalDate today = LocalDate.now();

        return accountRepository.findById(movimiento.getCuenta().getCuentaId())
                .map(cuenta -> {
                    movimiento.setCuenta(cuenta);
                    movimiento.setFechaCreacion(today);
                    movimiento.setUsuarioCreacion("wilsonp");

                    return movementRepository.save(movimiento);
                }).orElseThrow(()->new AccountNotFoundException("la cuenta a la cual se le va asignar el movimiento, no existe"));

    }

    /**
     * <h2>Metodo para leer un movimiento</h2>
     * @param movimientoId id del movimiento a consultar
     * @return el movimiento asociado al id pasado como parametro
     */
    @Override
    public Movimiento readMovement(Long movimientoId) {

    	if(!movementRepository.existsById(movimientoId)) {
    		throw new MovementNotFoundException("el movimiento con el id: "+movimientoId+" no existe");
    	}
        return movementRepository.findByMovimientoId(movimientoId);

    }

    /**
     * <h2>Metodo para leer los movimientos que tiene una cuenta</h2>
     * @param cuentaId id de la cuenta a la cual se le va a consultar los movimientos
     * @return lista de movimientos asociados a la cuenta consultada
     */
    @Override
    public List<Movimiento> readMovementsByAccount(Long cuentaId) {

    	if(!accountRepository.existsById(cuentaId)) {
    		throw new AccountNotFoundException("la cuenta con el id: "+cuentaId+" no existe");
    	}
    	
        return movementRepository.findByCuenta(accountRepository.findByCuentaId(cuentaId));
    }

    /**
     * <h2>Metodo para actualizar un movimiento</h2>
     * @param movimiento datos del movimiento que se va a actualizar
     * @param movimientoId id del movimiento que se va a actualizar
     * @return movimiento actualizado.
     */
    @Override
    public Movimiento updateMovement(Movimiento movimiento, Long movimientoId) {
    	
    	if(!movementRepository.existsById(movimientoId)) {
    		throw new MovementNotFoundException("el movimiento con el id: "+movimientoId+" no existe");
    	}

        Movimiento movementToUpdate = movementRepository.findByMovimientoId(movimientoId);

        LocalDate today = LocalDate.now();

        movementToUpdate.setTipo(movimiento.getTipo());
        movementToUpdate.setValor(movimiento.getValor());
        movementToUpdate.setPeriodo(movimiento.getPeriodo());
        movementToUpdate.setEstado(movimiento.getEstado());
        movementToUpdate.setFechaMovimiento(movimiento.getFechaMovimiento());
        movementToUpdate.setFechaModificacion(today);
        movementToUpdate.setUsuarioModificacion("wilsonp");

        return movementRepository.save(movementToUpdate);
    }
    
    /**
     * <h2>Metodo para validar el estado de un movimiento</h2>
     * @param movimientoId id del movimiento a validar.
     * @return true si el estado es activo, false si el estado no es activo.
     */
    
    public boolean validateMovementStatus(Long movimientoId) {
    	
    	Movimiento movement = movementRepository.findByMovimientoId(movimientoId);
    	
    	if (movement.getEstado().equals("activo")) {
    		return true;
    	}else {
    		return false;
    	}
    	
    }
    

    /**
     * <h2>Metodo para eliminar un movimiento</h2>
     *
     * @param movimientoId id del movimiento que se va a eliminar
     * @return true si el movimiento se elimino con exito, false si no se pudo eliminar.
     */
    @Override
    public boolean deleteMovement(Long movimientoId) {
    	
    	if(!movementRepository.existsById(movimientoId)) {
    		throw new MovementNotFoundException("el movimiento con el id: "+movimientoId+" no existe");
    	}
        
    	if (validateMovementStatus(movimientoId)) {
            throw new MovementActiveStateException("el movimiento con el id "+movimientoId+" no se puede eliminar porque su estado es activo");
            
        } 
            movementRepository.deleteById(movimientoId);
            return true;
        }    

}

