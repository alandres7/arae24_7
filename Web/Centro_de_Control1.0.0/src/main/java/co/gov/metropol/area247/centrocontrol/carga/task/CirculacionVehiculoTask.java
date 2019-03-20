package co.gov.metropol.area247.centrocontrol.carga.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import co.gov.metropol.area247.centrocontrol.carga.service.ICirculacionVehiculoService;

@Component
public class CirculacionVehiculoTask {
 
    private static Logger LOGGER = LoggerFactory.getLogger(CirculacionVehiculoTask.class);
 
    @Autowired
    private ICirculacionVehiculoService service;
 
    @Scheduled(fixedRateString = "${area247.carga.circulacion.task}")
    public void consultarServicioMetro() {
        LOGGER.info("Ejecutando Tarea carga de circulacion de vehiculos");
        //service.procesarArchivo();
        service.cargarCirculacionVehiculo();
    }

}