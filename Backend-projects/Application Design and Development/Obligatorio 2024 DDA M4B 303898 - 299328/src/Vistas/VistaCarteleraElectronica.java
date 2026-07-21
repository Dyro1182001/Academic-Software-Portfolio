package Vistas;

import Controladores.ParkingController;
import Observador.Observable;
import Observador.Observador;
import clasesDeEjemplo.Parking;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaCarteleraElectronica extends JFrame implements Observador {

    private ParkingController parkingController;
    private Parking parking;
    private JLabel lblDisponibilidad;
    private JTable tblCocheras;
    private JTable tblPrecios;
    private Timer autoUpdateTimer;

    public VistaCarteleraElectronica(ParkingController controller, Parking parking) {
        this.parkingController = controller;
        this.parking = parking;
        initialize();
        parkingController.agregar(this); 
        startAutoUpdate();
        setVisible(true);
    }

    private void initialize() {
        setTitle("Cartelera electrónica - " + parking.getNombre());
        setLayout(new BorderLayout());

        JPanel panelTop = new JPanel();
        lblDisponibilidad = new JLabel("Disponibilidad: " + parking.getCocherasLibres());
        lblDisponibilidad.setFont(new Font("Arial", Font.BOLD, 24));
        panelTop.add(lblDisponibilidad);

        add(panelTop, BorderLayout.NORTH);

        String[] cocheraColumnNames = {"Cocheras", "Disponibilidad"};
        Object[][] cocheraData = parkingController.getCocherasData(parking);
        tblCocheras = new JTable(cocheraData, cocheraColumnNames);
        JScrollPane scrollPaneCocheras = new JScrollPane(tblCocheras);

        String[] preciosColumnNames = {"Tipo de vehículo", "Precio/<UT>"};
        Object[][] preciosData = parkingController.getPreciosData(parking);
        tblPrecios = new JTable(preciosData, preciosColumnNames);
        JScrollPane scrollPanePrecios = new JScrollPane(tblPrecios);

        JPanel panelCenter = new JPanel(new GridLayout(2, 1));
        panelCenter.add(scrollPaneCocheras);
        panelCenter.add(scrollPanePrecios);

        add(panelCenter, BorderLayout.CENTER);

        JPanel panelBottom = new JPanel();
        JButton btnCerrar = new JButton("Cerrar");

        panelBottom.add(btnCerrar);
        add(panelBottom, BorderLayout.SOUTH);

        pack();

        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                parkingController.quitar(VistaCarteleraElectronica.this);
            }
        });
    }

    @Override
    public void actualizar(Observable origen, Object evento) {
        if (evento == Observable.Evento.ACTUALIZAR_DISPONIBILIDAD || evento == Observable.Evento.ACTUALIZAR_PRECIOS || evento == Observable.Evento.INGRESAR_EGRESAR_ESTADIA) {
            updateData();
        }
    }

    public void updateData() {
        lblDisponibilidad.setText("Disponibilidad: " + parking.getCocherasLibres());

        String[] cocheraColumnNames = {"Cocheras", "Disponibilidad"};
        Object[][] cocheraData = parkingController.getCocherasData(parking);
        tblCocheras.setModel(new javax.swing.table.DefaultTableModel(cocheraData, cocheraColumnNames));

        String[] preciosColumnNames = {"Tipo de vehículo", "Precio/<UT>"};
        Object[][] preciosData = parkingController.getPreciosData(parking);
        tblPrecios.setModel(new javax.swing.table.DefaultTableModel(preciosData, preciosColumnNames));
    }
    
    private void startAutoUpdate() {
        autoUpdateTimer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });
        autoUpdateTimer.start();
    }
}
