package com.example.se_project.service;

import com.example.se_project.entity.Station;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IStationService {
    public List<Station> inquireAllStations();

}
