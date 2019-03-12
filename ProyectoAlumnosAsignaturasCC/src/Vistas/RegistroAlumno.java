package Vistas;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class RegistroAlumno extends JFrame {

    JTextField idAlumno, apellidos, curso, titulacion, nombre;
    JLabel lblNombre, lblApellidos, lblTitulacion, lblCurso, lblIDAlumno, info, info2;
    JButton aceptar;
    Datos consulta;
    Home principal;

    public RegistroAlumno(JFrame frameP) {
        super("Registro");
        frameP.dispose();
        idAlumno = new JTextField();
        apellidos = new JTextField();
        curso = new JTextField();
        titulacion = new JTextField();
        nombre = new JTextField();

        lblApellidos = new JLabel("Ingresa los apellidos");
        lblNombre = new JLabel("Ingresa el nombre");
        lblTitulacion = new JLabel("Ingresa la titulacion");
        lblCurso = new JLabel("Ingresa el curso");
        lblIDAlumno = new JLabel("Ingresa el ID");
        info = new JLabel("Formulario para el registro de un alumno");
        info2 = new JLabel("Recuerda llenar tus datos correctamente");

        aceptar = new JButton("Aceptar");

        setLayout(null);
        setBounds(0, 0, 400, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void muestraFrame() {
        info.setBounds(85, 30, 250, 30);
        info2.setBounds(90, 60, 250, 30);

        lblIDAlumno.setBounds(15, 100, 150, 30);
        idAlumno.setBounds(135, 100, 250, 30);

        lblApellidos.setBounds(15, 150, 150, 30);
        apellidos.setBounds(135, 150, 250, 30);

        lblNombre.setBounds(15, 200, 150, 30);
        nombre.setBounds(135, 200, 250, 30);

        lblCurso.setBounds(15, 250, 150, 30);
        curso.setBounds(135, 250, 250, 30);

        lblTitulacion.setBounds(15, 300, 150, 30);
        titulacion.setBounds(135, 300, 250, 30);

        aceptar.setBounds(150, 350, 100, 30);

        add(info);
        add(info2);

        add(lblNombre);
        add(lblApellidos);
        add(lblTitulacion);
        add(lblCurso);
        add(lblIDAlumno);

        add(idAlumno);
        add(apellidos);
        add(curso);
        add(titulacion);
        add(nombre);

        add(aceptar);

        aceptar.addActionListener((e) -> {
            consulta = new Datos();

            try {
                consulta.persisteAlumno(Integer.parseInt(idAlumno.getText()), apellidos.getText(), nombre.getText(), Integer.parseInt(curso.getText()), Integer.parseInt(titulacion.getText()));
                JOptionPane.showMessageDialog(this, "Registro exitoso", "Registro", 1);
            } catch (Exception exp) {
                JOptionPane.showMessageDialog(this, "Hubo un problema en el registro", "Registro", JOptionPane.ERROR_MESSAGE);
            }

            this.dispose();
            principal = new Home();
            principal.armaHome();
            principal.muestraDatos();

        });

        setVisible(true);
    }

}
