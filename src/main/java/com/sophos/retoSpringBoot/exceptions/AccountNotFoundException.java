package com.sophos.retoSpringBoot.exceptions;


/**
 *<h1>AccountNotFoundException</h1>
 *Esta excepcion es lanzada cuando no se encuentra una cuenta especifica en la base de datos.
 * 
 *@author Wilson David Pachon.
 *@version 1.0.0 2022
 *@since 1.0.0
 */

public class AccountNotFoundException extends RuntimeException{
	/**
	 * 
	 * @param mensaje El mensaje de excepcion que se mostrara al usuario.
	 */
	public AccountNotFoundException(String mensaje) {
		super(mensaje);
	}
	
}
