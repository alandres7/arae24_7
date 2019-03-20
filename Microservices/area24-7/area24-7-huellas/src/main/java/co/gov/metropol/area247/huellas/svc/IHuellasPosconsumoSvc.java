/**
 * 
 */
package co.gov.metropol.area247.huellas.svc;

/**
 * @author ageo.fuentes@ada.co
 * @see
 *
 */
public interface IHuellasPosconsumoSvc {

	boolean updatePuntosPosconsumo(Long idCapa);

	String getDetailXIdMarcador(long idMarcador) throws Exception;
	
}
