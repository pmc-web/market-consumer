package com.pmc.market.repository;

import com.pmc.market.entity.dto.ProductDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductDto, Long> {


}
