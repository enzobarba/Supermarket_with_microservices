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

    public List <Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public List <Order> getAllUserOrders(UUID userId){
        return orderRepository.findByUserId(userId);
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
