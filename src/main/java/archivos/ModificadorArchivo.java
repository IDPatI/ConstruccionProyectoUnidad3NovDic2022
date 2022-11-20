package archivos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ModificadorArchivo {
    
    public static void guardar(String ruta, String contenido) throws IOException{
        File doc = new File(ruta);
        if(!doc.exists()){
            try {
                doc.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileWriter escritor = new FileWriter(doc);
        BufferedWriter bw = new BufferedWriter(escritor);
        bw.write(contenido);
        bw.close();
    }

}
