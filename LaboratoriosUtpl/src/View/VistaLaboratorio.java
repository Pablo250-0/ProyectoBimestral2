
package View;
import Controller.ControladorLaboratorio;
import Model.Laboratorio;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class VistaLaboratorio extends JPanel{
    private final ControladorLaboratorio controlador;
    private final DefaultTableModel modelo = UtilVista.modeloNoEditable(
            "Código", "Nombre", "Edificio", "Aula", "Capacidad", "Activos");
    private final JTable tabla = new JTable(modelo);

    public VistaLaboratorio(ControladorLaboratorio controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout(10, 10));
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.add(UtilVista.titulo("Laboratorios"), BorderLayout.WEST);
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton registrar = new JButton("Registrar");
        JButton buscar = new JButton("Buscar");
        JButton refrescar = new JButton("Actualizar tabla");
        acciones.add(registrar);
        acciones.add(buscar);
        acciones.add(refrescar);
        encabezado.add(acciones, BorderLayout.EAST);
        tabla.setAutoCreateRowSorter(true);
        add(encabezado, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        registrar.addActionListener(e -> registrar());
        buscar.addActionListener(e -> buscar());
        refrescar.addActionListener(e -> mostrarLaboratorios());
        mostrarLaboratorios();
    }

    public void mostrarLaboratorios() {
        modelo.setRowCount(0);
        for (Laboratorio laboratorio : controlador.listarLaboratorios()) {
            modelo.addRow(new Object[]{laboratorio.getCodigoLab(),
                laboratorio.getNombreLab(), laboratorio.getEdificio(),
                laboratorio.getAula(), laboratorio.getCapacidad(),
                laboratorio.getActivos().size()});
        }
    }

    private void registrar() {
        JTextField codigo = new JTextField();
        JTextField nombre = new JTextField();
        JTextField edificio = new JTextField();
        JTextField aula = new JTextField();
        JTextField capacidad = new JTextField();
        JPanel formulario = new JPanel(new GridLayout(0, 2, 6, 6));
        formulario.add(new JLabel("Código:")); formulario.add(codigo);
        formulario.add(new JLabel("Nombre:")); formulario.add(nombre);
        formulario.add(new JLabel("Edificio:")); formulario.add(edificio);
        formulario.add(new JLabel("Aula:")); formulario.add(aula);
        formulario.add(new JLabel("Capacidad:")); formulario.add(capacidad);

        if (JOptionPane.showConfirmDialog(this, formulario, "Nuevo laboratorio",
                JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
            return;
        }
        try {
            Laboratorio laboratorio = new Laboratorio(
                    UtilVista.requerido(codigo.getText(), "código"),
                    UtilVista.requerido(nombre.getText(), "nombre"),
                    UtilVista.requerido(edificio.getText(), "edificio"),
                    UtilVista.requerido(aula.getText(), "aula"),
                    UtilVista.entero(capacidad.getText(), "capacidad"),
                    new ArrayList<>());
            controlador.registrarLaboratorio(laboratorio);
            mostrarLaboratorios();
        } catch (Exception e) {
            UtilVista.error(this, e);
        }
    }

    private void buscar() {
        String codigo = JOptionPane.showInputDialog(this, "Código del laboratorio:");
        if (codigo == null) return;
        Laboratorio laboratorio = controlador.buscarLaboratorio(codigo.trim());
        JOptionPane.showMessageDialog(this, laboratorio == null
                ? "No se encontró el laboratorio." : laboratorio.toString());
    }
}
