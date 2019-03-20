package co.gov.metropol.area247.contenedora.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.contenedora.dao.IContenedoraCapaRepository;
import co.gov.metropol.area247.contenedora.mapper.IContenedoraMapper;
import co.gov.metropol.area247.contenedora.model.Capa;
import co.gov.metropol.area247.contenedora.model.Categoria;
import co.gov.metropol.area247.contenedora.model.dto.CapaDto;
import co.gov.metropol.area247.contenedora.service.IContenedoraCapaNService;
import co.gov.metropol.area247.contenedora.util.NivelCapa;
import co.gov.metropol.area247.core.domain.capa.dto.CapaN;

@Service
public class IContenedoraCapaNServiceImpl implements IContenedoraCapaNService {
	
	@Autowired
	IContenedoraCapaRepository capaDao;
	
	private Capa capaAux;
	
	@Value("${iconos.server.url}")
	private String urlIconos;
	
	@Autowired
	@Qualifier("capaLteDtoMapper")
	IContenedoraMapper<CapaDto, CapaN> capaNMapperDtoS;
	
	@Autowired
	@Qualifier("categoriaLteMapper")
	IContenedoraMapper<Categoria, CapaN> categoriaLteMappper;
	
	private static final int NIVEL_CAPA = 2;
	private static final int NIVEL_CATEGORIA = 3;
	
	
	@Override
	public List<CapaN> getCapaN(NivelCapa nivelCapa, Long idCapaN) {
		int nivel = nivelCapa.getNivel();
		List<CapaN> capasNDto = new ArrayList<>();
		switch(nivel) {
			case NIVEL_CAPA:
				List<CapaDto> capasDtoSinListas = capaDao.capaDtoObtenerPorIdAplicacion(idCapaN);
				capasDtoSinListas.forEach(capaDtoSinListas -> {
					CapaN dto = capaNMapperDtoS.entityToModel(capaDtoSinListas);
					dto.setRutaIconoCapa(urlIconos+capaDtoSinListas.getIcono().getId());
					if(capaDtoSinListas.getIconoMarcador()!=null) {
						dto.setRutaIconoMarker(urlIconos+capaDtoSinListas.getIconoMarcador().getId());
					}
					capasNDto.add(dto);
				});
				break;
			case NIVEL_CATEGORIA:
				capaAux = capaDao.findOne(idCapaN);
				List<Categoria> categoriasAux = capaAux.getCategorias();
				categoriasAux.forEach(categoria->{
					CapaN dto = new CapaN();
					dto.setId(categoria.getId());
					dto.setNombre(categoria.getNombre());
					dto.setNombreTipoCapa(categoria.getTipoCategoria().getNombre());
					if(categoria.getIcono()!=null) {
					   dto.setRutaIconoCapa(urlIconos+categoria.getIcono().getId());
					}
					if(categoria.getIconoMarcador()!=null) {
						dto.setRutaIconoMarker(urlIconos+categoria.getIconoMarcador().getId());
					}
					capasNDto.add(dto);
					});
				break;			
		}
		
		return capasNDto;
	}

}
