package cons_u3_pf;


import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import archivos.JsonParser;
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
    
    @Test
    public void listaObjetos() throws Exception{
        String rutaMia = "C:/Users/EQUIPO 1/Documents/prueba.json";
        String json = LectorArchivos.ObtenerContenido(rutaMia);
        Object[][] resultado  = JsonParser.empleados(json);
        Object[][] comparar = new Object[3][4];
        assertEquals(comparar.getClass().arrayType(), resultado.getClass().arrayType());
        assertEquals(comparar.length, resultado.length);
        System.out.println(Arrays.deepToString(resultado));
    }

}
