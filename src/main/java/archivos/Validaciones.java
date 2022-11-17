package archivos;

import javax.swing.text.TabExpander;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Validaciones {

    public static boolean validarExJSON(String archivo){
        return archivo.endsWith(".json"); 
    }

    public static boolean validarFormato(String json){
        JSONObject objeto = new JSONObject(json);
        return validarFormato(objeto);
    }

    public static boolean validarFormato(JSONObject objeto){
        JSONArray trabajador;
        try{
            JSONObject trabajadores =  objeto.getJSONObject("employees");
            trabajador =  trabajadores.getJSONArray("employee");
        }catch(JSONException ex){
            return false;
        }

        for(int i = 0; i < trabajador.length();i++){
            try{
            String id = ((JSONObject)trabajador.get(i)).getString("id");
            String firstName = ((JSONObject)trabajador.get(i)).getString("firstName");
            String lastName = ((JSONObject)trabajador.get(i)).getString("lastName");
            String photo = ((JSONObject)trabajador.get(i)).getString("photo");
            }catch(JSONException je){
                return false;
            }

        }
        return true;
    }
}
