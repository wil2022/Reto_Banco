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

import com.sophos.retoSpringBoot.controller.MovimientoController;
import com.sophos.retoSpringBoot.entity.Cuenta;
import com.sophos.retoSpringBoot.entity.Movimiento;
import com.sophos.retoSpringBoot.service.MovimientoService;

/**
 * <h1>Test de la clase MovimientoController</h1>
 * En esta clase se realizan los tests correspondientes para verificar el
 * correcto funcionamiento de cada uno de los metodos definidos en el controlador
 * de movimiento.
 *  
 *@author Wilson David Pachon.
 *@version 1.0.0 2022
 *@since 1.0.0
 *
 */
@ExtendWith(MockitoExtension.class)
@Suite
public class MovimientoControllerTest {
	
	@InjectMocks
	MovimientoController movimientoController;
	
	@Mock
	private  MovimientoService movimientoService;	
	
	ResponseEntity<Movimiento> responseEntityMovement;
	ResponseEntity<List<Movimiento>> responseEntityListMovement;
	
	
	/**
	 * <h2>test del metodo readMovement</h2>
	 * test generado para verificar que al momento de buscar un movimiento por id,
	 * retorne el estado http OK.
	 * 
	 * @throws Exception
	 */
	@Test
	void testReadMovementShouldReturnStatusOk() throws Exception{
		
		Movimiento movimiento = new Movimiento();
		movimiento.setMovimientoId(1L);
		movimiento.setEstado("activo");
				
		when(movimientoService.readMovement(movimiento.getMovimientoId())).thenReturn(movimiento);
		
		responseEntityMovement = movimientoController.readMovement(movimiento.getMovimientoId());
		
		assertEquals(responseEntityMovement.getStatusCodeValue(), 200);
		assertEquals(responseEntityMovement.getBody().getEstado(), "activo");
		
	}
	
	/**
	 * <h2>test del metodo readMovement no encontrado.</h2>
	 * este test verifica que al momento de buscar un movimiento por un id que no existe,
	 * retorna el estado 404 NOT_FOUND.
	 * 
	 * @throws Exception
	 */
	@Test
	void testReadMovementNullShouldReturnStatusNotFound() throws Exception {
		
		Movimiento movimiento = new Movimiento();
		movimiento.setMovimientoId(1L);
		
		when(movimientoService.readMovement(movimiento.getMovimientoId())).thenReturn(null);
		
		responseEntityMovement = movimientoController.readMovement(movimiento.getMovimientoId());
		
		assertEquals(responseEntityMovement.getStatusCodeValue(), 404);
		assertNull(responseEntityMovement.getBody());		
		
	}	
	
	/**
	 * <h2>test del metodo readMovementsByAccount</h2>
	 * este test verifica que al momento de consultar los movimientos que tiene una cuenta,
	 * retorne un estado 200 Ok.
	 * @throws Exception
	 */
	@Test
	void testReadMovementsByAccountShouldReturnStatusOk() throws Exception {
		
		Cuenta cuenta = new Cuenta();
		cuenta.setCuentaId(1L);
		
		Movimiento movimiento = new Movimiento();
		
		List<Movimiento> ListaMovimientos = new ArrayList<>();
		
		ListaMovimientos.add(movimiento);
				
		when(movimientoService.readMovementsByAccount(cuenta.getCuentaId())).thenReturn(ListaMovimientos);
		
		responseEntityListMovement = movimientoController.readMovementsByAccount(cuenta.getCuentaId());
		
		assertEquals(responseEntityListMovement.getStatusCodeValue(), 200);
		assertEquals(responseEntityListMovement.getBody(), ListaMovimientos);
		assertEquals(responseEntityListMovement.getBody().size(), 1);
		
	} 
	
	/**
	 * <h2>test readMovementsByAccount Not Found</h2>
	 * este test verifica que al momento de consultar los movimientos que tiene una cuenta, sino
	 * encuentra ninguno, retorne el estado 404 NOT_FOUND.
	 * @throws Exception
	 */	
	@Test
	void testReadMovementsByAccountShouldReturnStatusNotFound() throws Exception {
		
		Cuenta cuenta = new Cuenta();
		cuenta.setCuentaId(1L);	
				
		when(movimientoService.readMovementsByAccount(cuenta.getCuentaId())).thenReturn(null);
		
		responseEntityListMovement = movimientoController.readMovementsByAccount(cuenta.getCuentaId());
		
		assertEquals(responseEntityListMovement.getStatusCodeValue(), 404);
		assertNull(responseEntityListMovement.getBody());
		
	} 
	
