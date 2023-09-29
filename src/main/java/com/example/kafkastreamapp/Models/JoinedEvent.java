package com.example.kafkastreamapp.Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinedEvent {
    private String empl_id;
    private String dept;
    private Boolean is_fte;
    private String dob;
    private String state;
    private String country;
}
