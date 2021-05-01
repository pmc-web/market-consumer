package com.pmc.market.repository;

import com.pmc.market.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final EntityManager em;

    // 상품등록
    public void save(Product product){
        if(product.getId() == null){
            em.persist(product);
        }else{
            em.merge(product);
        }
    }

    // 상품 단건조회
    public Product findOne(Long id){
        return em.find(Product.class, id);
    }

    // 상품 다중건조회
    public List<Product> findAll(){
        return em.createQuery("select p from Product p", Product.class)
                .getResultList();
    }
}
