package com.yogendra.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record VolumeInfo(String title, String subtitle, String[] authors) {
}
