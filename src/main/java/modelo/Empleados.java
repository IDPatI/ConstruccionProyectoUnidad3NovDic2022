package modelo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONObject;

import archivos.JsonParser;
import archivos.LectorArchivos;
import archivos.ModificadorArchivo;
import archivos.SaveImageFromUrl;

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
            guardarImagen(id);
        }
    }

    public static void modificarEmpleado(String id,String firstName, String lastName, String photo ) throws IOException{
        Empleado empleadoM = empleados.get(id);
        empleadoM.firstName = firstName;
        empleadoM.lastName = lastName;
        empleadoM.photo = photo;
        empleados.put(id, empleadoM);
        guardarImagen(id);
        jsonEmpleados  = JsonParser.jsonEmpleados();
        ModificadorArchivo.guardar(archivoEmpleados, jsonEmpleados.toString());
    }

    public static Empleado get(String id){
        return empleados.get(id);
    }


    private static JSONArray getListaEmpleados(){
        return jsonEmpleados.getJSONObject("employees").getJSONArray("employee");
    }
    private static void guardarImagen(String id){
        Empleado em = empleados.get(id);
        String direccion = "src/imagenes/"+ id +".jpg";
        System.out.println(id);
        try {
            SaveImageFromUrl.saveImage(em.photo, direccion);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se pudo cargar imagen:\n" + em.photo  ,"Error", JOptionPane.ERROR_MESSAGE);
        }

    }


}
