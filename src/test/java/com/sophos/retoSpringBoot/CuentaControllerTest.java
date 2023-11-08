package com.sophos.retoSpringBoot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.Suite;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.sophos.retoSpringBoot.controller.CuentaController;
import com.sophos.retoSpringBoot.entity.Cliente;
import com.sophos.retoSpringBoot.entity.Cuenta;
import com.sophos.retoSpringBoot.service.CuentaService;
/**
 * <h1>Test de la clase CuentaController</h1>
 * En esta clase se realizan los tests correspondientes para verificar el
 * correcto funcionamiento de cada uno de los metodos definidos en el controlador
 * de cuenta.
 *  
 *@author Wilson David Pachon.
 *@version 1.0.0 2022
 *@since 1.0.0
 *
 */
@ExtendWith(MockitoExtension.class)
@Suite
public class CuentaControllerTest {
	
	@InjectMocks
	CuentaController cuentaController;
	
	@Mock
	private CuentaService cuentaService;
	
	private ResponseEntity<Cuenta> responseEntityAccount;
	private ResponseEntity<List<Cuenta>> responseEntityListAccount;
	
	/**
	 * <h2>test del metodo readAccount</h2>
	 * test generado para verificar que al momento de buscar una cuenta por id,
	 * retorne el estado http OK.
	 * 
	 * @throws Exception
	 */
	@Test
	void testReadAccountShouldReturnStatusOk() throws Exception{
		
		Cuenta cuenta = new Cuenta();
		cuenta.setCuentaId(1L);
		cuenta.setEstadoCuenta("activo");
		
		when(cuentaService.readAccount(cuenta.getCuentaId())).thenReturn(cuenta);
		
		responseEntityAccount = cuentaController.readAccount(cuenta.getCuentaId());
		
		assertEquals(responseEntityAccount.getStatusCodeValue(), 200);
		assertEquals(responseEntityAccount.getBody().getEstadoCuenta(), "activo");
		
	}
	
	/**
	 * <h2>test del metodo readAccount no encontrado.</h2>
	 * este test verifica que al momento de buscar una cuenta por un id que no existe,
	 * retorna el estado 404 NOT_FOUND.
	 * 
	 * @throws Exception
	 */
	@Test
	void testReadAccountNullShouldReturnStatusNotFound() throws Exception {
		
		Cuenta cuenta = new Cuenta();
		cuenta.setCuentaId(1L);
		
		when(cuentaService.readAccount(cuenta.getCuentaId())).thenReturn(null);
		
		responseEntityAccount = cuentaController.readAccount(cuenta.getCuentaId());
		
		assertEquals(responseEntityAccount.getStatusCodeValue(), 404);
		assertNull(responseEntityAccount.getBody());		
		
	}	
	
	/**
	 * <h2>test del metodo readAccountsByClient</h2>
	 * este test verifica que al momento de consultar las cuentas que tiene un cliente,
	 * retorne un estado 200 Ok.
	 * @throws Exception
	 */
	
	@Test
	void testReadAccountsByClientShouldReturnStatusOk() throws Exception {
		
		Cliente cliente = new Cliente();
		cliente.setClienteId(1L);
		
		Cuenta cuenta = new Cuenta();
		
		List<Cuenta> ListaCuentas = new ArrayList<>();
		
		ListaCuentas.add(cuenta);
				
		when(cuentaService.readAccountsByClient(cliente.getClienteId())).thenReturn(ListaCuentas);
		
		responseEntityListAccount = cuentaController.readAccountsByClient(cliente.getClienteId());
		
		assertEquals(responseEntityListAccount.getStatusCodeValue(), 200);
		assertEquals(responseEntityListAccount.getBody(), ListaCuentas);
		assertEquals(responseEntityListAccount.getBody().size(), 1);
		
	} 
	
	/**
	 * <h2>test readAccountsByClient Not Found</h2>
	 * este test verifica que al momento de consultar las cuentas que tiene un cliente, sino
	 * encuentra ninguna, retorne el estado 404 NOT_FOUND.
	 * @throws Exception
	 */	
	@Test
	void testReadAccountsByClientShouldReturnStatusNotFound() throws Exception {
		
		Cliente cliente = new Cliente();
		cliente.setClienteId(1L);
						
		when(cuentaService.readAccountsByClient(cliente.getClienteId())).thenReturn(null);
		
		responseEntityListAccount = cuentaController.readAccountsByClient(cliente.getClienteId());
		
		assertEquals(responseEntityListAccount.getStatusCodeValue(), 404);			
		assertNull(responseEntityListAccount.getBody());
		
	} 
	
