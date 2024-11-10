package com.krakedev.inventarios.entidades;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) // Omite los campos null durante la serialización
public class Categoria {
	private int codigo;
	private String nombre;
	private Categoria categoriaPadre;

	public Categoria() {
		super();
	}

	public Categoria(int codigo, String nombre) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public Categoria(int codigo, String nombre, Categoria categoriaPadre) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.categoriaPadre = categoriaPadre;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Categoria getCategoriaPadre() {
		return categoriaPadre;
	}

	public void setCategoriaPadre(Categoria categoriaPadre) {
		this.categoriaPadre = categoriaPadre;
	}

	@Override
	public String toString() {
		return "Categoria [codigo=" + codigo + ", nombre=" + nombre + ", categoriaPadre=" + categoriaPadre + "]";
	}

}