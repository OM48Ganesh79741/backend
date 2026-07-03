package com.bhushan.foodapi.exceptionclass;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;

@Component
public class DateAndTimeExe 
{
  String msg;
  
  
 LocalDateTime localdate;

public String getMsg() {
	return msg;
}

public void setMsg(String msg) {
	this.msg = msg;
}

public LocalDateTime getLocaldate() {
	return localdate;
}

public void setLocaldate(LocalDateTime localdate) {
	this.localdate = localdate;
}
  
}
