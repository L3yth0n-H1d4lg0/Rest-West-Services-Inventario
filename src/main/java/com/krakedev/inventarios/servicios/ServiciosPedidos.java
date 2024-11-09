package com.krakedev.inventarios.servicios;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
		return "Hola mundo Rest Web Services";
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
}
