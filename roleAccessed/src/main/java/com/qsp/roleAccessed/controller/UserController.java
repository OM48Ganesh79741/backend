package com.qsp.roleAccessed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qsp.roleAccessed.entity.Booking;
import com.qsp.roleAccessed.repo.BookingRepository;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private BookingRepository bookingRepo;

    // ✅ USER BOOKING CREATE
    @PostMapping("/book")
    public Booking createBooking(@RequestBody Booking booking) {
        booking.setStatus("PENDING");
        return bookingRepo.save(booking);
    }

    // ❌ user cannot assign employee
}
