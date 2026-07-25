package View;
import DAO.DatosMemoria;
import Controller.ControladorActivos;
import Controller.ControladorLaboratorio;
import Controller.ControladorMantenimiento;
import Controller.ControladorReporte;
import Controller.ControladorUsuario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class VistaPrincipal extends JFrame {
    private static final Color AZUL_UTPL = new Color(0, 77, 122);
    private final DatosMemoria datos;

    public VistaPrincipal() {
        datos = new DatosMemoria();
        configurarVentana();
        crearContenido();
    }

    private void configurarVentana() {
        setTitle("UTPL - Inventario de Laboratorios");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 650));
        setLocationRelativeTo(null);
    }

    private void crearContenido() {
        ControladorActivos activos = new ControladorActivos(datos.activos, datos.activos);
        ControladorLaboratorio laboratorios = new ControladorLaboratorio(datos.laboratorios);
        ControladorMantenimiento mantenimientos = new ControladorMantenimiento(datos.mantenimientos);
        ControladorUsuario usuarios = new ControladorUsuario(datos.usuarios);
        ControladorReporte reportes = new ControladorReporte(datos.activos);

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.add(crearCabecera(), BorderLayout.NORTH);

        JTabbedPane menu = new JTabbedPane();
        menu.setFont(menu.getFont().deriveFont(Font.BOLD, 14f));
        menu.addTab("Activos", new VistaActivo(activos));
        menu.addTab("Laboratorios", new VistaLaboratorio(laboratorios));
        menu.addTab("Mantenimientos", new VistaMantenimiento(mantenimientos, activos));
        menu.addTab("Usuarios", new VistaUsuario(usuarios));
        menu.addTab("Reportes", new VistaReporte(reportes));
        menu.setBorder(BorderFactory.createEmptyBorder(10, 12, 12, 12));
        raiz.add(menu, BorderLayout.CENTER);

        JLabel pie = new JLabel("Sistema académico de gestión de laboratorios",
                SwingConstants.CENTER);
        pie.setBorder(BorderFactory.createEmptyBorder(6, 6, 8, 6));
        raiz.add(pie, BorderLayout.SOUTH);
        setContentPane(raiz);
    }

    private JPanel crearCabecera() {
        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(AZUL_UTPL);
        cabecera.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));
        JLabel titulo = new JLabel("LAB INVENTARIO UTPL");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 24f));
        JLabel subtitulo = new JLabel("Administración de recursos tecnológicos");
        subtitulo.setForeground(Color.WHITE);
        cabecera.add(titulo, BorderLayout.WEST);
        cabecera.add(subtitulo, BorderLayout.EAST);
        return cabecera;
    }

    public void mostrarMenu() {
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("No se pudo aplicar el estilo del sistema.");
            }
            new VistaPrincipal().mostrarMenu();
        });
    }
}
