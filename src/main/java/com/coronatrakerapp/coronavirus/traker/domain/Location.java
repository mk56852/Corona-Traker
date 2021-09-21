package com.coronatrakerapp.coronavirus.traker.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Location {

    private String state;
    private String country;
    private int todayCases;

}
