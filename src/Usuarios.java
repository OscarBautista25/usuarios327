import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Usuarios extends JFrame {
    private JPanel panel;
    private JTextField idText;
    private JTextField rolText;
    private JTextField nombreText;
    private JButton ingresarBoton;
    private JButton consultarBoton;
    private JTable tablaRegistros;
    private JList lista;
    Connection conexion;
    PreparedStatement preparar;
    String[] datos = {"id","nombre", "rol"};
    String[] registros = new String[10];
    DefaultTableModel modeloTabla = new DefaultTableModel(null, datos);
    DefaultListModel modelo = new DefaultListModel();
    Statement traer;
    ResultSet resultado;


    public Usuarios() {
        consultarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    consultar();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        ingresarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    ingresar();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public Connection conectar(){
        try{
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/usuario327","root","1234");
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return conexion;
    }
    void consultar() throws SQLException {
        conectar();
        tablaRegistros.setModel(modeloTabla);
        traer = conexion.createStatement();
        resultado = traer.executeQuery("Select id, nombre, rol from usuarios");
        //modelo.removeAllElements();
        //while (resultado.next()){
        //    modelo.addElement(resultado.getString(1) + " || " + resultado.getString(2) + " || " + resultado.getString(3));
        //}

        while (resultado.next()){
               registros[0] = resultado.getString("id");
               registros[1] = resultado.getString("nombre");
               registros[2] = resultado.getString("rol");
               modeloTabla.addRow(registros);
           }
    }
    void ingresar() throws SQLException {
        conectar();
        preparar = conexion.prepareStatement("Insert into usuarios (id, nombre, rol) values (?,?,?)");
        preparar.setInt(1,Integer.parseInt(idText.getText()));
        preparar.setString(2, nombreText.getText());
        preparar.setString(3, rolText.getText());
        if (preparar.executeUpdate()>0){
            lista.setModel(modelo);
            modelo.removeAllElements();
            modelo.addElement("El usuario ha sido ingresado exitosamente");

            idText.setText("");
            nombreText.setText("");
            rolText.setText("");
        }
    }

  public void mostrarVentanaUsuario() {
        Usuarios usuario1 = new Usuarios();
        usuario1.setContentPane(new Usuarios().panel);
        usuario1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        usuario1.setVisible(true);
        usuario1.pack();
    }
}
