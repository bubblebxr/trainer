package com.example.se_project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.se_project.entity.Stopover;
import com.example.se_project.entity.Train;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ITrainMapper extends BaseMapper<Train> {
//    @Select("select * from trains where start_station = #{start_station}"+
//"and arrive_station =#{arrive_station} and date =#{date} ORDER BY " + CASE WHEN #{sort_type} = 2 THEN start_time END DESC,"+
//"CASE WHEN #{sort_type} = 3 THEN duration END ASC,"+
//"CASE WHEN #{sort_type} = 1 THEN start_time END ASC;")

    List<Train> searchTrain(String start_station, String arrive_station, String date, Integer is_GD, Integer sort_type, List<Boolean> seat_type, Boolean isHide);

    List<Stopover> searchStopover(String trainId);

}
