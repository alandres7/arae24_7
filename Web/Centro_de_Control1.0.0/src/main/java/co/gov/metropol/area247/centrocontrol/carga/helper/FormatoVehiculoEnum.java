package co.gov.metropol.area247.centrocontrol.carga.helper;

/**
 * 
 * El orden de los elementos del Enum debe coincidir con el orden de los campos en el archivo csv de carga
 *
 */
public enum FormatoVehiculoEnum {
	S_NRO_PLACA,
	N_MODELO,
	S_DESC_COMBUTIBLE,
	EJES, //Va en el archivo, pero no en la tabla
	N_CAP_TONELADAS,
	ORGANISMO_TRANSITO, //Va en el archivo, pero no en la tabla
	S_DESC_CLASE,	
	S_DESC_CARROCERIA,
	S_DESC_MARCA,
	FTH, //Va en el archivo, pero no en la tabla
	TIPO_FTH //Va en el archivo, pero no en la tabla
//	ID_CARROCERIA, 
//	ID_CLASE, 
//	ID_COMBUSTIBLE, 
//	ID_MARCA 
	;
}
