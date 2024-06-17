package ejericico3.Modelo;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
public class LogAspect {

    @Pointcut("@annotation(Log)")
    public void logMethods() {}

    @After("logMethods()")
    public void logAfterMeodo(JoinPoint joinPoint) {
        String nombreMetodo = joinPoint.getSignature().getName();
        String parametros = Arrays.stream(joinPoint.getArgs())
                .map(Object::toString)                  //Con "stream" corrige el error al registrar los logs en el metodo "todosLosConcursos"
                .collect(Collectors.joining("|"));

        if (parametros.isEmpty()) {
            parametros = "sin parametros";
        }

        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        String logCompleto = String.format("\"%s\", \"%s\", \"%s\"", nombreMetodo, parametros, fecha);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("log.txt", true))) {
            writer.write(logCompleto);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
