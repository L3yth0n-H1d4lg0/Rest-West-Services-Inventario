package com.krakedev.inventarios.servicios;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.TipoDocumentosBDD;
import com.krakedev.inventarios.entidades.TipoDocumento;
import com.krakedev.inventarios.excepciones.KrakeDevException;

@Path("tiposdocumento")
public class ServiciosTipoDocumentos {

	@Path("recuperar")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response probarRecuperar() {
		TipoDocumentosBDD tdbdd = new TipoDocumentosBDD();
		ArrayList<TipoDocumento> tipoDocumentos = null;

		try {
			tipoDocumentos = tdbdd.recuperarTodos();
			return Response.ok(tipoDocumentos).build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

}