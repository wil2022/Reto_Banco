package com.sophos.retoSpringBoot;

import com.sophos.retoSpringBoot.entity.Cliente;
import com.sophos.retoSpringBoot.entity.Cuenta;
import com.sophos.retoSpringBoot.exceptions.ClientNotFound;
import com.sophos.retoSpringBoot.exceptions.ClientWithAccountsException;
import com.sophos.retoSpringBoot.exceptions.UnderAgeClientException;
import com.sophos.retoSpringBoot.repository.AccountRepository;
import com.sophos.retoSpringBoot.repository.ClientRepository;
import com.sophos.retoSpringBoot.service.implementations.ClienteServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;

/**
 * <h1>Test de la clase ClienteServiceImp</h1>
 * En esta clase se realizan los tests correspondientes para verificar el
 * correcto funcionamiento de la logica de negocio de cada uno de los metodos 
 * definidos en la capa de Servicio de cliente.
 * 
 *@author Wilson David Pachon.
 *@version 1.0.0 2022
 *@since 1.0.0
 *
 */

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClienteServiceImpTest {	
	
	@Mock
	AccountRepository accountRepository;
	
	@Mock
	ClientRepository clientRepository;
	
	@Autowired
	@InjectMocks
	ClienteServiceImp clienteServiceImp;
	
	/**
	 * Inicializa los objetos con la anotacion Mockito.
	 */
	@BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    } 

	/**
	 * <h2>test del metodo isOfLegalAge</h2>
	 * este test permite validar el metodo cuando se le pasa una fecha de nacimiento
	 * correspondiente a un cliente mayor de edad, el cual retorna un true.
	 */
	@Test
	void isOfLegalAgeShouldReturnTrue() {		
		
		LocalDate fechaNacimiento = LocalDate.of(1980, 12, 27);
		
		boolean resultado = clienteServiceImp.isOfLegalAge(fechaNacimiento);
		
		Assertions.assertTrue(resultado);
	}
	
	/**
	 * <h2>test del metodo isOfLegalAge False</h2>
	 * este test permite validar el metodo cuando se le pasa una fecha de nacimiento
	 * correspondiente a un cliente menor de edad, el cual retorna un false.
	 */
	@Test
	void NotOfLegalAgeShouldReturnFalse() {		

		LocalDate fechaNacimiento = LocalDate.of(2010, 10, 30);
		
		boolean resultado = clienteServiceImp.isOfLegalAge(fechaNacimiento);
		
		Assertions.assertFalse(resultado);
	}
	
	
	/**
	 * <h2>test del metodo createClient</h2>
	 * con este test se valida que al momento de crear un cliente mayor de edad,
	 * retorne un objeto de tipo cliente.
	 */
	@Test
	void testCreateClientShouldReturnClient() {		
		
		Cliente mockCliente = new Cliente();			
		
		mockCliente.setClienteId(1L);		
		mockCliente.setFechaNacimiento(LocalDate.of(1980, 05, 24));				
		
		Mockito.when(clientRepository.save(Mockito.any(Cliente.class))).thenReturn(mockCliente);		
		
		Cliente cliente = clienteServiceImp.createClient(mockCliente);		
		
		Assertions.assertEquals(mockCliente, cliente);
	}
	
	/**
	 * <h2>test del metodo createClient menor de edad</h2>
	 * con este test se valida que al momento de crear un cliente menor de edad,
	 * retorne una excepcion del tipo UnderAgeClientException.
	 */
	@Test
	void testCreateClientSholudReturnException() {		
		
		Cliente mockCliente = new Cliente();			
		
		mockCliente.setClienteId(1L);		
		mockCliente.setFechaNacimiento(LocalDate.of(2012, 06, 15));				
		
		lenient().when(clientRepository.save(Mockito.any(Cliente.class))).thenReturn(mockCliente);				
		
		assertThrows(UnderAgeClientException.class, ()->{clienteServiceImp.createClient(mockCliente);});
	}
	
	/**
	 * <h2>test del metodo readClient</h2>
	 * el test verifica que cuando se quiera consultar un cliente por un id existente, 
	 * retorne el cliente correspondiente a ese id.
	 */
	@Test
	void testReadClientShouldReturnClient() {
		
		Cliente mockCliente = new Cliente();			
		
		mockCliente.setClienteId(1L);	
		
		Mockito.when(clientRepository.existsById(mockCliente.getClienteId())).thenReturn(true);
		Mockito.when(clientRepository.findByClienteId(mockCliente.getClienteId())).thenReturn(mockCliente);		
		
		Cliente cliente = clienteServiceImp.readClient(mockCliente.getClienteId());		
		
		Assertions.assertEquals(mockCliente, cliente);		
		
	}	
	
	/**
	 * <h2>test readClient NotFound</h2>
	 * este test valida que cuando se quiere consultar un cliente por un id que no existe, 
	 * retorna una excepcion del tipo ClientNotFound.
	 * 
	 */
	@Test
	void testClientNotFoundShouldReturnException() {
		
		Cliente mockCliente = new Cliente();			
		
		mockCliente.setClienteId(1L);		
		
		lenient().when(clientRepository.existsById(mockCliente.getClienteId())).thenReturn(false);
			
		assertThrows(ClientNotFound.class, ()->{clienteServiceImp.readClient(mockCliente.getClienteId());});
		
	}	
	
	/**
	 * <h2>test del metodo readAllClients</h2>
	 * el test valida que cuando se llame a este metodo retorne una lista de clientes.
	 */
	@Test
	void testReadAllClientsShouldReturnClientsList() {
		
		Cliente mockCliente = new Cliente();
		
		ArrayList<Cliente> mockListaClientes = new ArrayList<>();
			
		mockListaClientes.add(mockCliente);
				
		Mockito.when(clientRepository.findAll()).thenReturn(mockListaClientes);
		
		List<Cliente> clientes = clienteServiceImp.readAllClients();
		
		Assertions.assertEquals(mockListaClientes, clientes);
		
	} 
	
	/**
	 * <h2>test readAllClients Empty</h2>
	 * test que valida cuando se llama a este metodo pero no hay ningun cliente en la lista,
	 * retorna una excepcion de tipo ClientNotFound.
	 * 
	 */
	@Test
	void testReadEmptyClientListShouldReturnException() {			
		
		ArrayList<Cliente> mockListaClientes = new ArrayList<>();		
				
		Mockito.when(clientRepository.findAll()).thenReturn(mockListaClientes);		
		
		assertThrows(ClientNotFound.class, ()->{clienteServiceImp.readAllClients();});
		
	}
	
	/**
	 * <h2>test metodo updateClient</h2>
	 * este test valida que al momento de actualizar un cliente,
	 * retorne ese cliente con los campos que se han actualizado.
	 */
	@Test
	void testUpdateClientShouldReturnClient() {
		
		Cliente mockCliente = new Cliente();			
		
		mockCliente.setClienteId(1L);	
		mockCliente.setFechaNacimiento(LocalDate.of(1980, 05, 24));
		
		Mockito.when(clientRepository.existsById(mockCliente.getClienteId())).thenReturn(true);
		Mockito.when(clientRepository.findByClienteId(mockCliente.getClienteId())).thenReturn(mockCliente);	
		
		mockCliente.setFechaNacimiento(LocalDate.of(1995, 05, 24));
		
		Mockito.when(clientRepository.save(Mockito.any(Cliente.class))).thenReturn(mockCliente);
		
		Cliente cliente = clienteServiceImp.updateClient(mockCliente, mockCliente.getClienteId());		
		
		Assertions.assertEquals(mockCliente, cliente);
	}
	
	/**
	 * <h2>test updateClient Not Found</h2>
	 * este test valida que cuando se quiera actualizar un cliente que no existe,
	 * se genera una excepcion de tipo ClientNotFound.
	 */
	@Test
	void testUpdateClientNotFoundShouldReturnException() {
		
		Cliente mockCliente = new Cliente();			
		
		mockCliente.setClienteId(1L);			
		
		Mockito.when(clientRepository.existsById(mockCliente.getClienteId())).thenReturn(false);
		
		assertThrows(ClientNotFound.class, ()->{clienteServiceImp.updateClient(mockCliente, mockCliente.getClienteId());});
	}
	
	/**
	 * <h2>test updateClient menor de edad</h2>
	 * test para validar que al momento de actualizar la fecha de nacimiento de un cliente
	 * por una que corresponda a un menor de edad, genere una excepcion de tipo UnderAgeClientException.
	 */
	@Test
	void testUpdateClientUnderAgeShouldReturnException() {
		
		Cliente mockCliente = new Cliente();			
		
		mockCliente.setClienteId(1L);	
		mockCliente.setFechaNacimiento(LocalDate.of(1980, 05, 24));
		
		Mockito.when(clientRepository.existsById(mockCliente.getClienteId())).thenReturn(true);
		Mockito.when(clientRepository.findByClienteId(mockCliente.getClienteId())).thenReturn(mockCliente);	
		
		mockCliente.setFechaNacimiento(LocalDate.of(2011, 05, 24));
		
		lenient().when(clientRepository.save(Mockito.any(Cliente.class))).thenReturn(mockCliente);				
		
		assertThrows(UnderAgeClientException.class, ()->{clienteServiceImp.updateClient(mockCliente, mockCliente.getClienteId());});
	}	
	
	/**
	 * <h2>test del metodo validateClientAccounts</h2>
	 * este test valida que al momento de buscar la lista de cuentas asociadas a un cliente,
	 * retrone true.
	 */
	@Test
	void validateClientWithAccountsShouldReturnTrue() {
		
		Cliente mockCliente = new Cliente();
		
		Cuenta mockCuenta = new Cuenta();
		
		List<Cuenta> mockListaCuentas = new ArrayList<>();
		
		mockListaCuentas.add(mockCuenta);
		
		mockCliente.setClienteId(1L);	
		
		Mockito.when(accountRepository.findByCliente(clientRepository.findByClienteId(mockCliente.getClienteId()))).thenReturn(mockListaCuentas);
		
		boolean resultado = clienteServiceImp.validateClientAccounts(mockCliente.getClienteId());
		
		Assertions.assertTrue(resultado);
	}
	
	
	/**
	 * <h2>test validateClientWithoutAccounts</h2>
	 * este test valida que al momento de consultar las cuentas a un cliente si este no tiene,
	 * retorne false.
	 */
	@Test
	void validateClientWithoutAccountsShouldReturnFalse() {
		
		Cliente mockCliente = new Cliente();	
		
		List<Cuenta> mockListaCuentas = new ArrayList<>();		
		
		mockCliente.setClienteId(1L);	
		
		Mockito.when(accountRepository.findByCliente(clientRepository.findByClienteId(mockCliente.getClienteId()))).thenReturn(mockListaCuentas);
		
		boolean resultado = clienteServiceImp.validateClientAccounts(mockCliente.getClienteId());
		
		Assertions.assertFalse(resultado);
	}
	
	/**
	 * <h2>test del metodo deleteClient</h2>
	 * este test valida que al momento de eliminar un cliente por un id que exista,
	 * retorne true.
	 */
	@Test
	void testDeleteClientShouldReturnTrue() {
		
		Cliente mockCliente = new Cliente();
		
		mockCliente.setClienteId(1L);
		
		Mockito.when(clientRepository.existsById(mockCliente.getClienteId())).thenReturn(true);
		
		boolean resultado = clienteServiceImp.deleteClient(mockCliente.getClienteId());
		
		Assertions.assertTrue(resultado);
		
	}
	
	/**
	 * <h2>test deleteClient Not Found</h2>
	 * con este test se valida que cuando se quiera eliminar un cliente que no existe,
	 * genere una excepcion de tipo ClientNotFound.
	 */
	@Test
	void testDeleteClientNotFoundShouldReturnException() {
		
		Cliente mockCliente = new Cliente();
		
		mockCliente.setClienteId(1L);
		
		Mockito.when(clientRepository.existsById(mockCliente.getClienteId())).thenReturn(false);		
		
		assertThrows(ClientNotFound.class, ()->{clienteServiceImp.deleteClient(mockCliente.getClienteId());});
		
	}
	
	/**
	 * <h2>test deleteClient con cuentas</h2>
	 * este test valida que cuando se quiera eliminar un cliente con cuentas asociadas,
	 * genere una excepcion de tipo ClientWithAccountsException.
	 */
	@Test
	void testDeleteClientWithAccountsShouldReturnException() {
		
		Cliente mockCliente = new Cliente();
		
		Cuenta mockCuenta = new Cuenta();
		
		List<Cuenta> mockListaCuentas = new ArrayList<>();
		
		mockListaCuentas.add(mockCuenta);
		
		mockCliente.setClienteId(1L);
		
		Mockito.when(clientRepository.existsById(mockCliente.getClienteId())).thenReturn(true);
		
		Mockito.when(accountRepository.findByCliente(clientRepository.findByClienteId(mockCliente.getClienteId()))).thenReturn(mockListaCuentas);		
		
		assertThrows(ClientWithAccountsException.class, ()->{clienteServiceImp.deleteClient(mockCliente.getClienteId());});
		
	} 
	
}

	
