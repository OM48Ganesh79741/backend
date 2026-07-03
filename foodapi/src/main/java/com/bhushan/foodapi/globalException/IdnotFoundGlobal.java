package com.bhushan.foodapi.globalException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bhushan.foodapi.exceptionclass.DateAndTimeExe;
import com.bhushan.foodapi.exceptionclass.IdNotFount;

@RestControllerAdvice
public class IdnotFoundGlobal 
{
	
	
	
	@ExceptionHandler(IdNotFount.class)
	public  ResponseEntity<DateAndTimeExe> idNotFound(IdNotFount msg)
	{
		DateAndTimeExe datetimes=new DateAndTimeExe();
		
		 datetimes.setMsg(msg.getMessage());
		 datetimes.setLocaldate(LocalDateTime.now());
	   return new ResponseEntity<>(datetimes,HttpStatus.OK);
	}

}