	/**
	 * <h2>test del metodo createMovement</h2>
	 * este test verifica que al momento de crear un movimiento exitosamente,
	 * retorne el estado 201 Created.
	 * @throws Exception
	 */
	@Test
	void testCreateMovementShouldReturnStatusCreated() throws Exception {
		
		Movimiento movimiento = new Movimiento();
		movimiento.setMovimientoId(1L);
		movimiento.setEstado("activo");
		
		when(movimientoService.createMovement(any(Movimiento.class))).thenReturn(movimiento);
		
		responseEntityMovement = movimientoController.createMovement(movimiento);
		
		assertEquals(responseEntityMovement.getStatusCodeValue(), 201);
		assertEquals(responseEntityMovement.getBody().getEstado(), "activo");
			
	}
	
	/**
	 * <h2>test del metodo createMovement Null</h2>
	 * este test verifica que al momento de intentar crear un movimiento sin exito,
	 * retorne el estado 400 BAD_REQUEST.
	 * 
	 * @throws Exception
	 */	
	@Test
	void testCreateMovementNullShouldReturnStatusBadRequest() throws Exception {
		
		Movimiento movimiento = new Movimiento();	
		
		when(movimientoService.createMovement(any(Movimiento.class))).thenReturn(null);
		
		responseEntityMovement = movimientoController.createMovement(movimiento);
		
		assertEquals(responseEntityMovement.getStatusCodeValue(), 400);
		assertNull(responseEntityMovement.getBody());
			
	}
		
	/**
	 * <h2>test del metodo updateMovement</h2>
	 * con este test se valida que al momento de actualizar un movimiento de manera exitosa,
	 * retorne un estado 200 OK.
	 * @throws Exception
	 */
	@Test
	void testUpdateMovementShouldReturnStatusOk() throws Exception{
		
		Movimiento movimiento = new Movimiento();
		movimiento.setMovimientoId(1L);
		movimiento.setEstado("activo");	
		
		Movimiento updateMovimiento = new Movimiento();
		updateMovimiento.setMovimientoId(movimiento.getMovimientoId());
		updateMovimiento.setEstado("inactivo");
		
		movimiento = updateMovimiento;		
		
		when(movimientoService.updateMovement(movimiento, movimiento.getMovimientoId())).thenReturn(updateMovimiento);
		
		responseEntityMovement = movimientoController.updateMovement(movimiento, movimiento.getMovimientoId());
		
		assertEquals(responseEntityMovement.getStatusCodeValue(), 200);
		assertEquals(updateMovimiento, movimiento);		
		
	}
	
	/**
	 * <h2>test del metodo updateMovement no encontrado</h2>
	 * con este test se valida que al momento de intentar actualizar un movimiento que no existe,
	 * retorne un estado 404 NOT_FOUND.
	 * @throws Exception
	 */
	@Test
	void testUpdateMovementNullShouldReturnStatusNotFound() throws Exception{
		
		Movimiento movimiento = new Movimiento();
		movimiento.setMovimientoId(1L);	
		
		when(movimientoService.updateMovement(movimiento, movimiento.getMovimientoId())).thenReturn(null);
		
		responseEntityMovement = movimientoController.updateMovement(movimiento, movimiento.getMovimientoId());
		
		assertEquals(responseEntityMovement.getStatusCodeValue(), 404);
		assertNull(responseEntityMovement.getBody());		
		
	}
	
	/**
	 * <h2>test del metodo deleteMovement</h2>
	 * Con este test se verifica que cuando se quiera eliminar un movimiento existente, 
	 * retorne true y un estado 200 OK.
	 * @throws Exception
	 */
	@Test
	void testDeleteMovementShouldReturnStatusOk() throws Exception {
		Movimiento movimiento = new Movimiento();
		movimiento.setMovimientoId(1L);	
		
		when(movimientoService.deleteMovement(movimiento.getMovimientoId())).thenReturn(true);
		
		ResponseEntity<Boolean> result = movimientoController.deleteMovement(movimiento.getMovimientoId());
		
		assertEquals(result.getStatusCodeValue(), 200);
		assertTrue(result.getBody());	
		
	}
	
	/**
	 * <h2>test del metodo deleteMovement Null</h2>
	 * este test verifica que cuando se intente eliminar un movimiento sin exito,
	 * retorne false y estado 400 BAD_REQUEST.
	 * 
	 * @throws Exception
	 */	
	@Test
	void testDeleteMovementNullShouldReturnStatusBadRequest() throws Exception {
		Movimiento movimiento = new Movimiento();
		movimiento.setMovimientoId(1L);	
		
		when(movimientoService.deleteMovement(movimiento.getMovimientoId())).thenReturn(false);
		
		ResponseEntity<Boolean> result = movimientoController.deleteMovement(movimiento.getMovimientoId());
		
		assertEquals(result.getStatusCodeValue(), 400);
		assertFalse(result.getBody());	
		
	}
	

}
