package com.jetbrains.easybank_v3.interfaces;

import java.util.Optional;

public interface iPerson<T> {

    Optional<T> AddPerson(T person);
    int DeletePerson(String keyword);
    Optional<T> UpdatePerson(T person);

}
