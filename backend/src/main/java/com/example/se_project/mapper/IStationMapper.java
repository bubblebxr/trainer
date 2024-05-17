package com.example.se_project.mapper;

import com.example.se_project.entity.Station;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IStationMapper {
    @Select("select * from stations")
    List<Station> inquireAllStations();
}
