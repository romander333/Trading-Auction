package com.romander.tradingauction.repository;

import com.romander.tradingauction.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
