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

import com.krakedev.inventarios.bdd.PedidosBDD;
import com.krakedev.inventarios.entidades.Pedido;
import com.krakedev.inventarios.excepciones.KrakeDevException;

@Path("pedidos")
public class ServiciosPedidos {

	@Path("saludar")
	@GET
	public String saludar() {
		return "Servicios Pedidos";
	}

	@Path("registrar")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crear(Pedido pedido) {
		PedidosBDD provBDD = new PedidosBDD();
		try {
			provBDD.insertar(pedido);
			return Response.status(Response.Status.CREATED).entity("Pedido registrado exitosamente").build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al registrar el pedido")
					.build();
		}
	}

	@Path("recibir")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response actualizar(Pedido pedido) {
		PedidosBDD pedidoBDD = new PedidosBDD();
		try {
			pedidoBDD.actualizar(pedido);
			return Response.status(Response.Status.CREATED).entity("Pedido actualizado exitosamente").build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@Path("buscar/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar(@PathParam("id") String idProveedor) {
		PedidosBDD pedidoBDD = new PedidosBDD();
		ArrayList<Pedido> pedidos;

		try {
			pedidos = pedidoBDD.buscar(idProveedor);
			return Response.ok(pedidos).build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al buscar pedidos").build();
		}
	}

}
