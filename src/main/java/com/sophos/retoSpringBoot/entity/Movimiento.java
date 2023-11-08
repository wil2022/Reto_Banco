package com.sophos.retoSpringBoot.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import java.time.LocalDate;


/**
 * <h1>Clase Movimiento</h1>
 * La clase Movimiento define los atributos, el constructor , los getters y setters,
 * necesarios para generar un objeto de tipo movimiento.
 *
 * @author Wilson David Pachon.
 * @version 1.0.0 2022
 * @since 1.0.0
 */
@Entity
@Table(name = "movimientos")
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movimientoId;
    private String tipo;
    private Long valor;
    private String periodo;
    private String estado;
    private LocalDate fechaMovimiento;
    private LocalDate fechaCreacion;
    private String usuarioCreacion;
    private LocalDate fechaModificacion;
    private String usuarioModificacion;

    /**
     * relacion muchos a uno con la tabla cuentas.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cuenta_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Cuenta cuenta;

    /**
     * constructor vacio de la clase Movimiento.
     */
    public Movimiento() {    }

    /**
     * Constructor Movimiento
     *
     * @param tipo tipo de movimiento
     * @param valor valor del movimiento
     * @param periodo periodo del movimiento
     * @param estado estado del movimiento.
     * @param fechaMovimiento fecha del movimiento.
     * @param fechaCreacion fecha de creacion del movimiento
     * @param usuarioCreacion usuario de creacion del movimiento.
     * @param fechaModificacion fecha de modificacion del movimiento
     * @param usuarioModificacion usuario de modificacion del movimiento.
     * @param cuenta cuenta a la que se asocia el movimiento.
     */
    public Movimiento(String tipo, Long valor, String periodo, String estado, LocalDate fechaMovimiento, LocalDate fechaCreacion, String usuarioCreacion, LocalDate fechaModificacion, String usuarioModificacion, Cuenta cuenta) {
        this.tipo = tipo;
        this.valor = valor;
        this.periodo = periodo;
        this.estado = estado;
        this.fechaMovimiento = fechaMovimiento;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaModificacion = fechaModificacion;
        this.usuarioModificacion = usuarioModificacion;
        this.cuenta = cuenta;
    }

    /**
     *
     * @return el id del movimiento.
     */
    public Long getMovimientoId() {
        return movimientoId;
    }

    /**
     *
     * @param movimientoId estaablece el id del movimiento.
     */
    public void setMovimientoId(Long movimientoId) {
        this.movimientoId = movimientoId;
    }

    /**
     *
     * @return el tipo de movimiento.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     *
     * @param tipo define el tipo de movimiento.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     *
     * @return el valor del movimiento.
     */
    public Long getValor() {
        return valor;
    }

    /**
     *
     * @param valor establece el valor del movimiento.
     */
    public void setValor(Long valor) {
        this.valor = valor;
    }

    /**
     *
     * @return el periodo del movimiento.
     */
    public String getPeriodo() {
        return periodo;
    }

    /**
     *
     * @param periodo establece el periodo del movimiento.
     */
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    /**
     *
     * @return el estado del movimiento.
     */
    public String getEstado() {
        return estado;
    }

    /**
     *
     * @param estado establece el estado del movimiento.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     *
     * @return la fecha del movimiento.
     */
    public LocalDate getFechaMovimiento() {
        return fechaMovimiento;
    }

    /**
     *
     * @param fechaMovimiento establece la fecha del movimiento
     */
    public void setFechaMovimiento(LocalDate fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    /**
     *
     * @return la fecha de creacion del movimiento.
     */
    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     *
     * @param fechaCreacion establece la fecha de creacion del movimiento.
     */
    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     *
     * @return el usuario de creacion del movimiento.
     */
    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    /**
     *
     * @param usuarioCreacion establece el usuario de creacion del movimiento
     */
    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    /**
     *
     * @return la fecha de modificacion del movimiento.
     */
    public LocalDate getFechaModificacion() {
        return fechaModificacion;
    }

    /**
     *
     * @param fechaModificacion establece la fecha de modificacion del movimiento.
     */
    public void setFechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    /**
     *
     * @return el usuario de modificacion del movimiento.
     */
    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    /**
     *
     * @param usuarioModificacion establece el usuario de modificacion del movimiento.
     */
    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    /**
     *
     * @return la cuenta a la que se le va a asignar el movimiento.
     */
    public Cuenta getCuenta() {
        return cuenta;
    }

    /**
     *
     * @param cuenta establece la cuenta a la que se le va a asignar el movimiento.
     */
    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

}
