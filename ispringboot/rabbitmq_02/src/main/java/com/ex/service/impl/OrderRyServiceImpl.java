package com.ex.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ex.entity.OrderRy;
import com.ex.mapper.OrderRyMapper;
import com.ex.service.OrderRyService;
import org.springframework.stereotype.Service;

@Service
public class OrderRyServiceImpl extends ServiceImpl<OrderRyMapper, OrderRy> implements OrderRyService {
}
