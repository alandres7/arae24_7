package co.gov.metropol.area247.centrocontrol.restcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import co.gov.metropol.area247.centrocontrol.carga.service.ICirculacionVehiculoService;
import co.gov.metropol.area247.centrocontrol.dto.CirculacionVehiculoDto;

@Controller
@RequestMapping("/circulacionVehiculo")
public class CirculacionVehiculoController {

 private static Logger LOGGER = LoggerFactory.getLogger(CirculacionVehiculoController.class);

 @Autowired
 private ICirculacionVehiculoService circulacionVehiculoService;

 @RequestMapping(value = "/addInfo", method = RequestMethod.POST)
 public ResponseEntity<String> cargar(@RequestBody CirculacionVehiculoDto circulacionVehiculo) {
  for (CirculacionVehiculoDto circulacion : circulacionVehiculo) {
   if (!((circulacion.getFecha() == null) || (circulacion.getCodigoDireccion() == 0)
     || (circulacion.getCarril() == "") || (circulacion.getFechaPaso() == null)
     || (circulacion.getVelocidadMedida() == 0) || (circulacion.getPlaca() == null)
     || (circulacion.getFechaRegistro() == null) || (circulacion.getFechaDia() == 0)
     || (circulacion.getFechaHora() == 0) || (circulacion.getIdSecretaria() == null))) {

    LOGGER.info("Procesando información de circulación vehículos...");
    circulacionVehiculoService.save(circulacion.getTokenAutenticacion(), circulacion);
   }
  }

  return new ResponseEntity<String>("Exito", HttpStatus.CREATED);
 }
 
 @RequestMapping(value = "/add", method = RequestMethod.POST)
 public ResponseEntity<String> cargarInfo(@RequestBody CirculacionVehiculoDto circulacionVehiculo) {
  circulacionVehiculoService.saveFile(circulacionVehiculo);
  return new ResponseEntity<String>("Exito", HttpStatus.CREATED);
 }

}