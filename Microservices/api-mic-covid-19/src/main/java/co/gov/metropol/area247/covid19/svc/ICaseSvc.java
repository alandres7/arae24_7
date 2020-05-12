package co.gov.metropol.area247.covid19.svc;

import java.util.List;

import co.gov.metropol.area247.covid19.model.C19Case;

public interface ICaseSvc {

	List<C19Case> getCasosCovid(String urlRecurso);

	boolean updateCasosCovid() throws Exception;

	String getNumCasos();

}
