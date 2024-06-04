package com.banhang.repository;

import com.banhang.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products, Integer> {

    @Query(nativeQuery = true, value = "select * from products order by sold DESC limit 5")
    List<Products> findTopProducts();
}
