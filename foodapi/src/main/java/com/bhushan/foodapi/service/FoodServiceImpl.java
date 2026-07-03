package com.bhushan.foodapi.service;
//
//import java.io.InputStream;
//import java.util.UUID;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.bhushan.foodapi.io.FoodRequest;
//import com.bhushan.foodapientity.FoodEntity;
//
//import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
//import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
//import software.amazon.awssdk.core.sync.RequestBody;
//import software.amazon.awssdk.regions.Region;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.PutObjectRequest;
//
//@Service
//public class FoodServiceImpl implements FoodService {
//
//    @Value("${aws.s3.bucket-name}")
//    private String bucketName;
//
//    @Value("${aws.access-key}")
//    private String accessKey;
//
//    @Value("${aws.secret-key}")
//    private String secretKey;
//
//    @Override
//    public String uploadFile(MultipartFile file) {
//
//        try {
//            // Create S3 client
//            S3Client s3Client = S3Client.builder()
//                    .region(Region.AP_SOUTH_1) // Mumbai region
//                    .credentialsProvider(
//                            StaticCredentialsProvider.create(
//                                    AwsBasicCredentials.create(accessKey, secretKey)
//                            )
//                    )
//                    .build();
//
//            // Get file extension
//            String originalFilename = file.getOriginalFilename();
//            String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
//
//            // Generate unique key
//            String key = UUID.randomUUID().toString() + "." + extension;
//
//            // Build request
//            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//                    .bucket(bucketName)
//                    .key(key)
//                    .contentType(file.getContentType())
//                    .acl("public-read") // optional
//                    .build();
//
//            // Upload file
//            s3Client.putObject(
//                    putObjectRequest,
//                    RequestBody.fromInputStream(file.getInputStream(), file.getSize())
//            );
//
//            // Return file URL
//            return "https://" + bucketName + ".s3.amazonaws.com/" + key;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("File upload failed: " + e.getMessage());
//        }
//    }
//    private FoodEntity convertToEntity(FoodRequest request, String imageUrl) {
//
//        return FoodEntity.builder()
//                .name(request.getName())
//                .description(request.getDescription())
//                .category(request.getCategory())
//                .price(request.getPrice())   // ✅ correct field
//                .imageUrl(imageUrl)          // ✅ S3 image URL save
//                .build();
//    }
//}


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bhushan.foodapi.entity.FoodEntity;
import com.bhushan.foodapi.exceptionclass.IdNotFount;
import com.bhushan.foodapi.io.FoodRequest;
import com.bhushan.foodapi.io.FoodResponce;
import com.bhushan.foodapi.repository.FodRepository;

@Service
public class FoodServiceImpl implements FoodService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private FodRepository foodRepository;

    @Override
    public String uploadFile(MultipartFile file) 
    {
        try {
            // 1. Folder check karein, nahi hai to bana dein
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // 2. Unique filename banayein
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir).resolve(fileName);

            // 3. File ko local folder mein save karein
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // 4. File ka path return karein (Database mein save karne ke liye)
            return "http://localhost:8080/images/" + fileName; 

        } catch (IOException e) {
            throw new RuntimeException("Could not store file. Error: " + e.getMessage());
        }
    }

    @Override
    public FoodResponce addFood(FoodRequest request, MultipartFile file) 
    {
//    	call uploaf file method and this method return a image loval folder paths
        String imageUrl = uploadFile(file);
        
        // BUILDER KI JAGAH YE LIKHEIN:
        FoodEntity entity = new FoodEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setCategory(request.getCategory());
        entity.setPrice(request.getPrice());
        entity.setImageUrl(imageUrl);
        
//        save data
        FoodEntity savedEntity = foodRepository.save(entity);
        
        // Response map karein
        FoodResponce response = new FoodResponce();
        response.setId(savedEntity.getId());
        response.setName(savedEntity.getName());
        response.setDescription(savedEntity.getDescription());
        response.setCategory(savedEntity.getCategory());
        response.setPrice(savedEntity.getPrice());
        response.setImageurl(savedEntity.getImageUrl());
        return response;
    }

    
    private FoodResponce convertToResponse(FoodEntity entity) {
        FoodResponce res = new FoodResponce();

        res.setId(entity.getId());
        res.setName(entity.getName());
        res.setDescription(entity.getDescription());
        res.setCategory(entity.getCategory());
        res.setPrice(entity.getPrice());
        res.setImageurl(entity.getImageUrl());

        return res;
    }
    
//    return food object
	@Override
	public List<FoodResponce> readFood() {
	    List<FoodEntity> dataEntries = foodRepository.findAll();

	    
	    //convert into responce one object
	    return dataEntries.stream()
	            .map(this::convertToResponse)
	            .toList();
	}

	@Override
	public FoodResponce readFooad(String id) {
		
		FoodEntity fp = foodRepository.findById(id)
	            .orElseThrow(() -> new IdNotFount("Food item with ID " + id + " not found"));

	    // Ab direct convert karke return karein id pura object leta hai
	    return convertToResponse(fp);
}

	@Override
	public Boolean deletefile(String filename) {
	
		try {
			File file=new File("D:\\uploads\\food-images");
		    
			if(file.exists())
			{
				 return file.delete();
			}
			else
			{
				System.out.println("file not deleted");
			    return false;
			}
		}
		catch (Exception e) 
		{
			return false;
		}
	}

	@Override
	public void deleteFood(String id) {

	    // 1. Fetch entity
	    FoodEntity entity = foodRepository.findById(id)
	            .orElseThrow(() -> new IdNotFount("Food item with ID " + id + " not found"));

	    // 2. Delete image from local storage
	    String imagePath = entity.getImageUrl();

	    if (imagePath != null) {
	        File file = new File(imagePath);

	        if (file.exists()) {
	            boolean deleted = file.delete();
	            System.out.println("Image deleted: " + deleted);
	        } else {
	            System.out.println("Image not found");
	        }
	    }

	    // 3. Delete from database
	    foodRepository.deleteById(id);
	}
}


