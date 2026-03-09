package com.sgp.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
  private static int contadorId = 0; // 'static' para que se comparta entre instancias.
  private final int idCliente;
  private final String nombreCompleto;
  private final List<String> direcciones;
  private String correo;

  // * Constructor:

  public Cliente(String nombreCompleto, String correo) {

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

    // Asignación de variables:

    this.idCliente = contadorId++;
    this.nombreCompleto = nombreCompleto;
    this.direcciones = new ArrayList<>();
    this.correo = correo;
  }

  // * Getters:

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

  // * Setters:

  public void setCorreo(String nuevoCorreo) { // Para asignar un nuevo correo de forma controlada.
    if (nuevoCorreo == null) {
      throw new NullPointerException("El correo no puede ser null.");
    }

    if (!nuevoCorreo.contains("@") || nuevoCorreo.isEmpty()) {
      throw new IllegalArgumentException("El correo introducido es inválido.");
    }

    this.correo = nuevoCorreo;
  }

  // * Otros métodos:

  @Override // Sobreescribe el método toString() propio de objetos en Java.
  public String toString() {
    return String.format("Cliente: %s, correo: %s, ID: %d", nombreCompleto, correo, idCliente);
  }

  /**
   * Para añadir direcciones de las que elegir de forma controlada
   * 
   * @param direccion Dirección a añadir
   */
  public void agregarDireccion(String direccion) {
    if (direccion == null || direccion.isEmpty()) {
      throw new IllegalArgumentException("La dirección no puede estar vacía.");
    }
    direcciones.add(direccion);
  }

  /**
   * Para eliminar direcciones de forma controlada
   * 
   * @param direccion Dirección a eliminar
   */
  public void eliminarDireccion(String direccion) {
    if (direccion == null || direccion.isEmpty() || !direcciones.contains(direccion)) {
      throw new IllegalArgumentException("La dirección no existe.");
    }
    direcciones.remove(direccion);
  }

  /**
   * Para modificar direcciones de forma controlada
   * 
   * @param numDireccion Nº de la lista, empezando por 0
   * @param direccion    Dirección nueva con la que modificar
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
