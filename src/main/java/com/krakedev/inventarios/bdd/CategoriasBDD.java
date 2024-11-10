package com.krakedev.inventarios.bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.Categoria;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class CategoriasBDD {

	public void insertar(Categoria categoria) throws KrakeDevException {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("insert into categorias(nombre, categoria_padre) " + "values(?,?)");
			ps.setString(1, categoria.getNombre());
			ps.setInt(2, categoria.getCategoriaPadre().getCodigo());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al insertar la categoría. Detalle: " + e.getMessage());
		} catch (KrakeDevException e) {
			throw e;
		} finally {
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void actualizar(Categoria categoria) throws KrakeDevException {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("update categorias set nombre = ?, categoria_padre = ? where codigo = ?");
			ps.setString(1, categoria.getNombre());
			ps.setInt(2, categoria.getCategoriaPadre().getCodigo());
			ps.setInt(3, categoria.getCodigo());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al actualizar el producto. Detalle: " + e.getMessage());
		} catch (KrakeDevException e) {
			throw e;
		} finally {
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

	public ArrayList<Categoria> recuperarTodos() throws KrakeDevException {
		ArrayList<Categoria> categorias = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("select codigo, nombre, categoria_padre from categorias");
			rs = ps.executeQuery();

			while (rs.next()) {
				int codigo = rs.getInt("codigo");
				String nombre = rs.getString("nombre");
				Integer codigoPadre = rs.getObject("categoria_padre", Integer.class);

				Categoria categoriaPadre = null;

				if (codigoPadre != null) {
					PreparedStatement psPadre = con.prepareStatement("select nombre from categorias where codigo = ?");
					psPadre.setInt(1, codigoPadre);
					ResultSet rsPadre = psPadre.executeQuery();

					if (rsPadre.next()) {
						String nombrePadre = rsPadre.getString("nombre");
						categoriaPadre = new Categoria(codigoPadre, nombrePadre);

					}

					rsPadre.close();
					psPadre.close();
				}

				Categoria categoria = new Categoria(codigo, nombre, categoriaPadre);
				categorias.add(categoria);
			}

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

		return categorias;
	}

}
