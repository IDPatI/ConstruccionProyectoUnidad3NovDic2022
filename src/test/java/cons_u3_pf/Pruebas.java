package cons_u3_pf;


import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import archivos.LectorArchivos;
import archivos.Validaciones;

public class Pruebas {
    @Test
    public void leerArchivo() throws Exception{
        String rutaMia = "D:/Desktop/archivo.json";
        String json = LectorArchivos.ObtenerContenido(rutaMia);
        System.out.println(json);
    }

    @Test
    public void verFormato() throws Exception{
        String rutaMia = "D:/Desktop/archivo.json";
        assertTrue(Validaciones.validarExJSON(rutaMia));

        String json = LectorArchivos.ObtenerContenido(rutaMia);
        System.out.println(json);
        assertTrue(Validaciones.validarFormato(json));
    } 

}
