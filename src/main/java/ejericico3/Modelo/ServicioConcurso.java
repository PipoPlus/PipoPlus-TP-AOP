package ejericico3.Modelo;

import java.util.List;
import java.util.Optional;

public class ServicioConcurso {

    ConcursoRegistro concursosDAO;


    public ServicioConcurso(ConcursoRegistro concursosDAO1){
        this.concursosDAO = concursosDAO1;
    }


    public List<Concurso> todosLosConcursos(){
        return concursosDAO.todosLosConcursos();
    }


    public void guardarInscripcion(String nombre, String apellido, String id, String telefono, String email, String nombreConcurso) {
        Optional<Integer> concursoIdOptional = concursosDAO.encontrarIdConcursoPorNombre(nombreConcurso);
        if (concursoIdOptional.isPresent()) {
            int competitionId = concursoIdOptional.get();
            concursosDAO.guardarInscripcion(apellido, nombre, telefono, email, nombreConcurso, competitionId);
        } else {
            System.err.println("No se encontro el ID del concurso para el nombre: " + nombreConcurso);
        }
    }


}
