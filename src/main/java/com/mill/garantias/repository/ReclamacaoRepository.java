package com.mill.garantias.repository;

import com.mill.garantias.model.Reclamacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReclamacaoRepository extends JpaRepository<Reclamacao, Long> {

    Optional<Reclamacao> findTopByOrderByIdDesc();

    @Query("SELECT r FROM Reclamacao r WHERE " +
           "(:cliente IS NULL OR :cliente = '' OR LOWER(r.nomeCliente) LIKE LOWER(CONCAT('%', :cliente, '%'))) AND " +
           "(:situacao IS NULL OR :situacao = '' OR r.situacao = :situacao) AND " +
           "(:numero IS NULL OR :numero = '' OR LOWER(r.numero) LIKE LOWER(CONCAT('%', :numero, '%')))")
    Page<Reclamacao> findWithFilters(
            @Param("cliente") String cliente,
            @Param("situacao") String situacao,
            @Param("numero") String numero,
            Pageable pageable);
}
