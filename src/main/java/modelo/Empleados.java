package modelo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import archivos.JsonParser;
import archivos.LectorArchivos;
import archivos.ModificadorArchivo;

public class Empleados {
    public static JSONObject jsonEmpleados;
    public static String archivoEmpleados ="";
    public static  Map<String,Empleado> empleados;

    public Empleados(String archivo) throws Exception{
        archivoEmpleados = archivo;
        String contenido = LectorArchivos.ObtenerContenido(archivo);
        jsonEmpleados = new JSONObject(contenido);

        empleados = new HashMap<>();

        JSONArray trabajador = getListaEmpleados();
        for(int i = 0; i < trabajador.length();i++){
            String id = trabajador.getJSONObject(i).getString("id");
            String firstName = trabajador.getJSONObject(i).getString("firstName");
            String lastName = trabajador.getJSONObject(i).getString("lastName");
            String photo = trabajador.getJSONObject(i).getString("photo");
            Empleado empleadoA = new Empleado();
            empleadoA.firstName = firstName;
            empleadoA.lastName = lastName;
            empleadoA.photo = photo;
            empleados.put(id, empleadoA);
        }
    }

    public void modificarEmpleado(String id,String firstName, String lastName, String photo ) throws IOException{
        Empleado empleadoM = empleados.get(id);
        empleadoM.firstName = firstName;
        empleadoM.lastName = lastName;
        empleadoM.photo = photo;
        empleados.put(id, empleadoM);
        jsonEmpleados  = JsonParser.jsonEmpleados();
        ModificadorArchivo.guardar(archivoEmpleados, jsonEmpleados.toString());
    }

    private static JSONArray getListaEmpleados(){
        return jsonEmpleados.getJSONObject("employees").getJSONArray("employee");
    }


}
