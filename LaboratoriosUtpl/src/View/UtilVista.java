package View;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class UtilVista {
    public static JLabel titulo(String texto) {
        JLabel titulo = new JLabel(texto);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 22f));
        return titulo;
    }

    public static DefaultTableModel modeloNoEditable(String... columnas) {
        return new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    public static String idSeleccionado(Component padre, JTable tabla) {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(padre, "Primero seleccione una fila.");
            return null;
        }
        return tabla.getValueAt(fila, 0).toString();
    }

    public static String requerido(String valor, String nombreCampo) {
        String texto = valor == null ? "" : valor.trim();
        if (texto.isEmpty()) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " es obligatorio.");
        }
        return texto;
    }

    public static int entero(String valor, String nombreCampo) {
        try {
            return Integer.parseInt(requerido(valor, nombreCampo));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(nombreCampo + " debe ser un número entero.");
        }
    }

    public static double decimal(String valor, String nombreCampo) {
        try {
            return Double.parseDouble(requerido(valor, nombreCampo));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(nombreCampo + " debe ser un número válido.");
        }
    }

    public static void error(Component padre, Exception e) {
        JOptionPane.showMessageDialog(padre, e.getMessage(), "Dato incorrecto",
                JOptionPane.ERROR_MESSAGE);
    }
}
