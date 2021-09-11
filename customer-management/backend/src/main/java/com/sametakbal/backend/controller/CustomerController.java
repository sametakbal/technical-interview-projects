package com.sametakbal.backend.controller;

import com.sametakbal.backend.entity.Customer;
import com.sametakbal.backend.service.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customers")
@AllArgsConstructor
public class CustomerController {
    private final ICustomerService service;

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers() {
        return new ResponseEntity<>(service.all(), HttpStatus.OK);
    }

    @GetMapping("/{idNumber}")
    public ResponseEntity<Customer> getCustomerWithIdNumber(@PathVariable String idNumber) {
        return new ResponseEntity<>(service.customerFromIdNumber(idNumber), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Customer>> search(@RequestParam(defaultValue = "0") Integer cityId,
                                                 @RequestParam(defaultValue = "0") Integer districtId,
                                                 @RequestParam(defaultValue = "") String searchTerm,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "15") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(service.search(cityId, districtId, searchTerm, pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> saveCustomer(@RequestBody Customer customer) {
        try {
            service.createOrUpdate(customer);
        } catch (IllegalStateException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Integer customerId) {
        try {
            service.delete(customerId);
        } catch (IllegalStateException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
