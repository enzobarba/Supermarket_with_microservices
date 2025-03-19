package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import com.labISD.demo.dto.*;
import java.util.ArrayList;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;

    public String getAllOrders(){
        List <Order> orders = orderRepository.findAll();
        if(orders.size() == 0){
            return "no orders";
        }
        return orders.toString();
    }

    public String getAllUserOrders(UUID userId){
        List <Order> userOrders = orderRepository.findByUserId(userId);
        if(userOrders.size() == 0){
            return "no user order";
        }
        return userOrders.toString();
    }

    public void createOrder(OrderDTO orderDTO){
        Order newOrder = new Order(orderDTO.userId(), orderDTO.totalAmount());
        List <OrderItem> orderItems = new ArrayList<>();
        orderDTO.items().forEach(item ->{
            orderItems.add(new OrderItem(item.productId(), newOrder, item.name(),item.quantity(), item.unitPrice()));
        });
        newOrder.setItems(orderItems);
        orderRepository.save(newOrder);
    }
}
