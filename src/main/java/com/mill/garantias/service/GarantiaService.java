package com.mill.garantias.service;

import com.mill.garantias.model.Garantia;
import com.mill.garantias.model.GarantiaItem;
import com.mill.garantias.repository.GarantiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mill.garantias.model.AnaliseQualidade;
import org.springframework.context.annotation.Lazy;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class GarantiaService {

    @Autowired
    private GarantiaRepository garantiaRepository;

    @Autowired
    @Lazy
    private AnaliseQualidadeService analiseQualidadeService;

    public Page<Garantia> listar(String cliente, String status, String numero, Pageable pageable) {
        return garantiaRepository.findWithFilters(cliente, status, numero, pageable);
    }

    public List<Garantia> listarTodas() {
        return garantiaRepository.findAll();
    }

    public Optional<Garantia> buscarPorId(Long id) {
        return garantiaRepository.findById(id);
    }

    @Transactional
    public Garantia salvar(Garantia garantia) {
        boolean isNew = (garantia.getId() == null);
        if (isNew) {
            garantia.setNumero(gerarNumero());
        } else {
            if (garantia.getNumero() == null) {
                garantiaRepository.findById(garantia.getId()).ifPresent(existente -> {
                    garantia.setNumero(existente.getNumero());
                });
            }
        }
        // Garante referência bidirecional dos itens
        if (garantia.getItens() != null) {
            for (GarantiaItem item : garantia.getItens()) {
                item.setGarantia(garantia);
            }
        }
        Garantia salva = garantiaRepository.save(garantia);

        if (isNew || !analiseQualidadeService.existsByGarantiaId(salva.getId())) {
            // Criação automática de um registro em Análise de Qualidade com status inicial "Aguardando Análise"
            AnaliseQualidade analise = new AnaliseQualidade();
            analise.setGarantia(salva);
            analise.setReclamacao(salva.getReclamacao());
            analise.setStatus("Aguardando Análise");
            analise.setDataAnalise(LocalDate.now());
            // Preenche dados do primeiro item se disponível
            if (salva.getItens() != null && !salva.getItens().isEmpty()) {
                GarantiaItem primeiroItem = salva.getItens().get(0);
                analise.setCodigoProduto(primeiroItem.getCodigo());
                analise.setDescricaoProduto(primeiroItem.getDescricao());
            } else {
                analise.setCodigoProduto("");
                analise.setDescricaoProduto("");
            }
            analise.setResponsavelTecnico("Pendente");
            analiseQualidadeService.salvar(analise);
        }

        return salva;
    }

    @Transactional
    public void excluir(Long id) {
        garantiaRepository.deleteById(id);
    }

    private String gerarNumero() {
        Optional<Garantia> ultima = garantiaRepository.findTopByOrderByIdDesc();
        long proximo = ultima.map(g -> {
            try {
                String num = g.getNumero().replace("GAR-", "");
                return Long.parseLong(num) + 1;
            } catch (Exception e) {
                return 1L;
            }
        }).orElse(1L);
        return String.format("GAR-%04d", proximo);
    }
}
