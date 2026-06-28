package com.mill.garantias.repository;

import com.mill.garantias.model.Garantia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GarantiaRepository extends JpaRepository<Garantia, Long> {

    Optional<Garantia> findTopByOrderByIdDesc();

    @Query("SELECT g FROM Garantia g WHERE " +
           "(:cliente IS NULL OR :cliente = '' OR LOWER(g.nomeCliente) LIKE LOWER(CONCAT('%', :cliente, '%'))) AND " +
           "(:status IS NULL OR :status = '' OR g.status = :status) AND " +
           "(:numero IS NULL OR :numero = '' OR LOWER(g.numero) LIKE LOWER(CONCAT('%', :numero, '%')))")
    Page<Garantia> findWithFilters(
            @Param("cliente") String cliente,
            @Param("status") String status,
            @Param("numero") String numero,
            Pageable pageable);
}
