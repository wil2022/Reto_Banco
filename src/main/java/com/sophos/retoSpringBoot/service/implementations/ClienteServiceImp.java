package com.sophos.retoSpringBoot.service.implementations;

import com.sophos.retoSpringBoot.entity.Cliente;
import com.sophos.retoSpringBoot.exceptions.ClientNotFound;
import com.sophos.retoSpringBoot.exceptions.ClientWithAccountsException;
import com.sophos.retoSpringBoot.exceptions.UnderAgeClientException;
import com.sophos.retoSpringBoot.repository.AccountRepository;
import com.sophos.retoSpringBoot.repository.ClientRepository;
import com.sophos.retoSpringBoot.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 *<h1>Implementacion Servicio Cliente</h1>
 *En esta clase se implementa la logica de negocio de la entidad Cliente.
 *
 *@author Wilson David Pachon.
 *@version 1.0.0 2022
 *@since 1.0.0
 */

@Service
public class ClienteServiceImp implements ClienteService { 
    /**
     * inyeccion de la dependencia ClientRepository.
     */
    @Autowired
    private ClientRepository clientRepository;
    /**
     * inyeccion de la dependencia AccountRepository.
     */
    @Autowired
    private AccountRepository accountRepository;

    /**
     * <h2>Metodo para validar si es mayor de edad</h2> 
     * Se realiza la comparacion de la fecha actual con la fecha de nacimiento
     * para determinar si es mayor de edad.
     * 
     * @param fechaNacimiento la fecha de nacimiento del cliente.
     * @return true si es mayor de 18, false si es menor de 18.
     */
    public boolean isOfLegalAge(LocalDate fechaNacimiento) {
    	
    	LocalDate today = LocalDate.now();
        
    	LocalDate birth = fechaNacimiento;

        Integer age = today.compareTo(birth);

        //System.out.println("edad : " + age);
        
        if(age > 18) {
        	return true; 
        }else {
        	return false;        	
        }        
    	
    }
    
    /**
     * <h2>Metodo para crear un cliente</h2> 
     * 
     * @param cliente objeto de tipo Cliente
     * @return Cliente creado.
     */
    @Override
    public Cliente createClient(Cliente cliente){

    LocalDate today = LocalDate.now();

        if(!isOfLegalAge(cliente.getFechaNacimiento())){

        	throw new UnderAgeClientException("cliente menor de edad");
        		  
        }
        
     
         cliente.setFechaCreacion(today);
         cliente.setUsuarioCreacion("wilsonp");
         return clientRepository.save(cliente);
         
    }

    

    
    @Override
    public Cliente readClient(Long clienteId) {
    	
    	if(!clientRepository.existsById(clienteId)) {
    		throw new ClientNotFound("el cliente con el id: "+clienteId+" no existe");
    	}    	

        return clientRepository.findByClienteId(clienteId);
    }
    
    /**
     * <h2>Metodo para obtener la lista de todos clientes.</h2>
     *
     * @return lista de todos los clientes.
     */
    @Override
    public List<Cliente> readAllClients() { 
    	
    	if(clientRepository.findAll().isEmpty()) {
    		throw new ClientNotFound("no se encontro ningun cliente");
    	}
    	
        return clientRepository.findAll(); 
    }
    
    /**
     * <h2>Metodo para actualizar un cliente</h2>
     * @param cliente datos del cliente que se va a actualizar
     * @param clienteId id del cliente el cual se va a actualizar.
     * @return Cliente con los datos actualizados.
     */
    @Override
    public Cliente updateClient(Cliente cliente, Long clienteId) {
    	
    	if(!clientRepository.existsById(clienteId)) {
    		throw new ClientNotFound("el cliente con el id: "+clienteId+" no existe");
    	}

        Cliente clientToUpdate = clientRepository.findByClienteId(clienteId);

        LocalDate today = LocalDate.now(); 

        clientToUpdate.setTipoId(cliente.getTipoId());
        clientToUpdate.setNumeroId(cliente.getNumeroId());
        clientToUpdate.setEstadoCliente(cliente.getEstadoCliente());
        clientToUpdate.setTipoCliente(cliente.getTipoCliente());
        clientToUpdate.setDireccion(cliente.getDireccion());
        clientToUpdate.setTelefono(cliente.getTelefono());
        clientToUpdate.setCorreo(cliente.getCorreo());
        clientToUpdate.setPrimerNombre(cliente.getPrimerNombre());
        clientToUpdate.setSegundoNombre(cliente.getSegundoNombre());
        clientToUpdate.setPrimerApellido(cliente.getPrimerApellido());
        clientToUpdate.setSegundoApellido(cliente.getSegundoApellido());
        clientToUpdate.setFechaNacimiento(cliente.getFechaNacimiento());   
        clientToUpdate.setFechaModificacion(today);
        clientToUpdate.setUsuarioModificacion("wilsonp");        
        
        if(!isOfLegalAge(cliente.getFechaNacimiento())){

        	throw new UnderAgeClientException("cliente menor de edad");
        		  
        }

        return clientRepository.save(clientToUpdate);


    }
        
    /**
     * <h2>Metodo que valida si un cliente tiene cuentas asociadas</h2>
     * @param clienteId id del cliente que se va a validar
     * @return true si tiene cuentas, false si no tiene cuentas asociadas.
     */
    
    public boolean validateClientAccounts(Long clienteId) {
    	
    	Integer amountAccounts = accountRepository.findByCliente(clientRepository.findByClienteId(clienteId)).size();
    	
    	if(amountAccounts > 0) {
    		return true;
    	}else {
    		return false;	
    	}
    	
    }
        

    /**
     *<h2>Metodo para eliminar un cliente</h2>
     * @param clienteId id del cliente que se va a eliminar.
     * @return true si se elimino el cliente con exito, false sino se pudo eliminar el cliente.
     */
    @Override
    public boolean deleteClient(Long clienteId) {

    	if(!clientRepository.existsById(clienteId)) {
    		throw new ClientNotFound("el cliente con el id: "+clienteId+" no existe");
    	}
    	
        if(validateClientAccounts(clienteId)){
        	throw new ClientWithAccountsException("el cliente con el id "+clienteId+" no se puede eliminar porque tiene cuentas asociadas");        	
        }
        
         clientRepository.deleteById(clienteId);
         return true;
        }

 } 
