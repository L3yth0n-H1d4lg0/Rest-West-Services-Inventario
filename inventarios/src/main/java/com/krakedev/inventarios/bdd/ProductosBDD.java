package com.krakedev.inventarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.Categoria;
import com.krakedev.inventarios.entidades.Producto;
import com.krakedev.inventarios.entidades.UnidadDeMedida;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class ProductosBDD {

	public ArrayList<Producto> buscar(String subcadena) throws KrakeDevException {
		ArrayList<Producto> productos = new ArrayList<Producto>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Producto producto = null;
		System.out.println("Entrando al método");

		try {
			con = ConexionBDD.obtenerConexion();
			System.out.println("Conexión exitosa");
			ps = con.prepareStatement(
					"select prod.codigo, prod.nombre as nombre_producto, udm.codigo as nombre_udm, udm.descripcion as descripcion_udm, cast(prod.precio_venta as decimal(6,2)), prod.tiene_iva , cast(prod.coste as decimal(5,4)), prod.categoria, cat.nombre as nombre_categoria, stock "
							+ "from productos prod, unidades_medida udm, categorias cat "
							+ "where (prod.unidad_medida= udm.codigo) and (prod.categoria = cat.codigo) and (upper(prod.nombre)) like ?");
			ps.setString(1, "%" + subcadena.toUpperCase() + "%");
			System.out.println("Enviando consulta");
			rs = ps.executeQuery();

			while (rs.next()) {
				System.out.println("Extrayendo");
				int codigoProducto = rs.getInt("codigo");
				String nombreProducto = rs.getString("nombre_producto");
				String nombreUnidadMedida = rs.getString("nombre_udm");
				String descripcionUnidadMedida = rs.getString("descripcion_udm");
				BigDecimal precioVenta = rs.getBigDecimal("precio_venta");
				boolean tieneIva = rs.getBoolean("tiene_iva");
				BigDecimal coste = rs.getBigDecimal("coste");
				int codigoCategoria = rs.getInt("categoria");
				String nombreCategoria = rs.getString("nombre_categoria");
				int stock = rs.getInt("stock");

				UnidadDeMedida udm = new UnidadDeMedida();
				udm.setCodigo(nombreUnidadMedida);
				udm.setDescripcion(descripcionUnidadMedida);

				Categoria categoria = new Categoria();
				categoria.setCodigo(codigoCategoria);
				categoria.setNombre(nombreCategoria);
				System.out.println("Seteado");
				producto = new Producto(codigoProducto, nombreProducto, udm, precioVenta, tieneIva, coste, categoria,
						stock);
				productos.add(producto);
			}

		} catch (KrakeDevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al consultar. Detalle: " + e.getMessage());
		} finally {
			if (rs != null)
				try {
					rs.close();
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

		return productos;
	}
}
