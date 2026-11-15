package com.example.practica1p.entity;

import java.io.Serializable;
import java.time.OffsetDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity 
@Table(name="CONSULTAS_POKEMON")
public class ConsultaPokemon implements Serializable {

    private static final long serialVersionUID = 211091713L;

    @SequenceGenerator (
        name = "ConsultaPokemon",
        sequenceName = "SEQ_CONSULTAS_POKEMON",
        allocationSize = 1
    )

    @Id 
    @GeneratedValue (
        strategy = GenerationType.SEQUENCE,
        generator = "ConsultaPokemon"
    )

    //Mapeo de campos

    @Column (name = "id")
    private Long id;

    @Column (name = "fecha_consulta")
    private OffsetDateTime fechaConsulta;

    @Column (name = "url_consultada")
    private String urlConsultada;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column (name = "respuesta_api")
    private String respuestaAPI;

    @Column (name = "id_pokemon")
    private Long idPokemon;

    @Column (name = "nombre_pokemon")
    private String nombrePokemon;

    @Column (name = "altura")
    private Integer altura;

    @Column (name = "peso")
    private Integer peso;

    @Column (name = "tipos")
    private String tipos;

    @Column (name = "ruta_imagen")
    private String rutaImagen;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OffsetDateTime getFechaConsulta() {
        return this.fechaConsulta;
    }

    public void setFechaConsulta(OffsetDateTime fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public String getUrlConsultada() {
        return this.urlConsultada;
    }

    public void setUrlConsultada(String urlConsultada) {
        this.urlConsultada = urlConsultada;
    }

    public String getRespuestaAPI() {
        return this.respuestaAPI;
    }

    public void setRespuestaAPI(String respuestaAPI) {
        this.respuestaAPI = respuestaAPI;
    }

    public Long getIdPokemon() {
        return this.idPokemon;
    }

    public void setIdPokemon(Long idPokemon) {
        this.idPokemon = idPokemon;
    }

    public String getNombrePokemon() {
        return this.nombrePokemon;
    }

    public void setNombrePokemon(String nombrePokemon) {
        this.nombrePokemon = nombrePokemon;
    }

    public Integer getAltura() {
        return this.altura;
    }

    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    public Integer getPeso() {
        return this.peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public String getTipos() {
        return this.tipos;
    }

    public void setTipos(String tipos) {
        this.tipos = tipos;
    }

    public String getRutaImagen() {
        return this.rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
}
