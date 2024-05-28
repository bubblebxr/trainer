package com.example.se_project.service.Impl;

import com.example.se_project.entity.Station;
import com.example.se_project.mapper.IStationMapper;
import com.example.se_project.service.IStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("stationService")
public class StationServiceImpl implements IStationService {
    @Autowired
    private IStationMapper stationMapper;

    @Override
    public List<Station> inquireAllStations() {
        return stationMapper.inquireAllStations();
    }
}
