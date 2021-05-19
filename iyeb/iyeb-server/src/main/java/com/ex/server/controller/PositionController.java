package com.ex.server.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ex.server.dto.ResponseBean;
import com.ex.server.entity.Position;
import com.ex.server.service.PositionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ttfor
 * @since 2021-04-21
 */
@RestController
@RequestMapping("/sys/config")
public class PositionController {

    @Autowired
    PositionService positionService;

    @ApiOperation("查询所有职位信息")
    @GetMapping("/getAll")
    public ResponseBean getAll(){
        return ResponseBean.success(positionService.list());
    }

    @ApiOperation("增加职位")
    @PostMapping("/add")
    public ResponseBean insertPosition(@RequestBody Position position){
        position.setCreateDate(LocalDateTime.now());
        if (positionService.save(position)) {
            return ResponseBean.success("增加职位成功");
        }
        return ResponseBean.fail("增加职位失败");
    }

    @ApiOperation("删除职位")
    @GetMapping("/{id}")
    public ResponseBean delPosition(@PathVariable("id") int id){
        if (positionService.removeById(id)) {
            return ResponseBean.success("删除职位成功");
        }
        return ResponseBean.fail("删除职位失败");
    }

    @ApiOperation("更新职位")
    @PostMapping("/update")
    public ResponseBean updatePosition(@RequestBody Position position){
        if (positionService.updateById(position)) {
            return ResponseBean.success("更新职位成功");
        }
        return ResponseBean.fail("更新职位失败");
    }

    @ApiOperation("删除选中的职位")
    @PostMapping("/selected")
    public ResponseBean delSelected(@RequestBody List<Integer> ids){
        if (positionService.removeByIds(ids)) {
            return ResponseBean.success("删除选中成功");
        }
        return ResponseBean.fail("删除选中失败");
    }
}

