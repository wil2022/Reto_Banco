package com.sophos.retoSpringBoot.controller;

import com.sophos.retoSpringBoot.entity.Cliente;
import com.sophos.retoSpringBoot.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <h1>Controlador Cliente</h1>
 *Es el encargado de exponer la funcionalidad de la clase Cliente
 *para que pueda ser consumida por el frontend.
 *
 *@author Wilson David Pachon.
 *@version 1.0.0 2022
 *@since 1.0.0
 *
 */
@RestController
@RequestMapping("/api/cliente")
//@CrossOrigin(origins = "http://localhost:4200/")
@CrossOrigin(origins = "http://localhost:3000/")

public class ClienteController {  
    /**
     * inyeccion de la dependencia ClienteService
     */
    @Autowired
    ClienteService clienteService;


    /**
     * <h2>Metodo para consultar un cliente especifico</h2>
     * @param clienteId el id del cliente que se quiere consultar
     * @return cliente asociado al id pasado como parametro
     */
    @GetMapping("/{clienteId}")
    @ResponseBody
    public ResponseEntity<Cliente> readClient(@PathVariable("clienteId") Long clienteId) {
        Cliente response = null;
        HttpStatus status = null;               
             
            response = clienteService.readClient(clienteId);  
            
            if(response != null) {
            status = HttpStatus.OK;
            return ResponseEntity.status(status).body(response);            
    
            }else {
            	status = HttpStatus.NOT_FOUND;
                return ResponseEntity.status(status).body(response);
            }
    }

    /**
     * <h2>Metodo para leer todos los clientes</h2>
     * @return lista de todos los clientes.
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Cliente>> readAllClients() {
        List<Cliente> response = null;
        HttpStatus status = null;        
        
            response = clienteService.readAllClients();   
            
            if(response != null) {  
	            status = HttpStatus.OK;            
	            return ResponseEntity.status(status).body(response);            
            
            }else {
	        	status = HttpStatus.NOT_FOUND;            
	            return ResponseEntity.status(status).body(response);
            }
    }
    /**
     * <h2>Metodo que permite crear un cliente</h2>
     * @param cliente el cliente que se va a crear.
     * @return objeto de tipo cliente.
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<Cliente> createClient(@RequestBody Cliente cliente){
        Cliente response = null;
        HttpStatus status = null;        
        
            response = clienteService.createClient(cliente);
            if(response != null) {  
	            status = HttpStatus.CREATED;               
	            return ResponseEntity.status(status).body(response);     
            }else {
            	status = HttpStatus.BAD_REQUEST;               
	            return ResponseEntity.status(status).body(response);     
            }
    }

    /**
     * <h2>Metodo para actualizar un cliente</h2>
     * @param cliente datos del cliente a actualizar.
     * @param clienteId id del cliente que se va a actualizar
     * @return el cliente actualizado.
     */
    @PutMapping("/{clienteId}")
    @ResponseBody    
    public ResponseEntity<Cliente> updateClient(@RequestBody Cliente cliente, @PathVariable("clienteId") Long clienteId){
        Cliente response = null;
        HttpStatus status = null;
        
            response = clienteService.updateClient(cliente, clienteId);            
            if(response != null) {
	            status = HttpStatus.OK;            
	            return ResponseEntity.status(status).body(response);       

            }else {            	
            	status = HttpStatus.NOT_FOUND;            
	            return ResponseEntity.status(status).body(response);    
            }
            	
       }

    /**
     *<h2>Metodo para eliminar un cliente</h2>
     * @param clienteId id del cliente que se quiere eliminar
     * @return true si el cliente se elimino con exito, false si el cliente no se pudo eliminar.
     */
    @DeleteMapping("/{clienteId}")
    @ResponseBody
    public ResponseEntity<Boolean> deleteClient(@PathVariable("clienteId") Long clienteId){
        boolean response;
        HttpStatus status = null;          
        
        
            response = clienteService.deleteClient(clienteId); 
            
            if(response) {
            status = HttpStatus.OK;
            return ResponseEntity.status(status).body(response);

            }else {
            	status = HttpStatus.BAD_REQUEST;
                return ResponseEntity.status(status).body(response);
            }

    	}
}
