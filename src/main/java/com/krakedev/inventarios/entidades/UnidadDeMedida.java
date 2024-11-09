package com.krakedev.inventarios.entidades;

public class UnidadDeMedida {
	private String codigo;
	private String descripcion;
	private CategoriaUDM categoriaUDM;

	public UnidadDeMedida() {
		super();
	}

	public UnidadDeMedida(String codigo, String descripcion, CategoriaUDM categoriaUDM) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.categoriaUDM = categoriaUDM;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public CategoriaUDM getCategoriaUDM() {
		return categoriaUDM;
	}

	public void setCategoriaUDM(CategoriaUDM categoriaUDM) {
		this.categoriaUDM = categoriaUDM;
	}

	@Override
	public String toString() {
		return "UnidadDeMedida [codigo=" + codigo + ", descripcion=" + descripcion + ", categoriaUDM=" + categoriaUDM
				+ "]";
	}

}