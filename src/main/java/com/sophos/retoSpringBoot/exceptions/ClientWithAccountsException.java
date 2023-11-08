package com.sophos.retoSpringBoot.exceptions;
/**
 *<h1>ClientWithAccountsException</h1>
 *Esta excepcion es lanzada cuando un cliente tiene cuentas asociadas y por lo
 *tanto no se puede eliminar.
 * 
 *@author Wilson David Pachon.
 *@version 1.0.0 2022
 *@since 1.0.0
 */
public class ClientWithAccountsException extends RuntimeException{
	/**
	 * 
	 * @param mensaje El mensaje de excepcion que se mostrara al usuario.
	 */	
	public ClientWithAccountsException(String mensaje) {
		super(mensaje);
	}
	
}
