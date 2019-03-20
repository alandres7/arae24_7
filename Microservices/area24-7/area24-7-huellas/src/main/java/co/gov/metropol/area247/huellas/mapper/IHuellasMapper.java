package co.gov.metropol.area247.huellas.mapper;

public interface IHuellasMapper<T, S> {
	
	public T modelToEntity(S model);
	
	public S entityToModel(T entity);
	
	public S entityToModelDiscriminated(T entity);
	
}
