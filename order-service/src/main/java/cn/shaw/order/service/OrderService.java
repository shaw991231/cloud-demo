package cn.shaw.order.service;

import cn.shaw.order.mapper.OrderMapper;
import cn.shaw.order.pojo.Order;
import cn.shaw.order.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RestTemplate restTemplate;

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        // 2.RestTemplate发起http请求,查询用户
        // 2.1 url路径
        String url = "http://userservice/user/" + order.getUserId();
        // 2.2 发送http请求,实现远程调用
        User user = restTemplate.getForObject(url, User.class);
        // 3.封装user到Order
        order.setUser(user);
        // 4.返回
        return order;
    }
}
