package com.krakedev.inventarios.servicios;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.VentasBDD;
import com.krakedev.inventarios.entidades.Venta;
import com.krakedev.inventarios.excepciones.KrakeDevException;

@Path("ventas")
public class ServiciosVentas {

	@Path("saludar")
	@GET
	public String saludar() {
		return "Servicios Ventas";
	}

	@Path("guardar")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crear(Venta venta) {
		VentasBDD venBDD = new VentasBDD();
		try {
			venBDD.insertar(venta);
			return Response.status(Response.Status.CREATED).entity("Venta registrada exitosamente").build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al registrar la venta").build();
		}
	}

}