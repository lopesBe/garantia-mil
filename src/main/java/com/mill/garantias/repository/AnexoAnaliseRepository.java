package com.mill.garantias.repository;

import com.mill.garantias.model.AnexoAnalise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnexoAnaliseRepository extends JpaRepository<AnexoAnalise, Long> {
    List<AnexoAnalise> findByAnaliseQualidadeId(Long analiseQualidadeId);
}
