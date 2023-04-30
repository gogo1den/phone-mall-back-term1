package com.example.demo.persistence;

import com.example.demo.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {

    //@Query(value = "select * from Todo t where t.userid = ?1", nativeQuery = true)
    List<ProductEntity> findByUserId(String userId);
    List<ProductEntity> findByTitleContaining(String title);
}
