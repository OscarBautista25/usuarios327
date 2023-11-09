import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JFrame{
    private JPanel panelLog;
    private JTextField usuarioText;
    private JPasswordField contraText;
    private JButton botonAgregar;
    private JButton botonValidar;
    Connection conexion;
    PreparedStatement preparar;
    Statement traer;
    ResultSet resultado;

    public Login() {
        botonAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    agregarAdministrador();
                    JOptionPane.showMessageDialog(null, "Registro Exitoso");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Registro Fallido" + e.getMessage());;
            }
            }
        });

        botonValidar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    validarAdministador();
                } catch (SQLException e) {
                    e.printStackTrace();
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

    public void agregarAdministrador() throws SQLException {
        conectar();
        preparar = conexion.prepareStatement("Insert into administradores (usuario, pass) values (?,?)");
        preparar.setString(1, usuarioText.getText());
        preparar.setString(2, String.valueOf(contraText.getText()));
        preparar.executeUpdate();
    }

    public void validarAdministador() throws SQLException{
        conectar();
        int validacion = 0;
        String usuario = usuarioText.getText();
        String pass = String.valueOf(contraText.getText());

        try{
            traer = conexion.createStatement();
            resultado = traer.executeQuery("Select * from administradores where usuario ='"+usuario+ "'and pass='"+pass+"'");
            if(resultado.next()){
                validacion=1;
                if (validacion==1){
                    Usuarios enlazar = new Usuarios();
                    enlazar.mostrarVentanaUsuario();
                }

            }else {
                JOptionPane.showMessageDialog(null, "Error de acceso, usuario no autorizado");
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());

        }



    }


    public static void main(String[] args) {
        Login login1 = new Login();
        login1.setContentPane(new Login().panelLog);
        login1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login1.setVisible(true);
        login1.pack();


}
}