package View;
import Controller.ControladorActivos;
import Controller.ControladorMantenimiento;
import Model.Activo;
import Model.EstadoM;
import Model.Hardware;
import Model.Mantenimiento;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class VistaMantenimiento extends JPanel {
    private final ControladorMantenimiento controlador;
    private final ControladorActivos controladorActivos;
    private final DefaultTableModel modelo = UtilVista.modeloNoEditable(
            "ID", "Activo", "Tipo", "Descripción", "Costo", "Estado", "Fecha");
    private final JTable tabla = new JTable(modelo);

    public VistaMantenimiento(ControladorMantenimiento controlador,
            ControladorActivos controladorActivos) {
        this.controlador = controlador;
        this.controladorActivos = controladorActivos;
        setLayout(new BorderLayout(10, 10));
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.add(UtilVista.titulo("Mantenimientos"), BorderLayout.WEST);
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton registrar = new JButton("Registrar");
        JButton historial = new JButton("Consultar historial");
        JButton pendientes = new JButton("Ver pendientes");
        acciones.add(registrar); acciones.add(historial); acciones.add(pendientes);
        encabezado.add(acciones, BorderLayout.EAST);
        tabla.setAutoCreateRowSorter(true);
        add(encabezado, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        registrar.addActionListener(e -> registrar());
        historial.addActionListener(e -> consultarHistorial());
        pendientes.addActionListener(e -> mostrarPendientes());
        mostrarPendientes();
    }

    public void mostrarHistorial(String idActivo) {
        llenarTabla(controlador.consultarHistorial(idActivo));
    }

    private void mostrarPendientes() {
        llenarTabla(controlador.listarPendientes());
    }

    private void llenarTabla(Iterable<Mantenimiento> mantenimientos) {
        modelo.setRowCount(0);
        for (Mantenimiento m : mantenimientos) {
            modelo.addRow(new Object[]{m.getIdMantenimiento(),
                m.getActivo() == null ? "" : m.getActivo().getId(), m.getTipo(),
                m.getDescripcion(), String.format("$ %.2f", m.getCosto()),
                m.getEstado(), m.getFecha()});
        }
    }

    private void registrar() {
        JTextField id = new JTextField();
        JTextField idActivo = new JTextField();
        JTextField tipo = new JTextField("Preventivo");
        JTextField descripcion = new JTextField();
        JTextField costo = new JTextField();
        JComboBox<EstadoM> estado = new JComboBox<>(EstadoM.values());
        estado.setSelectedItem(EstadoM.MANTENIMIENTO);
        JPanel f = new JPanel(new GridLayout(0, 2, 6, 6));
        f.add(new JLabel("ID mantenimiento:")); f.add(id);
        f.add(new JLabel("ID del hardware:")); f.add(idActivo);
        f.add(new JLabel("Tipo:")); f.add(tipo);
        f.add(new JLabel("Descripción:")); f.add(descripcion);
        f.add(new JLabel("Costo:")); f.add(costo);
        f.add(new JLabel("Estado:")); f.add(estado);
        if (JOptionPane.showConfirmDialog(this, f, "Nuevo mantenimiento",
                JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) return;
        try {
            Activo activo = controladorActivos.buscarActivo(
                    UtilVista.requerido(idActivo.getText(), "ID del activo"));
            if (!(activo instanceof Hardware)) {
                throw new IllegalArgumentException(
                        "El activo debe existir y ser una computadora o impresora.");
            }
            Mantenimiento mantenimiento = new Mantenimiento(
                    UtilVista.requerido(id.getText(), "ID"),
                    UtilVista.requerido(tipo.getText(), "tipo"),
                    UtilVista.requerido(descripcion.getText(), "descripción"),
                    UtilVista.decimal(costo.getText(), "costo"),
                    (EstadoM) estado.getSelectedItem(), LocalDate.now(),
                    (Hardware) activo);
            controlador.registrarMantenimiento(mantenimiento);
            mostrarHistorial(activo.getId());
        } catch (Exception e) {
            UtilVista.error(this, e);
        }
    }

    private void consultarHistorial() {
        String id = JOptionPane.showInputDialog(this, "ID del activo:");
        if (id != null) mostrarHistorial(id.trim());
    }
}
