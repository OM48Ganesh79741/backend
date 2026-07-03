package com.bhushan.foodapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bhushan.foodapi.io.FoodRequest;
import com.bhushan.foodapi.io.FoodResponce;
import com.bhushan.foodapi.service.FoodService;

import lombok.AllArgsConstructor;
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/foods")
public class FoodController {

	@Autowired
    private  FoodService foodService;


	//store food api comes  imag description and details
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<FoodResponce> addFood(
            @RequestPart("food") FoodRequest request, 
            @RequestPart("file") MultipartFile file) 
    {
        FoodResponce response = foodService.addFood(request, file);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    
    //display food
    @GetMapping("/readfood")
    public  List<FoodResponce> readfoods()
    {
    	return foodService.readFood();
    }
    
    
    
//    display food based on id
    @GetMapping("/readid/{id}")
    public FoodResponce readFood(@PathVariable("id")String id)
    {
    	return foodService.readFooad(id);
    }
    
    @DeleteMapping("/{id}")
    public void deleteFood(@PathVariable("id") String id)
    {
    	foodService.deleteFood(id);
    }
    
    
}