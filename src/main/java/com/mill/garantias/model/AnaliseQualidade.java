package com.mill.garantias.model;

import com.mill.garantias.model.AnexoAnalise;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "analises_qualidade")
public class AnaliseQualidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String numero;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reclamacao_id")
    private Reclamacao reclamacao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "garantia_id")
    private Garantia garantia;

    @OneToMany(mappedBy = "analiseQualidade", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<AnexoAnalise> anexos = new ArrayList<>();

    @Column(name = "data_analise")
    private LocalDate dataAnalise;

    @Column(name = "responsavel_tecnico", length = 200)
    private String responsavelTecnico;

    @Column(name = "codigo_produto", length = 50)
    private String codigoProduto;

    @Column(name = "descricao_produto", length = 300)
    private String descricaoProduto;

    @Column(name = "lote_serie", length = 100)
    private String loteSerie;

    @Column(name = "data_fabricacao")
    private LocalDate dataFabricacao;

    @Column(columnDefinition = "TEXT")
    private String evidencias;

    @Column(name = "causa_raiz", columnDefinition = "TEXT")
    private String causaRaiz;

    @Column(name = "conclusao_tecnica", columnDefinition = "TEXT")
    private String conclusaoTecnica;

    @Column(name = "acao_corretiva", columnDefinition = "TEXT")
    private String acaoCorretiva;

    @Column(length = 50)
    private String status;

    @Column(name = "veredito", length = 50)
    private String veredito;

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

    public Reclamacao getReclamacao() { return reclamacao; }
    public void setReclamacao(Reclamacao reclamacao) { this.reclamacao = reclamacao; }

    public Garantia getGarantia() { return garantia; }
    public void setGarantia(Garantia garantia) { this.garantia = garantia; }

    public List<AnexoAnalise> getAnexos() { return anexos; }
    public void setAnexos(List<AnexoAnalise> anexos) { this.anexos = anexos; }

    public LocalDate getDataAnalise() { return dataAnalise; }
    public void setDataAnalise(LocalDate dataAnalise) { this.dataAnalise = dataAnalise; }

    public String getResponsavelTecnico() { return responsavelTecnico; }
    public void setResponsavelTecnico(String responsavelTecnico) { this.responsavelTecnico = responsavelTecnico; }

    public String getCodigoProduto() { return codigoProduto; }
    public void setCodigoProduto(String codigoProduto) { this.codigoProduto = codigoProduto; }

    public String getDescricaoProduto() { return descricaoProduto; }
    public void setDescricaoProduto(String descricaoProduto) { this.descricaoProduto = descricaoProduto; }

    public String getLoteSerie() { return loteSerie; }
    public void setLoteSerie(String loteSerie) { this.loteSerie = loteSerie; }

    public LocalDate getDataFabricacao() { return dataFabricacao; }
    public void setDataFabricacao(LocalDate dataFabricacao) { this.dataFabricacao = dataFabricacao; }

    public String getEvidencias() { return evidencias; }
    public void setEvidencias(String evidencias) { this.evidencias = evidencias; }

    public String getCausaRaiz() { return causaRaiz; }
    public void setCausaRaiz(String causaRaiz) { this.causaRaiz = causaRaiz; }

    public String getConclusaoTecnica() { return conclusaoTecnica; }
    public void setConclusaoTecnica(String conclusaoTecnica) { this.conclusaoTecnica = conclusaoTecnica; }

    public String getAcaoCorretiva() { return acaoCorretiva; }
    public void setAcaoCorretiva(String acaoCorretiva) { this.acaoCorretiva = acaoCorretiva; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getVeredito() { return veredito; }
    public void setVeredito(String veredito) { this.veredito = veredito; }

    public LocalDateTime getDataRegistro() { return dataRegistro; }
    public void setDataRegistro(LocalDateTime dataRegistro) { this.dataRegistro = dataRegistro; }
}
