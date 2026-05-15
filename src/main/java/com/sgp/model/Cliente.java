package com.sgp.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private static int contadorId = 0; // 'static' para que se comparta entre instancias.
    private final int idCliente;
    private final String nombreCompleto;
    private final List<String> direcciones;
    private String correo;
    private int annosAntiguedad;
    private boolean esVip;
    private String pais;

    // * CONSTRUCTOR:

    public Cliente(String nombreCompleto, String correo, int annosAntiguedad, boolean esVip, String pais) {

        // Validación interna de 'nombreCompleto':
        if (nombreCompleto == null || nombreCompleto.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }

        // Validaciones internas de 'correo':
        if (correo == null) {
            throw new NullPointerException("El correo no puede ser null.");
        }
        if (!correo.contains("@") || correo.isEmpty()) {
            throw new IllegalArgumentException("El correo introducido es inválido.");
        }

        // Validación interna de 'annosAntiguedad':
        if (annosAntiguedad < 0) {
            throw new NullPointerException("Los años de antigüedad no pueden ser negativos.");
        }

        // Validación interna de 'pais':
        if (pais == null || pais.isEmpty()) {
            throw new IllegalArgumentException("El país no puede estar vacío.");
        }

        // Asignación de variables:
        this.idCliente = contadorId++;
        this.nombreCompleto = nombreCompleto;
        this.direcciones = new ArrayList<>();
        this.correo = correo;
        this.annosAntiguedad = annosAntiguedad;
        this.esVip = esVip;
        this.pais = pais;
    }

    // * GETTERS:

    public int getIdCliente() {
        return idCliente;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public List<String> getDirecciones() {
        return new ArrayList<>(direcciones);
    }

    public String getCorreo() {
        return correo;
    }

    public int getAnnosAntiguedad() {
        return annosAntiguedad;
    }

    public boolean getEsVip() {
        return esVip;
    }

    public String getPais() {
        return pais;
    }

    // * SETTERS:

    /**
     * Asigna un nuevo correo al cliente de forma controlada.
     * 
     * @param nuevoCorreo
     */
    public void setCorreo(String nuevoCorreo) {
        if (nuevoCorreo == null) {
            throw new NullPointerException("El correo no puede ser null.");
        }

        if (!nuevoCorreo.contains("@") || nuevoCorreo.isEmpty()) {
            throw new IllegalArgumentException("El correo introducido es inválido.");
        }

        this.correo = nuevoCorreo;
    }

    /**
     * Asigna un nuevo número de años de antigüedad al cliente de forma controlada.
     * 
     * @param annosAntiguedad
     */
    public void setAnnosAntiguedad(int annosAntiguedad) {
        if (annosAntiguedad < 0) {
            throw new NullPointerException("Los años de antigüedad no pueden ser negativos.");
        }

        this.annosAntiguedad = annosAntiguedad;
    }

    /**
     * Asigna un nuevo valor de estado VIP al cliente.
     * 
     * @param esVip
     */
    public void setEsVip(boolean esVip) {
        this.esVip = esVip;
    }

    /**
     * Asigna un nuevo país al cliente.
     * 
     * @param pais
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    // * OTROS MÉTODOS:

    // Sobreescribe el método toString() propio de objetos en Java:
    @Override
    public String toString() {
        return String.format("Cliente: %s, correo: %s, ID: %d", nombreCompleto, correo, idCliente);
    }

    /**
     * Para añadir direcciones de las que elegir de forma controlada.
     * 
     * @param direccion Dirección a añadir.
     */
    public void agregarDireccion(String direccion) {
        if (direccion == null || direccion.isEmpty()) {
            throw new IllegalArgumentException("La dirección no puede estar vacía.");
        }
        direcciones.add(direccion);
    }

    /**
     * Para eliminar direcciones de forma controlada.
     * 
     * @param direccion Dirección a eliminar.
     */
    public void eliminarDireccion(String direccion) {
        if (direccion == null || direccion.isEmpty() || !direcciones.contains(direccion)) {
            throw new IllegalArgumentException("La dirección no existe.");
        }
        direcciones.remove(direccion);
    }

    /**
     * Para modificar direcciones de forma controlada.
     * 
     * @param numDireccion Nº de la lista, empezando por 0.
     * @param direccion    Dirección nueva con la que modificar.
     */
    public void modificarDireccion(int numDireccion, String direccion) {
        if (numDireccion < 0 || numDireccion >= direcciones.size()) {
            throw new IndexOutOfBoundsException("Índice inválido: " + numDireccion);
        }

        if (direccion == null || direccion.isEmpty()) {
            throw new IllegalArgumentException("La dirección no puede estar vacía.");
        }

        direcciones.set(numDireccion, direccion);
    }
}
