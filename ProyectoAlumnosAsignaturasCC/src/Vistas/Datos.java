package Vistas;

import Controlador.AlumnoasignaturaJpaController;
import Controlador.AlumnosJpaController;
import Controlador.AsignaturasJpaController;
import Controlador.exceptions.IllegalOrphanException;
import Controlador.exceptions.NonexistentEntityException;
import JPA.Alumnoasignatura;
import JPA.AlumnoasignaturaPK;
import JPA.Alumnos;
import JPA.Asignaturas;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Datos {

    EntityManagerFactory emf;

    AlumnoasignaturaJpaController aAC;
    AlumnosJpaController alC;
    AsignaturasJpaController asC;

    public int contadorAlumno = 0, contadorAsignatura = 0, contadorAA = 0;

    public Datos() {
        emf = Persistence.createEntityManagerFactory("ProyectoAlumnosAsignaturasCCPU");

        aAC = new AlumnoasignaturaJpaController(emf);
        alC = new AlumnosJpaController(emf);
        asC = new AsignaturasJpaController(emf);
    }

    public void persisteAlumno(int idAlumno, String apellidos, String nombre, int curso, int titulacion) throws Exception {

        Alumnos alumno = new Alumnos();

        alumno.setIdAlumno(idAlumno);
        alumno.setApellidos(apellidos);
        alumno.setNombre(nombre);
        alumno.setCurso(curso);
        alumno.setTitulacion(titulacion);

        alC.create(alumno);

    }

    public void persisteAsignatura(int idAsignatura, String nombreAsignatura, String tipoAsignatura, float creditos) throws Exception {

        Asignaturas asignatura = new Asignaturas();

        asignatura.setIdAsignatura(idAsignatura);
        asignatura.setNombre(nombreAsignatura);
        asignatura.setTipo(tipoAsignatura);
        asignatura.setCreditos(creditos);

        asC.create(asignatura);

    }

    public void persisteAlumnoAsignatura(int idAlumno, int idAsignatura, Character cursada) throws Exception {

        Alumnoasignatura aa = new Alumnoasignatura(idAlumno, idAsignatura);
        aa.setCursada(cursada);

        aAC.create(aa);

    }

    public String[][] registrosAlumnos() {

        String[][] registros;
        int numRegistros, contador = 0;

        // Obtener numero de registros a travez de un count
        numRegistros = alC.getAlumnosCount();

        registros = new String[numRegistros][5];

        List<Alumnos> listaUsuarios = alC.findAlumnosEntities();
        for (Alumnos usr : listaUsuarios) {

            registros[contador][0] = usr.getIdAlumno().toString();
            registros[contador][1] = usr.getApellidos();
            registros[contador][2] = usr.getNombre();
            registros[contador][3] = usr.getCurso() + "";
            registros[contador][4] = usr.getTitulacion() + "";

            contador++;

        }
        contador = 0;
        return registros;
    }

    public String[][] registrosAsignaturas() {

        String[][] registros;
        int numRegistros, contador = 0;

        // Obtener numero de registros 
        numRegistros = asC.getAsignaturasCount();

        registros = new String[numRegistros][4];

        List<Asignaturas> listaUsuarios = asC.findAsignaturasEntities();
        for (Asignaturas usr : listaUsuarios) {

            registros[contador][0] = usr.getIdAsignatura() + "";
            registros[contador][1] = usr.getNombre();
            registros[contador][2] = usr.getTipo() + "";
            registros[contador][3] = usr.getCreditos() + "";

            contador++;

        }
        contador = 0;
        return registros;

    }

    public String[][] registrosAA() {
        String[][] registros;
        int numRegistros, contador = 0;

        // Obtener numero de registros a travez de un count
        numRegistros = aAC.getAlumnoasignaturaCount();

        registros = new String[numRegistros][3];

        List<Alumnoasignatura> listaUsuarios = aAC.findAlumnoasignaturaEntities();
        for (Alumnoasignatura usr : listaUsuarios) {

            registros[contador][0] = usr.getAlumnoasignaturaPK().getIdAlumno() + "";
            registros[contador][1] = usr.getAlumnoasignaturaPK().getIdAsignatura() + "";
            registros[contador][2] = usr.getCursada() + "";

            contador++;

        }
        contador = 0;
        return registros;
    }

    public void eliminaAlumno(int idUsuario) throws IllegalOrphanException, NonexistentEntityException {

        alC.destroy(idUsuario);

    }

    public void eliminaAsignatura(int idAsignatura) throws IllegalOrphanException, NonexistentEntityException {

        asC.destroy(idAsignatura);

    }

    public String[] muestraSiguienteAlumno() {
        String[] registro = new String[5];
        String[][] datos = registrosAlumnos();

        contadorAlumno++;

        if (contadorAlumno >= datos.length) {
            contadorAlumno = datos.length - 1;
        }

        for (int i = 0; i < datos[0].length; i++) {
            registro[i] = datos[contadorAlumno][i];
        }
        return registro;

    }

    public String[] muestraSiguienteAsignatura() {
        String[] registro = new String[4];
        String[][] datos = registrosAsignaturas();

        contadorAsignatura++;

        if (contadorAsignatura >= datos.length) {
            contadorAsignatura = datos.length - 1;
        }

        for (int i = 0; i < datos[0].length; i++) {
            registro[i] = datos[contadorAsignatura][i];
        }

        return registro;

    }

    public String[] muestraSiguienteAA() {
        String[] registro = new String[34];
        String[][] datos = registrosAA();

        contadorAA++;

        if (contadorAA >= datos.length) {
            contadorAA = datos.length - 1;
        }

        for (int i = 0; i < datos[0].length; i++) {
            registro[i] = datos[contadorAA][i];
        }

        return registro;
    }

    public String[] muestraAnteriorAlumno() {
        String[] registro = new String[5];
        String[][] datos = registrosAlumnos();

        contadorAlumno--;

        if (contadorAlumno < 1) {
            contadorAlumno = 0;
        }

        for (int i = 0; i < datos[0].length; i++) {
            registro[i] = datos[contadorAlumno][i];
        }

        return registro;
    }

    public String[] muestraAnteriorAsignatura() {
        String[] registro = new String[4];
        String[][] datos = registrosAsignaturas();

        contadorAsignatura--;

        if (contadorAsignatura < 1) {
            contadorAsignatura = 0;
        }

        for (int i = 0; i < datos[0].length; i++) {
            registro[i] = datos[contadorAsignatura][i];
        }

        return registro;
    }

    public String[] muestraAnteriorAA() {
        String[] registro = new String[3];
        String[][] datos = registrosAA();

        contadorAA--;

        if (contadorAA < 1) {
            contadorAA = 0;
        }

        for (int i = 0; i < datos[0].length; i++) {
            registro[i] = datos[contadorAA][i];
        }

        return registro;
    }

    public void actualizaAlumno(int idAlumno, String apellidos, String nombre, int curso, int titulacion) throws NonexistentEntityException, Exception {
        Alumnos alumno;

        alumno = new Alumnos();

        alumno.setIdAlumno(idAlumno);
        alumno.setApellidos(apellidos);
        alumno.setNombre(nombre);
        alumno.setCurso(curso);
        alumno.setTitulacion(titulacion);

        alC.edit(alumno);

    }

    public void actualizaAsignatura(Integer id_asignatura, String tipo, String nombre, float creditos) throws NonexistentEntityException, Exception {
        Asignaturas asignatura;

        asignatura = new Asignaturas();
        asignatura.setIdAsignatura(id_asignatura);
        asignatura.setTipo(tipo);
        asignatura.setNombre(nombre);
        asignatura.setCreditos(creditos);

        asC.edit(asignatura);

    }

    public void actualizaAA(int idAlumno, int idAsignatura, Character cursada) throws NonexistentEntityException, Exception {
        AlumnoasignaturaPK aaPK = new AlumnoasignaturaPK(idAlumno, idAsignatura);

        Alumnoasignatura aa = new Alumnoasignatura();

        aa.setCursada(cursada);
        aa.setAlumnoasignaturaPK(aaPK);

        aAC.edit(aa);

    }

    public String[] alumno(int idAlumno) {

        String[] datosAlumno = new String[5];
        Alumnos alumno = alC.findAlumnos(idAlumno);

        datosAlumno[0] = alumno.getIdAlumno() + "";
        datosAlumno[1] = alumno.getApellidos() + "";
        datosAlumno[2] = alumno.getNombre() + "";
        datosAlumno[3] = alumno.getCurso() + "";
        datosAlumno[4] = alumno.getTitulacion() + "";

        return datosAlumno;

    }

    public String[] asignatura(int idAsignatura) {

        String[] datosAsignatura = new String[4];
        Asignaturas asignatura = asC.findAsignaturas(idAsignatura);

        datosAsignatura[0] = asignatura.getIdAsignatura() + "";
        datosAsignatura[1] = asignatura.getNombre();
        datosAsignatura[2] = asignatura.getTipo() + "";
        datosAsignatura[3] = asignatura.getCreditos() + "";

        return datosAsignatura;

    }

}
