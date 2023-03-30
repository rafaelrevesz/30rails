package com.siemens.mo.thirtyrails.map.web;

import com.siemens.mo.thirtyrails.map.Rotation;
import com.siemens.mo.thirtyrails.position.Position;
import jakarta.validation.Valid;

public record TrackDto(TrackType type, Rotation rotation, @Valid Position position) {
}
