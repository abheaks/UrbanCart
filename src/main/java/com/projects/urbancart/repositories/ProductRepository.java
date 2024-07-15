package com.projects.urbancart.repositories;

import com.projects.urbancart.models.Product;
import com.projects.urbancart.projections.ProductWithTitleAndDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    Optional<Product> findById(long id);


    //SQL Query -> native query
    @Query(value = "select title as title, description as description from product where id = :id", nativeQuery = true)
    ProductWithTitleAndDescription someOtherRandomQuery(@Param("id") Long id);
}
