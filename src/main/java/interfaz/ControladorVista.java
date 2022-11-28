package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import archivos.JsonParser;
import archivos.LectorArchivos;
import archivos.SaveImageFromUrl;
import archivos.Validaciones;
import modelo.Empleado;
import modelo.Empleados;

public class ControladorVista  implements ActionListener{
    public static VistaDatos vista;
    private VistaModificar vistaModif;

    public ControladorVista() {
        vista = new VistaDatos();
        vistaModif = new VistaModificar();
        vista.setVisible(true);
        vista.getBotonMostrar().addActionListener(this);
        vista.getBotonLimpiar().addActionListener(this);
        vista.getBotonCargar().addActionListener(this);
        vista.getBotonEditar().addActionListener(this);
        vista.getBotonEliminar().addActionListener(this);
        Empleados.archivoEmpleados = "";
        vista.getTablaEmp().setDefaultRenderer(vista.getTablaEmp().getColumnClass(3), new TablaImagenes());
    }
    //Metodos de la pantalla principal
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
    //--------------

    @Override
    public void actionPerformed(ActionEvent e) {

        //Ver empleados, Acciones de la pantalla principal
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

        if(e.getSource() == vista.getBotonEliminar()) {
            int filaSeleccionada = vista.getTablaEmp().getSelectedRow();
            if(filaSeleccionada != -1) {
                DefaultTableModel dtm = (DefaultTableModel)vista.getTablaEmp().getModel();
                dtm.removeRow(filaSeleccionada);
                JOptionPane.showMessageDialog(null, "Se borro la fila correctamente");
             } else {
                JOptionPane.showMessageDialog(null, "No se ha seleccionado una fila","Aviso", JOptionPane.INFORMATION_MESSAGE);
             }
        }

        //Modificar, Acciones de la ventana Modificar
        if(e.getSource() == vista.getBotonEditar()) {
            if(Empleados.archivoEmpleados.equals("")){
                JOptionPane.showMessageDialog(null, "No se ha cargado ningun archivo","Aviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }else{
                inicializarVistaModif();
            }
        }

        if(e.getSource() == vistaModif.getBotonCancelar()) {
            vistaModif.dispose();
        }

        if(e.getSource() == vistaModif.getBotonActualizar()) {
            modificarAccion();
        }
        
    }


    //Metodos Modificar

    public void inicializarVistaModif() {
        int filaSeleccionada = vista.getTablaEmp().getSelectedRow();


        if(filaSeleccionada != -1) {
            String id = (String)vista.getTablaEmp().getValueAt(filaSeleccionada, 0);
            Empleado actual = Empleados.get(id);
            System.out.println(actual);
            vistaModif.setNombre(actual.firstName);
            vistaModif.setApellido(actual.lastName);
            vistaModif.setFoto(actual.photo);
            vistaModif.setVisible(true);
            vistaModif.getBotonCancelar().addActionListener(this);
            vistaModif.getBotonActualizar().addActionListener(this);
            vistaModif.setIdTitulo(id);
        } else {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila primero","Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }

    public void modificarAccion(){
        String fieldNombre = vistaModif.getNombre();
        String fieldApellido = vistaModif.getApellido();
        String fieldFoto = vistaModif.getFoto();
        if(verificarCamposModificar()) {
            try {
                Empleados.modificarEmpleado(vistaModif.getId(), fieldNombre, fieldApellido, fieldFoto);
            } catch (IOException e1) {
            }
            actualizarTabla();
            vistaModif.dispose();
        } else {
            return;
        }
    }


    public boolean verificarCamposModificar(){
        String fieldNombre = vistaModif.getNombre();
        String fieldApellido = vistaModif.getApellido();
        String fieldFoto = vistaModif.getFoto(); 
        if((fieldNombre.equals("") || fieldApellido.equals("") || fieldFoto.equals("")) == true){
            JOptionPane.showMessageDialog(null, "Alguno de los campos esta vacío","Aviso", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }else{
            return true;
        }
    }




    
    public static void main(String[] args) {
        ControladorVista controlador = new ControladorVista();
    }

}
