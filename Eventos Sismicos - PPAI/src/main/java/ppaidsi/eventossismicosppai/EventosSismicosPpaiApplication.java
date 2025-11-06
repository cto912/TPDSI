package ppaidsi.eventossismicosppai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ppaidsi.eventossismicosppai.model.EventoSismico;
import ppaidsi.eventossismicosppai.model.GestorEventoSismico;
import ppaidsi.eventossismicosppai.repository.EventoSismicoRepo;

import java.util.List;

@SpringBootApplication
public class EventosSismicosPpaiApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(EventosSismicosPpaiApplication.class, args);
    }

}
