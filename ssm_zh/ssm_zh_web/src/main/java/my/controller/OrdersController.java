package my.controller;

import com.github.pagehelper.PageInfo;
import my.domain.Orders;
import my.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    OrdersService ordersService;
    @RequestMapping("/all")
    @RolesAllowed("ADMIN")
    /**
    * Description:
    * <分页查询订单>
    * @param pageNum 当前页
     * @param pageSize 当前页记录数
    * @return: org.springframework.web.servlet.ModelAndView
    * @Author: DJ
    * @Date: 2020-03-30 21:36
    */
    public ModelAndView findAll(@RequestParam(name = "pageNum",required = true,defaultValue = "1") int pageNum,
                                @RequestParam(name = "pageSize",required = true,defaultValue = "4") int pageSize) throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Orders> all = ordersService.findAll(pageNum,pageSize);
        PageInfo<Orders> pageInfo = new PageInfo<Orders>(all);
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("order-list");
        return mv;
    }
    @RolesAllowed("ADMIN")
    @RequestMapping("/show")
    public ModelAndView findOrdersById(@RequestParam(name = "id",required = true) String id) throws Exception {
        ModelAndView mv = new ModelAndView();
        Orders orders = ordersService.findOrdersById(id);
        mv.addObject("orders", orders);
        mv.setViewName("orders-show");
        return mv;
    }
}
