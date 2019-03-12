package Vistas;

import Controlador.exceptions.IllegalOrphanException;
import Controlador.exceptions.NonexistentEntityException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingConstants;

public class Home extends JFrame {

    JPanel pPrincipal, pEncabezado, pCuerpo, pBotones, pMarcadores;

    JLabel lbl1, lbl2, lbl3, lbl4, lbl5;
    JLabel lblIdAsignatura, lblNombreA, lblTipo, lblCreditos;

    JLabel cursada;

    JButton nuevo, eliminar, buscar, actualizar, inicio, fin, adelante, atras;

    JTextField jTF1, jTF2, jTF3, jTF4, jTF5;

    JComboBox jCB;

    Datos jpa;
    RegistroAlumno reg;
    RegistroAsignatura regA;
    RegistroAA regAA;

    String[] datos;

    public Home() {
        super("JPA");

        pPrincipal = new JPanel();
        pEncabezado = new JPanel();
        pCuerpo = new JPanel();
        pBotones = new JPanel();
        pMarcadores = new JPanel();

        pPrincipal.setLayout(new BorderLayout());
        pEncabezado.setLayout(new FlowLayout());
        pCuerpo.setLayout(new BoxLayout(pCuerpo, BoxLayout.Y_AXIS));
        pBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 15));
        pBotones.setPreferredSize(new Dimension(150, 300));
        pMarcadores.setLayout(new FlowLayout());

        lbl1 = new JLabel("Id alumno");
        lbl2 = new JLabel("Apellidos");
        lbl3 = new JLabel("Nombre/s");
        lbl4 = new JLabel("Curso");
        lbl5 = new JLabel("Titulación");

        nuevo = new JButton("Nuevo");
        eliminar = new JButton("Eliminar");
        buscar = new JButton("Buscar");
        actualizar = new JButton("Actualizar");
        inicio = new JButton("<<");
        fin = new JButton(">>");
        atras = new JButton("<");
        adelante = new JButton(">");

        jTF1 = new JTextField();
        jTF2 = new JTextField();
        jTF3 = new JTextField();
        jTF4 = new JTextField();
        jTF5 = new JTextField();

        jTF1.setHorizontalAlignment(SwingConstants.CENTER);
        jTF1.setEditable(false);
        jTF2.setHorizontalAlignment(SwingConstants.CENTER);
        jTF3.setHorizontalAlignment(SwingConstants.CENTER);
        jTF4.setHorizontalAlignment(SwingConstants.CENTER);
        jTF5.setHorizontalAlignment(SwingConstants.CENTER);

        jCB = new JComboBox();

        jCB.addItem("Alumnos");
        jCB.addItem("Asignaturas");
        jCB.addItem("Relaciones");

        jpa = new Datos();

        this.setSize(500, 300);
        this.setContentPane(pPrincipal);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void armaHome() {
        // JPanel pPrincipal, pEncabezado, pCuerpo, pBotones, pMarcadores;

        pEncabezado.add(jCB);

        pCuerpo.add(lbl1);
        pCuerpo.add(jTF1);
        pCuerpo.add(lbl2);
        pCuerpo.add(jTF2);
        pCuerpo.add(lbl3);
        pCuerpo.add(jTF3);
        pCuerpo.add(lbl4);
        pCuerpo.add(jTF4);
        pCuerpo.add(lbl5);
        pCuerpo.add(jTF5);

        pBotones.add(nuevo);
        nuevo.addActionListener((e) -> {

            nuevoRegistro();

        });

        pBotones.add(eliminar);
        eliminar.addActionListener((e) -> {
            try {
                elimina();
            } catch (IllegalOrphanException | NonexistentEntityException ex) {

            }
        });

        pBotones.add(buscar);
        buscar.addActionListener((e) -> {
            busca();
        });

        pBotones.add(actualizar);
        actualizar.addActionListener((e) -> {
            try {
                actualiza();
            } catch (IllegalOrphanException | NonexistentEntityException ex) {

            } catch (Exception ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        pMarcadores.add(inicio);
        inicio.addActionListener((e) -> {
            inicio();
        });

        pMarcadores.add(atras);
        atras.addActionListener((e) -> {

            datosAnterior();

        });

        pMarcadores.add(adelante);
        adelante.addActionListener((e) -> {

            muestraDatosSiguiente();

        });
        pMarcadores.add(fin);
        fin.addActionListener((e) -> {
            fin();
        });

        pPrincipal.add(pEncabezado, BorderLayout.NORTH);
        pPrincipal.add(pCuerpo, BorderLayout.CENTER);
        pPrincipal.add(pBotones, BorderLayout.EAST);
        pPrincipal.add(pMarcadores, BorderLayout.SOUTH);

        jCB.addItemListener((e) -> {

            String seleccion = (String) jCB.getSelectedItem();

            if (seleccion.equalsIgnoreCase("alumnos")) {
                muestraDatos();
            } else if (seleccion.equalsIgnoreCase("asignaturas")) {
                muestraDatosAsignaturas();

            } else if (seleccion.equalsIgnoreCase("relaciones")) {
                muestraDatosAA();
            }
        });

        this.setVisible(true);

    }

    public void muestraDatos() {

        lbl1.setText("Id Alumno");
        lbl2.setText("Apellidos");
        lbl3.setText("Nombre/s");
        lbl4.setText("Curso");
        lbl5.setText("Titulacion");

        jTF1.setText(jpa.registrosAlumnos()[0][0]);
        jTF2.setText(jpa.registrosAlumnos()[0][1]);
        jTF3.setText(jpa.registrosAlumnos()[0][2]);
        jTF4.setText(jpa.registrosAlumnos()[0][3]);
        jTF5.setText(jpa.registrosAlumnos()[0][4]);

        jTF4.setEnabled(true);
        jTF5.setEnabled(true);

    }

    public void muestraDatosAsignaturas() {
        lbl1.setText("Id Asignatura");
        lbl2.setText("Nombre");
        lbl3.setText("Tipo");
        lbl4.setText("Creditos");
        lbl5.setText("----");

        jTF1.setText(jpa.registrosAsignaturas()[0][0]);
        jTF2.setText(jpa.registrosAsignaturas()[0][1]);
        jTF3.setText(jpa.registrosAsignaturas()[0][2]);
        jTF4.setText(jpa.registrosAsignaturas()[0][3]);

        jTF4.setEnabled(true);

        jTF5.setText("");
        jTF5.setEnabled(false);

    }

    public void muestraDatosAA() {
        lbl1.setText("Id Alumno");
        lbl2.setText("Id Asignatura");
        lbl3.setText("Cursada");
        lbl4.setText("----");
        lbl5.setText("----");

        jTF1.setText(jpa.registrosAA()[0][0]);
        jTF2.setText(jpa.registrosAA()[0][1]);
        jTF3.setText(jpa.registrosAA()[0][2]);

        jTF4.setText("");
        jTF4.setEnabled(false);

        jTF5.setText("");
        jTF5.setEnabled(false);
    }

    public void muestraDatosSiguiente() {

        if (jCB.getSelectedItem().equals("Alumnos")) {
            datos = jpa.muestraSiguienteAlumno();

            jTF1.setText(datos[0]);
            jTF2.setText(datos[1]);
            jTF3.setText(datos[2]);
            jTF4.setText(datos[3]);
            jTF5.setText(datos[4]);
        } else if (jCB.getSelectedItem().equals("Asignaturas")) {
            datos = jpa.muestraSiguienteAsignatura();

            jTF1.setText(datos[0]);
            jTF2.setText(datos[1]);
            jTF3.setText(datos[2]);
            jTF4.setText(datos[3]);
            jTF5.setText("");
        } else if (jCB.getSelectedItem().equals("Relaciones")) {
            datos = jpa.muestraSiguienteAA();

            jTF1.setText(datos[0]);
            jTF2.setText(datos[1]);
            jTF3.setText(datos[2]);
            jTF4.setText("");
            jTF5.setText("");
        }
    }

    public void inicio() {
        if (jCB.getSelectedItem().equals("Alumnos")) {

            jTF1.setText(jpa.registrosAlumnos()[0][0]);
            jTF2.setText(jpa.registrosAlumnos()[0][1]);
            jTF3.setText(jpa.registrosAlumnos()[0][2]);
            jTF4.setText(jpa.registrosAlumnos()[0][3]);
            jTF5.setText(jpa.registrosAlumnos()[0][4]);

            jpa.contadorAlumno = 0;

        } else if (jCB.getSelectedItem().equals("Asignaturas")) {

            jTF1.setText(jpa.registrosAsignaturas()[0][0]);
            jTF2.setText(jpa.registrosAsignaturas()[0][1]);
            jTF3.setText(jpa.registrosAsignaturas()[0][2]);
            jTF4.setText(jpa.registrosAsignaturas()[0][3]);
            jTF5.setText("");

            jpa.contadorAsignatura = 0;
        } else if (jCB.getSelectedItem().equals("Relaciones")) {
            jTF1.setText(jpa.registrosAA()[0][0]);
            jTF2.setText(jpa.registrosAA()[0][1]);
            jTF3.setText(jpa.registrosAA()[0][2]);
            jTF4.setText("");
            jTF5.setText("");

            jpa.contadorAA = 0;
        }
    }

    public void fin() {
        if (jCB.getSelectedItem().equals("Alumnos")) {

            jTF1.setText(jpa.registrosAlumnos()[jpa.registrosAlumnos().length - 1][0]);
            jTF2.setText(jpa.registrosAlumnos()[jpa.registrosAlumnos().length - 1][1]);
            jTF3.setText(jpa.registrosAlumnos()[jpa.registrosAlumnos().length - 1][2]);
            jTF4.setText(jpa.registrosAlumnos()[jpa.registrosAlumnos().length - 1][3]);
            jTF5.setText(jpa.registrosAlumnos()[jpa.registrosAlumnos().length - 1][4]);

            jpa.contadorAlumno = jpa.registrosAlumnos().length - 1;

        } else if (jCB.getSelectedItem().equals("Asignaturas")) {

            jTF1.setText(jpa.registrosAsignaturas()[jpa.registrosAsignaturas().length - 1][0]);
            jTF2.setText(jpa.registrosAsignaturas()[jpa.registrosAsignaturas().length - 1][1]);
            jTF3.setText(jpa.registrosAsignaturas()[jpa.registrosAsignaturas().length - 1][2]);
            jTF4.setText(jpa.registrosAsignaturas()[jpa.registrosAsignaturas().length - 1][3]);
            jTF5.setText("");

            jpa.contadorAsignatura = jpa.registrosAsignaturas().length - 1;
        } else if (jCB.getSelectedItem().equals("Relaciones")) {

            jTF1.setText(jpa.registrosAA()[jpa.registrosAA().length - 1][0]);
            jTF2.setText(jpa.registrosAA()[jpa.registrosAA().length - 1][1]);
            jTF3.setText(jpa.registrosAA()[jpa.registrosAA().length - 1][2]);
            jTF4.setText("");
            jTF5.setText("");

            jpa.contadorAA = jpa.registrosAA().length - 1;

        }
    }

    public void datosAnterior() {

        if (jCB.getSelectedItem().equals("Alumnos")) {
            datos = jpa.muestraAnteriorAlumno();

            jTF1.setText(datos[0]);
            jTF2.setText(datos[1]);
            jTF3.setText(datos[2]);
            jTF4.setText(datos[3]);
            jTF5.setText(datos[4]);
        } else if (jCB.getSelectedItem().equals("Asignaturas")) {
            datos = jpa.muestraAnteriorAsignatura();

            jTF1.setText(datos[0]);
            jTF2.setText(datos[1]);
            jTF3.setText(datos[2]);
            jTF4.setText(datos[3]);
            jTF5.setText("");
        } else if (jCB.getSelectedItem().equals("Relaciones")) {
            datos = jpa.muestraAnteriorAA();

            jTF1.setText(datos[0]);
            jTF2.setText(datos[1]);
            jTF3.setText(datos[2]);
            jTF4.setText("");
            jTF5.setText("");
        }

    }

    public void nuevoRegistro() {
        if (jCB.getSelectedItem().equals("Alumnos")) {
            reg = new RegistroAlumno(this);
            reg.muestraFrame();

        } else if (jCB.getSelectedItem().equals("Asignaturas")) {
            regA = new RegistroAsignatura(this);
            regA.muestraFrame();
        } else if (jCB.getSelectedItem().equals("Relaciones")) {
            regAA = new RegistroAA(this);
            regAA.muestraFrame();
        }
    }

    public void elimina() throws IllegalOrphanException, NonexistentEntityException {
        if (jCB.getSelectedItem().equals("Alumnos")) {
            jpa.eliminaAlumno(Integer.parseInt(JOptionPane.showInputDialog(this, "Ingresa el ID del alumno", "Eliminación", JOptionPane.INFORMATION_MESSAGE)));
            JOptionPane.showMessageDialog(this, "Registro eliminado correctamente");
        } else if (jCB.getSelectedItem().equals("Asignaturas")) {
            jpa.eliminaAsignatura(Integer.parseInt(JOptionPane.showInputDialog(this, "Ingresa el ID de la asignatura", "Eliminación", JOptionPane.INFORMATION_MESSAGE)));
            JOptionPane.showMessageDialog(this, "Registro eliminado correctamente");
        }
    }

    public void actualiza() throws IllegalOrphanException, NonexistentEntityException, Exception {
        if (jCB.getSelectedItem().equals("Alumnos")) {
            jpa.actualizaAlumno(Integer.parseInt(jTF1.getText()), jTF2.getText(), jTF3.getText(), Integer.parseInt(jTF4.getText()), Integer.parseInt(jTF5.getText()));
            JOptionPane.showMessageDialog(this, "Registro actualizado");
        } else if (jCB.getSelectedItem().equals("Asignaturas")) {
            jpa.actualizaAsignatura(Integer.parseInt(jTF1.getText()), jTF2.getText(), jTF3.getText(), Float.parseFloat(jTF4.getText()));
            JOptionPane.showMessageDialog(this, "Registro actualizado");
        } else if (jCB.getSelectedItem().equals("Relaciones")) {
            jpa.actualizaAA(Integer.parseInt(jTF1.getText()), Integer.parseInt(jTF2.getText()), jTF3.getText().charAt(0));
            JOptionPane.showMessageDialog(this, "Registro actualizado");
        }

    }

    public void busca() {
        if (jCB.getSelectedItem().equals("Alumnos")) {
            String[] datosAlumno = jpa.alumno(Integer.parseInt(JOptionPane.showInputDialog(this, "Ingresa el ID del alumno")));

            jTF1.setText(datosAlumno[0]);
            jTF2.setText(datosAlumno[1]);
            jTF3.setText(datosAlumno[2]);
            jTF4.setText(datosAlumno[3]);
            jTF5.setText(datosAlumno[4]);

        } else if (jCB.getSelectedItem().equals("Asignaturas")) {
            String[] datosAsignatura = jpa.asignatura(Integer.parseInt(JOptionPane.showInputDialog(this, "Ingresa el ID de la asignatura")));

            jTF1.setText(datosAsignatura[0]);
            jTF2.setText(datosAsignatura[1]);
            jTF3.setText(datosAsignatura[2]);
            jTF4.setText(datosAsignatura[3]);
            jTF5.setText("-----");

        }

    }

}
