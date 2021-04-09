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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {

    private long id;
    private String name;
    private float extension;
    private boolean assistance;
    private String startingDate;
}
