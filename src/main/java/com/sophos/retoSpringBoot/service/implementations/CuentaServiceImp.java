package com.sophos.retoSpringBoot.service.implementations;

import com.sophos.retoSpringBoot.entity.Cuenta;
import com.sophos.retoSpringBoot.exceptions.AccountNotFoundException;
import com.sophos.retoSpringBoot.exceptions.AccountWithMovementsException;
import com.sophos.retoSpringBoot.exceptions.ClientNotFound;
import com.sophos.retoSpringBoot.repository.AccountRepository;
import com.sophos.retoSpringBoot.repository.ClientRepository;
import com.sophos.retoSpringBoot.repository.MovementRepository;
import com.sophos.retoSpringBoot.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 *<h1>Implementacion Servicio Cuenta</h1>
 *En esta clase se implementa la logica de negocio de la entidad Cuenta.
 *
 *@author Wilson David Pachon.
 *@version 1.0.0 2022
 *@since 1.0.0
 */
@Service
public class CuentaServiceImp implements CuentaService { 
    /**
     * inyeccion de la dependencia accountRepository.
     */
    @Autowired
    private AccountRepository accountRepository;
    /**
     * inyeccion de la dependencia clientRepository
     */
    @Autowired
    private ClientRepository clientRepository;
    /**
     * inyeccion de la dependencia movementRepository
     */
    @Autowired
    private MovementRepository movementRepository;

    /**
     * <h2>Metodo para crear una cuenta</h2>
     * Para crear la cuenta se obtiene un cliente por id, al
     * cual se va asociar dicha cuenta.
     * @param cuenta cuenta a crear.
     * @return objeto de tipo cuenta.
     */
    @Override
    public Cuenta createAccount(Cuenta cuenta){	
    	
    	
        return clientRepository.findById(cuenta.getCliente().getClienteId())
                .map(cliente -> { 
                    LocalDate today = LocalDate.now();
                    cuenta.setCliente(cliente);
                    cuenta.setFechaCreacion(today);
                    cuenta.setUsuarioCreacion("wilsonp");
            return accountRepository.save(cuenta);
        }).orElseThrow(()-> new ClientNotFound("el cliente al cual se le va asignar la cuenta no existe"));
    } 

    /**
     * <h2>Metodo para leer una cuenta</h2>
     * @param cuentaId id de la cuenta a consultar
     * @return la cuenta asociada al id pasado como parametro.
     */
    @Override
    public Cuenta readAccount(Long cuentaId) {
    	
    	if(!accountRepository.existsById(cuentaId)) {
    		throw new AccountNotFoundException("la cuenta con el id: "+cuentaId+" no existe");
    	}

        return accountRepository.findByCuentaId(cuentaId);

    }

    /**
     * <h2>Metodo para leer las cuentas por cliente</h2>
     * @param clienteId id del cliente a consultar.
     * @return lista de cuentas asociadas al cliente.
     */
    @Override
    public List<Cuenta> readAccountsByClient(Long clienteId) {

    	if(!clientRepository.existsById(clienteId)) {
    		throw new ClientNotFound("el cliente con el id: "+clienteId+" no existe");
    	}
        return accountRepository.findByCliente(clientRepository.findByClienteId(clienteId));
    } 

    /**
     * <h2>Metodo para actualizar una cuenta</h2>
     * @param cuenta datos de la cuenta que se va a actualizar.
     * @param cuentaId id de la cuenta a actualizar
     * @return cuenta actualizada
     */

    @Override
    public Cuenta updateAccount(Cuenta cuenta, Long cuentaId) {
    	
    	if(!accountRepository.existsById(cuentaId)) {
    		throw new AccountNotFoundException("la cuenta con el id: "+cuentaId+" no existe");
    	}

        Cuenta accountToUpdate = accountRepository.findByCuentaId(cuentaId);
        LocalDate today = LocalDate.now();

        accountToUpdate.setProducto(cuenta.getProducto());
        accountToUpdate.setEstadoCuenta(cuenta.getEstadoCuenta());
        accountToUpdate.setValorCredito(cuenta.getValorCredito());
        accountToUpdate.setFechaApertura(cuenta.getFechaApertura());
        accountToUpdate.setFechaModificacion(today);
        accountToUpdate.setUsuarioModificacion("wilsonp");

        return accountRepository.save(accountToUpdate);
    }
    
    /**
     * <h2>Metodo para validar si una cuenta tiene movimientos</h2>
     * @param cuentaId id de la cuenta a validar.
     * @return true si tiene movimientos asociados, false si no tiene movimientos.
     */
    
    public boolean validateAccountMovements(Long cuentaId) {
    	
    	Integer amountMovements = movementRepository.findByCuenta(accountRepository.findByCuentaId(cuentaId)).size();
    	
    	if(amountMovements > 0) {
    		return true;
    	}else {
    		return false;
    	}
    	
    }

    /**
     * <h2>Metodo para eliminar una cuenta</h2>
     *
     * @param cuentaId id de la cuenta a eliminar.
     * @return true si la cuenta se elimino con exito, false si no se pudo eliminar la cuenta.
     */
    @Override
    public boolean deleteAccount(Long cuentaId) { 
    	
    	if(!accountRepository.existsById(cuentaId)) {
    		throw new AccountNotFoundException("la cuenta con el id: "+cuentaId+" no existe");
    	}
        
    	if(validateAccountMovements(cuentaId)){
    		throw new AccountWithMovementsException("la cuenta con el id "+cuentaId+" no se puede eliminar porque tiene movimientos asociados");                        
        }
            accountRepository.deleteById(cuentaId);
            return true;        
    }  


}
