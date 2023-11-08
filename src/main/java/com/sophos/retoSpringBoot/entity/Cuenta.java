package com.sophos.retoSpringBoot.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * <h1>Clase Cuenta</h1>
 * La clase Cuenta define los atributos, el constructor , los getters y setters,
 * necesarios para generar un objeto de tipo cuenta.
 *
 * @author Wilson David Pachon.
 * @version 1.0.0 2022
 * @since 1.0.0
 */
@Entity
@Table(name = "cuentas")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cuentaId;
    private String producto;
    private String estadoCuenta;
    private String valorCredito;
    private LocalDate fechaApertura;
    private LocalDate fechaCreacion;
    private String usuarioCreacion;
    private LocalDate fechaModificacion;
    private String usuarioModificacion;

    /**
     * genera la relacion muchos a uno con la tabla cliente
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Cliente cliente;

    /**
     * genera la relacion uno a muchos con la tabla movimientos.
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cuenta")
    private Set<Movimiento> movimientos = new HashSet<>();

    /**
     * Constructor vacio de la clase Cuenta.
     */
    public Cuenta() {
    }

    /**
     *Constructor Cuenta
     *
     * @param producto producto de la cuenta.
     * @param estadoCuenta define el estado de la cuenta.
     * @param valorCredito el valor del credito de la cuenta
     * @param fechaApertura fecha de apertura de la cuenta.
     * @param fechaCreacion fecha de creacion de la cuenta.
     * @param usuarioCreacion usuario de creacion de la cuenta.
     * @param fechaModificacion fecha de modificacion de la cuenta.
     * @param usuarioModificacion usuario de modificacion de la cuenta.
     * @param cliente cliente al cual se le va a asignar la cuenta.
     * @param movimientos movimientos asociados a la cuenta.
     */
    public Cuenta(String producto, String estadoCuenta, String valorCredito, LocalDate fechaApertura, LocalDate fechaCreacion, String usuarioCreacion, LocalDate fechaModificacion, String usuarioModificacion, Cliente cliente, Set<Movimiento> movimientos) {
        this.producto = producto;
        this.estadoCuenta = estadoCuenta;
        this.valorCredito = valorCredito;
        this.fechaApertura = fechaApertura;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaModificacion = fechaModificacion;
        this.usuarioModificacion = usuarioModificacion;
        this.cliente = cliente;
        this.movimientos = movimientos;
    }

    /**
     *
     * @return el id de la cuenta.
     */
    public Long getCuentaId() {
        return cuentaId;
    }

    /**
     *
     * @param cuentaId establece el id de la cuenta.
     */
    public void setCuentaId(Long cuentaId) {
        this.cuentaId = cuentaId;
    }

    /**
     *
     * @return el producto de la cuenta
     */
    public String getProducto() {
        return producto;
    }

    /**
     *
     * @param producto establece el producto de la cuenta.
     */
    public void setProducto(String producto) {
        this.producto = producto;
    }

    /**
     *
     * @return el estado de la cuenta.
     */
    public String getEstadoCuenta() {
        return estadoCuenta;
    }

    /**
     *
     * @param estadoCuenta establece el estado de la cuenta.
     */
    public void setEstadoCuenta(String estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }

    /**
     *
     * @return el valor del credito de la cuenta.
     */
    public String getValorCredito() {
        return valorCredito;
    }

    /**
     *
     * @param valorCredito establece el valor del credito de la cuenta.
     */
    public void setValorCredito(String valorCredito) {
        this.valorCredito = valorCredito;
    }

    /**
     *
     * @return la fecha de apertura de la cuenta
     */
    public LocalDate getFechaApertura() {

        return fechaApertura;
    }

    /**
     *
     * @param fechaApertura establece la fecha de apertura de la cuenta.
     */
    public void setFechaApertura(LocalDate fechaApertura) {

        this.fechaApertura = fechaApertura;
    }

    /**
     *
     * @return la fecha de creacion de la cuenta.
     */
    public LocalDate getFechaCreacion() {

        return fechaCreacion;
    }

    /**
     *
     * @param fechaCreacion establece la fecha de creacion de la cuenta.
     */
    public void setFechaCreacion(LocalDate fechaCreacion) {

        this.fechaCreacion = fechaCreacion;
    }

    /**
     *
     * @return usuario de creacion de la cuenta
     */
    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    /**
     *
     * @param usuarioCreacion establece el usuario de cracion de la cuenta.
     */
    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    /**
     *
     * @return la fecha de modificacion de la cuenta.
     */
    public LocalDate getFechaModificacion() {

        return fechaModificacion;
    }

    /**
     *
     * @param fechaModificacion establece la fecha de modificacion de la cuenta.
     */
    public void setFechaModificacion(LocalDate fechaModificacion) {

        this.fechaModificacion = fechaModificacion;
    }

    /**
     *
     * @return el usuario de modificacion de la cuenta.
     */
    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    /**
     *
     * @param usuarioModificacion establece el usuario de modificacion de la cuenta.
     */
    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    /**
     *
     * @return el cliente al cual se le asocia la cuenta.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     *
     * @param cliente establece el cliente al cual se le va a asignar la cuenta.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     *
     * @return los movimientos asociados a la cuenta.
     */
    public Set<Movimiento> getMovimientos() {
        return movimientos;
    }

    /**
     *
     * @param movimientos establece los movimientos asociados a la cuenta.
     */
    public void setMovimientos(Set<Movimiento> movimientos) {
        this.movimientos = movimientos;

        for(Movimiento movimiento : movimientos){
            movimiento.setCuenta(this);
        }
    }
}
