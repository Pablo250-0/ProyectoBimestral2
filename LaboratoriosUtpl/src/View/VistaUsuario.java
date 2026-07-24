
package View;
import Controller.ControladorUsuario;
import Model.CarreraIngenieria;
import Model.Rol;
import Model.Usuario;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class VistaUsuario extends JPanel {
    private final ControladorUsuario controlador;
    private final DefaultTableModel modelo = UtilVista.modeloNoEditable(
            "Código", "Cédula", "Nombres", "Correo", "Carrera", "Rol");
    private final JTable tabla = new JTable(modelo);

    public VistaUsuario(ControladorUsuario controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout(10, 10));
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.add(UtilVista.titulo("Usuarios"), BorderLayout.WEST);
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton registrar = new JButton("Registrar");
        JButton buscar = new JButton("Buscar");
        JButton eliminar = new JButton("Eliminar");
        JButton refrescar = new JButton("Actualizar tabla");
        acciones.add(registrar); acciones.add(buscar);
        acciones.add(eliminar); acciones.add(refrescar);
        encabezado.add(acciones, BorderLayout.EAST);
        tabla.setAutoCreateRowSorter(true);
        add(encabezado, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        registrar.addActionListener(e -> registrar());
        buscar.addActionListener(e -> buscar());
        eliminar.addActionListener(e -> eliminar());
        refrescar.addActionListener(e -> mostrarUsuarios());
        mostrarUsuarios();
    }

    public void mostrarUsuarios() {
        modelo.setRowCount(0);
        for (Usuario usuario : controlador.listarUsuarios()) {
            modelo.addRow(new Object[]{usuario.getCodigoUsuario(), usuario.getCedula(),
                usuario.getNombres(), usuario.getCorreo(),
                usuario.getCarrera().getNombre(), usuario.getRol()});
        }
    }

    private void registrar() {
        JTextField codigo = new JTextField();
        JTextField cedula = new JTextField();
        JTextField nombres = new JTextField();
        JTextField correo = new JTextField();
        JTextField telefono = new JTextField();
        JTextField carrera = new JTextField();
        JComboBox<Rol> rol = new JComboBox<>(Rol.values());
        JPanel f = new JPanel(new GridLayout(0, 2, 6, 6));
        f.add(new JLabel("Código:")); f.add(codigo);
        f.add(new JLabel("Cédula:")); f.add(cedula);
        f.add(new JLabel("Nombres:")); f.add(nombres);
        f.add(new JLabel("Correo:")); f.add(correo);
        f.add(new JLabel("Teléfono:")); f.add(telefono);
        f.add(new JLabel("Carrera:")); f.add(carrera);
        f.add(new JLabel("Rol:")); f.add(rol);
        if (JOptionPane.showConfirmDialog(this, f, "Nuevo usuario",
                JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) return;
        try {
            String nombreCarrera = UtilVista.requerido(carrera.getText(), "carrera");
            CarreraIngenieria c = new CarreraIngenieria("CARR-01", nombreCarrera, "UTPL");
            Usuario usuario = new Usuario(
                    UtilVista.requerido(codigo.getText(), "código"), c,
                    UtilVista.requerido(correo.getText(), "correo"),
                    (Rol) rol.getSelectedItem(),
                    UtilVista.requerido(cedula.getText(), "cédula"),
                    UtilVista.requerido(nombres.getText(), "nombres"),
                    UtilVista.requerido(telefono.getText(), "teléfono"),
                    "Loja");
            controlador.registrarUsuario(usuario);
            mostrarUsuarios();
        } catch (Exception e) {
            UtilVista.error(this, e);
        }
    }

    private void buscar() {
        String codigo = JOptionPane.showInputDialog(this, "Código del usuario:");
        if (codigo == null) return;
        Usuario usuario = controlador.buscarUsuario(codigo.trim());
        JOptionPane.showMessageDialog(this, usuario == null
                ? "No se encontró el usuario." : usuario.toString());
    }

    private void eliminar() {
        String codigo = UtilVista.idSeleccionado(this, tabla);
        if (codigo != null && JOptionPane.showConfirmDialog(this,
                "¿Eliminar el usuario " + codigo + "?", "Confirmar",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            controlador.eliminarUsuario(codigo);
            mostrarUsuarios();
        }
    }
}
