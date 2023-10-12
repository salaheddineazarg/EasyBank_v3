package com.jetbrains.easybank_v3.interfaces;

import java.util.Map;
import java.util.Optional;

public interface iOperation<T> {
    Optional<T> Add(T operation);
    int Delete(int code);
    Map<String,Object> Search();
    Optional<T> SearchByNum(String code);
}
