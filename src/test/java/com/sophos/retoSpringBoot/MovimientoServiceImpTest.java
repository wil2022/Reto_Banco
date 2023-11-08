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

import com.sophos.retoSpringBoot.entity.Cuenta;
import com.sophos.retoSpringBoot.entity.Movimiento;
import com.sophos.retoSpringBoot.exceptions.AccountNotFoundException;
import com.sophos.retoSpringBoot.exceptions.MovementActiveStateException;
import com.sophos.retoSpringBoot.exceptions.MovementNotFoundException;
import com.sophos.retoSpringBoot.repository.AccountRepository;
import com.sophos.retoSpringBoot.repository.ClientRepository;
import com.sophos.retoSpringBoot.repository.MovementRepository;
import com.sophos.retoSpringBoot.service.implementations.MovimientoServiceImp;

/**
 * <h1>Test de la clase MovimientoServiceImp</h1>
 * En esta clase se realizan los tests correspondientes para verificar el
 * correcto funcionamiento de la logica de negocio de cada uno de los metodos 
 * definidos en la capa de Servicio de movimiento.
 * 
 *@author Wilson David Pachon.
 *@version 1.0.0 2022
 *@since 1.0.0
 *
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MovimientoServiceImpTest {
	
	@Mock
	AccountRepository accountRepository;
	
	@Mock
	ClientRepository clientRepository;
	
	@Mock
	MovementRepository movementRepository;
	
	@Autowired
	@InjectMocks
	MovimientoServiceImp movimientoServiceImp;
	
	/**
	 * Inicializa los objetos con la anotacion Mockito.
	 */
	@BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
    }
	
	/**
	 * <h2>test del metodo createMovement</h2>
	 * con este test se valida que al momento de crear un movimiento asociado a una cuenta existente,
	 * retorne un objeto de tipo movimiento.
	 */
	@Test
	void testCreateMovementShouldReturnMovement() {
		
		Cuenta mockCuenta = new Cuenta();
		mockCuenta.setCuentaId(1L);		
		
		Movimiento mockMovimiento = new Movimiento();
		mockMovimiento.setCuenta(mockCuenta);		
		mockMovimiento.setFechaCreacion(LocalDate.now());
		mockMovimiento.setUsuarioCreacion("wilsonp");
		
		Mockito.when(accountRepository.findById(mockMovimiento.getCuenta().getCuentaId())).thenReturn(Optional.of(mockCuenta));
		Mockito.when(movementRepository.save(Mockito.any(Movimiento.class))).thenReturn(mockMovimiento);
		
		
		Movimiento movimiento = movimientoServiceImp.createMovement(mockMovimiento);
			
		
		Assertions.assertEquals(mockMovimiento, movimiento);
	}
	
	/**
	 * <h2>test del metodo createMovement</h2>
	 * con este test se valida que al momento de crear un movimiento asociado a una cuenta que no existe,
	 * genere una excepcion de tipo AccountNotFoundException.
	 */
	@Test
	void testCreateMovementShouldReturnException() {
		
		Cuenta mockCuenta = new Cuenta();
		mockCuenta.setCuentaId(1L);	
		
		Movimiento mockMovimiento = new Movimiento();
		mockMovimiento.setCuenta(mockCuenta);	
		
		
		lenient().when(movementRepository.save(Mockito.any(Movimiento.class))).thenReturn(mockMovimiento);
		
		assertThrows(AccountNotFoundException.class, ()->{movimientoServiceImp.createMovement(mockMovimiento);});
				
	}
	
	/**
	 * <h2>test del metodo readMovement</h2>
	 * el test verifica que cuando se quiera consultar un movimiento por un id existente, 
	 * retorne el movimiento correspondiente a ese id.
	 */
	@Test
	void testReadMovementShouldReturnMovement() {
		Movimiento mockMovimiento = new Movimiento();
		mockMovimiento.setMovimientoId(1L);			
		
		
		Mockito.when(movementRepository.existsById(mockMovimiento.getMovimientoId())).thenReturn(true);
		Mockito.when(movementRepository.findByMovimientoId(mockMovimiento.getMovimientoId())).thenReturn(mockMovimiento);		
		
		Movimiento movimiento = movimientoServiceImp.readMovement(mockMovimiento.getMovimientoId());
			
		Assertions.assertEquals(mockMovimiento, movimiento);		
		
	}
	
	/**
	 * <h2>test readMovement NotFound</h2>
	 * este test valida que cuando se quiere consultar un movimiento por un id que no existe, 
	 * genere una excepcion del tipo MovementNotFoundException.
	 * 
	 */
	@Test
	void testMovementNotFoundShouldReturnException() {
		
		Movimiento mockMovimiento = new Movimiento();
		mockMovimiento.setMovimientoId(1L);			
		
				
		lenient().when(movementRepository.existsById(mockMovimiento.getMovimientoId())).thenReturn(false);		
			
		assertThrows(MovementNotFoundException.class, ()->{movimientoServiceImp.readMovement(mockMovimiento.getMovimientoId());});
		
	}	
	
	/**
	 * <h2>test del metodo readMovementsByAccount</h2>
	 * este test verifica que al momento de consultar los movimientos asociados a una cuenta existente,
	 * retorne una lista de movimientos.
	 */
	@Test
	void testReadMovementsByAccountShouldReturnMovementsList() {
		
		Cuenta mockCuenta = new Cuenta();
		mockCuenta.setCuentaId(1L);
		Movimiento mockMovimiento = new Movimiento();
		
		List<Movimiento> mockListaMovimientos = new ArrayList<>();
		
		mockListaMovimientos.add(mockMovimiento);		
		
		Mockito.when(accountRepository.existsById(mockCuenta.getCuentaId())).thenReturn(true);
		Mockito.when(movementRepository.findByCuenta(accountRepository.findByCuentaId(mockCuenta.getCuentaId()))).thenReturn(mockListaMovimientos);
		
		List<Movimiento> movimientos = movimientoServiceImp.readMovementsByAccount(mockCuenta.getCuentaId());
		
		Assertions.assertEquals(mockListaMovimientos, movimientos);
	}
	
	/**
	 * <h2>test del metodo readMovementsByAccount</h2>
	 * este test verifica que al momento de consultar los movimientos asociados a una cuenta que no existe,
	 * genere una excepcion de tipo AccountNotFoundException.
	 */
	@Test
	void ReadMovementsByAccountNotFoundShouldReturnException() {
		Cuenta mockCuenta = new Cuenta();
		mockCuenta.setCuentaId(1L);		
		
		Mockito.when(accountRepository.existsById(mockCuenta.getCuentaId())).thenReturn(false);
		
		assertThrows(AccountNotFoundException.class, ()->{movimientoServiceImp.readMovementsByAccount(mockCuenta.getCuentaId());});
	}
	
	/**
	 * <h2>test metodo updateMovement</h2>
	 * este test valida que al momento de actualizar un movimiento,
	 * retorne ese movimiento con los campos que se han actualizado.
	 */
	@Test
	void testUpdateMovementShouldReturnMovement() {
		
		Movimiento mockMovimiento = new Movimiento();
		mockMovimiento.setMovimientoId(1L);			
		
		Mockito.when(movementRepository.existsById(mockMovimiento.getMovimientoId())).thenReturn(true);
		Mockito.when(movementRepository.findByMovimientoId(mockMovimiento.getMovimientoId())).thenReturn(mockMovimiento);
		
		mockMovimiento.setEstado("activo");
		
		Mockito.when(movementRepository.save(Mockito.any(Movimiento.class))).thenReturn(mockMovimiento);
		
		Movimiento movimiento = movimientoServiceImp.updateMovement(mockMovimiento, mockMovimiento.getMovimientoId());
				
		Assertions.assertEquals(mockMovimiento, movimiento);
		
	}
	
	/**
	 * <h2>test updateMovement Not Found</h2>
	 * este test valida que cuando se quiera actualizar un movimiento que no existe,
	 * se genera una excepcion de tipo MovementNotFoundException.
	 */
	@Test
	void testUpdateMovementNotFoundShouldReturnException() {
		
		Movimiento mockMovimiento = new Movimiento();
		mockMovimiento.setMovimientoId(1L);		
		
		Mockito.when(movementRepository.existsById(mockMovimiento.getMovimientoId())).thenReturn(false);
		
		assertThrows(MovementNotFoundException.class, ()->{movimientoServiceImp.updateMovement(mockMovimiento, mockMovimiento.getMovimientoId());});
	}
	
	/**
	 * <h2>test del metodo validateMovementStatus</h2>
	 * este test verifica que al momento de validar el estado de un movimiento, si este se encuentra activo,
	 * retorne true.
	 */
	@Test
	void testValidateMovementStatusActiveShouldReturnTrue() {
		
		Movimiento mockMovimiento = new Movimiento();
		mockMovimiento.setMovimientoId(1L);		
		mockMovimiento.setEstado("activo");
		Mockito.when(movementRepository.findByMovimientoId(mockMovimiento.getMovimientoId())).thenReturn(mockMovimiento);
		
		boolean resultado = movimientoServiceImp.validateMovementStatus(mockMovimiento.getMovimientoId());
		
		Assertions.assertTrue(resultado);	
	}	
	
	/**
	 * <h2>test del metodo validateMovementStatusFalse</h2>
	 * este test verifica que al momento de validar el estado de un movimiento, si este se encuentra inactivo,
	 * retorne false.
	 */
	@Test
	void testValidateMovementStatusInactiveShouldReturnFalse() {
		
		Movimiento mockMovimiento = new Movimiento();
		mockMovimiento.setMovimientoId(1L);		
		mockMovimiento.setEstado("inactivo");
		Mockito.when(movementRepository.findByMovimientoId(mockMovimiento.getMovimientoId())).thenReturn(mockMovimiento);
		
		boolean resultado = movimientoServiceImp.validateMovementStatus(mockMovimiento.getMovimientoId());
		
		Assertions.assertFalse(resultado);	
	}
	
	/**
	 * <h2>test del metodo deleteMovement</h2>
	 * este test valida que al momento de eliminar un movimiento por un id existente,
	 * retorne true.
	 */
	@Test 
	void testDeleteMovementShouldReturnTrue() {
		
		Movimiento mockMovimiento = new Movimiento();
		
		mockMovimiento.setMovimientoId(1L);		
		mockMovimiento.setEstado("inactivo");
		
		Mockito.when(movementRepository.existsById(mockMovimiento.getMovimientoId())).thenReturn(true);
		Mockito.when(movementRepository.findByMovimientoId(mockMovimiento.getMovimientoId())).thenReturn(mockMovimiento);		
		boolean resultado = movimientoServiceImp.deleteMovement(mockMovimiento.getMovimientoId());
		
		Assertions.assertTrue(resultado);
	
	}	
	
	/**
	 * <h2>test deleteMovement Not Found</h2>
	 * con este test se valida que cuando se quiera eliminar un movimiento que no existe,
	 * genere una excepcion de tipo MovementNotFoundException.
	 */
	@Test
	void testDeleteMovementNotFoundShouldReturnException() {
		
		Movimiento mockMovimiento = new Movimiento();
		
		mockMovimiento.setMovimientoId(1L);	
		Mockito.when(movementRepository.existsById(mockMovimiento.getMovimientoId())).thenReturn(false);		
		
		assertThrows(MovementNotFoundException.class, ()->{movimientoServiceImp.deleteMovement(mockMovimiento.getMovimientoId());});
	}
	
	
	/**
	 * <h2>test deleteMovement con estado activo</h2>
	 * este test valida que cuando se quiera eliminar un movimiento con el estado activo,
	 * genere una excepcion de tipo MovementActiveStateException.
	 */
	@Test
	void testDeleteMovementWithActiveStatusShouldReturnException() {		
		
		Movimiento mockMovimiento = new Movimiento();
		
		mockMovimiento.setMovimientoId(1L);		
		mockMovimiento.setEstado("activo");
		
		Mockito.when(movementRepository.existsById(mockMovimiento.getMovimientoId())).thenReturn(true);
		Mockito.when(movementRepository.findByMovimientoId(mockMovimiento.getMovimientoId())).thenReturn(mockMovimiento);		
		
		assertThrows(MovementActiveStateException.class, ()->{movimientoServiceImp.deleteMovement(mockMovimiento.getMovimientoId());});
	} 
	

}
