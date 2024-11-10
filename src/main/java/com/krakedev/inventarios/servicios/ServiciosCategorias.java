package com.krakedev.inventarios.servicios;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.CategoriasBDD;
import com.krakedev.inventarios.entidades.Categoria;
import com.krakedev.inventarios.excepciones.KrakeDevException;

@Path("categorias")
public class ServiciosCategorias {

	@Path("saludar")
	@GET
	public String saludar() {
		return "Servicios Categoria";
	}

	@Path("crear")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertar(Categoria categoria) {
		CategoriasBDD catBDD = new CategoriasBDD();
		try {
			catBDD.insertar(categoria);
			return Response.status(Response.Status.CREATED).entity("Categoría creada exitosamente").build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al crear categoria").build();
		}
	}

	@Path("actualizar")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response actualizar(Categoria categoria) {
		CategoriasBDD catBDD = new CategoriasBDD();
		try {
			catBDD.actualizar(categoria);
			return Response.status(Response.Status.CREATED).entity("Categoría actualizada exitosamente").build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al actualizar categoria")
					.build();
		}
	}

	@Path("probarRecuperar")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response probarRecuperar() {
		CategoriasBDD catBDD = new CategoriasBDD();
		ArrayList<Categoria> categorias = null;

		try {
			categorias = catBDD.recuperarTodos();
			return Response.ok(categorias).build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al recuperar categorias")
					.build();
		}
	}
}
