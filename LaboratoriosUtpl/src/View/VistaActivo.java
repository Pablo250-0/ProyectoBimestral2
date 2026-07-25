package View;
import Controller.ControladorActivos;
import Model.Activo;
import Model.ActivoDigital;
import Model.ActivoFisico;
import Model.Computadora;
import Model.EstadoM;
import Model.Impresora;
import Model.Licencia;
import Model.Software;
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

public class VistaActivo extends JPanel {
    
    private final ControladorActivos controlador;
    private final DefaultTableModel modelo = UtilVista.modeloNoEditable(
            "ID", "Nombre", "Tipo", "Estado", "Fecha ingreso", "Costo actual");
    private final JTable tabla = new JTable(modelo);

    public VistaActivo(ControladorActivos controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout(10, 10));

        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.add(UtilVista.titulo("Gestión de activos"), BorderLayout.WEST);
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton nuevo = new JButton("Registrar");
        JButton buscar = new JButton("Buscar");
        JButton eliminar = new JButton("Eliminar");
        JButton actualizar = new JButton("Actualizar tabla");
        acciones.add(nuevo);
        acciones.add(buscar);
        acciones.add(eliminar);
        acciones.add(actualizar);
        encabezado.add(acciones, BorderLayout.EAST);

        tabla.setFillsViewportHeight(true);
        tabla.setAutoCreateRowSorter(true);
        add(encabezado, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        nuevo.addActionListener(e -> capturarDatos());
        buscar.addActionListener(e -> buscarActivo());
        eliminar.addActionListener(e -> eliminarActivo());
        actualizar.addActionListener(e -> mostrarActivos());
        mostrarActivos();
    }

    public void mostrarActivos() {
        modelo.setRowCount(0);
        for (Activo activo : controlador.listarActivos()) {
            modelo.addRow(new Object[]{
                activo.getId(), activo.getNombre(), activo.getClass().getSimpleName(),
                estadoCalculado(activo), activo.getFechaIngreso(),
                String.format("$ %.2f", activo.calcularCosto())
            });
        }
    }

    private String estadoCalculado(Activo activo) {
        if (activo instanceof ActivoFisico) {
            return ((ActivoFisico) activo).verificarEstado();
        }
        if (activo instanceof ActivoDigital) {
            return ((ActivoDigital) activo).verificarEstado();
        }
        return activo.getEstado();
    }

    public void capturarDatos() {
        JComboBox<String> tipo = new JComboBox<>(new String[]{"Computadora", "Impresora", "Software"});
        JTextField id = new JTextField();
        JTextField nombre = new JTextField();
        JTextField estado = new JTextField("Activo");
        JTextField costo = new JTextField();
        JTextField dato1 = new JTextField();
        JTextField dato2 = new JTextField();

        JPanel formulario = new JPanel(new GridLayout(0, 2, 6, 6));
        formulario.add(new JLabel("Tipo:"));
        formulario.add(tipo);
        formulario.add(new JLabel("ID:"));
        formulario.add(id);
        formulario.add(new JLabel("Nombre:"));
        formulario.add(nombre);
        formulario.add(new JLabel("Estado general:"));
        formulario.add(estado);
        formulario.add(new JLabel("Costo base:"));
        formulario.add(costo);
        formulario.add(new JLabel("Dato principal:"));
        formulario.add(dato1);
        formulario.add(new JLabel("Dato secundario:"));
        formulario.add(dato2);

        int opcion = JOptionPane.showConfirmDialog(this, formulario,
                "Nuevo activo (dato principal/secundario según el tipo)",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (opcion != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            Activo activo = crearActivo(tipo.getSelectedIndex(), id.getText(),
                    nombre.getText(), estado.getText(), costo.getText(),
                    dato1.getText(), dato2.getText());
            controlador.registrarActivo(activo);
            mostrarActivos();
            JOptionPane.showMessageDialog(this, "Activo registrado correctamente.");
        } catch (Exception e) {
            UtilVista.error(this, e);
        }
    }

    private Activo crearActivo(int tipo, String id, String nombre, String estado,
            String costo, String dato1, String dato2) {
        String idLimpio = UtilVista.requerido(id, "ID");
        String nombreLimpio = UtilVista.requerido(nombre, "nombre");
        double costoBase = UtilVista.decimal(costo, "costo");
        LocalDate hoy = LocalDate.now();

        if (tipo == 0) {
            return new Computadora("Windows", UtilVista.requerido(dato1, "procesador"),
                    UtilVista.requerido(dato2, "RAM"), 512, hoy.plusMonths(6), 0,
                    hoy, EstadoM.FUNCIONAL, "Laboratorio", "S-" + idLimpio,
                    "UTPL", 60, nombreLimpio, idLimpio, estado, hoy, null, costoBase);
        }
        if (tipo == 1) {
            return new Impresora(UtilVista.requerido(dato1, "tipo de impresión"),
                    UtilVista.decimal(dato2, "velocidad"), 100, hoy.plusMonths(6),
                    0, hoy, EstadoM.FUNCIONAL, "Laboratorio", "S-" + idLimpio,
                    "UTPL", 60, nombreLimpio, idLimpio, estado, hoy, null, costoBase);
        }
        Licencia licencia = new Licencia(UtilVista.requerido(dato2, "tipo de licencia"),
                hoy.plusYears(1));
        return new Software("Multiplataforma", 1, "Académico", licencia,
                UtilVista.requerido(dato1, "proveedor"), "v2.0", nombreLimpio,
                idLimpio, estado, hoy, null, costoBase);
    }

    private void buscarActivo() {
        String id = JOptionPane.showInputDialog(this, "Ingrese el ID del activo:");
        if (id == null) {
            return;
        }
        Activo activo = controlador.buscarActivo(id.trim());
        JOptionPane.showMessageDialog(this, activo == null
                ? "No se encontró el activo."
                : activo.toString() + "\nEstado calculado: " + estadoCalculado(activo));
    }

    private void eliminarActivo() {
        String id = UtilVista.idSeleccionado(this, tabla);
        if (id == null) {
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el activo " + id + "?", "Confirmar",
                JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            controlador.eliminarActivo(id);
            mostrarActivos();
        }
    }
}
