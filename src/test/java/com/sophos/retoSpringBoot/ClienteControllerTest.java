package com.sophos.retoSpringBoot;


import com.sophos.retoSpringBoot.controller.ClienteController;
import com.sophos.retoSpringBoot.entity.Cliente;
import com.sophos.retoSpringBoot.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.Suite;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

/**
 * <h1>Test de la clase ClienteController</h1>
 * En esta clase se realizan los tests correspondientes para verificar el
 * correcto funcionamiento de cada uno de los metodos definidos en el controlador
 * de cliente.
 *  
 *@author Wilson David Pachon.
 *@version 1.0.0 2022
 *@since 1.0.0
 *
 */

@ExtendWith(MockitoExtension.class)
@Suite
public class ClienteControllerTest {	
	
	
	@InjectMocks
	ClienteController clienteController;	
	
	@Mock
	private ClienteService clienteService;
	
	private ResponseEntity<Cliente> responseEntityClient;
	private ResponseEntity<List<Cliente>> responseEntityListClient;
	
	/**
	 * <h2>test del metodo readClient</h2>
	 * test generado para verificar que al momento de buscar un cliente por id,
	 * retorne el estado http OK.
	 * 
	 * @throws Exception
	 */
	
	@Test
	void testReadClientShouldReturnStatusOK() throws Exception {
		
		Cliente cliente = new Cliente();
		cliente.setClienteId(1L);
		cliente.setPrimerNombre("Wilson");
		cliente.setPrimerApellido("Pachon");
		
		when(clienteService.readClient(cliente.getClienteId())).thenReturn(cliente);
		
		responseEntityClient = clienteController.readClient(cliente.getClienteId());	
		assertEquals(responseEntityClient.getStatusCodeValue(), 200);
		assertEquals(responseEntityClient.getBody().getPrimerNombre(),"Wilson");
		
	}
	
	/**
	 * <h2>test del metodo readClient no encontrado.</h2>
	 * este test verifica que al momento de buscar un cliente por un id que no existe,
	 * retorna el estado 404 NOT_FOUND.
	 * 
	 * @throws Exception
	 */
	@Test
	void testReadClientNullShouldReturnStatusNotFound() throws Exception {
		
		Cliente cliente = new Cliente();
		cliente.setClienteId(1L);		
		
		when(clienteService.readClient(cliente.getClienteId())).thenReturn(null);
		
		responseEntityClient = clienteController.readClient(cliente.getClienteId());
		
		assertEquals(responseEntityClient.getStatusCodeValue(), 404);
		assertNull(responseEntityClient.getBody());		
		
	}	
	
	/**
	 * <h2>test del metodo readAllClients</h2>
	 * este test verifica que al momento de llamar a este metodo y exista almenos un cliente
	 * en la lista, retorne un estado OK.
	 *  
	 * @throws Exception
	 */
	@Test
	void testReadAllClientsShouldReturnStatusOk() throws Exception {
		
		Cliente cliente = new Cliente();
		
		List<Cliente> ListaClientes = new ArrayList<>();
		
		ListaClientes.add(cliente);
				
		when(clienteService.readAllClients()).thenReturn(ListaClientes);
		
		responseEntityListClient = clienteController.readAllClients();
		
		assertEquals(responseEntityListClient.getStatusCodeValue(), 200);
		assertEquals(responseEntityListClient.getBody(), ListaClientes);
		assertEquals(responseEntityListClient.getBody().size(), 1);
		
	} 
	
