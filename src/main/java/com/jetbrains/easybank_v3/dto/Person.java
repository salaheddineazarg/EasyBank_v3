package com.jetbrains.easybank_v3.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
@Data
@SuperBuilder
@NoArgsConstructor
public abstract class Person {

    protected  String firstName;

    protected  String lastName;

    protected LocalDate dateBirth;

    protected String nbPhone;
}
