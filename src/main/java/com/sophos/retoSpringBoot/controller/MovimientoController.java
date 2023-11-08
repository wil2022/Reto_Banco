package com.sophos.retoSpringBoot.controller;

import com.sophos.retoSpringBoot.entity.Movimiento;
import com.sophos.retoSpringBoot.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * <h1>Controlador Movimiento</h1>
 *Es el encargado de exponer la funcionalidad de la clase Movimiento
 *para que pueda ser consumida por el frontend.
 *
 *@author Wilson David Pachon.
 *@version 1.0.0 2022
 *@since 1.0.0
 *
 */
@RestController
@RequestMapping("/api/movimiento")
//@CrossOrigin(origins = "http://localhost:4200/")
@CrossOrigin(origins = "http://localhost:3000/")

public class MovimientoController {
    /**
     * inyeccion de la dependencia MovimientoService
     */
    @Autowired
    MovimientoService movimientoService;

    /**
     * <h2>Metodo para leer un movimiento</h2>
     * @param movimientoId id del movimiento a consultar
     * @return movimiento asociado al id pasado como parametro
     */
    @GetMapping("/{movimientoId}")
    @ResponseBody
    public ResponseEntity<Movimiento> readMovement(@PathVariable("movimientoId") Long movimientoId) {
        Movimiento response = null;
        HttpStatus status = null;
        
            response = movimientoService.readMovement(movimientoId);
            if(response != null) {
	            status = HttpStatus.OK;     
	            return ResponseEntity.status(status).body(response);
            }else {
            	status = HttpStatus.NOT_FOUND;            
	            return ResponseEntity.status(status).body(response); 
            }
    }

    /**
     * <h2>Metodo para consultar los movimientos asociados a una cuenta</h2>
     * @param cuentaId id de la cuenta a la cual se le va consultar los movimientos
     * @return lista de movimientos asociados a la cuenta consultada.
     */

    @GetMapping("/cuenta/{cuentaId}")
    @ResponseBody
    public ResponseEntity<List<Movimiento>> readMovementsByAccount(@PathVariable("cuentaId") Long cuentaId) {
        List<Movimiento> response = null;
        HttpStatus status = null;
        
            response = movimientoService.readMovementsByAccount(cuentaId); 
            
            if(response != null) {
	            status = HttpStatus.OK;            
	            return ResponseEntity.status(status).body(response); 
            }else {
	        	status = HttpStatus.NOT_FOUND;            
	            return ResponseEntity.status(status).body(response);
            }

    }

    /**
     * <h2>Metodo para crear un movimiento</h2>
     * @param movimiento el objeto tipo movimiento que se va a crear
     * @return objeto de tipo movimiento.
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<Movimiento> createMovement(@RequestBody Movimiento movimiento) {
        Movimiento response = null;
        HttpStatus status = null;        
        
            response = movimientoService.createMovement(movimiento);            
            if(response != null) {		
    			status = HttpStatus.CREATED;		                
    			return ResponseEntity.status(status).body(response);
    			
    		}else {
    			status = HttpStatus.BAD_REQUEST;               
                return ResponseEntity.status(status).body(response);  
    		}           
    }

    /**
     * <h2>Metodo para actualizar un movimiento</h2>
     * @param movimiento datos del movimiento que se va a actualizar
     * @param movimientoId id del movimiento que se va a actualizar
     * @return movimiento actualizado.
     */
        @PutMapping("/{movimientoId}")
        @ResponseBody
        public ResponseEntity<Movimiento> updateMovement(@RequestBody Movimiento movimiento, @PathVariable("movimientoId") Long movimientoId){
            Movimiento response = null;
            HttpStatus status = null;
            
                response = movimientoService.updateMovement(movimiento, movimientoId);                
                if(response != null) {
                    status = HttpStatus.OK;                
                    return ResponseEntity.status(status).body(response);            
                }else {
                	status = HttpStatus.NOT_FOUND;            
    	            return ResponseEntity.status(status).body(response);                 
                }          
        
        }

    /**
     * <h2>Metodo para eliminar un movimiento</h2>
     * @param movimientoId id del movimiento que se va a eliminar
     * @return true si el movimiento se elimino, false si no se pudo eliminar.
     */
    @DeleteMapping("/{movimientoId}")
    @ResponseBody
    public ResponseEntity<Boolean> deleteMovement(@PathVariable("movimientoId") Long movimientoId){
        boolean response;
        HttpStatus status = null;        
        
        response = movimientoService.deleteMovement(movimientoId);            
        
        if(response == true) {            
            status = HttpStatus.OK;                
            return ResponseEntity.status(status).body(true);      
        }else {
	    	status = HttpStatus.BAD_REQUEST;                
	        return ResponseEntity.status(status).body(response);    
		    }       

    }

}

