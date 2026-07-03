package com.bhushan.foodapi.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bhushan.foodapi.io.FoodRequest;
import com.bhushan.foodapi.io.FoodResponce;

public interface FoodService 
{
	
   String  uploadFile(MultipartFile file);
   
   FoodResponce addFood(FoodRequest requset,MultipartFile file);
   
   
  List<FoodResponce> readFood();
 FoodResponce readFooad(String id);
 Boolean deletefile(String filename);
 void deleteFood(String id);
}
