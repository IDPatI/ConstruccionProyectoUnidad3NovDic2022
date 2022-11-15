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
        for(int i = 0; i < trabajador.length();i++){
            String id = ((JSONObject)trabajador.get(i)).getString("id");
            String firstName = ((JSONObject)trabajador.get(i)).getString("firstName");
            String lastName = ((JSONObject)trabajador.get(i)).getString("lastName");
            String photo = ((JSONObject)trabajador.get(i)).getString("photo");

        }
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
