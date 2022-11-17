package archivos;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonParser {
    
    public static Object[][] empleados(String json){
        JSONObject objeto = new JSONObject(json);
        return empleados(objeto);
    }
    public static Object[][] empleados(JSONObject objeto){
        JSONObject trabajadores =  objeto.getJSONObject("employees");
        JSONArray trabajador =  trabajadores.getJSONArray("employee");

        Object[][] regresar = new Object[trabajador.length()][4];
        for(int i = 0; i < trabajador.length();i++){
            String id = trabajador.getJSONObject(i).getString("id");
            String firstName = trabajador.getJSONObject(i).getString("firstName");
            String lastName = trabajador.getJSONObject(i).getString("lastName");
            String photo = trabajador.getJSONObject(i).getString("photo");
            regresar[i][0] = id;
            regresar[i][1] = firstName;
            regresar[i][2] = lastName;
            regresar[i][3] = photo;
        }
        return regresar;
    }

}
