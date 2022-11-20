package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import archivos.JsonParser;
import archivos.LectorArchivos;
import archivos.Validaciones;
import modelo.Empleado;
import modelo.Empleados;

public class ControladorVista  implements ActionListener{
    private VistaDatos vista;
    private VistaEditar vistaEditar;
    private String archivoCargado;

    public ControladorVista() {
        vista = new VistaDatos();
        vistaEditar = new VistaEditar();
        vista.setVisible(true);
        vista.getBotonMostrar().addActionListener(this);
        vista.getBotonLimpiar().addActionListener(this);
        vista.getBotonCargar().addActionListener(this);
        vista.getBotonEditar().addActionListener(this);
        Empleados.archivoEmpleados = "";
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

    public void actualizarTabla(){
        Object[][] tabla = JsonParser.empleados(Empleados.jsonEmpleados);
        DefaultTableModel dtm = (DefaultTableModel)vista.getTablaEmp().getModel();
        dtm.setRowCount(0);
        for (Object[] object : tabla) {
            dtm.addRow(object);
        }
    }

    public boolean validar(String ruta){
        if(!Validaciones.validarExJSON(ruta)){
            JOptionPane.showMessageDialog(null, "No es un archivo JSON","error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            if(!Validaciones.validarFormato(LectorArchivos.ObtenerContenido(ruta))){
                JOptionPane.showMessageDialog(null, "No tiene el formato adecuado","Formato", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "El archivo no pudo ser encontrado","Archivo no encontrado", JOptionPane.INFORMATION_MESSAGE);
        }
        return true;
    }

    public void rellenarTablaEditar(DefaultTableModel dtm, VistaEditar vista) {
        DefaultTableModel modelo = (DefaultTableModel)vista.getTablaModif().getModel();
        modelo.setRowCount(0);
        for(int i = 0; i < dtm.getRowCount(); i++) {
            Object[] row = new String[4];
            for (int j = 0; j < 4; j++) {
                row[j] = dtm.getValueAt(i, j);
            }
            modelo.addRow(row);
        }
    }

    public void rellenarTablaEmp(DefaultTableModel dtm, VistaDatos vista) {
        DefaultTableModel modelo = (DefaultTableModel)vista.getTablaEmp().getModel();
        modelo.setRowCount(0);
        for(int i = 0; i < dtm.getRowCount(); i++) {
            Object[] row = new String[4];
            for (int j = 0; j < 4; j++) {
                row[j] = dtm.getValueAt(i, j);
            }
            modelo.addRow(row);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.getBotonMostrar()) {
            if(Empleados.archivoEmpleados.equals("")){
                JOptionPane.showMessageDialog(null, "No se a cargado ningun archivo","Aviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }else{
                actualizarTabla();
            }
        }

        if(e.getSource() == vista.getBotonLimpiar()) {
            DefaultTableModel dtm = (DefaultTableModel)vista.getTablaEmp().getModel();
            dtm.setRowCount(0);
        }

        if(e.getSource() == vista.getBotonCargar()) {
            String ruta = seleccionarRuta();
            if(!validar(ruta)){
                return;
            }
            try {
                Empleados em = new Empleados(ruta);
            } catch (Exception e1) {
            }
            actualizarTabla();
        }

        if(e.getSource() == vista.getBotonEditar()) {
            if(archivoCargado.equals("")){
                JOptionPane.showMessageDialog(null, "No se a cargado ningun archivo","Aviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }else{
                vistaEditar.setVisible(true);
                vistaEditar.getBotonCancelar().addActionListener(this);
                vistaEditar.getBotonActualizar().addActionListener(this);
                DefaultTableModel dtm = (DefaultTableModel)vista.getTablaEmp().getModel();
                rellenarTablaEditar(dtm, vistaEditar);
            }
        }

        if(e.getSource() == vistaEditar.getBotonCancelar()) {
            vistaEditar.dispose();
        }

        if(e.getSource() == vistaEditar.getBotonActualizar()) {
            String[] opciones = {"Actualizar", "Cancelar"};
            int x = JOptionPane.showOptionDialog(null, "¿Que quieres actualizar?",
                "Elige una opción",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
            switch(x) {
                case 0:
                    DefaultTableModel dtm = (DefaultTableModel)vistaEditar.getTablaModif().getModel();
                    rellenarTablaEmp(dtm, vista);
                    vistaEditar.dispose();
                    break;
                case 1:
                    break;
                default:
                    break;
            }
        }
        
    }
    
    public static void main(String[] args) {
        ControladorVista controlador = new ControladorVista();
    }

}
