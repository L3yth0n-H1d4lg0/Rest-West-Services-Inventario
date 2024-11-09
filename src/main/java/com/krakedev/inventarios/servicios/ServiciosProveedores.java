package com.krakedev.inventarios.servicios;

import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.ProveedoresBDD;
import com.krakedev.inventarios.entidades.Proveedor;
import com.krakedev.inventarios.excepciones.KrakeDevException;

@Path("proveedores")
public class ServiciosProveedores {

	@Path("saludar")
	@GET
	public String saludar() {
		return "Hola mundo Rest Web Services";
	}

	@Path("buscar/{sub}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar(@PathParam("sub") String subcadena) {
		ProveedoresBDD provBDD = new ProveedoresBDD();
		ArrayList<Proveedor> proveedores;

		try {
			proveedores = provBDD.buscar(subcadena);
			return Response.ok(proveedores).build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al buscar proveedores").build();
		}
	}

	@Path("crear")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crear(Proveedor proveedor) {
		ProveedoresBDD provBDD = new ProveedoresBDD();
		try {
			provBDD.insertar(proveedor);
			return Response.status(Response.Status.CREATED).entity("Proveedor creado exitosamente").build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al crear proveedor").build();
		}
	}
	
}