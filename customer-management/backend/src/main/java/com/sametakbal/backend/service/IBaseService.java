package com.sametakbal.backend.service;
import java.util.List;

public interface IBaseService<T>{
    List<T> all();
    T entityWithId(Integer id);
    void createOrUpdate(T entity);
    void delete(Integer id);
    String isValid(T entity);
}
