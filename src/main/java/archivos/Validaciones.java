package archivos;

import org.json.JSONArray;
import org.json.JSONObject;

public class Validaciones {

    public static boolean validarExJSON(String archivo){
        return archivo.endsWith(".json"); 
    }

    public static boolean validarFormato(String json){
        JSONObject objeto = new JSONObject(json);
        JSONObject trabajadores =  objeto.getJSONObject("employees");
        JSONArray trabajador =  trabajadores.getJSONArray("employee");
        System.out.println(trabajador);
        return true;
    }

    public static boolean validarFormato(JSONObject objeto){
        JSONObject trabajadores =  objeto.getJSONObject("employees");
        JSONArray trabajador =  trabajadores.getJSONArray("employee");
        System.out.println(trabajador);
        return true;
    }
}