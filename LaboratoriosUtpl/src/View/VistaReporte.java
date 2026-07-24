package View;
import Controller.ControladorReporte;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class VistaReporte extends JPanel {
    private final ControladorReporte controlador;
    private final JTextArea areaReporte = new JTextArea();

    public VistaReporte(ControladorReporte controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout(10, 10));
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.add(UtilVista.titulo("Reportes"), BorderLayout.WEST);
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton inventario = new JButton("Reporte de inventario");
        JButton costos = new JButton("Reporte de costos");
        acciones.add(inventario);
        acciones.add(costos);
        encabezado.add(acciones, BorderLayout.EAST);
        areaReporte.setEditable(false);
        areaReporte.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        add(encabezado, BorderLayout.NORTH);
        add(new JScrollPane(areaReporte), BorderLayout.CENTER);
        inventario.addActionListener(e -> generarReporteInventario());
        costos.addActionListener(e -> generarReporteCostos());
        generarReporteInventario();
    }

    public void generarReporteInventario() {
        areaReporte.setText(controlador.generarReporteInventario());
    }

    public void generarReporteCostos() {
        areaReporte.setText(controlador.generarReporteCostos());
    }
}
