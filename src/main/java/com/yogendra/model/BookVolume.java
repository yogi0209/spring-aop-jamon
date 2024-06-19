package com.yogendra.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookVolume(VolumeInfo volumeInfo) {
}
