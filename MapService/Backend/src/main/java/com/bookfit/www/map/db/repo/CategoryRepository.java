package com.bookfit.www.map.db.repo;

import com.bookfit.www.map.db.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
