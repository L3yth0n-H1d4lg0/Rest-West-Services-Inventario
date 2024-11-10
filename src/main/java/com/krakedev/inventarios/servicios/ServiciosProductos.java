package com.krakedev.inventarios.servicios;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.ProductosBDD;
import com.krakedev.inventarios.entidades.Producto;
import com.krakedev.inventarios.excepciones.KrakeDevException;

@Path("productos")
public class ServiciosProductos {

	@Path("saludar")
	@GET
	public String saludar() {
		return "Servicios Productos";
	}

	@Path("buscar/{sub}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar(@PathParam("sub") String subcadena) {
		ProductosBDD prodBDD = new ProductosBDD();
		ArrayList<Producto> productos;

		try {
			productos = prodBDD.buscar(subcadena);
			return Response.ok(productos).build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al buscar productos").build();
		}
	}

	@Path("crear")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crear(Producto producto) {
		ProductosBDD prodBDD = new ProductosBDD();
		try {
			prodBDD.insertar(producto);
			return Response.status(Response.Status.CREATED).entity("Producto creado exitosamente").build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al crear producto").build();
		}
	}

	@Path("actualizar")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response actualizar(Producto producto) {
		ProductosBDD prodBDD = new ProductosBDD();
		try {
			prodBDD.actualizar(producto);
			return Response.status(Response.Status.CREATED).entity("Producto actualizado exitosamente").build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al actualizar producto")
					.build();
		}
	}

	@Path("buscarPorId/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorId(@PathParam("id") int codigo) {
		ProductosBDD prodBDD = new ProductosBDD();
		Producto producto;

		try {
			producto = prodBDD.buscarPorId(codigo);
			return Response.ok(producto).build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al buscar el producto").build();
		}
	}

}
