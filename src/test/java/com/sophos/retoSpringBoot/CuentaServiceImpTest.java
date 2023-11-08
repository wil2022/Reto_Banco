package com.sophos.retoSpringBoot;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.lenient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.sophos.retoSpringBoot.entity.Cliente;
import com.sophos.retoSpringBoot.entity.Cuenta;
import com.sophos.retoSpringBoot.entity.Movimiento;
import com.sophos.retoSpringBoot.exceptions.AccountNotFoundException;
import com.sophos.retoSpringBoot.exceptions.AccountWithMovementsException;
import com.sophos.retoSpringBoot.exceptions.ClientNotFound;
import com.sophos.retoSpringBoot.repository.AccountRepository;
import com.sophos.retoSpringBoot.repository.ClientRepository;
import com.sophos.retoSpringBoot.repository.MovementRepository;
import com.sophos.retoSpringBoot.service.implementations.CuentaServiceImp;

/**
 * <h1>Test de la clase CuentaServiceImp</h1>
 * En esta clase se realizan los tests correspondientes para verificar el
 * correcto funcionamiento de la logica de negocio de cada uno de los metodos 
 * definidos en la capa de Servicio de cuenta.
 * 
 *@author Wilson David Pachon.
 *@version 1.0.0 2022
 *@since 1.0.0
 *
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CuentaServiceImpTest {
	
	
	@Mock
	AccountRepository accountRepository;
	
	@Mock
	ClientRepository clientRepository;
	
	@Mock
	MovementRepository movementRepository;
	
	@Autowired
	@InjectMocks
	CuentaServiceImp cuentaServiceImp;
	
	/**
	 * Inicializa los objetos con la anotacion Mockito.
	 */
	@BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
    }
	
	/**
	 * <h2>test del metodo createAccount</h2>
	 * con este test se valida que al momento de crear una cuenta asociada a un cliente existente,
	 * retorne un objeto de tipo cuenta.
	 */
	@Test
	void testCreateAccountShouldReturnAccount() {
		
		Cliente mockCliente = new Cliente();
		mockCliente.setClienteId(1L);
		mockCliente.setFechaNacimiento(LocalDate.of(1980, 05, 24));
		
		Cuenta mockCuenta = new Cuenta();
		mockCuenta.setCliente(mockCliente);
		mockCuenta.setFechaCreacion(LocalDate.now());
		mockCuenta.setUsuarioCreacion("wilsonp");
		
		Mockito.when(clientRepository.findById(mockCuenta.getCliente().getClienteId())).thenReturn(Optional.of(mockCliente));
		Mockito.when(accountRepository.save(Mockito.any(Cuenta.class))).thenReturn(mockCuenta);
		
		Cuenta cuenta = cuentaServiceImp.createAccount(mockCuenta);		
		
		Assertions.assertEquals(mockCuenta, cuenta);
	}
	
	/**
	 * <h2>test del metodo createAccount</h2>
	 * con este test se valida que al momento de crear una cuenta asociada a un cliente que no existe,
	 * retorne una excepcion de tipo ClientNotFound.
	 */
	@Test
	void testCreateAccountShouldReturnException() {
		
		Cliente mockCliente = new Cliente();
		mockCliente.setClienteId(1L);		
		
		Cuenta mockCuenta = new Cuenta();
		mockCuenta.setCliente(mockCliente);		
		
		lenient().when(accountRepository.save(Mockito.any(Cuenta.class))).thenReturn(mockCuenta);
		
		assertThrows(ClientNotFound.class, ()->{cuentaServiceImp.createAccount(mockCuenta);});
				
	}
	
	
	/**
	 * <h2>test del metodo readAccount</h2>
	 * el test verifica que cuando se quiera consultar una cuenta por un id existente, 
	 * retorne la cuenta correspondiente a ese id.
	 */
	@Test
	void testReadAccountShouldReturnAccount() {
		
		Cuenta mockCuenta = new Cuenta();					
		
		mockCuenta.setCuentaId(1L);
		
		Mockito.when(accountRepository.existsById(mockCuenta.getCuentaId())).thenReturn(true);
		Mockito.when(accountRepository.findByCuentaId(mockCuenta.getCuentaId())).thenReturn(mockCuenta);		
		
		Cuenta cuenta = cuentaServiceImp.readAccount(mockCuenta.getCuentaId());	
		
		Assertions.assertEquals(mockCuenta, cuenta);		
		
	}
	
	
	/**
	 * <h2>test readAccount NotFound</h2>
	 * este test valida que cuando se quiere consultar una cuenta por un id que no existe, 
	 * genere una excepcion del tipo AccountNotFoundException.
	 * 
	 */
	@Test
	void testAccountNotFoundShouldReturnException() {
		
		Cuenta mockCuenta = new Cuenta();
				
		mockCuenta.setCuentaId(1L);
				
		lenient().when(accountRepository.existsById(mockCuenta.getCuentaId())).thenReturn(false);		
			
		assertThrows(AccountNotFoundException.class, ()->{cuentaServiceImp.readAccount(mockCuenta.getCuentaId());});
		
	}	
	
	/**
	 * <h2>test del metodo readAccountsByClient</h2>
	 * este test verifica que al momento de consultar las cuentas asociadas a un cliente existente,
	 * retorne una lista de cuentas.
	 */
	@Test
	void testReadAccountsByClientShouldReturnAccountsList() {
		
		Cliente mockCliente = new Cliente();	
		
		Cuenta mockCuenta = new Cuenta();
		
		List<Cuenta> mockListaCuentas = new ArrayList<>();
		
		mockListaCuentas.add(mockCuenta);
		
		mockCliente.setClienteId(1L);
		
		Mockito.when(clientRepository.existsById(mockCliente.getClienteId())).thenReturn(true);
		Mockito.when(accountRepository.findByCliente(clientRepository.findByClienteId(mockCliente.getClienteId()))).thenReturn(mockListaCuentas);
		
		List<Cuenta> cuentas = cuentaServiceImp.readAccountsByClient(mockCliente.getClienteId());
		
		Assertions.assertEquals(mockListaCuentas, cuentas);
	}
	
	/**
	 * <h2>test del metodo readAccountsByClient</h2>
	 * este test verifica que al momento de consultar las cuentas asociadas a un cliente que no existe,
	 * genere una excepcion de tipo ClientNotFound.
	 */
	@Test
	void ReadAccountsByClientNotFoundShouldReturnException() {
		
		Cliente mockCliente = new Cliente();
		mockCliente.setClienteId(1L);
		
		Mockito.when(clientRepository.existsById(mockCliente.getClienteId())).thenReturn(false);
		
		assertThrows(ClientNotFound.class, ()->{cuentaServiceImp.readAccountsByClient(mockCliente.getClienteId());});
	}
	
	/**
	 * <h2>test metodo updateAccount</h2>
	 * este test valida que al momento de actualizar una cuenta,
	 * retorne esa cuenta con los campos que se han actualizado.
	 */
	@Test
	void testUpdateAccountShouldReturnAccount() {
		
		Cuenta mockCuenta = new Cuenta();
		mockCuenta.setCuentaId(1L);
		
		Mockito.when(accountRepository.existsById(mockCuenta.getCuentaId())).thenReturn(true);
		Mockito.when(accountRepository.findByCuentaId(mockCuenta.getCuentaId())).thenReturn(mockCuenta);
		
		mockCuenta.setEstadoCuenta("activo");
		
		Mockito.when(accountRepository.save(Mockito.any(Cuenta.class))).thenReturn(mockCuenta);
		
		Cuenta cuenta = cuentaServiceImp.updateAccount(mockCuenta, mockCuenta.getCuentaId());
		
		Assertions.assertEquals(mockCuenta, cuenta);
		
	}
	
	/**
	 * <h2>test updateAccount Not Found</h2>
	 * este test valida que cuando se quiera actualizar una cuenta que no existe,
	 * se genera una excepcion de tipo AccountNotFoundException.
	 */
	@Test
	void testUpdateAccountNotFoundShouldReturnException() {
		
		Cuenta mockCuenta = new Cuenta();
		mockCuenta.setCuentaId(1L);
		
		Mockito.when(accountRepository.existsById(mockCuenta.getCuentaId())).thenReturn(false);
		
		assertThrows(AccountNotFoundException.class, ()->{cuentaServiceImp.updateAccount(mockCuenta, mockCuenta.getCuentaId());});
	}
	
	/**
	 * <h2>test del metodo validateAccountMovements</h2>
	 * este test valida que al momento de buscar la lista de movimientos asociados a una cuenta,
	 * retorne true.
	 */
	@Test
	void validateAccountMovementsShouldReturnTrue() {
		
		Cuenta mockCuenta = new Cuenta();
		mockCuenta.setCuentaId(1L);
		
		Movimiento mockMovimiento = new Movimiento();
		
		List<Movimiento> mockListaMovimientos = new ArrayList<>();
		
		mockListaMovimientos.add(mockMovimiento);
		
		Mockito.when(movementRepository.findByCuenta(accountRepository.findByCuentaId(mockCuenta.getCuentaId()))).thenReturn(mockListaMovimientos);
		
		boolean resultado = cuentaServiceImp.validateAccountMovements(mockCuenta.getCuentaId());
		
		Assertions.assertTrue(resultado);		
		
	}
	
	/**
	 * <h2>test validateAccountWithoutMovements</h2>
	 * este test valida que al momento de consultar los movimientos a una cuenta si esta no tiene,
	 * retorne false.
	 */
	@Test
	void validateAccountWithoutMovementsShouldReturnFalse() {
		
		Cuenta mockCuenta = new Cuenta();
		mockCuenta.setCuentaId(1L);		
		
		List<Movimiento> mockListaMovimientos = new ArrayList<>();	
		
		Mockito.when(movementRepository.findByCuenta(accountRepository.findByCuentaId(mockCuenta.getCuentaId()))).thenReturn(mockListaMovimientos);
		
		boolean resultado = cuentaServiceImp.validateAccountMovements(mockCuenta.getCuentaId());
		
		Assertions.assertFalse(resultado);		
		
	}
	
	/**
	 * <h2>test del metodo deleteAccount</h2>
	 * este test valida que al momento de eliminar una cuenta por un id existente,
	 * retorne true.
	 */
	@Test 
	void testDeleteAccountShouldReturnTrue() {
		
		Cuenta mockCuenta = new Cuenta();
		
		mockCuenta.setCuentaId(1L);
		
		Mockito.when(accountRepository.existsById(mockCuenta.getCuentaId())).thenReturn(true);
		
		boolean resultado = cuentaServiceImp.deleteAccount(mockCuenta.getCuentaId());
		
		Assertions.assertTrue(resultado);
	
	}	 
		
	/**
	 * <h2>test deleteAccount Not Found</h2>
	 * con este test se valida que cuando se quiera eliminar una cuenta que no existe,
	 * genere una excepcion de tipo AccountNotFoundException.
	 */
	@Test
	void testDeleteAccountNotFoundShouldReturnException() {
		
		Cuenta mockCuenta = new Cuenta();
		
		mockCuenta.setCuentaId(1L);
		
		Mockito.when(accountRepository.existsById(mockCuenta.getCuentaId())).thenReturn(false);
		
		assertThrows(AccountNotFoundException.class, ()->{cuentaServiceImp.deleteAccount(mockCuenta.getCuentaId());});
	} 
	
	
	/**
	 * <h2>test deleteAccount con movimientos</h2>
	 * este test valida que cuando se quiera eliminar una cuenta con movimientos asociados,
	 * genere una excepcion de tipo AccountWithMovementsException.
	 */
	@Test
	void testDeleteAccountWithMovementsShouldReturnException() {
		
		Cuenta mockCuenta = new Cuenta();
		mockCuenta.setCuentaId(1L);
		
		Movimiento mockMovimiento = new Movimiento();
		
		List<Movimiento> mockListaMovimientos = new ArrayList<>();
		
		mockListaMovimientos.add(mockMovimiento);
		
		Mockito.when(accountRepository.existsById(mockCuenta.getCuentaId())).thenReturn(true);
		
		Mockito.when(movementRepository.findByCuenta(accountRepository.findByCuentaId(mockCuenta.getCuentaId()))).thenReturn(mockListaMovimientos);
		
		assertThrows(AccountWithMovementsException.class, ()->{cuentaServiceImp.deleteAccount(mockCuenta.getCuentaId());});
	} 
	

}
