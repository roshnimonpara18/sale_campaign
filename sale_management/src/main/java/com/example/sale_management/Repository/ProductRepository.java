package com.example.sale_management.Repository;

import com.example.sale_management.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository <Product , Integer> {
}
