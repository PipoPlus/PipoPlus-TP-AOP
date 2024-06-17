package ejericico3.Persistencia;

import ejericico3.Modelo.Concurso;
import ejericico3.Modelo.ConcursoRegistro;
import ejericico3.Modelo.Log;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConcursoBaseDeDatos implements ConcursoRegistro {

    public  String JDBC_URL;
    public  String USER;
    public  String PASSWORD;
    private final List<Concurso> concursos;

    public ConcursoBaseDeDatos(String JDBC_URL, String USER, String PASSWORD) {
        this.concursos = new ArrayList<>();
        this.JDBC_URL = JDBC_URL;
        this.USER = USER;
        this.PASSWORD = PASSWORD;

    }

    @Override
    @Log
    public List<Concurso> todosLosConcursos() {

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, nombre, fechaInicioInscripcion, fechaFinInscripcion FROM concursos")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                LocalDate fechaInicio = rs.getDate("fechaInicioInscripcion").toLocalDate();
                LocalDate fechaFin = rs.getDate("fechaFinInscripcion").toLocalDate();
                // Crear objeto Concurso con los datos obtenidos
                Concurso concurso = new Concurso(id, nombre, fechaInicio, fechaFin);
                concursos.add(concurso);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return concursos;
    }

    @Override
    public Optional<Integer> encontrarIdConcursoPorNombre(String nombre) {
        for (Concurso concurso : concursos) {
            if (concurso.getNombreConcurso().equals(nombre)) {
                return Optional.of(concurso.getIdConcurso());
            }
        }
        return Optional.empty();
    }

    @Override
    @Log
    public void guardarInscripcion(String apellido, String nombre, String telefono, String email, String concurso, int id) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO inscriptos (apellido, nombre, telefono, email, concurso, id) VALUES (?, ?, ?, ?, ?, ?)")) {

            stmt.setString(1, apellido);
            stmt.setString(2, nombre);
            stmt.setString(3, telefono);
            stmt.setString(4, email);
            stmt.setString(5, concurso);
            stmt.setInt(6, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
