package com.sophos.retoSpringBoot.exceptions;
/**
 *<h1>MovementNotFoundException</h1>
 *Esta excepcion es lanzada cuando no se encuentra un movimiento especifico en la base de datos.
 * 
 *@author Wilson David Pachon.
 *@version 1.0.0 2022
 *@since 1.0.0
 */
public class MovementNotFoundException extends RuntimeException{
	/**
	 * 
	 * @param mensaje El mensaje de excepcion que se mostrara al usuario.
	 */
	public MovementNotFoundException(String mensaje) {
		super(mensaje);
	}
	
}
