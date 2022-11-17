package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ControladorVista  implements ActionListener{
    private VistaDatos vista;

    public ControladorVista() {
        vista = new VistaDatos();
        vista.setVisible(true);
        vista.getBotonMostrar().addActionListener(this);
        vista.getBotonLimpiar().addActionListener(this);
        vista.getBotonCargar().addActionListener(this);

    }

    public String seleccionarRuta(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(fileChooser);
        try {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();                                        
            return ruta;
        } catch (NullPointerException e) {
            return "No se ha seleccionado ningún fichero";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "error";
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.getBotonMostrar()) {
            Object[][] datos = {{2342,"Robert","Downey","FOTO"},{5363,"David","Pat","FOTO"}};
            DefaultTableModel dtm = (DefaultTableModel)vista.getTablaEmp().getModel();
            dtm.addRow(datos[0]);
            dtm.addRow(datos[1]);
        }

        if(e.getSource() == vista.getBotonLimpiar()) {
            DefaultTableModel dtm = (DefaultTableModel)vista.getTablaEmp().getModel();
            dtm.setRowCount(0);
        }

        if(e.getSource() == vista.getBotonCargar()) {
            String ruta = seleccionarRuta();
            JOptionPane.showMessageDialog(null, ruta,"éxito!", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    
    public static void main(String[] args) {
        ControladorVista controlador = new ControladorVista();
    }

}
