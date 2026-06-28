package com.mill.garantias.service;

import com.mill.garantias.model.Reclamacao;
import com.mill.garantias.repository.ReclamacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReclamacaoService {

    @Autowired
    private ReclamacaoRepository reclamacaoRepository;

    public Page<Reclamacao> listar(String cliente, String situacao, String numero, Pageable pageable) {
        return reclamacaoRepository.findWithFilters(cliente, situacao, numero, pageable);
    }

    public List<Reclamacao> listarTodas() {
        return reclamacaoRepository.findAll();
    }

    public Optional<Reclamacao> buscarPorId(Long id) {
        return reclamacaoRepository.findById(id);
    }

    @Transactional
    public Reclamacao salvar(Reclamacao reclamacao) {
        if (reclamacao.getId() == null) {
            reclamacao.setNumero(gerarNumero());
        }
        return reclamacaoRepository.save(reclamacao);
    }

    @Transactional
    public void excluir(Long id) {
        reclamacaoRepository.deleteById(id);
    }

    private String gerarNumero() {
        Optional<Reclamacao> ultima = reclamacaoRepository.findTopByOrderByIdDesc();
        long proximo = ultima.map(r -> {
            try {
                String num = r.getNumero().replace("REC-", "");
                return Long.parseLong(num) + 1;
            } catch (Exception e) {
                return 1L;
            }
        }).orElse(1L);
        return String.format("REC-%04d", proximo);
    }
}
