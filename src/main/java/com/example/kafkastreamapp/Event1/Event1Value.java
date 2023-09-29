package com.example.kafkastreamapp.Event1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event1Value {
    private String empl_id;
    private Boolean is_fte;
    private String dept;
    private String dob;
    private String country;
}

