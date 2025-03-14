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
        Order newOrder = new Order(orderDTO.getUserId(), orderDTO.getTotalAmount());
        List <OrderItem> orderItems = new ArrayList<>();
        orderDTO.getItems().forEach(item ->{
            orderItems.add(new OrderItem(item.getProductId(), newOrder, item.getName(),item.getQuantity(), item.getUnitPrice()));
        });
        newOrder.setItems(orderItems);
        orderRepository.save(newOrder);
    }
}
