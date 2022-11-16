package archivos;

import java.io.File;
import java.util.Scanner;

public class LectorArchivos {
    

    public static String ObtenerContenido(String ruta) throws Exception{
        String retornar ="";
        File doc = new File(ruta);
        if(!doc.exists()){
            throw new Exception("El archivo no existe");
        }

        Scanner lector = new Scanner(doc);
        while(lector.hasNextLine()){
            retornar += lector.nextLine();
        }
        lector.close();
        return retornar;
    }
}
