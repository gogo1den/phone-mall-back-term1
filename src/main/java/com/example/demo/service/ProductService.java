package com.example.demo.service;

import com.example.demo.model.ProductEntity;
import com.example.demo.persistence.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public String testService() {
        //ProductEntity 생성
        ProductEntity entity = ProductEntity.builder().title("My first product item").build();
        //ProductEntity 저장
        repository.save(entity);
        //ProductEntity 검색
        ProductEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }

    public List<ProductEntity> create(final ProductEntity entity) {
        //Validations
        if(entity == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }

        if(entity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }

        repository.save(entity);

        log.info("Entity Id : {} is saved.", entity.getId());

        return repository.findByUserId(entity.getUserId());

    }

    public List<ProductEntity>retrieve(final String userId) {
        return repository.findByUserId(userId);

    }

    public List<ProductEntity> search(ProductEntity entity) {
        final List<ProductEntity> origin = repository.findByTitleContaining(entity.getTitle());


        return origin;
    }


    public Optional<ProductEntity> update(ProductEntity entity) {
        validate(entity);

        final Optional<ProductEntity> original = repository.findById(entity.getId());

        original.ifPresent(product -> {
            product.setTitle(entity.getTitle());
            product.setMaker(entity.getMaker());
            product.setColor(entity.getColor());

            repository.save(product);
        });

        return repository.findById(entity.getId());
    }

    public List<ProductEntity> delete(final ProductEntity entity) {
        validate(entity);

        try{
            repository.delete(entity);
        } catch(Exception e){
            log.error("error deleting entity", entity.getId(), e);

            throw new RuntimeException("error deleting entity" + entity.getId());
        }
        return retrieve(entity.getUserId());
    }

    private void validate(final ProductEntity entity){
        if(entity == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null.");
        }
        if(entity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }


}
