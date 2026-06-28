package com.mill.garantias.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reclamacoes")
public class Reclamacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String numero;

    @Column(name = "cnpj_cliente", length = 18)
    private String cnpjCliente;

    @Column(name = "nome_cliente", length = 200)
    private String nomeCliente;

    @Column(length = 100)
    private String cidade;

    @Column(length = 2)
    private String estado;

    @Column(name = "numero_nota", length = 50)
    private String numeroNota;

    @Column(name = "data_nota")
    private LocalDate dataNota;

    @Column(name = "data_ocorrencia")
    private LocalDate dataOcorrencia;

    @Column(precision = 15, scale = 2)
    private BigDecimal valor;

    private Integer quantidade;

    @Column(length = 50)
    private String situacao;

    @Column(name = "descricao_defeito", columnDefinition = "TEXT")
    private String descricaoDefeito;

    @Column(name = "itens_envolvidos", columnDefinition = "TEXT")
    private String itensEnvolvidos;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Column(length = 200)
    private String responsavel;

    @Column(name = "data_registro")
    private LocalDateTime dataRegistro;

    @PrePersist
    public void prePersist() {
        if (dataRegistro == null) {
            dataRegistro = LocalDateTime.now();
        }
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getCnpjCliente() { return cnpjCliente; }
    public void setCnpjCliente(String cnpjCliente) { this.cnpjCliente = cnpjCliente; }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getNumeroNota() { return numeroNota; }
    public void setNumeroNota(String numeroNota) { this.numeroNota = numeroNota; }

    public LocalDate getDataNota() { return dataNota; }
    public void setDataNota(LocalDate dataNota) { this.dataNota = dataNota; }

    public LocalDate getDataOcorrencia() { return dataOcorrencia; }
    public void setDataOcorrencia(LocalDate dataOcorrencia) { this.dataOcorrencia = dataOcorrencia; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public String getSituacao() { return situacao; }
    public void setSituacao(String situacao) { this.situacao = situacao; }

    public String getDescricaoDefeito() { return descricaoDefeito; }
    public void setDescricaoDefeito(String descricaoDefeito) { this.descricaoDefeito = descricaoDefeito; }

    public String getItensEnvolvidos() { return itensEnvolvidos; }
    public void setItensEnvolvidos(String itensEnvolvidos) { this.itensEnvolvidos = itensEnvolvidos; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }

    public LocalDateTime getDataRegistro() { return dataRegistro; }
    public void setDataRegistro(LocalDateTime dataRegistro) { this.dataRegistro = dataRegistro; }
}
