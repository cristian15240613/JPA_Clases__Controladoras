package Vistas;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class RegistroAA extends JFrame {

    JTextField idAlumno, idAsignatura, cursada;
    JLabel lblIdAlumno, lblIdAsignatura, lblCursada, info2;
    JButton aceptar;
    Datos consulta;
    Home principal;

    public RegistroAA(JFrame frameP) {
        super("Registro");
        frameP.dispose();
        idAlumno = new JTextField();
        idAsignatura = new JTextField();
        cursada = new JTextField();

        lblIdAlumno = new JLabel("ID del alumno");
        lblIdAsignatura = new JLabel("ID de la asignatura");
        lblCursada = new JLabel("¿Cursada? (S o N)");

        info2 = new JLabel("Recuerda llenar tus datos correctamente");

        aceptar = new JButton("Aceptar");

        setLayout(null);
        setBounds(0, 0, 400, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void muestraFrame() {

        info2.setBounds(90, 60, 250, 30);

        lblIdAlumno.setBounds(15, 100, 150, 30);
        idAlumno.setBounds(135, 100, 250, 30);

        lblIdAsignatura.setBounds(15, 150, 150, 30);
        idAsignatura.setBounds(135, 150, 250, 30);

        lblCursada.setBounds(15, 200, 150, 30);
        cursada.setBounds(135, 200, 250, 30);

        aceptar.setBounds(150, 250, 100, 30);

        add(info2);

        add(idAlumno);
        add(lblIdAsignatura);
        add(lblCursada);
        add(lblIdAlumno);

        add(idAlumno);
        add(idAsignatura);
        add(cursada);

        add(aceptar);

        aceptar.addActionListener((e) -> {
            consulta = new Datos();

            try {
                consulta.persisteAlumnoAsignatura(Integer.parseInt(idAlumno.getText()), Integer.parseInt(idAsignatura.getText()), cursada.getText().charAt(0));
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
