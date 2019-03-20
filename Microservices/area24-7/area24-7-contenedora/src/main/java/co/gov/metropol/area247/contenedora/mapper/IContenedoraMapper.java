package co.gov.metropol.area247.contenedora.mapper;

public interface IContenedoraMapper<T,S> {
	
	public T modelToEntity(S model);
	
	public S entityToModel(T entity);
}
