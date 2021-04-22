package com.ex.service.impl;

import com.ex.entity.IOrder;
import com.ex.mapper.OrderMapper;
import com.ex.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author ttfor
 * @since 2021-04-18
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, IOrder> implements OrderService {
}
