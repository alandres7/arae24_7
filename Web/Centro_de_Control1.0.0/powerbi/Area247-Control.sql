--BD: Area247-Control

CREATE SEQUENCE  HIBERNATE_SEQUENCE MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;

CREATE TABLE T247CAR_FORMATO_CARGA 
(
  ID NUMBER(19,0) NOT NULL 
,S_NOMBRE VARCHAR2(100) NOT NULL 
,S_SEPARADOR VARCHAR2(1)
,N_LINEAS_ENCABEZADO NUMBER(4,0)
,S_FORMATO_FECHA VARCHAR2(30)
,N_MAX_BYTES NUMBER(10,0)
,N_MAX_ERRORES NUMBER(4,0)
, CONSTRAINT PK_FORMATO_CARGA PRIMARY KEY 
  (
    ID 
  )
  ENABLE 
);

COMMENT ON TABLE T247CAR_FORMATO_CARGA IS 'Formatos para carga de archivos';
COMMENT ON COLUMN T247CAR_FORMATO_CARGA.S_NOMBRE IS 'Nombre para identificar el formato';
COMMENT ON COLUMN T247CAR_FORMATO_CARGA.S_SEPARADOR IS 'Separador de campos. Suele ser ; o ,';
COMMENT ON COLUMN T247CAR_FORMATO_CARGA.N_LINEAS_ENCABEZADO IS 'Numero de lineas al principio del archivo que no se deben procesar (encabezado del archivo)';
COMMENT ON COLUMN T247CAR_FORMATO_CARGA.S_FORMATO_FECHA IS 'Formato Java de fecha usado en los campos fecha del archivo';
COMMENT ON COLUMN T247CAR_FORMATO_CARGA.N_MAX_BYTES IS 'Numero maximo de bytes permitidos en una sola carga';
COMMENT ON COLUMN T247CAR_FORMATO_CARGA.N_MAX_ERRORES IS 'Numero maximo de errores encontrados antes de abortar la validacion del archivo';

CREATE TABLE T247CAR_LOG_CARGA 
(
  ID NUMBER(19,0) NOT NULL 
,D_FECHA TIMESTAMP NOT NULL
,S_USUARIO VARCHAR2(100) NOT NULL
,ID_FORMATO NUMBER(19,0) NOT NULL 
, CONSTRAINT PK_LOG_CARGA PRIMARY KEY 
  (
    ID 
  )
  ENABLE 
, CONSTRAINT FK_LOG_CARGA FOREIGN KEY (ID_FORMATO)
	  REFERENCES T247CAR_FORMATO_CARGA (ID) ENABLE  
);
COMMENT ON TABLE T247CAR_LOG_CARGA IS 'Log de la carga de archivos';
COMMENT ON COLUMN T247CAR_LOG_CARGA.D_FECHA IS 'Fecha y hora de la carga';
COMMENT ON COLUMN T247CAR_LOG_CARGA.ID_FORMATO IS 'Formato del archivo cargado';

ALTER TABLE T247CAR_INFORMACION_CAMARA MODIFY S_LONGITUD NUMBER(9,6);
ALTER TABLE T247CAR_INFORMACION_CAMARA MODIFY S_LATITUD NUMBER(9,6);

--DROP VIEW VW_REPORTES_CARGA;
CREATE VIEW VW_REPORTES_CARGA AS
    SELECT 
        CV.D_FECHA_PASO AS D_FECHA_PASO,
        EXTRACT(year from CV.D_FECHA_PASO) AS D_FECHA_ANIO,
        EXTRACT(month from CV.D_FECHA_PASO) AS D_FECHA_MES,
        EXTRACT(day from CV.D_FECHA_PASO) AS D_FECHA_DIA,
        CV.N_FECHA_HORA AS N_FECHA_HORA,
        C.S_CODIGO_CAMARA AS S_CODIGO_CAMARA,
        V.S_NRO_PLACA AS S_NRO_PLACA,
        V.N_CAP_TONELADAS AS N_CAP_TONELADAS, 
        V.S_DESC_CARROCERIA AS S_DESC_CARROCERIA, 
        V.S_DESC_CLASE AS S_DESC_CLASE, 
        V.S_DESC_COMBUTIBLE AS S_DESC_COMBUTIBLE, 
        V.S_DESC_MARCA AS S_DESC_MARCA, 
        V.N_MODELO AS N_MODELO,
        C.S_DESCRIPCION_CAMARA AS S_DESCRIPCION_CAMARA,  
        C.S_LONGITUD AS S_LONGITUD,
        C.S_LATITUD AS S_LATITUD,        
        C.S_DESCRIPCION_ZONA AS S_DESCRIPCION_ZONA,
        C.S_DESCRIPCION_CIUDAD AS S_DESCRIPCION_CIUDAD,
        C.S_DESCRIPCION_SECRETARIA AS S_DESCRIPCION_SECRETARIA
    FROM T247CAR_CIRCULACION_VEHICULO CV
    JOIN T247CAR_INFORMACION_CAMARA C ON C.S_CODIGO_CAMARA = TO_CHAR(CV.N_CODIGO_DIRECCION)
    JOIN T247CAR_VEHICULO V ON V.S_NRO_PLACA = CV.S_PLACA
    --WHERE C.S_VIGENTE = ??????
;
--COMMENT ON VIEW VW_REPORTES_CARGA IS 'Vista usada por PowerBI para los reportes de carga de veh�culos y c�maras';

CREATE VIEW VW_VEHICULO_CLASE AS SELECT ROWNUM AS TIPOID, NOMBRE FROM (SELECT DISTINCT S_DESC_CLASE AS NOMBRE FROM T247CAR_VEHICULO) ORDER BY NOMBRE;
CREATE VIEW VW_VEHICULO_CARROCERIA AS SELECT ROWNUM AS TIPOID, NOMBRE FROM (SELECT DISTINCT S_DESC_CARROCERIA AS NOMBRE FROM T247CAR_VEHICULO) ORDER BY NOMBRE;
CREATE VIEW VW_VEHICULO_MARCA AS SELECT ROWNUM AS TIPOID, NOMBRE FROM (SELECT DISTINCT S_DESC_MARCA AS NOMBRE FROM T247CAR_VEHICULO) ORDER BY NOMBRE;
CREATE VIEW VW_VEHICULOS_COMBUTIBLE AS SELECT ROWNUM AS TIPOID, NOMBRE FROM (SELECT DISTINCT S_DESC_COMBUTIBLE AS NOMBRE FROM T247CAR_VEHICULO) ORDER BY NOMBRE;

CREATE VIEW VW_FOTODETECCION AS
    SELECT 
        CV.ID AS ID,
        TO_CHAR(CV.D_FECHA_PASO, 'YYYY/MM/DD') AS FECHA,
        CV.N_FECHA_HORA AS HORA,
        CV.S_PLACA AS PLACA,
        C.S_LONGITUD AS LONGITUD,
        C.S_LATITUD AS LATITUD        
    FROM T247CAR_CIRCULACION_VEHICULO CV
    JOIN T247CAR_INFORMACION_CAMARA C ON C.S_CODIGO_CAMARA = TO_CHAR(CV.N_CODIGO_DIRECCION)
;

