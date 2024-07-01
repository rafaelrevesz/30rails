package com.siemens.mo.thirtyrails.map.web;

import com.siemens.mo.thirtyrails.station.StationOrientation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record StationDto(@Min(1) @Max(4) int stationId, StationOrientation orientation, @Min(1) @Max(6) int position) {
}
