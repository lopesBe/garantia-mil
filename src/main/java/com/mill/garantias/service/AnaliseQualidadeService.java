package com.mill.garantias.service;

import com.mill.garantias.model.AnaliseQualidade;
import com.mill.garantias.repository.AnaliseQualidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AnaliseQualidadeService {

    @Autowired
    private AnaliseQualidadeRepository analiseRepository;

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
        }
        return analiseRepository.save(analise);
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
