package co.gov.metropol.area247.contenedora.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.contenedora.model.Marcador;

@Repository
public interface IContenedoraMarcadorRepository extends CrudRepository<Marcador, Long> {

	Marcador findByNombre(String nombre);
	
	@Query(name="obtenerPorCodigoPorCapa", value="SELECT D247CON_MARCADOR.*\n" + 
			"FROM D247CON_MARCADOR\n" + 
			"JOIN D247CON_CAPA_MARCADOR\n" + 
			"ON D247CON_MARCADOR.ID = D247CON_CAPA_MARCADOR.ID_MARCADOR\n" + 
			"WHERE D247CON_CAPA_MARCADOR.ID_CAPA = :idCapa AND D247CON_MARCADOR.N_CODIGO = :codigo", nativeQuery=true)
	List<Marcador> encontrarPorCodigoYCapa(@Param("codigo")int codigo, @Param("idCapa")Long idCapa);

	@Query(name = "obtenerMarcadorPorCategoriaCodigo", value = "SELECT\n" + "    d247con_marcador.*\n" + "FROM\n"
			+ "    d247con_marcador\n"
			+ "    INNER JOIN d247con_categoria_marcador ON d247con_marcador.id = d247con_categoria_marcador.id_marcador\n"
			+ "    INNER JOIN d247con_categoria ON d247con_categoria.id = d247con_categoria_marcador.id_categoria\n"
			+ "    WHERE d247con_categoria.id = :idCategoria and d247con_categoria_marcador.n_codigo = :codigo", nativeQuery = true)
	Marcador obtenerMarcadorPorCategoriaCodigo(@Param("idCategoria") final Long idCategoria,
			@Param("codigo") final int codigo);

	@Query(name = "obtenerMarcadorPorSubCategoriaCodigo", value = "SELECT " + "    d247con_marcador.*\n" + "FROM\n"
			+ "    d247con_marcador "
			+ "    INNER JOIN d247con_subcategoria_marcador ON d247con_subcategoria_marcador.id_marcador = d247con_marcador.id "
			+ "    INNER JOIN d247con_subcategoria ON d247con_subcategoria.id = d247con_subcategoria_marcador.id_subcategoria "
			+ "    WHERE d247con_subcategoria.id = :idSubCategoria and d247con_categoria_marcador.n_codigo = :codigo", nativeQuery = true)
	Marcador obtenerMarcadorPorSubCategoriaCodigo(@Param("idSubCategoria") final Long idSubCategoria,
			@Param("codigo") final int codigo);
	
	
	@Query(name="obtenerPorCapaSinMunicipio", 
			value="SELECT D247CON_MARCADOR.* FROM D247CON_MARCADOR LEFT JOIN D247CON_CATEGORIA \n" + 
				  "ON D247CON_MARCADOR.ID_CATEGORIA = D247CON_CATEGORIA.ID \n" + 
				  "WHERE (D247CON_MARCADOR.ID_CAPA = :idCapa OR D247CON_CATEGORIA.ID_CAPA = :idCapa) \n" +
				  "AND D247CON_MARCADOR.S_MUNICIPIO IS NULL", nativeQuery=true)
	List<Marcador> obtenerPorCapaSinMunicipio(@Param("idCapa")Long idCapa);
	
	
	@Query(name="obtenerPorIdCapa", 
			value="SELECT D247CON_MARCADOR.* FROM D247CON_MARCADOR LEFT JOIN D247CON_CATEGORIA \n" + 
				  "ON D247CON_MARCADOR.ID_CATEGORIA = D247CON_CATEGORIA.ID \n" + 
				  "WHERE (D247CON_MARCADOR.ID_CAPA = :idCapa OR D247CON_CATEGORIA.ID_CAPA = :idCapa) ", nativeQuery=true)
	List<Marcador> obtenerPorIdCapa(@Param("idCapa")Long idCapa);
	
	
	@Query(name="obtenerPorIdCategoria", 
			value="SELECT D247CON_MARCADOR.* FROM D247CON_MARCADOR \n" +  
				  "WHERE  D247CON_MARCADOR.ID_CATEGORIA = :idCategoria ", nativeQuery=true)
	List<Marcador> obtenerPorIdCategoria(@Param("idCategoria")Long idCategoria);


}
