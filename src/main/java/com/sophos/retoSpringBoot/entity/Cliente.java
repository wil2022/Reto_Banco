package com.sophos.retoSpringBoot.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * <h1>CLase Cliente</h1>
 * La clase Cliente define los atributos, el constructor , los getters y setters,
 * necesarios para generar un objeto de tipo cliente.
 *
 * @author Wilson David Pachon.
 * @version 1.0.0 2022
 * @since 1.0.0
 */

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="clienteId")
    private Long clienteId;
    @Column(name="tipoId")
    private String tipoId;
    @Column(name="numeroId")
    private Long numeroId;
    @Column(name="estadoCliente")
    private String estadoCliente;
    @Column(name="tipoCliente")
    private String tipoCliente;
    @Column(name="direccion")
    private String direccion;
    @Column(name="telefono")
    private String telefono;
    
    @Column(name="correo", nullable=false)
    @Email(message= "Correo no cumple con los requisitos")
    @NotBlank(message = "correo is mandatory")
    private String correo;
    @Column(name="primerNombre")
    private String primerNombre;
    @Column(name="segundoNombre")
    private String segundoNombre;
    @Column(name="primerApellido")
    private String primerApellido;
    @Column(name="segundoApellido")
    private String segundoApellido;
    @Column(name="fechaNacimiento")
    private LocalDate fechaNacimiento;
    @Column(name="fechaCreacion")
    private LocalDate fechaCreacion;
    @Column(name="usuarioCreacion")
    private String usuarioCreacion;
    @Column(name="fechaModificacion")
    private LocalDate fechaModificacion;
    @Column(name="usuarioModificacion")
    private String usuarioModificacion;

    /**
     * Genera la relacion uno a muchos, con la tabla cuentas
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private Set<Cuenta> cuentas = new HashSet<>();

    /**
     * Constructor vacio de la clase cliente.
     */

public Cliente() {
    }

    /**
     *Constructor Cliente
     *
     * @param tipoId tipo de documento
     * @param numeroId numero de documento
     * @param estadoCliente define si el cliente se encuentra activo o inactivo.
     * @param tipoCliente define el tipo de Cliente
     * @param direccion direccion del cliente
     * @param telefono telefono del cliente
     * @param correo correo del cliente
     * @param primerNombre primer nombre del cliente
     * @param segundoNombre segundo nombre del cliente
     * @param primerApellido primer apellido del cliente
     * @param segundoApellido segundo apellido del cliente.
     * @param fechaNacimiento define la fecha de nacimiento.
     * @param fechaCreacion la fecha en la cual se crea el cliente
     * @param usuarioCreacion el usuario que ha creado el cliente.
     * @param fechaModificacion la fecha en que se ha modificado el cliente.
     * @param usuarioModificacion el usuario que ha modificado el cliente.
     * @param cuentas las cuentas asociadas al cliente.
     */ 

    public Cliente(String tipoId, Long numeroId, String estadoCliente, String tipoCliente, String direccion, String telefono, String correo, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, LocalDate fechaNacimiento, LocalDate fechaCreacion, String usuarioCreacion, LocalDate fechaModificacion, String usuarioModificacion, Set<Cuenta> cuentas) {
        this.tipoId = tipoId;
        this.numeroId = numeroId;
        this.estadoCliente = estadoCliente;
        this.tipoCliente = tipoCliente;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaModificacion = fechaModificacion;
        this.usuarioModificacion = usuarioModificacion;
        this.cuentas = cuentas;
    }
    

    /**
     *
     * @return el identificador del cliente.
     */
    public Long getClienteId() {
        return clienteId;
    }

    /**
     *
     * @param clienteId el id del cliente
     */

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    /**
     *
     * @return el tipo de id del cliente.
     */
    public String getTipoId() {
        return tipoId;
    }

    /**
     *
     * @param tipoId establece el tipo de id del cliente.
     */

    public void setTipoId(String tipoId) {
        this.tipoId = tipoId;
    }

    /**
     *
     * @return el numero de id del cliente
     */
    public Long getNumeroId() {
        return numeroId;
    }

    /**
     *
     * @param numeroId establece el numero de id del cliente.
     */
    public void setNumeroId(Long numeroId) {
        this.numeroId = numeroId;
    }

    /**
     *
     * @return el estado del cliente.
     */
    public String getEstadoCliente() {
        return estadoCliente;
    }

    /**
     *
     * @param estadoCliente establece el estado del cliente.
     */
    public void setEstadoCliente(String estadoCliente) {
        this.estadoCliente = estadoCliente;
    }

    /**
     *
     * @return el tipo de cliente.
     */
    public String getTipoCliente() {
        return tipoCliente;
    }

    /**
     *
     * @param tipoCliente establece el tipo de cliente.
     */
    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    /**
     *
     * @return la direccion del cliente.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     *
     * @param direccion establece la direccion del cliente.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     *
     * @return el telefono del cliente.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     *
     * @param telefono establece el telefono del cliente.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     *
     * @return el correo del cliente.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     *
     * @param correo esablece el correo del cliente.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     *
     * @return el primer nombre del cliente.
     */
    public String getPrimerNombre() {
        return primerNombre;
    }

    /**
     *
     * @param primerNombre establece el primer nombre del cliente.
     */
    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    /**
     *
     * @return el segundo nombre del cliente.
     */
    public String getSegundoNombre() {
        return segundoNombre;
    }

    /**
     *
     * @param segundoNombre establece el segundo nombre del cliente.
     */
    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    /**
     *
     * @return el primer apellido del cliente.
     */
    public String getPrimerApellido() {
        return primerApellido;
    }

    /**
     *
     * @param primerApellido establece el primer apellido del cliente.
     */
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    /**
     *
     * @return el segundo apellido del cliente.
     */
    public String getSegundoApellido() {
        return segundoApellido;
    }

    /**
     *
     * @param segundoApellido establece el segundo apellido del cliente.
     */
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    /**
     *
     * @return la fecha de nacimiento del cliente.
     */
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     *
     * @param fechaNacimiento establece la fecha de nacimiento del cliente.
     */
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     *
     * @return la fecha de creacion del cliente.
     */
    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     *
     * @param fechacreacion establece la fecha de creacion del cliente.
     */
    public void setFechaCreacion(LocalDate fechacreacion) {
        this.fechaCreacion = fechacreacion;
    }

    /**
     *
     * @return el usuario de creacion del cliente
     */
    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    /**
     *
     * @param usuarioCreacion establece el usuario de creacion del cliente
     */
    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    /**
     *
     * @return la fecha de modificacion del cliente.
     */
    public LocalDate getFechaModificacion() {
        return fechaModificacion;
    }

    /**
     *
     * @param fechaModificacion establece la fecha de modificacion del cliente.
     */
    public void setFechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    /**
     *
     * @return el usuario de modificacion
     */
    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    /**
     *
     * @param usuarioModificacion establece el usuario de modificacion.
     */
    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    /**
     *
     * @return las cuentas asociadas al cliente.
     */
    public Set<Cuenta> getCuentas() {
        return cuentas;
    }

    /**
     *
     * @param cuentas establece las cuentas del cliente.
     */
    public void setCuentas(Set<Cuenta> cuentas) {

        this.cuentas = cuentas;

        for(Cuenta cuenta : cuentas){
            cuenta.setCliente(this);
        }
    }

}
