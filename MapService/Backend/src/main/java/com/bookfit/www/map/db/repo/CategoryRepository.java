package com.bookfit.www.map.db.repo;

import com.bookfit.www.map.db.entity.Category;
import com.bookfit.www.map.dto.main.CategoryVO;
import com.bookfit.www.map.dto.main.MapCategoryVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("select c from Category c")
    List<CategoryVO> findAllMappedCategoryVO();

    List<Category> findByCodeIn(List<String> codes);

}
