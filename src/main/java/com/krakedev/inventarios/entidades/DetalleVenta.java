package com.krakedev.inventarios.entidades;

import java.math.BigDecimal;

public class DetalleVenta {
	private Venta cabecera;
	private Producto producto;
	private int cantidad;
	private BigDecimal precioVenta;
	private BigDecimal subtotal;
	private BigDecimal subtotalConIva;

	public DetalleVenta() {
		super();
	}

	public DetalleVenta(Venta cabecera, Producto producto, int cantidad, BigDecimal precioVenta, BigDecimal subtotal,
			BigDecimal subtotalConIva) {
		super();
		this.cabecera = cabecera;
		this.producto = producto;
		this.cantidad = cantidad;
		this.precioVenta = precioVenta;
		this.subtotal = subtotal;
		this.subtotalConIva = subtotalConIva;
	}

	public Venta getCabecera() {
		return cabecera;
	}

	public void setCabecera(Venta cabecera) {
		this.cabecera = cabecera;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getSubtotalConIva() {
		return subtotalConIva;
	}

	public void setSubtotalConIva(BigDecimal subtotalConIva) {
		this.subtotalConIva = subtotalConIva;
	}

	@Override
	public String toString() {
		return "DetalleVenta [cabecera=" + cabecera + ", producto=" + producto + ", cantidad=" + cantidad
				+ ", precioVenta=" + precioVenta + ", subtotal=" + subtotal + ", subtotalConIva=" + subtotalConIva
				+ "]";
	}

}