package com.mill.garantias.service;

import com.mill.garantias.model.Garantia;
import com.mill.garantias.model.GarantiaItem;
import com.mill.garantias.repository.GarantiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GarantiaService {

    @Autowired
    private GarantiaRepository garantiaRepository;

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
        if (garantia.getId() == null) {
            garantia.setNumero(gerarNumero());
        }
        // Garante referência bidirecional dos itens
        if (garantia.getItens() != null) {
            for (GarantiaItem item : garantia.getItens()) {
                item.setGarantia(garantia);
            }
        }
        return garantiaRepository.save(garantia);
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
