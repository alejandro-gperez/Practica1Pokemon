package com.example.practica1p.service;

import java.time.OffsetDateTime;
import java.util.StringJoiner;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import com.example.practica1p.dto.PokemonResponseDTO;
import com.example.practica1p.entity.ConsultaPokemon;
import com.example.practica1p.repository.ConsultaPokemonRepository;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;


@RestController 
@RequestMapping("/pokemon")
public class PokemonService {

    ConsultaPokemonRepository consultaPokemonRepository;
    
    public PokemonService(ConsultaPokemonRepository consultaPokemonRepository) {
        this.consultaPokemonRepository = consultaPokemonRepository;
    }

    private static final String POKEAPI_BASE_URL = "https://pokeapi.co/api/v2/pokemon/";

    RestClient restClient = RestClient.create(POKEAPI_BASE_URL);

    @GetMapping("/find")
    public ResponseEntity<?> getPokemonInfo(@RequestParam String name) {

        String pokemon;

        try {

        pokemon = restClient.get()
        .uri("{name}", name)
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
           .body("Pokemon no encontrado :(");

            



        }

        

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode respuestaCompleta = objectMapper.readTree(pokemon);

       Long id = respuestaCompleta.get("id").asLong();
       String nombre = respuestaCompleta.get("name").asString();
       Integer altura = respuestaCompleta.get("height").asInt();
       Integer peso = respuestaCompleta.get("weight").asInt();
       String rutaImagen = respuestaCompleta.get("sprites").get("front_default").asString();

       //Tipos
       JsonNode tipos = respuestaCompleta.get("types");
       StringJoiner t = new StringJoiner(",");

       for (JsonNode p : tipos) {
        t.add(p.get("type").get("name").asString());
       }

       PokemonResponseDTO respuesta = new PokemonResponseDTO();

       respuesta.setIdPokemon(id);
       respuesta.setNombrePokemon(nombre);
       respuesta.setAltura(altura);
       respuesta.setPeso(peso);
       respuesta.setRutaImagen(rutaImagen);
       respuesta.setTipos(t.toString());

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
       consultaPokemon.setRutaImagen(rutaImagen);

       consultaPokemonRepository.save(consultaPokemon);

        return ResponseEntity.ok(respuesta);    
    }

    
    
    

    
    
    

}
