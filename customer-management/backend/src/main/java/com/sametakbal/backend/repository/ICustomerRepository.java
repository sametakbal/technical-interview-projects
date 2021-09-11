package com.sametakbal.backend.repository;

import com.sametakbal.backend.entity.City;
import com.sametakbal.backend.entity.Customer;
import com.sametakbal.backend.entity.District;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICustomerRepository extends CrudRepository<Customer, Integer> {
    Optional<Customer> findByIdNumber(String idNumber);

    Optional<Customer> findByEmail(String email);

    List<Customer> findAllByCityAndDistrictAndIdNumberLikeAndFirstNameIsLikeAndLastNameIsLikeAndEmailIsLikeAndPhoneIsLike(City city, District district,
                                                                                         String idNumber, String firstName,
                                                                                         String lastName, String email,
                                                                                         String phone, Pageable pageable);
    List<Customer> findAllByCityOrIdNumberLikeOrFirstNameIsLikeOrLastNameIsLikeOrEmailIsLikeOrPhoneIsLike(City city,String idNumber, String firstName,
                                                                                                                    String lastName, String email,
                                                                                                                    String phone, Pageable pageable);
    List<Customer> findAllByDistrictOrIdNumberLikeOrFirstNameIsLikeOrLastNameIsLikeOrEmailIsLikeOrPhoneIsLike(District district,
                                                                                                                    String idNumber, String firstName,
                                                                                                                    String lastName, String email,
                                                                                                                    String phone, Pageable pageable);

    List<Customer> findAllByIdNumberLikeOrFirstNameIsLikeOrLastNameIsLikeOrEmailIsLikeOrPhoneIsLike(String idNumber, String firstName,
                                                                                                                    String lastName, String email,
                                                                                                                    String phone, Pageable pageable);

}
