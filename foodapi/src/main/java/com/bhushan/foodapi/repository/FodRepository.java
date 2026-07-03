package com.bhushan.foodapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bhushan.foodapi.entity.FoodEntity;

@Repository
public interface FodRepository extends JpaRepository<FoodEntity, String>
{

}
