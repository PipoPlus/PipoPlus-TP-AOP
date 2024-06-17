package ejericico3.Persistencia;

import ejericico3.Modelo.Concurso;
import ejericico3.Modelo.ConcursoRegistro;
import ejericico3.Modelo.Log;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConcursoDisco implements ConcursoRegistro {

    String concursoTxt;
    String inscriptosTxt;

    private final List<Concurso> concursos;



    public ConcursoDisco(String rutaConcursos,String rutaInscriptos) {
        this.concursos = new ArrayList<>();
        this.concursoTxt = rutaConcursos;
        this.inscriptosTxt = rutaInscriptos;
    }


    @Override
    @Log
    public List<Concurso> todosLosConcursos() {

        try (BufferedReader br = new BufferedReader(new FileReader(concursoTxt))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                String startDateStr = parts[2].trim();
                String endDateStr = parts[3].trim();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDate startDate = LocalDate.parse(startDateStr, formatter);
                LocalDate endDate = LocalDate.parse(endDateStr, formatter);
                Concurso competition = new Concurso(id, name, startDate, endDate);
                if (competition.estaAbierto()) {
                    concursos.add(competition);
                }

            }
        } catch (IOException e) {
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

    @Log
    public void guardarInscripcion(String apellido, String nombre, String telefono, String email, String concurso, int id) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inscriptosTxt, true))) {

            // Formato: apellido, nombre, teléfono, email, idconcurso
            String inscriptionData = String.format("%s, %s, %s, %s, %d\n", apellido, nombre, telefono, email, id);
            writer.write(inscriptionData);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

}
