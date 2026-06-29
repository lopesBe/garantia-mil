package com.mill.garantias.service;

import com.mill.garantias.model.AnaliseQualidade;
import com.mill.garantias.repository.AnaliseQualidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mill.garantias.model.Garantia;
import com.mill.garantias.repository.GarantiaRepository;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class AnaliseQualidadeService {

    @Autowired
    private AnaliseQualidadeRepository analiseRepository;

    @Autowired
    private GarantiaRepository garantiaRepository;

    public Page<AnaliseQualidade> listar(String produto, String status, String numero, Pageable pageable) {
        return analiseRepository.findWithFilters(produto, status, numero, pageable);
    }

    public Optional<AnaliseQualidade> buscarPorId(Long id) {
        return analiseRepository.findById(id);
    }

    @Transactional
    public AnaliseQualidade salvar(AnaliseQualidade analise) {
        if (analise.getId() == null) {
            analise.setNumero(gerarNumero());
        } else {
            if (!StringUtils.hasText(analise.getNumero())) {
                analiseRepository.findById(analise.getId()).ifPresent(existente -> {
                    analise.setNumero(existente.getNumero());
                });
            }
        }
        AnaliseQualidade salva = analiseRepository.save(analise);

        // Se a análise possui uma garantia vinculada, sincroniza o status/decisão
        if (salva.getGarantia() != null) {
            Garantia garantia = salva.getGarantia();
            if ("Procedente".equalsIgnoreCase(salva.getVeredito())) {
                garantia.setStatus("Procedente");
                garantia.setConclusao(salva.getConclusaoTecnica());
                garantia.setResponsavelDecisao(salva.getResponsavelTecnico());
                garantia.setDataDecisao(LocalDate.now());
                garantiaRepository.save(garantia);
            } else if ("Improcedente".equalsIgnoreCase(salva.getVeredito())) {
                garantia.setStatus("Improcedente");
                garantia.setConclusao(salva.getConclusaoTecnica());
                garantia.setResponsavelDecisao(salva.getResponsavelTecnico());
                garantia.setDataDecisao(LocalDate.now());
                garantiaRepository.save(garantia);
            } else if ("Concluída".equalsIgnoreCase(salva.getStatus())) {
                garantia.setStatus("Concluída");
                garantia.setConclusao(salva.getConclusaoTecnica());
                garantia.setResponsavelDecisao(salva.getResponsavelTecnico());
                garantia.setDataDecisao(LocalDate.now());
                garantiaRepository.save(garantia);
            } else if ("Em Andamento".equalsIgnoreCase(salva.getStatus())) {
                garantia.setStatus("Em Análise");
                garantiaRepository.save(garantia);
            }
        }

        return salva;
    }

    public boolean existsByGarantiaId(Long garantiaId) {
        return analiseRepository.existsByGarantiaId(garantiaId);
    }

    @Transactional
    public void excluir(Long id) {
        analiseRepository.deleteById(id);
    }

    private String gerarNumero() {
        Optional<AnaliseQualidade> ultima = analiseRepository.findTopByOrderByIdDesc();
        long proximo = ultima.map(a -> {
            try {
                String num = a.getNumero().replace("QLD-", "");
                return Long.parseLong(num) + 1;
            } catch (Exception e) {
                return 1L;
            }
        }).orElse(1L);
        return String.format("QLD-%04d", proximo);
    }
}
