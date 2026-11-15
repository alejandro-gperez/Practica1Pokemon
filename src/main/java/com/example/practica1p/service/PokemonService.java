package com.example.practica1p.service;

import java.util.StringJoiner;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import com.example.practica1p.dto.PokemonResponseDTO;
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

    RestClient restClient = RestClient.create("https://pokeapi.co/api/v2/");

    @GetMapping("/find")
    public PokemonResponseDTO getPokemonInfo(@RequestParam String name) {

        String pokemon = restClient.get()
        .uri("pokemon/{name}", name)
        .retrieve()
        .body(String.class);

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
    
        return respuesta;
    }

    
    
    

}
