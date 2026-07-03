package com.qsp.roleAccessed.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.qsp.roleAccessed.entity.Booking;
import com.qsp.roleAccessed.entity.Employee;
import com.qsp.roleAccessed.entity.User;
import com.qsp.roleAccessed.repo.BookingRepository;
import com.qsp.roleAccessed.repo.EmployeeRepository;
import com.qsp.roleAccessed.repo.UserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController 
{
	@Autowired
    private BookingRepository bookingRepo;
	 
	@Autowired
    private EmployeeRepository employeeRepo;


    // ✅ ALL BOOKINGS (ONLY ADMIN)
    @GetMapping("/bookings")
    public List<Booking> getAllBookings() 
    {
        return bookingRepo.findAll();
    }
    
    
    
    // ✅ ALL EMPLOYEES (ONLY ADMIN)
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() 
    {
        return employeeRepo.findAll();
    }
    
    

    // 🔥 ASSIGN EMPLOYEE (ONLY ADMIN)
    @PutMapping("/assign/{bookingId}")
    public String assignEmployee(@PathVariable Long bookingId,
                                 @RequestBody Map<String, Long> body) {

        Long empId = body.get("employeeId");

        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Employee emp = employeeRepo.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        booking.setEmployee(emp);
        booking.setStatus("ASSIGNED");

        bookingRepo.save(booking);

        return "Employee Assigned Successfully";
    }

    
    
}
