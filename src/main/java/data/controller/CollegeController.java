package data.controller;

import data.dto.CollegeDto;
import data.mapper.CollegeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CollegeController {

    @Autowired
    CollegeMapper collegeMapper;

    @GetMapping("/college")
    public List<CollegeDto> selectCollegeList(){
        return collegeMapper.selectCollegeList();
    }
}
