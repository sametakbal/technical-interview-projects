package com.sametakbal.api.dao.interfaces;

import java.util.List;

public interface IDao<T> {
    List<T> get(String term);

    int add(T t);

    int update(T t);

    int remove(int id);
}
