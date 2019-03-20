package co.gov.metropol.area247.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.gov.metropol.area247.batch.processor.RuntItemProcessor;
import co.gov.metropol.area247.batch.writer.RuntItemWriter;
import co.gov.metropol.area247.model.RuntDTO;
import co.gov.metropol.area247.repository.domain.VehiculosRunt;

@Configuration
@EnableBatchProcessing
public class CsvFileReader {
	
	
	@Autowired
	public JobBuilderFactory job;
	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Bean(name = "runtLineMapper")
	public LineMapper<RuntDTO> createRuntLineMapper (){
		DefaultLineMapper<RuntDTO> runtLineMapper = new DefaultLineMapper<>();
		
		runtLineMapper.setLineTokenizer(createRuntTokenizer());
		runtLineMapper.setFieldSetMapper(createRuntInformationMapper());
		
		return runtLineMapper;
	}
	
	@Bean(name = "runtTokenizer")
	public LineTokenizer createRuntTokenizer() {
		DelimitedLineTokenizer runtTokenizer = new DelimitedLineTokenizer();
		runtTokenizer.setDelimiter(",");
		runtTokenizer.setNames(new String[] {"placa" , "modelo" , "tipoConbustible" , "ejes" , "capacidadCarga" , "organizmoTransito", "clase"});
		return runtTokenizer;
	}
	
	@Bean(name = "runtInformationMapper")
	public FieldSetMapper<RuntDTO> createRuntInformationMapper (){
		BeanWrapperFieldSetMapper<RuntDTO> runtInformationMapper = new BeanWrapperFieldSetMapper<>();
		runtInformationMapper.setTargetType(RuntDTO.class);
		return runtInformationMapper;
	}
	
	@Bean(name = "runtWriter")
	public ItemWriter<VehiculosRunt> runtWriter (){
		return new RuntItemWriter();
	}
	
	@Bean(name = "runtProcessor")
	public ItemProcessor<RuntDTO, VehiculosRunt> runtProcessor(){
		return new RuntItemProcessor();
	}
}
