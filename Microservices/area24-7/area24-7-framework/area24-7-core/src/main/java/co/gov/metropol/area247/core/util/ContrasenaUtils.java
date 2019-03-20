package co.gov.metropol.area247.core.util;

import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;

public class ContrasenaUtils {
	
	public static final String NUMEROS = "0123456789";
	public static final String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
	public static final String ESPECIALES = "*+@#$%&/=_:";
	public static final int TAMANIO_PSSWRD = 8;
	public static final String CADENA_VACIA = "";
	public static final String GUION = "-";
	
	public static String genContrasenaSystem(){ 
		Random random = new Random();
		int cantOpcionGen = 4;
		int opcionGenAleatoria = random.nextInt(cantOpcionGen);
		String genPass;
		switch(opcionGenAleatoria){
			case 0:
				genPass = genContrasenaTradicional();
				break;
			case 1:
				genPass = genContrasenaUUID();
				break;
			case 2:
				genPass = genContrasenaRNDSUtilsV2();
				break;
			case 3:
			default:
				genPass = genContrasenaRNDSUtilsV1();
				break;
		}
		return genPass;
	}
	
	private static String genContrasenaTradicional(){
		String clave = NUMEROS + MAYUSCULAS + MINUSCULAS + ESPECIALES; 
		Random random = new Random();
		StringBuilder contrasenaGen = new StringBuilder(CADENA_VACIA);
		for (int i = 0; i < TAMANIO_PSSWRD; i++) {
			contrasenaGen.append(clave.charAt((random.nextInt(clave.length()))));
		}
		return contrasenaGen.toString();
	}
	
	private static String genContrasenaUUID() {
		String idUnico = UUID.randomUUID().toString();
		String idUnicoGuion = idUnico.replace(GUION, CADENA_VACIA);
		Random random = new Random();
		int caracteresUUID = 32;
		int numRandom = random.nextInt(caracteresUUID);
		int topeInfRandom = 8;
		int topeSupRandom = 32 - 7;
		String clave = "";
		if (numRandom < topeInfRandom) {
			clave = idUnicoGuion.substring(numRandom, numRandom + topeInfRandom);
		} else if (numRandom > topeSupRandom) {
			clave = idUnicoGuion.substring(numRandom - topeInfRandom, numRandom);
		} else {
			int cantDireccion = 2;
			int randomDireccion = random.nextInt(cantDireccion);
			if (randomDireccion == 0) {
				clave = idUnicoGuion.substring(numRandom, numRandom + topeInfRandom);
			} else {
				clave = idUnicoGuion.substring(numRandom - topeInfRandom, numRandom);
			}
		}
		return clave;
	}
	
	private static String genContrasenaRNDSUtilsV1(){
		return RandomStringUtils.randomAlphanumeric(TAMANIO_PSSWRD);
	}
	
	private static String genContrasenaRNDSUtilsV2(){
		return RandomStringUtils.random(TAMANIO_PSSWRD, NUMEROS + MAYUSCULAS + MINUSCULAS + ESPECIALES);
	}
	
	
}
