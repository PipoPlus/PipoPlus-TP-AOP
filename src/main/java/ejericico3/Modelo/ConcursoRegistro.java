package ejericico3.Modelo;

import java.util.List;
import java.util.Optional;

public interface ConcursoRegistro {

    List<Concurso> todosLosConcursos();

    Optional<Integer> encontrarIdConcursoPorNombre(String nombre);

    void guardarInscripcion(String apellido, String nombre, String telefono, String email, String concurso, int id);

}
