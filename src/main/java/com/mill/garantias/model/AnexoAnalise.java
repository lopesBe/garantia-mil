package com.mill.garantias.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "anexos_analise")
public class AnexoAnalise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_arquivo", nullable = false, length = 255)
    private String nomeArquivo;

    @Column(nullable = false, length = 500)
    private String caminho;

    @Column(nullable = false, length = 100)
    private String tipo;

    @Column(name = "data_upload")
    private LocalDateTime dataUpload;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analise_qualidade_id")
    @JsonIgnore
    private AnaliseQualidade analiseQualidade;

    @PrePersist
    public void prePersist() {
        if (dataUpload == null) {
            dataUpload = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getDataUpload() {
        return dataUpload;
    }

    public void setDataUpload(LocalDateTime dataUpload) {
        this.dataUpload = dataUpload;
    }

    public AnaliseQualidade getAnaliseQualidade() {
        return analiseQualidade;
    }

    public void setAnaliseQualidade(AnaliseQualidade analiseQualidade) {
        this.analiseQualidade = analiseQualidade;
    }
}