	/**
	 * <h2>test del metodo readAllClients con lista vacia</h2>
	 * este test verifica que al momento de llamar a este metodo y devuelva una lista vacia,
	 * retorne un estado 404 NOT_FOUND.
	 * 
	 * @throws Exception
	 */
	@Test
	void testReadAllClientsShouldReturnStatusNotFound() throws Exception {
						
		when(clienteService.readAllClients()).thenReturn(null);
		
		responseEntityListClient = clienteController.readAllClients();
		
		assertEquals(responseEntityListClient.getStatusCodeValue(), 404);		
		assertNull(responseEntityListClient.getBody());
	}
	
	
	/**
	 * <h2>test del metodo createClient</h2>
	 * este test verifica que al momento de crear un cliente exitosamente,
	 * retorne el estado 201 Created.
	 * @throws Exception
	 */
	@Test
	void testCreateClientShouldReturnStatusCreated() throws Exception {
		
		Cliente cliente = new Cliente();
		cliente.setClienteId(1L);
		cliente.setPrimerNombre("David");
		
		when(clienteService.createClient(any(Cliente.class))).thenReturn(cliente);
		
		responseEntityClient = clienteController.createClient(cliente);
		
		assertEquals(responseEntityClient.getStatusCodeValue(), 201);
		assertEquals(responseEntityClient.getBody().getPrimerNombre(), cliente.getPrimerNombre());
			
	}
	
	
	/**
	 * <h2>test del metodo createClient Null</h2>
	 * este test verifica que al momento de intentar crear un cliente sin exito,
	 * retorne el estado 400 BAD_REQUEST.
	 * 
	 * @throws Exception
	 */	
	@Test
	void testCreateClientNullShouldReturnStatusBadRequest() throws Exception {		
	
		Cliente cliente = new Cliente();
		when(clienteService.createClient(any(Cliente.class))).thenReturn(null);
		
		responseEntityClient = clienteController.createClient(cliente);
		
		assertEquals(responseEntityClient.getStatusCodeValue(), 400);
		assertNull(responseEntityClient.getBody());
		
	}
	
	/**
	 * <h2>test del metodo updateClient</h2>
	 * con este test se valida que al momento de actualizar un cliente de manera exitosa,
	 * retorne un estado 200 OK.
	 * @throws Exception
	 */
	@Test
	void testUpdateClientShouldReturnStatusOk() throws Exception{
		
		Cliente cliente = new Cliente();
		cliente.setClienteId(1L);
		cliente.setPrimerNombre("Andres");
		
		Cliente updateCliente = new Cliente();
		updateCliente.setClienteId(cliente.getClienteId());
		updateCliente.setPrimerNombre("David");
		
		cliente=updateCliente;		
		
		when(clienteService.updateClient(cliente, cliente.getClienteId())).thenReturn(updateCliente);
		
		responseEntityClient = clienteController.updateClient(cliente, cliente.getClienteId());
		
		assertEquals(responseEntityClient.getStatusCodeValue(), 200);
		assertEquals(updateCliente, cliente);		
		
	}
	
	/**
	 * <h2>test del metodo updateClient no encontrado</h2>
	 * con este test se valida que al momento de intentar actualizar un cliente que no existe,
	 * retorne un estado 404 NOT_FOUND.
	 * @throws Exception
	 */
	
	@Test
	void testUpdateClientNullShouldReturnStatusNotFound() throws Exception{
		
		Cliente cliente = new Cliente();
		cliente.setClienteId(1L);				
		
		when(clienteService.updateClient(cliente, cliente.getClienteId())).thenReturn(null);
		
		responseEntityClient = clienteController.updateClient(cliente, cliente.getClienteId());
		
		assertEquals(responseEntityClient.getStatusCodeValue(), 404);
		assertNull(responseEntityClient.getBody());		
		
	} 
	
	
	/**
	 * <h2>test del metodo deleteClient</h2>
	 * Con este test se verifica que cuando se quiera eliminar un cliente existente, 
	 * retorne true y un estado 200 OK.
	 * @throws Exception
	 */
	@Test
	void testDeleteClientShouldReturnStatusOk() throws Exception {
		Cliente cliente = new Cliente();
		cliente.setClienteId(1L);
		
		when(clienteService.deleteClient(cliente.getClienteId())).thenReturn(true);
		
		ResponseEntity<Boolean> result = clienteController.deleteClient(cliente.getClienteId());
		
		assertEquals(result.getStatusCodeValue(), 200);
		assertTrue(result.getBody());	
		
	}
	
	/**
	 * <h2>test del metodo deleteClient Null</h2>
	 * este test verifica que cuando se intente eliminar un cliente sin exito,
	 * retorne false y estado 400 BAD_REQUEST.
	 * 
	 * @throws Exception
	 */	
	@Test
	void testDeleteClientNullShouldReturnStatusBadRequest() throws Exception {
		Cliente cliente = new Cliente();
		cliente.setClienteId(1L);
		
		when(clienteService.deleteClient(cliente.getClienteId())).thenReturn(false);
		
		ResponseEntity<Boolean> result = clienteController.deleteClient(cliente.getClienteId());
		
		assertEquals(result.getStatusCodeValue(), 400);		
		assertFalse(result.getBody());
	}	
	
	
}
