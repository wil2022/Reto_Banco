package com.sophos.retoSpringBoot.exceptions;
/**
 *<h1>AccountWithMovementsException</h1>
 *Esta excepcion es lanzada cuando una cuenta tiene movimientos asociados y por lo
 *tanto no se puede eliminar.
 * 
 *@author Wilson David Pachon.
 *@version 1.0.0 2022
 *@since 1.0.0
 */
public class AccountWithMovementsException extends RuntimeException{
	/**
	 * 
	 * @param mensaje El mensaje de excepcion que se mostrara al usuario.
	 */
	public AccountWithMovementsException(String mensaje) {
		super(mensaje);
	}
		

}
