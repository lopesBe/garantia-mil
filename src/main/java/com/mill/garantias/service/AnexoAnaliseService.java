package com.mill.garantias.service;

import com.mill.garantias.model.AnexoAnalise;
import com.mill.garantias.model.AnaliseQualidade;
import com.mill.garantias.repository.AnexoAnaliseRepository;
import com.mill.garantias.repository.AnaliseQualidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AnexoAnaliseService {

    @Autowired
    private AnexoAnaliseRepository anexoRepository;

    @Autowired
    private AnaliseQualidadeRepository analiseRepository;

    public List<AnexoAnalise> listarPorAnalise(Long analiseId) {
        return anexoRepository.findByAnaliseQualidadeId(analiseId);
    }

    @Transactional
    public AnexoAnalise salvar(AnexoAnalise anexo) {
        return anexoRepository.save(anexo);
    }

    @Transactional
    public void excluir(Long id) {
        anexoRepository.deleteById(id);
    }

    public Optional<AnexoAnalise> buscarPorId(Long id) {
        return anexoRepository.findById(id);
    }

    public Optional<AnaliseQualidade> buscarAnalise(Long id) {
        return analiseRepository.findById(id);
    }
}
