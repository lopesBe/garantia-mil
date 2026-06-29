package com.mill.garantias.repository;

import com.mill.garantias.model.AnaliseQualidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnaliseQualidadeRepository extends JpaRepository<AnaliseQualidade, Long> {

    Optional<AnaliseQualidade> findTopByOrderByIdDesc();

    boolean existsByGarantiaId(Long garantiaId);

    @Query("SELECT a FROM AnaliseQualidade a WHERE " +
           "(:produto IS NULL OR :produto = '' OR LOWER(a.descricaoProduto) LIKE LOWER(CONCAT('%', :produto, '%')) OR LOWER(a.codigoProduto) LIKE LOWER(CONCAT('%', :produto, '%'))) AND " +
           "(:status IS NULL OR :status = '' OR a.status = :status) AND " +
           "(:numero IS NULL OR :numero = '' OR LOWER(a.numero) LIKE LOWER(CONCAT('%', :numero, '%')))")
    Page<AnaliseQualidade> findWithFilters(
            @Param("produto") String produto,
            @Param("status") String status,
            @Param("numero") String numero,
            Pageable pageable);
}
