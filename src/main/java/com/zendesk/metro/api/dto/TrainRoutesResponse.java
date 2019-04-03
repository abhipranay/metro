package com.zendesk.metro.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zendesk.metro.processing.Path;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrainRoutesResponse {
    public List<Path> paths;
    public List<List<String>> steps;
}
