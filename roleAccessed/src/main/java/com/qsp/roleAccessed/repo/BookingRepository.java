package com.qsp.roleAccessed.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qsp.roleAccessed.entity.Booking;


public interface BookingRepository extends JpaRepository<Booking, Long> 
{
	}