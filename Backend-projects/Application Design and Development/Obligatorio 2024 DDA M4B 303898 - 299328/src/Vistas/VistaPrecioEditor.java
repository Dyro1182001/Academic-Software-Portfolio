package Vistas;

import Controladores.ParkingController;
import Excepciones.PrecioInvalidoException;
import Observador.Observable;
import Observador.Observador;
import clasesDeEjemplo.Parking;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaPrecioEditor extends JFrame implements Observador {

    private ParkingController parkingController;
    private Parking parking;
    private JTable tblPrecios;
    private JTextField txtNuevoValor;
    private JButton btnGuardar;
    private JButton btnCerrar;

    public VistaPrecioEditor(ParkingController controller, Parking parking) {
        this.parkingController = controller;
        this.parking = parking;
        initialize();
        parkingController.agregar(this);
        setVisible(true);
    }

    private void initialize() {
        setTitle("Lista de precios - " + parking.getNombre());
        setLayout(new BorderLayout());

        String[] columnNames = {"Tipo de vehículo", "Precio/<UT>"};
        Object[][] data = parkingController.getPreciosData(parking);

        tblPrecios = new JTable(data, columnNames);
        JScrollPane scrollPanePrecios = new JScrollPane(tblPrecios);

        add(scrollPanePrecios, BorderLayout.CENTER);

        JPanel panelBottom = new JPanel();
        txtNuevoValor = new JTextField(10);
        btnGuardar = new JButton("Guardar");
        btnCerrar = new JButton("Cerrar");

        panelBottom.add(new JLabel("Nuevo valor:"));
        panelBottom.add(txtNuevoValor);
        panelBottom.add(btnGuardar);
        panelBottom.add(btnCerrar);
        add(panelBottom, BorderLayout.SOUTH);

        pack();

        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                parkingController.quitar(VistaPrecioEditor.this);
            }
        });

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tblPrecios.getSelectedRow();
                if (selectedRow != -1) {
                    String tipoVehiculo = (String) tblPrecios.getValueAt(selectedRow, 0);
                    try {
                        double nuevoValor = Double.parseDouble(txtNuevoValor.getText());
                        parkingController.actualizarPrecio(parking, tipoVehiculo, nuevoValor);
                        JOptionPane.showMessageDialog(null, "Precio actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Valor inválido. Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un tipo de vehículo de la lista.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

    @Override
    public void actualizar(Observable origen, Object evento) {
        if (evento == Observable.Evento.ACTUALIZAR_PRECIOS) {
            updateData();
        }
    }

    public void updateData() {
        String[] columnNames = {"Tipo de vehículo", "Precio/<UT>"};
        Object[][] data = parkingController.getPreciosData(parking);
        tblPrecios.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }
}
