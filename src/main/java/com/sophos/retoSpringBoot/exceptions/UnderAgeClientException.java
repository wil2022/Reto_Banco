package com.sophos.retoSpringBoot.exceptions;
/**
 *<h1>UnderAgeClientException</h1>
 *Esta excepcion es lanzada cuando se quiere crear o actualizar un cliente menor de edad.
 * 
 *@author Wilson David Pachon.
 *@version 1.0.0 2022
 *@since 1.0.0
 */
public class UnderAgeClientException extends RuntimeException{
	/**
	 * 
	 * @param mensaje El mensaje de excepcion que se mostrara al usuario.
	 */
	public UnderAgeClientException (String mensaje) {
		super(mensaje);
	}
	
}
