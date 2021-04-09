package com.sanvalero.greenrouteapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Creado por @ author: Pedro Orós
 * el 08/04/2021
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Road {

    private long id;
    private String name;
    private float length;
    private boolean toll;
    private String buildDate;
    private String image;
}
