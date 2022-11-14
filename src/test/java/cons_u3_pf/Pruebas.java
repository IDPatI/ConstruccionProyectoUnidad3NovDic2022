package cons_u3_pf;

<<<<<<< HEAD
import org.junit.Test;

public class Pruebas {
    @Test
    public void prueba1(){
        
    }
=======

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import archivos.LectorArchivos;
import archivos.Validaciones;

public class Pruebas {
    @Test
    public void leerArchivo() throws Exception{
        String rutaMia = "C:/Users/EQUIPO 1/Documents/prueba.json";
        String json = LectorArchivos.ObtenerContenido(rutaMia);
        System.out.println(json);
    }

    @Test
    public void verFormato() throws Exception{
        String rutaMia = "C:/Users/EQUIPO 1/Documents/prueba.json";
        assertTrue(Validaciones.validarExJSON(rutaMia));

        String json = LectorArchivos.ObtenerContenido(rutaMia);
        System.out.println(json);
        assertTrue(Validaciones.validarFormato(json));
    } 

>>>>>>> feature-Lectura-validacion
}
