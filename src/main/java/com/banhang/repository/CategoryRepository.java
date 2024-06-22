package com.banhang.repository;

import com.banhang.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, Integer> {

}
