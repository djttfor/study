package com.ex.server.controller;


import com.ex.server.dto.ResponseBean;
import com.ex.server.entity.Joblevel;
import com.ex.server.entity.Position;
import com.ex.server.service.JoblevelService;
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
@RequestMapping("/sys/config/jobLevel")
public class JoblevelController {
    @Autowired
    JoblevelService joblevelService;

    @ApiOperation("查询所有职称信息")
    @GetMapping("/getAll")
    public ResponseBean getAll(){
        return ResponseBean.success(joblevelService.list());
    }

    @ApiOperation("增加职称")
    @PostMapping("/add")
    public ResponseBean insertPosition(@RequestBody Joblevel joblevel){
        joblevel.setCreateDate(LocalDateTime.now());
        if (joblevelService.save(joblevel)) {
            return ResponseBean.success("增加职称成功");
        }
        return ResponseBean.fail("增加职位失败");
    }

    @ApiOperation("删除职称")
    @GetMapping("/{id}")
    public ResponseBean delPosition(@PathVariable("id") int id){
        if (joblevelService.removeById(id)) {
            return ResponseBean.success("删除职称成功");
        }
        return ResponseBean.fail("删除职称失败");
    }

    @ApiOperation("更新职称")
    @PostMapping("/update")
    public ResponseBean updatePosition(@RequestBody Joblevel joblevel){
        if (joblevelService.updateById(joblevel)) {
            return ResponseBean.success("更新职称成功");
        }
        return ResponseBean.fail("更新职称失败");
    }

    @ApiOperation("删除选中的职位")
    @PostMapping("/selected")
    public ResponseBean delSelected(@RequestBody List<Integer> ids){
        if (joblevelService.removeByIds(ids)) {
            return ResponseBean.success("删除选中成功");
        }
        return ResponseBean.fail("删除选中失败");
    }
}

