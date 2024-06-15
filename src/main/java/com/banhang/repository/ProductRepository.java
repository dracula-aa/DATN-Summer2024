package com.banhang.repository;

import com.banhang.entity.Categories;
import com.banhang.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Products, Integer> {

    @Query(nativeQuery = true, value = "select * from products order by sold DESC limit 4")
    List<Products> findTopProducts();

    Optional<Products> findBySlug(String slug);

    @Query(nativeQuery = true, value = "SELECT * FROM products ORDER BY "
            + "CASE WHEN :type = 'price_asc' THEN price END ASC, "
            + "CASE WHEN :type = 'price_desc' THEN price END DESC, "
            + "CASE WHEN :type = 'sold_desc' THEN sold END DESC")
    List<Products> filterProduct(String type);


    @Query(nativeQuery = true, value = "SELECT * FROM products WHERE name LIKE :keywords")
    List<Products> findByKeywords(@Param("keywords") String keywords);

    @Query(nativeQuery = true, value = "SELECT p.* FROM products p JOIN categories c ON p.category_id = c.id WHERE c.slug = :slug")
    List<Products> findAllByCategorySlug(@Param("slug") String slug);

}
