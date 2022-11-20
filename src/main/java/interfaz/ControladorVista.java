package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import archivos.JsonParser;
import archivos.LectorArchivos;
import archivos.Validaciones;
import modelo.Empleado;
import modelo.Empleados;

public class ControladorVista  implements ActionListener{
    private VistaDatos vista;
    private VistaModificar vistaModif;
    private String archivoCargado;

    public ControladorVista() {
        vista = new VistaDatos();
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

    public void inicializarVistaModif() {
        int filaSeleccionada = vista.getTablaEmp().getSelectedRow();
        if(filaSeleccionada != -1) {
            vistaModif = new VistaModificar();
            vistaModif.setVisible(true);
            vistaModif.getBotonCancelar().addActionListener(this);
            vistaModif.getBotonActualizar().addActionListener(this);
            vistaModif.setFilaSeleccionada(filaSeleccionada);
        } else {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila primero","Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }

    public void actualizarFila(String id, String nombre, String apellido, String foto, int fila) {
        DefaultTableModel dtm = (DefaultTableModel)vista.getTablaEmp().getModel();
        dtm.setValueAt(id, fila, 0);
        dtm.setValueAt(nombre, fila, 1);
        dtm.setValueAt(apellido, fila, 2);
        dtm.setValueAt(foto, fila, 3);
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
            if(Empleados.archivoEmpleados.equals("")){
                JOptionPane.showMessageDialog(null, "No se a cargado ningun archivo","Aviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }else{
                inicializarVistaModif();
            }
        }

        if(e.getSource() == vistaModif.getBotonCancelar()) {
            vistaModif.dispose();
        }

        if(e.getSource() == vistaModif.getBotonActualizar()) {
            String fieldId = vistaModif.getId().getText();
            String fieldNombre = vistaModif.getNombre().getText();
            String fieldApellido = vistaModif.getApellido().getText();
            String fieldFoto = vistaModif.getFoto().getText();
            if((fieldId.equals("") || fieldNombre.equals("") || fieldApellido.equals("") || fieldFoto.equals("")) == true) {
                JOptionPane.showMessageDialog(null, "Alguno de los campos esta vacío","Aviso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                actualizarFila(fieldId, fieldNombre, fieldApellido, fieldFoto, vistaModif.getFilaSeleccionada());
                vistaModif.dispose();
            }
        }
        
    }
    
    public static void main(String[] args) {
        ControladorVista controlador = new ControladorVista();
    }

}
