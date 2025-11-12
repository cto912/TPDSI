package ppaidsi.eventossismicosppai.configuration;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ppaidsi.eventossismicosppai.model.*;
import ppaidsi.eventossismicosppai.repository.*;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initDataBase(EmpleadoRepo empleadoRepo, SesionRepo sesionRepo, EventoSismicoRepo eventoSismicoRepo, UsuarioRepo usuarioRepo,
                                   TipoDeDatoRepo tipoDeDatoRepo, AlcanceSismoRepo alcanceSismoRepo, ClasificacionSismoRepo clasificacionSismoRepo,
                                   OrigenDeGeneracionRepo origenDeGeneracionRepo, EstacionSismologicaRepo estacionSismologicaRepo,
                                   SismografoRepo sismografoRepo) {
        return args -> {

            //------------------------------------------TIPOS DE DATO------------------------------------------------------------------------
            TipoDeDato velocidadDeOnda = new TipoDeDato("Velocidad de onda", "Km/seg", 0.0);
            TipoDeDato frecuenciaDeOnda = new TipoDeDato("Frecuencia de onda", "Hz", 0.0);
            TipoDeDato longitud = new TipoDeDato("Longitud", "Km/ciclo", 0.0);
            tipoDeDatoRepo.save(velocidadDeOnda);
            tipoDeDatoRepo.save(frecuenciaDeOnda);
            tipoDeDatoRepo.save(longitud);

            //------------------------------------------DETALLES DE MUESTRA------------------------------------------------------------------------
            DetalleMuestraSismica detalleMuestra11 = new DetalleMuestraSismica(7, velocidadDeOnda);
            DetalleMuestraSismica detalleMuestra12 = new DetalleMuestraSismica(10, frecuenciaDeOnda);
            DetalleMuestraSismica detalleMuestra13 = new DetalleMuestraSismica(0.7, longitud);

            DetalleMuestraSismica detalleMuestra21 = new DetalleMuestraSismica(7.02, velocidadDeOnda);
            DetalleMuestraSismica detalleMuestra22 = new DetalleMuestraSismica(10, frecuenciaDeOnda);
            DetalleMuestraSismica detalleMuestra23 = new DetalleMuestraSismica(0.69, longitud);

            DetalleMuestraSismica detalleMuestra31 = new DetalleMuestraSismica(6.99, velocidadDeOnda);
            DetalleMuestraSismica detalleMuestra32 = new DetalleMuestraSismica(10.01, frecuenciaDeOnda);
            DetalleMuestraSismica detalleMuestra33 = new DetalleMuestraSismica(0.7, longitud);

            DetalleMuestraSismica detalleMuestra111 = new DetalleMuestraSismica(8, velocidadDeOnda);
            DetalleMuestraSismica detalleMuestra112 = new DetalleMuestraSismica(7.33, frecuenciaDeOnda);
            DetalleMuestraSismica detalleMuestra113 = new DetalleMuestraSismica(1.3, longitud);

            DetalleMuestraSismica detalleMuestra121 = new DetalleMuestraSismica(7.93, velocidadDeOnda);
            DetalleMuestraSismica detalleMuestra122 = new DetalleMuestraSismica(8, frecuenciaDeOnda);
            DetalleMuestraSismica detalleMuestra123 = new DetalleMuestraSismica(1, longitud);

            DetalleMuestraSismica detalleMuestra131 = new DetalleMuestraSismica(8.03, velocidadDeOnda);
            DetalleMuestraSismica detalleMuestra132 = new DetalleMuestraSismica(7.99, frecuenciaDeOnda);
            DetalleMuestraSismica detalleMuestra133 = new DetalleMuestraSismica(0.98, longitud);

            DetalleMuestraSismica detalleMuestra211 = new DetalleMuestraSismica(8, velocidadDeOnda);
            DetalleMuestraSismica detalleMuestra212 = new DetalleMuestraSismica(7.33, frecuenciaDeOnda);
            DetalleMuestraSismica detalleMuestra213 = new DetalleMuestraSismica(1.3, longitud);

            DetalleMuestraSismica detalleMuestra221 = new DetalleMuestraSismica(7.93, velocidadDeOnda);
            DetalleMuestraSismica detalleMuestra222 = new DetalleMuestraSismica(8, frecuenciaDeOnda);
            DetalleMuestraSismica detalleMuestra223 = new DetalleMuestraSismica(1, longitud);

            DetalleMuestraSismica detalleMuestra231 = new DetalleMuestraSismica(8.03, velocidadDeOnda);
            DetalleMuestraSismica detalleMuestra232 = new DetalleMuestraSismica(7.99, frecuenciaDeOnda);
            DetalleMuestraSismica detalleMuestra233 = new DetalleMuestraSismica(0.98, longitud);

            //------------------------------------------MUESTRAS SISMICAS------------------------------------------------------------------------
            MuestraSismica muestraSismica11 = new MuestraSismica(LocalDateTime.of(2025, 2, 21, 19, 5, 41),
                                                                 List.of(detalleMuestra11, detalleMuestra12, detalleMuestra13));
            MuestraSismica muestraSismica12 = new MuestraSismica(LocalDateTime.of(2025, 2, 21, 19, 10, 41),
                                                                 List.of(detalleMuestra21, detalleMuestra22, detalleMuestra23));
            MuestraSismica muestraSismica13 = new MuestraSismica(LocalDateTime.of(2025, 2, 21, 19, 15, 41),
                                                                 List.of(detalleMuestra31, detalleMuestra32, detalleMuestra33));

            MuestraSismica muestraSismica21 = new MuestraSismica(LocalDateTime.now(),
                                                                 List.of(detalleMuestra111, detalleMuestra112, detalleMuestra113));
            MuestraSismica muestraSismica22 = new MuestraSismica(LocalDateTime.now().plusSeconds(10),
                                                                 List.of(detalleMuestra121, detalleMuestra122, detalleMuestra123));
            MuestraSismica muestraSismica23 = new MuestraSismica(LocalDateTime.now().plusSeconds(20),
                                                                 List.of(detalleMuestra131, detalleMuestra132, detalleMuestra133));

            MuestraSismica muestraSismica31 = new MuestraSismica(LocalDateTime.now(),
                                                                 List.of(detalleMuestra211, detalleMuestra212, detalleMuestra213));
            MuestraSismica muestraSismica32 = new MuestraSismica(LocalDateTime.now().plusSeconds(10),
                                                                 List.of(detalleMuestra221, detalleMuestra222, detalleMuestra223));
            MuestraSismica muestraSismica33 = new MuestraSismica(LocalDateTime.now().plusSeconds(20),
                                                                 List.of(detalleMuestra231, detalleMuestra232, detalleMuestra233));


            //------------------------------------------ESTACIONES SISMOLOGICAS------------------------------------------------------------------------
            EstacionSismologica estacionSismologica1 = new EstacionSismologica("ABC123", "CERTIFICADO.PDF", LocalDateTime.of(2020, 10, 21, 0, 0),
                                                                                205.4, 405.4, "Estacion Mina Clavero", 134);
            EstacionSismologica estacionSismologica2 = new EstacionSismologica("DEF456", "CERTIFICADO.PDF", LocalDateTime.of(2020, 10, 21, 0, 0),
                                                                                205.4, 405.4, "Estacion Mina Clavero", 134);
            estacionSismologicaRepo.save(estacionSismologica1);
            estacionSismologicaRepo.save(estacionSismologica2);

            //------------------------------------------SISMOGRAFOS------------------------------------------------------------------------
            Sismografo sismografo1 = new Sismografo(LocalDateTime.of(2021, 8, 17, 0, 0), "SIS23", "QWE23RTY", estacionSismologica1);
            Sismografo sismografo2 = new Sismografo(LocalDateTime.of(2021, 8, 17, 0, 0), "SIS23", "QWE23RTY", estacionSismologica2);
            sismografoRepo.save(sismografo1);
            sismografoRepo.save(sismografo2);

            //------------------------------------------SERIES TEMPORALES------------------------------------------------------------------------
            SerieTemporal serieTemporal1 = new SerieTemporal(false, LocalDateTime.of(2025, 2, 21, 19, 5, 41),
                                                             LocalDateTime.of(2025, 2, 21, 19, 5, 41), 50, sismografo1,
                                                             List.of(muestraSismica11, muestraSismica12, muestraSismica13));
            SerieTemporal serieTemporal2 = new SerieTemporal(false, LocalDateTime.now(),
                                                             LocalDateTime.now(), 50, sismografo1,
                                                             List.of(muestraSismica21, muestraSismica22, muestraSismica23));
            SerieTemporal serieTemporal3 = new SerieTemporal(false, LocalDateTime.now(),
                                                             LocalDateTime.now(), 50, sismografo2,
                                                             List.of(muestraSismica31, muestraSismica32, muestraSismica33));

            //------------------------------------------USUARIOS------------------------------------------------------------------------
            Usuario usuario1 = new Usuario("analista1", "analista1");
            usuarioRepo.save(usuario1);

            //------------------------------------------SESIONES------------------------------------------------------------------------

            //------------------------------------------EMPLEADOS------------------------------------------------------------------------
            Empleado empleado1 = new Empleado("Juan Fernando", "Quintero", "jfq10@gmail.com", "341091218", usuario1);
            empleadoRepo.save(empleado1);

            //------------------------------------------ESTADOS------------------------------------------------------------------------

            //------------------------------------------CLASIFICACIONES------------------------------------------------------------------------
            ClasificacionSismo superficial = new ClasificacionSismo(0, 60, "Superficial");
            ClasificacionSismo intermedio = new ClasificacionSismo(61, 300, "Intermedio");
            ClasificacionSismo profundo = new ClasificacionSismo(301, 650, "Profundo");
            clasificacionSismoRepo.save(superficial);
            clasificacionSismoRepo.save(intermedio);
            clasificacionSismoRepo.save(profundo);

            //------------------------------------------ALCANCES------------------------------------------------------------------------
            AlcanceSismo local = new AlcanceSismo("Distancia entre el epicentro del sismo y el punto de observacion hasta 100km","local");
            AlcanceSismo regional = new AlcanceSismo("Distancia entre el epicentro del sismo y el punto de observacion hasta 1000km","regional");
            AlcanceSismo teleSismo = new AlcanceSismo("Distancia entre el epicentro del sismo y el punto de observacion hasta 10000km","tele-sismo");
            alcanceSismoRepo.save(local);
            alcanceSismoRepo.save(regional);
            alcanceSismoRepo.save(teleSismo);

            //------------------------------------------ORIGENES DE GENERACION------------------------------------------------------------------------
            OrigenDeGeneracion origenDeGeneracion1 = new OrigenDeGeneracion("-", "sismo interplaca");
            OrigenDeGeneracion origenDeGeneracion2 = new OrigenDeGeneracion("-", "sismo volcanico");
            OrigenDeGeneracion origenDeGeneracion3 = new OrigenDeGeneracion("-", "sismo provocado por explociones");
            OrigenDeGeneracion origenDeGeneracion4 = new OrigenDeGeneracion("-", "indefinido");
            origenDeGeneracionRepo.save(origenDeGeneracion1);
            origenDeGeneracionRepo.save(origenDeGeneracion2);
            origenDeGeneracionRepo.save(origenDeGeneracion3);
            origenDeGeneracionRepo.save(origenDeGeneracion4);


            //------------------------------------------EVENTOS SISMICOS------------------------------------------------------------------------
            EventoSismico eventoSismico1 = new EventoSismico(LocalDateTime.of(2025, 2, 21, 19, 6, 13), LocalDateTime.of(2025, 2, 21, 19, 5, 41),
                                                             303.45, 269.76, 405.79, 419.89, 3.3, superficial, local, origenDeGeneracion4, "PendienteDeRevision",
                                                             List.of(serieTemporal1, serieTemporal3));
            EventoSismico eventoSismico2 = new EventoSismico(LocalDateTime.now().plusMinutes(1), LocalDateTime.now(),
                                                             205.33, 386.26, 419.89, 405.79, 3.8, superficial, local, origenDeGeneracion4, "AutoDetectado",
                                                             List.of(serieTemporal2));
            eventoSismicoRepo.save(eventoSismico1);
            eventoSismicoRepo.save(eventoSismico2);

            System.out.println("DATOS CARGADOS");
        };
    }
}
