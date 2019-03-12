/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class RegistroAsignatura extends JFrame {

    JTextField idAsignatura, nombre, tipo, creditos;
    JLabel lblIdAsignatura, lblNombre, lblTipo, lblCreditos, info, info2;
    JButton aceptar;
    Datos consulta;
    Home principal;

    public RegistroAsignatura(JFrame frameP) {
        super("Registro");
        frameP.dispose();
        idAsignatura = new JTextField();
        nombre = new JTextField();
        tipo = new JTextField();
        creditos = new JTextField();

        lblIdAsignatura = new JLabel("Ingresa el ID");
        lblNombre = new JLabel("Ingresa el nombre");
        lblTipo = new JLabel("Ingresa el tipo");
        lblCreditos = new JLabel("Ingresa los creditos");

        info = new JLabel("Formulario para el registro de ua asignatura");
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

        lblIdAsignatura.setBounds(15, 100, 150, 30);
        idAsignatura.setBounds(135, 100, 250, 30);

        lblNombre.setBounds(15, 150, 150, 30);
        nombre.setBounds(135, 150, 250, 30);

        lblTipo.setBounds(15, 200, 150, 30);
        tipo.setBounds(135, 200, 250, 30);

        lblCreditos.setBounds(15, 250, 150, 30);
        creditos.setBounds(135, 250, 250, 30);

        aceptar.setBounds(150, 300, 100, 30);

        add(info);
        add(info2);

        add(idAsignatura);
        add(lblIdAsignatura);
        add(lblNombre);
        add(lblTipo);
        add(lblCreditos);

        add(idAsignatura);
        add(nombre);
        add(tipo);
        add(creditos);

        add(aceptar);

        aceptar.addActionListener((e) -> {
            consulta = new Datos();

            try {
                consulta.persisteAsignatura(Integer.parseInt(idAsignatura.getText()), nombre.getText(), tipo.getText(), Float.parseFloat(creditos.getText()));
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
