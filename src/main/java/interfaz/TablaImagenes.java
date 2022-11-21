package interfaz;

import java.awt.Component;
import java.awt.Image;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import archivos.SaveImageFromUrl;

public class TablaImagenes extends DefaultTableCellRenderer{

    @Override
    public Component getTableCellRendererComponent(JTable tabla, Object valor, boolean isSelected, boolean hasFocus, int row, int colum){
        String carpeta = "src/imagenes/";
        

        super.getTableCellRendererComponent(tabla,valor, isSelected, hasFocus, row, colum);
        if(colum == 3){
            String id = (String)(tabla.getModel().getValueAt(row, 0));
            String direccion = carpeta +id+".jpg";
            Image imagenA = new ImageIcon(direccion).getImage();
            ImageIcon imagen = new ImageIcon(imagenA);
            tabla.setRowHeight(row, imagen.getIconHeight());
            return new JLabel(imagen);
        }

        return this;
    }

    
}
