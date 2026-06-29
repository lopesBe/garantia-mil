package com.mill.garantias.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "garantias")
public class Garantia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String numero;

    @Column(name = "numero_nota", length = 50)
    private String numeroNota;

    @Column(name = "cnpj_cliente", length = 18)
    private String cnpjCliente;

    @Column(name = "nome_cliente", length = 200)
    private String nomeCliente;

    @Column(name = "data_solicitacao")
    private LocalDate dataSolicitacao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reclamacao_id")
    private Reclamacao reclamacao;

    @Column(length = 50)
    private String status;

    @Column(name = "observacoes_analise", columnDefinition = "TEXT")
    private String observacoesAnalise;

    @Column(columnDefinition = "TEXT")
    private String conclusao;

    @Column(name = "responsavel_decisao", length = 200)
    private String responsavelDecisao;

    @Column(name = "data_decisao")
    private LocalDate dataDecisao;

    @Column(name = "data_registro")
    private LocalDateTime dataRegistro;

    @OneToMany(mappedBy = "garantia", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<GarantiaItem> itens = new ArrayList<>();

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

    public String getNumeroNota() { return numeroNota; }
    public void setNumeroNota(String numeroNota) { this.numeroNota = numeroNota; }

    public String getCnpjCliente() { return cnpjCliente; }
    public void setCnpjCliente(String cnpjCliente) { this.cnpjCliente = cnpjCliente; }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

    public LocalDate getDataSolicitacao() { return dataSolicitacao; }
    public void setDataSolicitacao(LocalDate dataSolicitacao) { this.dataSolicitacao = dataSolicitacao; }

    public Reclamacao getReclamacao() { return reclamacao; }
    public void setReclamacao(Reclamacao reclamacao) { this.reclamacao = reclamacao; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getObservacoesAnalise() { return observacoesAnalise; }
    public void setObservacoesAnalise(String observacoesAnalise) { this.observacoesAnalise = observacoesAnalise; }

    public String getConclusao() { return conclusao; }
    public void setConclusao(String conclusao) { this.conclusao = conclusao; }

    public String getResponsavelDecisao() { return responsavelDecisao; }
    public void setResponsavelDecisao(String responsavelDecisao) { this.responsavelDecisao = responsavelDecisao; }

    public LocalDate getDataDecisao() { return dataDecisao; }
    public void setDataDecisao(LocalDate dataDecisao) { this.dataDecisao = dataDecisao; }

    public LocalDateTime getDataRegistro() { return dataRegistro; }
    public void setDataRegistro(LocalDateTime dataRegistro) { this.dataRegistro = dataRegistro; }

    public List<GarantiaItem> getItens() { return itens; }
    public void setItens(List<GarantiaItem> itens) { this.itens = itens; }
}
