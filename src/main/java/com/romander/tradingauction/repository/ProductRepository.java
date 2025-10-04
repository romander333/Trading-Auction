package com.romander.tradingauction.repository;

import com.romander.tradingauction.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @EntityGraph(attributePaths = "category")
    Page<Product> findAllByUser_Id(Long userId, Pageable pageable);

    @EntityGraph(attributePaths = "category")
    Optional<Product> findById(Long aLong);

    @EntityGraph(attributePaths = "category")
    Page<Product> findAll(Pageable pageable);
}
