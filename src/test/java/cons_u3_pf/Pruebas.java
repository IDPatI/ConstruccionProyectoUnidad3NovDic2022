package cons_u3_pf;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
    private static final String rutaMia ="C:/Users/EQUIPO 1/Documents/prueba.json"; 
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


        
        Empleados.modificarEmpleado("2","David Alberto", "Pat Cituk", "https://cdn.discordapp.com/attachments/903704345747525737/1017236782292684900/DPat_A_beautiful_painting_of_a_big_purple_sci-fi_Gate_ea4dcb4d-aa2e-4476-b94c-df4156ad2d65.png");
        Empleado actual = Empleados.get("2");
        assertEquals(resultado.toString(), actual.toString());

    }

    @Test
    public void pruebaEliminarEmpleado() throws Exception{
        Empleados e  = new Empleados(rutaMia);
        int antes = Empleados.empleados.size();
        Empleados.eliminarEmpleado( "5");


        System.out.println(Empleados.jsonEmpleados.toString());
        assertEquals(antes-1, Empleados.empleados.size());
    }

    @Test 
    public void empleadoNoExiste() throws Exception{
        Empleados e  = new Empleados(rutaMia);
        assertNull(Empleados.get("a"));
    }
    @Test
    public void pruebaAgregarEmpleado() throws Exception{
        Empleados e  = new Empleados(rutaMia);
        int antes = Empleados.empleados.size();
        Empleados.agregarEmpleado("300","Juan Pablo","Lopez Gomez", "https://pm1.narvii.com/6816/dd22c64d72a430978a187bdfcf795f8c01616773v2_hq.jpg");
        System.out.println(Empleados.jsonEmpleados.toString());
        assertEquals(antes+1, Empleados.empleados.size());
        assertNotNull(Empleados.get("300"));
    }


}
