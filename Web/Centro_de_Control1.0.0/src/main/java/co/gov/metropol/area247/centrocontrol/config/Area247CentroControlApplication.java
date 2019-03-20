package co.gov.metropol.area247.centrocontrol.config;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.api.client.util.Value;

import co.gov.metropol.area247.centrocontrol.jobs.JobTarea;
import co.gov.metropol.area247.centrocontrol.model.RespuestaLogin;
import co.gov.metropol.area247.centrocontrol.model.TareaProgramada;
import co.gov.metropol.area247.centrocontrol.model.UsuarioJSON;
import co.gov.metropol.area247.centrocontrol.seguridad.service.ISeguridadTareaProgramadaService;
import co.gov.metropol.area247.centrocontrol.service.CentroControlExchange;




@EntityScan(basePackages = "co.gov.metropol.area247.centrocontrol.model")

@ComponentScan(basePackages = {
    "co.gov.metropol.area247.centrocontrol.restcontroller",
    "co.gov.metropol.area247.centrocontrol.config",
    "co.gov.metropol.area247.centrocontrol.service",
    "co.gov.metropol.area247.centrocontrol.carga.service",
    "co.gov.metropol.area247.centrocontrol.security.service",
    "co.gov.metropol.area247.centrocontrol.security.provider",
    "co.gov.metropol.area247.centrocontrol.security.repository",
    "co.gov.metropol.area247.centrocontrol.seguridad.dao",
    "co.gov.metropol.area247.centrocontrol.seguridad.service",
    "co.gov.metropol.area247.centrocontrol.seguridad.service.impl",
    "co.gov.metropol.area247.centrocontrol.carga.task",
    "co.gov.metropol.area247.centrocontrol.carga.gateway"
})

@EnableJpaRepositories(basePackages = {
    "co.gov.metropol.area247.centrocontrol.repository",
	"co.gov.metropol.area247.centrocontrol.seguridad.dao"
})




/** Método a través del cual inicia nuestro aplicativo */
@SpringBootApplication
@EnableScheduling
public class Area247CentroControlApplication {
	
	@Autowired
	private ISeguridadTareaProgramadaService servicioTareasProgramadas;
	
	@Autowired
	private Environment env;
	

	public static void main(String[] args) {
	    SpringApplication.run(Area247CentroControlApplication.class, args);		  		
	}
	
	@Bean
    @Primary
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {						
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        return builder.modulesToInstall(new JavaTimeModule());
    }
	
	
	
		
	@Bean
	public boolean programarTareasGuardadas(){
		
		List<TareaProgramada> listaTareas = null;
		try {
			listaTareas = servicioTareasProgramadas.findByActivo(true);		

            if(!listaTareas.isEmpty() && listaTareas!=null){	
            	           	       	       		
			    for (TareaProgramada tarea : listaTareas) {			    	
			    	LocalDateTime now = LocalDateTime.now();  			    					
			    	LocalDateTime fechaIni = LocalDateTime.ofInstant(tarea.getFechaIni().toInstant(),ZoneId.systemDefault());

			    	String cronExpression = "";
			    	
			    	String seg="59",min,hor,day,mon,yea;
			    	
			    	if((fechaIni).isBefore(now)) {
			    		LocalDateTime tomorrow = now.plusDays(1);			    		
			    		min=""+fechaIni.getMinute();
					    hor=""+fechaIni.getHour();
					    day=""+tomorrow.getDayOfMonth();
					    mon=""+tomorrow.getMonthValue();
					    yea=""+tomorrow.getYear();
			    	}else {
			    		min=""+fechaIni.getMinute();
					    hor=""+fechaIni.getHour();
					    day=""+fechaIni.getDayOfMonth();
					    mon=""+fechaIni.getMonthValue();
					    yea=""+fechaIni.getYear();			    					    		
			    	}
			        
			    	switch(tarea.getUnidad()) {		
			            case "Minutos":  
			            	min = min + "/" + tarea.getIntervalo();
	                    break;
	                
			            case "Horas":  
			            	hor = hor + "/" + tarea.getIntervalo();
	                    break;
	                    
			            case "Dias":  
			            	day = day + "/" + tarea.getIntervalo();
	                    break;
	                    
			            case "Meses":  
			                mon = mon + "/" + tarea.getIntervalo();
	                    break;
	                    
			            case "Anios":  
			            	yea = yea + "/" + tarea.getIntervalo();
	                    break;				
			        }
			        
			        cronExpression = seg + " " + min + " " + hor + " " + day + " " + mon + " " + "?" + " " + yea;
			        			        
			        String id = "" + tarea.getIdRecurso();	
			        String tipoRecurso = "" + tarea.getTipoRecurso();	
			        String idAplicacion = "" + tarea.getIdAplicacion();			        			        
			        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
				    
				    String jobDetailIden = "";
				    String jobDetailGroup = "";
				    String cronTriggIden = "";
				    String cronTriggGroup = "";
				    
				    if(tipoRecurso.equals("CAPA")) {
				    	jobDetailIden = "jobCapa_"+id;
					    jobDetailGroup = "groupCapa_"+id;
					    cronTriggIden = "triggerCapa_"+id;
					    cronTriggGroup = "groupCapa_"+id;	
					}else {
						jobDetailIden = "jobCateg_"+id;
					    jobDetailGroup = "groupCateg_"+id;
					    cronTriggIden = "triggerCateg_"+id;
					    cronTriggGroup = "groupCateg_"+id;	
					}	
				    
				    JobDetail job = JobBuilder.newJob(JobTarea.class)  
				        .withIdentity(jobDetailIden,jobDetailGroup)
				        .usingJobData("idRecurso",id)
				        .usingJobData("tipoRecurso",tipoRecurso)
				        .usingJobData("idAplicacion",idAplicacion)
			            .usingJobData("serverEndpoint",env.getProperty("server.endpoints")).build();
				    
				    CronTrigger trigger = TriggerBuilder.newTrigger()
				    	.withIdentity(cronTriggIden,cronTriggGroup).startNow()
				    	.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
				    	.build();
	                			        
			        try {			        	
	                    scheduler.scheduleJob(job,trigger);	                    	                
	                } catch (Exception e) {
	                	System.out.print("Error en el proceso"); 
	                	return false;
	                }
			        
			        scheduler.start();	
			    }	
			    return true;
			} else {
				System.out.println("No hay tareas guardadas para reactivar."); 
				return false;
			}
			
		} catch (Exception e1) {
			System.out.print("Error en el proceso: " + e1);
			return false;
		}		
	}
	
	
	

}