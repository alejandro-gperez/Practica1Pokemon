package com.example.practica1p.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.StringJoiner;

import tools.jackson.core.JacksonException;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import com.example.practica1p.dto.PokemonResponseDTO;
import com.example.practica1p.entity.ConsultaPokemon;
import com.example.practica1p.repository.ConsultaPokemonRepository;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/pokemon")
public class PokemonService {

    ObjectMapper objectMapper = new ObjectMapper();

    ConsultaPokemonRepository consultaPokemonRepository;

    public PokemonService(ConsultaPokemonRepository consultaPokemonRepository) {
        this.consultaPokemonRepository = consultaPokemonRepository;
    }

    private static final String POKEAPI_BASE_URL = "https://pokeapi.co/api/v2/pokemon/";

    RestClient restClient = RestClient.create(POKEAPI_BASE_URL);

    @GetMapping("/find")
    public ResponseEntity<?> getPokemonInfo(@RequestParam(required = false) String name) {

        if (name == null || name.isBlank()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Debes colocar el nombre de un Pokémon!");
        }

        name = name.strip();

        String pokemon;
        try {

            pokemon = restClient.get()
                    .uri("{name}, name")
                    .retrieve()
                    .body(String.class);

        } catch (HttpClientErrorException.NotFound e) {

            String respuestaError = e.getResponseBodyAsString();

            ConsultaPokemon consultaPokemon = new ConsultaPokemon();

            consultaPokemon.setFechaConsulta(OffsetDateTime.now());
            consultaPokemon.setUrlConsultada(POKEAPI_BASE_URL + name);
            consultaPokemon.setRespuestaAPI(respuestaError);

            consultaPokemonRepository.save(consultaPokemon);

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Pokémon no encontrado :(");

        } catch (HttpClientErrorException e) {

            String respuestaError = e.getResponseBodyAsString();
            String conversion = respuestaError;

            if (respuestaError.isEmpty()) {
                conversion = null;
            } else {

                try {

                    objectMapper.readTree(respuestaError);

                } catch (JacksonException ex) {

                    conversion = objectMapper.writeValueAsString(respuestaError);

                }

            }

            ConsultaPokemon consultaPokemon = new ConsultaPokemon();

            consultaPokemon.setFechaConsulta(OffsetDateTime.now());
            consultaPokemon.setUrlConsultada(POKEAPI_BASE_URL + name);
            consultaPokemon.setRespuestaAPI(conversion);

            consultaPokemonRepository.save(consultaPokemon);

            return ResponseEntity
                    .status(HttpStatus.BAD_GATEWAY)
                    .body("Solicitud rechazada por el servicio externo >:(");

        } catch (HttpServerErrorException e) {

            String respuestaError = e.getResponseBodyAsString();
            String conversion = respuestaError;

            if (respuestaError.isEmpty()) {
                conversion = null;
            } else {

                try {

                    objectMapper.readTree(respuestaError);

                } catch (JacksonException ex) {

                    conversion = objectMapper.writeValueAsString(respuestaError);

                }

            }

            ConsultaPokemon consultaPokemon = new ConsultaPokemon();

            consultaPokemon.setFechaConsulta(OffsetDateTime.now());
            consultaPokemon.setUrlConsultada(POKEAPI_BASE_URL + name);
            consultaPokemon.setRespuestaAPI(conversion);

            consultaPokemonRepository.save(consultaPokemon);

            return ResponseEntity
                    .status(HttpStatus.BAD_GATEWAY)
                    .body("El servicio externo que usamos ha fallado :(");

        } catch (ResourceAccessException e) {

            ConsultaPokemon consultaPokemon = new ConsultaPokemon();

            consultaPokemon.setFechaConsulta(OffsetDateTime.now());
            consultaPokemon.setUrlConsultada(POKEAPI_BASE_URL + name);

            consultaPokemonRepository.save(consultaPokemon);

            return ResponseEntity
                    .status(HttpStatus.BAD_GATEWAY)
                    .body("No fue posible establecer conexión con el servidor D:");

        }

        JsonNode respuestaCompleta = objectMapper.readTree(pokemon);

        Long id = respuestaCompleta.get("id").asLong();
        String nombre = respuestaCompleta.get("name").asString();
        Integer altura = respuestaCompleta.get("height").asInt();
        Integer peso = respuestaCompleta.get("weight").asInt();

        JsonNode rutaImagen = respuestaCompleta.get("sprites").get("front_default");
        String rutaFinal;

        if (rutaImagen == null || rutaImagen.isNull()) {
            rutaFinal = respuestaCompleta.get("sprites").get("other").get("official-artwork").get("front_default").asString();
        } else {
            rutaFinal = respuestaCompleta.get("sprites").get("front_default").asString();
        }

        // Tipos
        JsonNode tipos = respuestaCompleta.get("types");
        StringJoiner t = new StringJoiner(",");
        List<String> l = new ArrayList<>();

        for (JsonNode p : tipos) {
            String valores = (p.get("type").get("name").asString());
            t.add(valores);
            l.add(valores);
        }

        PokemonResponseDTO respuesta = new PokemonResponseDTO();

        respuesta.setId(id);
        respuesta.setNombre(nombre);
        respuesta.setAltura(altura);
        respuesta.setPeso(peso);
        respuesta.setImagen(rutaFinal);
        respuesta.setTipos(l);

        // Guardar respuesta completa

        ConsultaPokemon consultaPokemon = new ConsultaPokemon();

        consultaPokemon.setFechaConsulta(OffsetDateTime.now());
        consultaPokemon.setUrlConsultada(POKEAPI_BASE_URL + name);
        consultaPokemon.setRespuestaAPI(pokemon);
        consultaPokemon.setIdPokemon(id);
        consultaPokemon.setNombrePokemon(nombre);
        consultaPokemon.setAltura(altura);
        consultaPokemon.setPeso(peso);
        consultaPokemon.setTipos(t.toString());
        consultaPokemon.setRutaImagen(rutaFinal);

        consultaPokemonRepository.save(consultaPokemon);

        return ResponseEntity.ok(respuesta);
    }

}
