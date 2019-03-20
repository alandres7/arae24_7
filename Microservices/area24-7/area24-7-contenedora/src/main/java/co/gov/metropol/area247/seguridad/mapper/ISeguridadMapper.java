package co.gov.metropol.area247.seguridad.mapper;

/**
 * 
 * @author ageofuentes
 *
 * @param <T>
 * @param <S>
 */
public interface ISeguridadMapper<T,S> {
	
	public T modelToEntity(S model);
	
	public S entityToModel(T entity);
	
}
