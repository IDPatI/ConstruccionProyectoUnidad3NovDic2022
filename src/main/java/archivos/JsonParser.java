package archivos;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;


import org.json.JSONArray;
import org.json.JSONObject;

import modelo.Empleado;
import modelo.Empleados;

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

    public static JSONObject jsonEmpleados(){
        JSONArray arregloE = new JSONArray();

        Set llavesS = Empleados.empleados.keySet();
        Collection<Empleado> EmpleadosAlterno = Empleados.empleados.values();

        Iterator<Empleado> lista = EmpleadosAlterno.iterator();
        Iterator<String> llaves = llavesS.iterator();

        while(llaves.hasNext()){
            JSONObject empleadoA = new JSONObject();
            empleadoA.put("id", llaves.next());

            Empleado empleadoES = lista.next();
            empleadoA.put("firstName", empleadoES.firstName);
            empleadoA.put("lastName", empleadoES.lastName);
            empleadoA.put("photo", empleadoES.photo);
            arregloE.put(empleadoA);
        }

        JSONObject preRegreso = new JSONObject();
        preRegreso.put("employee", arregloE);

        JSONObject regresar = new JSONObject();
        regresar.put("employees", preRegreso);

        return regresar;
        

    }

    


}
