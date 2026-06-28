package com.mill.garantias.model;

import jakarta.persistence.*;

@Entity
@Table(name = "garantia_itens")
public class GarantiaItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garantia_id")
    private Garantia garantia;

    @Column(length = 50)
    private String codigo;

    @Column(length = 300)
    private String descricao;

    private Integer quantidade;

    @Column(length = 20)
    private String unidade;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Garantia getGarantia() { return garantia; }
    public void setGarantia(Garantia garantia) { this.garantia = garantia; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public String getUnidade() { return unidade; }
    public void setUnidade(String unidade) { this.unidade = unidade; }
}
