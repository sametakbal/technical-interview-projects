package com.sametakbal.backend.service.impl;

import com.sametakbal.backend.entity.City;
import com.sametakbal.backend.entity.Customer;
import com.sametakbal.backend.entity.District;
import com.sametakbal.backend.repository.ICityRepository;
import com.sametakbal.backend.repository.ICustomerRepository;
import com.sametakbal.backend.repository.IDistrictRepository;
import com.sametakbal.backend.service.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {
    private final ICustomerRepository repo;
    private final ICityRepository cityRepo;
    private final IDistrictRepository districtRepo;

    @Override
    public List<Customer> all() {
        return (List<Customer>) repo.findAll();
    }

    @Override
    public Customer entityWithId(Integer id) {
        Optional<Customer> customer = repo.findById(id);
        if (customer.isPresent()) {
            return customer.get();
        }
        throw new IllegalStateException("Customer not found!");
    }

    @Override
    public List<Customer> search(Integer cityId, Integer districtId, String term, Pageable pageable) {
        Optional<City> city = cityRepo.findById(cityId);
        Optional<District> district = districtRepo.findById(districtId);
        if ( city.isPresent() && district.isPresent()) {
            return repo.findAllByCityAndDistrictAndIdNumberLikeAndFirstNameIsLikeAndLastNameIsLikeAndEmailIsLikeAndPhoneIsLike(
                    city.get(), district.get(), term, term, term, term, term, pageable);
        }else if(city.isPresent()){
            return repo.findAllByCityOrIdNumberLikeOrFirstNameIsLikeOrLastNameIsLikeOrEmailIsLikeOrPhoneIsLike(
                    city.get(),term, term, term, term, term, pageable);
        }else if (district.isPresent()){
            return repo.findAllByDistrictOrIdNumberLikeOrFirstNameIsLikeOrLastNameIsLikeOrEmailIsLikeOrPhoneIsLike(
                    district.get(),term, term, term, term, term, pageable);
        }
        return repo.findAllByIdNumberLikeOrFirstNameIsLikeOrLastNameIsLikeOrEmailIsLikeOrPhoneIsLike(term, term, term, term, term, pageable);
    }

    @Override
    public Customer customerFromIdNumber(String idNumber) {
        Optional<Customer> customer = repo.findByIdNumber(idNumber);
        if (customer.isPresent()){
            return customer.get();
        }
        throw new IllegalStateException("There is no user for this ID number.");
    }

    @Override
    public void createOrUpdate(Customer customer) {
        String errorMessage = isValid(customer);
        if (errorMessage != null) {
            throw new IllegalStateException(errorMessage);
        } else {
            if (customer.getCustomerId() == null) {
                Optional<Customer> temp = repo.findByEmail(customer.getEmail());
                if (temp.isPresent()) {
                    throw new IllegalStateException("This email is already in use!");
                }
                temp = repo.findByIdNumber(customer.getIdNumber());
                if (temp.isPresent()) {
                    throw new IllegalStateException("This ID number is already in use!");
                }
            }
            repo.save(customer);
        }
    }

    @Override
    public void delete(Integer id) {
        Optional<Customer> customer = repo.findById(id);
        if (!customer.isPresent()) {
            throw new IllegalStateException("Customer not found!");
        }
        repo.delete(customer.get());
    }

    public String isValid(Customer customer) {
        if (customer.getCity() == null) {
            return "City field cannot be blank!";
        }
        if (customer.getDistrict() == null) {
            return "County field cannot be blank!";
        }
        if (customer.getEmail() == null || customer.getEmail().equals("")) {
            return "Email field cannot be blank!";
        }
        if (customer.getFirstName() == null || customer.getFirstName().equals("")) {
            return "Name field cannot be empty!";
        }
        if (customer.getLastName() == null || customer.getLastName().equals("")) {
            return "Last name field cannot be blank!";
        }
        if (customer.getPhone() == null || customer.getPhone().equals("")) {
            return "The phone field cannot be blank!";
        }
        if (customer.getIdNumber() == null || customer.getIdNumber().equals("")) {
            return "ID Number cannot be blank!";
        }
        return null;
    }
}
