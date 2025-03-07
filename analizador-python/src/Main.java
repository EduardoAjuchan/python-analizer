import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Main extends JFrame {
    private JTextPane textPane;

    public Main() {
        super("Analizador Léxico Python - Resaltado de Sintaxis");
        initUI();
    }

    private void initUI() {
        // Crear un JTextPane y colocarlo en un JScrollPane
        textPane = new JTextPane();
        textPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textPane);

        // Botón para cargar el archivo
        JButton loadButton = new JButton("Cargar Archivo");
        loadButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarArchivo();
            }
        });

        // Organizar componentes en el JFrame
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(loadButton, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // Método que permite al usuario seleccionar el archivo
    private void cargarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if(result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            analizarArchivo(selectedFile);
        }
    }

    // Método que analiza el archivo usando PythonLexer y muestra el resultado
    private void analizarArchivo(File file) {
        // Limpiar el contenido previo del JTextPane
        textPane.setText("");
        StyledDocument doc = textPane.getStyledDocument();

        // Definir estilos para cada tipo de token
        Style styleReserved = doc.addStyle("Reserved", null);
        StyleConstants.setForeground(styleReserved, Color.BLUE);

        Style styleComment = doc.addStyle("Comment", null);
        StyleConstants.setForeground(styleComment, new Color(0, 128, 0)); // Verde

        Style styleString = doc.addStyle("String", null);
        StyleConstants.setForeground(styleString, Color.RED);

        Style styleNumber = doc.addStyle("Number", null);
        StyleConstants.setForeground(styleNumber, Color.ORANGE);

        Style styleOperator = doc.addStyle("Operator", null);
        StyleConstants.setForeground(styleOperator, Color.MAGENTA);

        Style styleVariable = doc.addStyle("Variable", null);
        StyleConstants.setForeground(styleVariable, Color.BLACK);

        Style styleSymbol = doc.addStyle("Symbol", null);
        StyleConstants.setForeground(styleSymbol, Color.GRAY);

        // Estilo por defecto para WHITESPACE y NEWLINE
        Style styleDefault = doc.addStyle("Default", null);
        StyleConstants.setForeground(styleDefault, Color.BLACK);

        try {
            Reader reader = new FileReader(file);
            PythonLexer lexer = new PythonLexer(reader);
            String token;

            // Procesamos los tokens retornados por el lexer
            while ((token = lexer.yylex()) != null) {
                // Imprimir en consola para depuración
                System.out.println("Token generado: " + token);

                // Se espera el formato "TIPO: lexema"
                String type = "";
                String lexeme = token;
                int index = token.indexOf(":");
                if (index != -1) {
                    type = token.substring(0, index).trim();
                    // Conserva el contenido exacto del lexema (sin trim para preservar espacios y saltos)
                    lexeme = token.substring(index + 1);
                }

                // Seleccionar el estilo según el tipo de token
                Style style;
                switch (type) {
                    case "RESERVED":
                        style = styleReserved;
                        break;
                    case "COMMENT":
                        style = styleComment;
                        break;
                    case "STRING":
                        style = styleString;
                        break;
                    case "FLOAT":
                    case "INT":
                        style = styleNumber;
                        break;
                    case "OPERATOR":
                        style = styleOperator;
                        break;
                    case "VARIABLE":
                        style = styleVariable;
                        break;
                    case "SYMBOL":
                        style = styleSymbol;
                        break;
                    case "WHITESPACE":
                    case "NEWLINE":
                        style = styleDefault;
                        break;
                    default:
                        style = styleDefault;
                        break;
                }
                // Insertar el lexema en el documento sin agregar espacios adicionales.
                doc.insertString(doc.getLength(), lexeme, style);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al analizar el archivo: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}