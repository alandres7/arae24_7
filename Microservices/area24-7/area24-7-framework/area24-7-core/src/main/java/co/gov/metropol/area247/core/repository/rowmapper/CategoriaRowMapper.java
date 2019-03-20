package co.gov.metropol.area247.core.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import co.gov.metropol.area247.core.domain.context.web.Capa;
import co.gov.metropol.area247.core.domain.context.web.Categoria;

public class CategoriaRowMapper implements RowMapper<Categoria> {

	@Override
	public Categoria mapRow(ResultSet rs, int rowNum) throws SQLException {
		Categoria categoria = new Categoria();
		categoria.setIdCategoria(rs.getLong("ID_CATEGORIA"));
		categoria.setNombre(rs.getString("NOMBRE_CATEGORIA"));
		categoria.setCapa(new Capa());
		categoria.getCapa().setId(rs.getLong("ID_CAPA"));
		categoria.getCapa().setNombre(rs.getString("NOMBRE_CAPA"));
		return categoria;
	}

}
