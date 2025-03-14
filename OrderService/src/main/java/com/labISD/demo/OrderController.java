package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.labISD.demo.dto.OrderDTO;
import java.util.UUID;

@RestController
public class OrderController {
    
    @Autowired
    private OrderService orderService;

    @GetMapping("/getAllOrders")
    public String getAllOrders(){
        return orderService.getAllOrders().toString();
    }

    @GetMapping("/getAllUserOrders")
    public String getAllUserOrder(@RequestParam(value = "userId") UUID userId){
        return orderService.getAllUserOrders(userId).toString();
    }

    @PostMapping("/createOrder")
    public void createOrder(@RequestBody OrderDTO orderDTO){
        orderService.createOrder(orderDTO);
    }
}