	/**
	 * <h2>test del metodo createAccount</h2>
	 * este test verifica que al momento de crear una cuenta exitosamente,
	 * retorne el estado 201 Created.
	 * @throws Exception
	 */
	@Test
	void testCreateAccountShouldReturnStatusCreated() throws Exception {
		
		Cuenta cuenta = new Cuenta();
		cuenta.setCuentaId(1L);
		cuenta.setEstadoCuenta("activo");
		
		when(cuentaService.createAccount(any(Cuenta.class))).thenReturn(cuenta);
		
		responseEntityAccount = cuentaController.createAccount(cuenta);
		
		assertEquals(responseEntityAccount.getStatusCodeValue(), 201);
		assertEquals(responseEntityAccount.getBody().getEstadoCuenta(), "activo");
			
	}
	
	/**
	 * <h2>test del metodo createAccount Null</h2>
	 * este test verifica que al momento de intentar crear una cuenta sin exito,
	 * retorne el estado 400 BAD_REQUEST.
	 * 
	 * @throws Exception
	 */	
	@Test
	void testCreateAccountNullShouldReturnStatusBadRequest() throws Exception {
		
		Cuenta cuenta = new Cuenta();		
		
		when(cuentaService.createAccount(any(Cuenta.class))).thenReturn(null);
		
		responseEntityAccount = cuentaController.createAccount(cuenta);
		
		assertEquals(responseEntityAccount.getStatusCodeValue(), 400);
		assertNull(responseEntityAccount.getBody());
			
	}
	
	/**
	 * <h2>test del metodo updateAccount</h2>
	 * con este test se valida que al momento de actualizar una cuenta de manera exitosa,
	 * retorne un estado 200 OK.
	 * @throws Exception
	 */
	@Test
	void testUpdateAccountShouldReturnStatusOk() throws Exception{
		
		Cuenta cuenta = new Cuenta();
		cuenta.setCuentaId(1L);
		cuenta.setEstadoCuenta("activo");		
		
		Cuenta updateCuenta = new Cuenta();
		updateCuenta.setCuentaId(cuenta.getCuentaId());
		updateCuenta.setEstadoCuenta("inactivo");
		
		cuenta = updateCuenta;		
		
		when(cuentaService.updateAccount(cuenta, cuenta.getCuentaId())).thenReturn(updateCuenta);
		
		responseEntityAccount = cuentaController.updateAccount(cuenta, cuenta.getCuentaId());
		
		assertEquals(responseEntityAccount.getStatusCodeValue(), 200);
		assertEquals(updateCuenta, cuenta);		
		
	}
	
	/**
	 * <h2>test del metodo updateAccount no encontrado</h2>
	 * con este test se valida que al momento de intentar actualizar una cuenta que no existe,
	 * retorne un estado 404 NOT_FOUND.
	 * @throws Exception
	 */
	@Test
	void testUpdateAccountNullShouldReturnStatusNotFound() throws Exception{
		
		Cuenta cuenta = new Cuenta();
		cuenta.setCuentaId(1L);
				
		when(cuentaService.updateAccount(cuenta, cuenta.getCuentaId())).thenReturn(null);
		
		responseEntityAccount = cuentaController.updateAccount(cuenta, cuenta.getCuentaId());
		
		assertEquals(responseEntityAccount.getStatusCodeValue(), 404);
		assertNull(responseEntityAccount.getBody());
		
	}
	
	/**
	 * <h2>test del metodo deleteAccount</h2>
	 * Con este test se verifica que cuando se quiera eliminar una cuenta existente, 
	 * retorne true y un estado 200 OK.
	 * @throws Exception
	 */
	@Test
	void testDeleteAccountShouldReturnStatusOk() throws Exception {
		Cuenta cuenta = new Cuenta();
		cuenta.setCuentaId(1L);
		
		when(cuentaService.deleteAccount(cuenta.getCuentaId())).thenReturn(true);
		
		ResponseEntity<Boolean> result = cuentaController.deleteAccount(cuenta.getCuentaId());
		
		assertEquals(result.getStatusCodeValue(), 200);
		assertTrue(result.getBody());	
		
	}
	
	/**
	 * <h2>test del metodo deleteAccount Null</h2>
	 * este test verifica que cuando se intente eliminar una cuenta sin exito,
	 * retorne false y estado 400 BAD_REQUEST.
	 * 
	 * @throws Exception
	 */	
	@Test
	void testDeleteAccountNullShouldReturnStatusBadRequest() throws Exception {
		Cuenta cuenta = new Cuenta();
		cuenta.setCuentaId(1L);
		
		when(cuentaService.deleteAccount(cuenta.getCuentaId())).thenReturn(false);
		
		ResponseEntity<Boolean> result = cuentaController.deleteAccount(cuenta.getCuentaId());
		
		assertEquals(result.getStatusCodeValue(), 400);
		assertFalse(result.getBody());	
		
	}		
	

}
