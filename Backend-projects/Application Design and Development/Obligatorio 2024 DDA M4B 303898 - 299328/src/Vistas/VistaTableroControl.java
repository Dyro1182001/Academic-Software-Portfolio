package Vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Controladores.ParkingController;
import Excepciones.AnomaliaException;
import Observador.Observable;
import Observador.Observador;
import clasesDeEjemplo.Parking;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VistaTableroControl extends JFrame implements Observador {

    private JLabel lblEstadias;
    private JLabel lblFacturacion;
    private JLabel lblCocherasOcupadas;
    private JLabel lblCocherasLibres;
    private JLabel lblEstado;
    private JLabel lblFactorDemanda;
    private JLabel lblMultas;

    private ParkingController parkingController;
    private JTable tblParkings;
    private JTable tblAnomalias;
    private JCheckBox chkMonitorearAnomalias;
    private JButton btnPrecios;
    private JButton btnCartelera;
    private JButton btnCerrar;
    private JTabbedPane tabbedPane;
    private Timer autoUpdateTimer;

    public VistaTableroControl() {
        parkingController = new ParkingController();
        initialize();
        startAutoUpdate();
        setVisible(true);
    }

    @Override
    public void actualizar(Observable origen, Object evento) {
        if (origen instanceof Parking) {
            Parking parking = (Parking) origen;
            actualizarTablero(parking);
            updateData();
        }
    }

    private void actualizarTablero(Parking parking) {
        lblEstadias.setText("Estadías: " + parking.getEstadias().size());
        lblFacturacion.setText("Facturación: $" + parking.getSubtotal());
        lblCocherasOcupadas.setText("Cocheras Ocupadas: " + parking.getCocherasOcupadas());
        lblCocherasLibres.setText("Cocheras Libres: " + parking.getCocherasLibres());
        lblEstado.setText("Estado: " + parking.getTendencia());
        lblFactorDemanda.setText("Factor de Demanda: " + parking.getFactorDemanda());
        lblMultas.setText("Multas: " + parking.getMultas());
    }

    private void initialize() {
        setTitle("Tablero de Control");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();

        JPanel panelTop = new JPanel();
        lblEstadias = new JLabel("Estadías: 0");
        lblFacturacion = new JLabel("Facturación: $0");
        panelTop.add(lblEstadias);
        panelTop.add(lblFacturacion);

        add(panelTop, BorderLayout.NORTH);

        JPanel panelCenter = new JPanel(new BorderLayout());

        String[] columnNames = {"Parking", "# Ocupadas", "# Libres", "Estado", "Factor demanda", "# Estadías", "Multas", "Subtotal"};
        Object[][] data = parkingController.getParkingData();
        tblParkings = new JTable(data, columnNames);
        JScrollPane scrollPaneParkings = new JScrollPane(tblParkings);
        panelCenter.add(scrollPaneParkings, BorderLayout.CENTER);

        JPanel panelBottom = new JPanel();
        chkMonitorearAnomalias = new JCheckBox("Monitorear Anomalías");
        btnPrecios = new JButton("Precios");
        btnCartelera = new JButton("Cartelera");
        btnCerrar = new JButton("Cerrar");

        panelBottom.add(chkMonitorearAnomalias);
        panelBottom.add(btnPrecios);
        panelBottom.add(btnCartelera);
        panelBottom.add(btnCerrar);
        panelCenter.add(panelBottom, BorderLayout.SOUTH);

        tabbedPane.addTab("Parkings", panelCenter);

        JPanel panelAnomalias = new JPanel(new BorderLayout());
        tblAnomalias = new JTable();
        JScrollPane scrollPaneAnomalias = new JScrollPane(tblAnomalias);
        panelAnomalias.add(scrollPaneAnomalias, BorderLayout.CENTER);

        tabbedPane.addTab("Anomalías", panelAnomalias);

        add(tabbedPane, BorderLayout.CENTER);

        pack();

       
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        btnPrecios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tblParkings.getSelectedRow();
                if (selectedRow != -1) {
                    String nombreParking = (String) tblParkings.getValueAt(selectedRow, 0);
                    try {
                        Parking parking = parkingController.obtenerParkingPorNombre(nombreParking);
                        if (parking != null) {
                            new VistaPrecioEditor(parkingController, parking).setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "Parking no encontrado. Por favor, seleccione un parking válido.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error al obtener el parking: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un parking de la lista.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCartelera.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tblParkings.getSelectedRow();
                if (selectedRow != -1) {
                    String nombreParking = (String) tblParkings.getValueAt(selectedRow, 0);
                    try {
                        Parking parking = parkingController.obtenerParkingPorNombre(nombreParking);
                        if (parking != null) {
                            new VistaCarteleraElectronica(parkingController, parking).setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "Parking no encontrado. Por favor, seleccione un parking válido.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error al obtener el parking: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un parking de la lista.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        chkMonitorearAnomalias.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chkMonitorearAnomalias.isSelected()) {
                    try {
                        parkingController.startMonitoringAnomalies();
                    } catch (AnomaliaException ex) {
                        Logger.getLogger(VistaTableroControl.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Error al iniciar el monitoreo de anomalías: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        chkMonitorearAnomalias.setSelected(false); 
                    }
                } else {
                    try {
                        parkingController.stopMonitoringAnomalies();
                    } catch (AnomaliaException ex) {
                        Logger.getLogger(VistaTableroControl.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Error al detener el monitoreo de anomalías: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        chkMonitorearAnomalias.setSelected(true); 
                    }
                }
                updateData();
            }
        });

        
        updateData();
    }

    public void updateData() {
        lblEstadias.setText("Estadías: " + parkingController.getTotalEstadias());
        lblFacturacion.setText("Facturación: $" + parkingController.getTotalFacturacion());

        String[] columnNames = {"Parking", "# Ocupadas", "# Libres", "Estado", "Factor demanda", "# Estadías", "Multas", "Subtotal"};
        Object[][] data = parkingController.getParkingData();
        tblParkings.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));

        if (chkMonitorearAnomalias.isSelected()) {
            String[] anomalyColumnNames = {"Fecha/Hora", "Propietario", "Código de anomalía", "Cochera"};
            Object[][] anomalyData = parkingController.getAnomaliesData();
            tblAnomalias.setModel(new javax.swing.table.DefaultTableModel(anomalyData, anomalyColumnNames));
        }
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
