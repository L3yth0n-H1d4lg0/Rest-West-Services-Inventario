package com.krakedev.inventarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.krakedev.inventarios.entidades.DetalleVenta;
import com.krakedev.inventarios.entidades.Venta;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class VentasBDD {

	public void insertar(Venta venta) throws KrakeDevException {
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement psDet = null;
		PreparedStatement psHis = null;
		ResultSet rsClave = null;
		int codigoCabecera = 0;

		Date fechaActual = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fechaSinMilisegundos = sdf.format(fechaActual);
		Timestamp fechaHoraActual = Timestamp.valueOf(fechaSinMilisegundos);

		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement(
					"insert into cabecera_ventas(fecha, total_sin_iva, iva, total) " + "values(?,?,?,?);",
					Statement.RETURN_GENERATED_KEYS);
			ps.setTimestamp(1, fechaHoraActual);
			ps.setBigDecimal(2, BigDecimal.ZERO);
			ps.setBigDecimal(3, BigDecimal.ZERO);
			ps.setBigDecimal(4, BigDecimal.ZERO);
			ps.executeUpdate();

			rsClave = ps.getGeneratedKeys();

			if (rsClave.next()) {
				codigoCabecera = rsClave.getInt(1);
			}

			ps.close();

			BigDecimal totalSinIva = BigDecimal.ZERO;
			BigDecimal totalConIva = BigDecimal.ZERO;

			ArrayList<DetalleVenta> detallesVenta = venta.getDetalles();

			DetalleVenta det;
			for (int i = 0; i < detallesVenta.size(); i++) {
				det = detallesVenta.get(i);
				psDet = con.prepareStatement(
						"insert into detalle_ventas(cabecera_ventas, producto, cantidad, precio_venta, subtotal, subtotal_con_iva) "
								+ "values(?,?,?,?,?,?)");
				psDet.setInt(1, codigoCabecera);
				psDet.setInt(2, det.getProducto().getCodigo());
				psDet.setInt(3, det.getCantidad());
				psDet.setBigDecimal(4, det.getProducto().getPrecioVenta());

				BigDecimal pv = det.getProducto().getPrecioVenta();
				BigDecimal cantidad = new BigDecimal(det.getCantidad());
				BigDecimal subtotal = pv.multiply(cantidad);

				psDet.setBigDecimal(5, subtotal);

				BigDecimal subtotalConIva;
				if (det.getProducto().isTieneIva()) {
					subtotalConIva = subtotal.multiply(new BigDecimal("1.12"));
				} else {
					subtotalConIva = subtotal;
				}

				psDet.setBigDecimal(6, subtotalConIva);

				psDet.executeUpdate();
				psDet.close();

				totalSinIva = totalSinIva.add(subtotal);
				totalConIva = totalConIva.add(subtotalConIva);

				psHis = con.prepareStatement(
						"insert into historial_stock(fecha, referencia, producto, cantidad) values(?, ?, ?, ?)");
				psHis.setTimestamp(1, fechaHoraActual);
				psHis.setString(2, "Venta " + codigoCabecera);
				psHis.setInt(3, det.getProducto().getCodigo());
				psHis.setInt(4, -1);
				psHis.executeUpdate();
				psHis.close();
			}

			BigDecimal ivaTotal = totalConIva.subtract(totalSinIva);

			ps = con.prepareStatement(
					"update cabecera_ventas set total_sin_iva = ?, iva = ?, total = ? where codigo = ?");
			ps.setBigDecimal(1, totalSinIva);
			ps.setBigDecimal(2, ivaTotal);
			ps.setBigDecimal(3, totalConIva);
			ps.setInt(4, codigoCabecera);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al registrar la venta. Detalle: " + e.getMessage());
		} catch (KrakeDevException e) {
			throw e;
		} finally {
			if (rsClave != null)
				try {
					rsClave.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (psHis != null)
				try {
					psHis.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (psDet != null)
				try {
					psDet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

}
