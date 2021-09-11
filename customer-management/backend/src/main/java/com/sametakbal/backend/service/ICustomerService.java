package com.sametakbal.backend.service;

import com.sametakbal.backend.entity.Customer;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICustomerService extends IBaseService<Customer>{
    Customer customerFromIdNumber(String idNumber);
    List<Customer> search(Integer cityId,Integer districtId,String term, Pageable pageable);
}
