package co.gov.metropol.area247.service.impl;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.annotations.Log;
import co.gov.metropol.area247.annotations.LogReturnValue;
import co.gov.metropol.area247.logging.LoggingUtil;
import co.gov.metropol.area247.model.ArchivoDTO;
import co.gov.metropol.area247.model.CargaArchivoDTO;
import co.gov.metropol.area247.model.RuntDTO;
import co.gov.metropol.area247.repository.domain.VehiculosRunt;
import co.gov.metropol.area247.repository.domain.support.enums.EstadoArchivo;
import co.gov.metropol.area247.service.IArchivoService;
import co.gov.metropol.area247.service.ICargaArchivoService;
import co.gov.metropol.area247.service.impl.abstracts.AbstractCargaArchivoService;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class CargaArchivoServiceImpl extends AbstractCargaArchivoService implements ICargaArchivoService{	
	
	@Autowired
	private IArchivoService archivoService;
	
	@Autowired
	private LineMapper<RuntDTO> runtLineMapper;
	
	@Autowired
	private ItemWriter<VehiculosRunt> runtWriter;
	
	@Autowired
	private ItemProcessor<RuntDTO, VehiculosRunt> runtProcessor;
	
	@Autowired
	private JobBuilderFactory ejecutarJobRunt;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private JobLauncher jobLauncher;
		
	@Override
	public void procesarArchivoRunt (@Log CargaArchivoDTO cargaArchivoDTO) {
		
		try {
			
			ArchivoDTO archivoDTO = save(cargaArchivoDTO);
			
			FlatFileItemReader<RuntDTO> runtItemReader = new FlatFileItemReader<>();
			runtItemReader.setResource(new InputStreamResource(cargaArchivoDTO.getArchivo().getInputStream()));
			runtItemReader.setLinesToSkip(1);
			runtItemReader.setLineMapper(runtLineMapper);
			
			jobLauncher.run(ejecutarJobRunt(runtItemReader), new JobParameters());
			
			update(archivoDTO);
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al procesasr el archivo del RUNT %s.", cargaArchivoDTO), e);
			throw new Area247Exception(String.format("Error al procesasr el archivo del RUNT %s.", cargaArchivoDTO), e);
		}
	}
	
	@LogReturnValue
	private Job ejecutarJobRunt(FlatFileItemReader<RuntDTO> runtItemReader) {
		try {
			return ejecutarJobRunt.get("ejecutarJobRunt").start(runtStep(runtItemReader)).build();
		} catch (Exception e) {
			LoggingUtil.logException("Error al ejecutar el job para la carga del runt.", e);
			throw new Area247Exception("Error al ejecutar el job para la carga del runt.", e);
		}
		
	}
	
	@LogReturnValue
	private Step runtStep(FlatFileItemReader<RuntDTO> runtItemReader) {
		try {
			return stepBuilderFactory.get("runtStep")
					.<RuntDTO, VehiculosRunt>chunk(1000)
					.reader(runtItemReader)
					.processor(runtProcessor)
					.writer(runtWriter)
					.faultTolerant()
					.skipLimit(1000)
					.skip(Area247Exception.class)
					.allowStartIfComplete(true)
					.build();
		} catch (Exception e) {
			LoggingUtil.logException("Error al ejecutar los parsos para la carga del runt.", e);
			throw new Area247Exception("Error al ejecutar los parsos para la carga del runt.", e);
		}
		
	}
	
	@LogReturnValue
	private ArchivoDTO save(@Log CargaArchivoDTO cargaArchivoDTO) {
		try {
			ArchivoDTO archivo  = cargaArchivoDTO.getArchivoDTO();
			archivoService.save(archivo);
			
			return archivo;
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al guardar la informacion del archivo cargado %s.", cargaArchivoDTO), e);
			throw new Area247Exception(String.format("Error al guardar la informacion del archivo cargado %s.", cargaArchivoDTO), e);
		}
	}
	
	private void update(@Log ArchivoDTO archivoDTO) {
		try {
			archivoDTO.setEstado(EstadoArchivo.OK);
			archivoService.update(archivoDTO);
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al actualizar la informacion del archivo cargado %s.", archivoDTO), e);
			throw new Area247Exception(String.format("Error al actualizar la informacion del archivo cargado %s.", archivoDTO), e);
		}
		
		
	}

}
