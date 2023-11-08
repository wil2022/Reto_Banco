package com.sophos.retoSpringBoot.exceptions;

/**
 *<h1>MovementActiveStateException</h1>
 *Esta excepcion es lanzada cuando el estado de un movimiento es activo y por lo
 *tanto no se puede eliminar.
 * 
 *@author Wilson David Pachon.
 *@version 1.0.0 2022
 *@since 1.0.0
 */
public class MovementActiveStateException extends RuntimeException{	
	/**
	 * 
	 * @param mensaje El mensaje de excepcion que se mostrara al usuario.
	 */	
	public MovementActiveStateException(String mensaje) {
		super(mensaje);
	}
	
}
