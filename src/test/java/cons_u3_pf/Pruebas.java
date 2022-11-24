package cons_u3_pf;


import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.json.JSONObject;
import org.junit.Test;

import archivos.JsonParser;
import archivos.LectorArchivos;
import archivos.Validaciones;
import modelo.Empleado;
import modelo.Empleados;

public class Pruebas {
    private static final String rutaMia ="D:/Desktop/archivo.json"; 
    @Test
    public void leerArchivo() throws Exception{
        String json = LectorArchivos.ObtenerContenido(rutaMia);
        System.out.println(json);
    }

    @Test
    public void verFormato() throws Exception{
        assertTrue(Validaciones.validarExJSON(rutaMia));

        String json = LectorArchivos.ObtenerContenido(rutaMia);
        System.out.println(json);
        assertTrue(Validaciones.validarFormato(json));
    }
    
    @Test
    public void listaObjetos() throws Exception{
        String json = LectorArchivos.ObtenerContenido(rutaMia);
        Object[][] resultado  = JsonParser.empleados(json);
        Object[][] comparar = new Object[3][4];
        assertEquals(comparar.getClass().arrayType(), resultado.getClass().arrayType());
        assertEquals(comparar.length, resultado.length);
        System.out.println(Arrays.deepToString(resultado));
    }

    @Test
    public void pruebaEmpleadosConversion() throws Exception{
        Empleados e  = new Empleados(rutaMia);
        JSONObject resultado = JsonParser.jsonEmpleados();
        System.out.println(resultado);
        System.out.println("---------");
        System.out.println(Empleados.jsonEmpleados);
        assertEquals(Empleados.jsonEmpleados.toString(), resultado.toString());
    }

    @Test
    public void pruebaModificarEmpleado() throws Exception{
        Empleados e  = new Empleados(rutaMia);
        Empleado resultado = new Empleado();
        resultado.firstName = "David Alberto";
        resultado.lastName = "Pat Cituk";
        resultado.photo = "https://cdn.discordapp.com/attachments/903704345747525737/1017236782292684900/DPat_A_beautiful_painting_of_a_big_purple_sci-fi_Gate_ea4dcb4d-aa2e-4476-b94c-df4156ad2d65.png";


        
        e.modificarEmpleado("2","David Alberto", "Pat Cituk", "https://cdn.discordapp.com/attachments/903704345747525737/1017236782292684900/DPat_A_beautiful_painting_of_a_big_purple_sci-fi_Gate_ea4dcb4d-aa2e-4476-b94c-df4156ad2d65.png");
        Empleado actual = Empleados.get("2");
        assertEquals(resultado.toString(), actual.toString());

    }

}
