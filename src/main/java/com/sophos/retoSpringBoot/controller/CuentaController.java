package com.sophos.retoSpringBoot.controller;

import com.sophos.retoSpringBoot.entity.Cuenta;
import com.sophos.retoSpringBoot.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * <h1>Controlador Cuenta</h1>
 *Es el encargado de exponer la funcionalidad de la clase Cuenta
 *para que pueda ser consumida por el frontend.
 *
 *@author Wilson David Pachon.
 *@version 1.0.0 2022
 *@since 1.0.0
 *
 */
@RestController
@RequestMapping("/api/cuenta")
//@CrossOrigin(origins = "http://localhost:4200/")
@CrossOrigin(origins = "http://localhost:3000/")
public class CuentaController { 
    /**
     * inyeccion de la dependencia CuentaService
     */
    @Autowired
    CuentaService cuentaService;

    /**
     * <h2>Metodo para leer una cuenta</h2>
     * @param cuentaId id de la cuenta a consultar
     * @return cuenta asociada al id pasado como parametro.
     */
    @GetMapping("/{cuentaId}")
    @ResponseBody
    public ResponseEntity<Cuenta> readAccount(@PathVariable("cuentaId") Long cuentaId) {
        Cuenta response = null;
        HttpStatus status = null;
        
            response = cuentaService.readAccount(cuentaId);
            if(response != null) {
	            status = HttpStatus.OK;                
	            return ResponseEntity.status(status).body(response);            

            }else {
            	status = HttpStatus.NOT_FOUND;            
	            return ResponseEntity.status(status).body(response); 
            }
            
    }

    /**
     * <h2>Metodo para leer las cuentas asociadas a un cliente</h2>
     * @param clienteId id del cliente al cual se va a consultar las cuentas que tiene.
     * @return lista de las cuentas asociadas al cliente.
     */
    @GetMapping("/cliente/{clienteId}")
    @ResponseBody
    public ResponseEntity<List<Cuenta>> readAccountsByClient(@PathVariable("clienteId") Long clienteId) {
        List<Cuenta> response = null;
        HttpStatus status = null;
        
            response = cuentaService.readAccountsByClient(clienteId);
            
            if(response != null) {              
            status = HttpStatus.OK;                
            return ResponseEntity.status(status).body(response);  
            
            }else {
	        	status = HttpStatus.NOT_FOUND;            
	            return ResponseEntity.status(status).body(response);
            }
    }


    /**
     * <h2>Metodo par acrear una cuenta</h2>
     * @param cuenta la cuenta que se va a crear
     * @return objeto de tipo cuenta.
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<Cuenta> createAccount(@RequestBody Cuenta cuenta){
        Cuenta response = null;
        HttpStatus status = null;      

		response = cuentaService.createAccount(cuenta);
		if(response != null) {		
			status = HttpStatus.CREATED;		                
			return ResponseEntity.status(status).body(response);
			
		}else {
			status = HttpStatus.BAD_REQUEST;               
            return ResponseEntity.status(status).body(response);  
		}
		
    }

    

    /**
     * <h2>Metodo para actualizar una cuenta</h2>
     * @param cuenta datos de la cuenta que se va actualizar
     * @param cuentaId id de la cuenta a actualizar
     * @return cuenta actualizada.
     */

    @PutMapping("/{cuentaId}")
    @ResponseBody
    public ResponseEntity<Cuenta> updateAccount(@RequestBody Cuenta cuenta, @PathVariable("cuentaId") Long cuentaId){
        Cuenta response = null;
        HttpStatus status = null;
        
            response = cuentaService.updateAccount(cuenta, cuentaId);
            if(response != null) {
                status = HttpStatus.OK;                
                return ResponseEntity.status(status).body(response);            
            }else {
            	status = HttpStatus.NOT_FOUND;            
	            return ResponseEntity.status(status).body(response); 
            
            }
      }

    /**
     * <h2>Metodo para eliminar una cuenta</h2>
     * @param cuentaId id de la cuenta que se va a eliminar
     * @return true si la cuenta se elimino, false si no se pudo eliminar la cuenta.
     */
    @DeleteMapping("/{cuentaId}")
    @ResponseBody
    public ResponseEntity<Boolean> deleteAccount(@PathVariable("cuentaId") Long cuentaId){
        boolean response;
        HttpStatus status = null;
        
            response = cuentaService.deleteAccount(cuentaId);
            
            if(response == true) {            
            status = HttpStatus.OK;                
            return ResponseEntity.status(status).body(true);           
        
		    }else {
		    	status = HttpStatus.BAD_REQUEST;                
		        return ResponseEntity.status(status).body(response);    
		    }

    	}
}

