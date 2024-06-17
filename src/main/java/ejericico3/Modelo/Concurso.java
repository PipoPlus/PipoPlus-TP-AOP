package ejericico3.Modelo;

import java.time.LocalDate;

public class Concurso {

    int idConcurso;
    String nombreConcurso;
    LocalDate fechaInicioInscripcion;
    LocalDate fechaFinInscripcion;


 public Concurso(int id, String nombre, LocalDate inscripcionFechaI, LocalDate inscripcionFechaF){

     this.idConcurso = id;
     this.nombreConcurso = nombre;
     this.fechaInicioInscripcion = inscripcionFechaI;
     this.fechaFinInscripcion = inscripcionFechaF;

 }

    public int getIdConcurso() {
        return idConcurso;
    }

    public String getNombreConcurso() {
        return nombreConcurso;
    }

    public LocalDate getFechaInicioInscripcion() {
        return fechaInicioInscripcion;
    }

    public LocalDate getFechaFinInscripcion() {
        return fechaFinInscripcion;
    }

    public boolean estaAbierto() {
        LocalDate tiempoActual = LocalDate.now();
        return !tiempoActual.isBefore(fechaInicioInscripcion) && !tiempoActual.isAfter(fechaFinInscripcion);
    }

}
