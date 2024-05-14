package base.backend.Base.Project.controllers;

import base.backend.Base.Project.models.Order;
import base.backend.Base.Project.models.dto.OrderDTO;
import base.backend.Base.Project.models.dao.OrderDAO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/orders")
@Tag(name = "Orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderDTO> orderDTOs = orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Integer id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order
                .map(o -> ResponseEntity.ok(convertToDTO(o)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUserId(@PathVariable Integer userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        List<OrderDTO> orderDTOs = orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDTOs);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        Order newOrder = orderService.createOrder(orderDTO);
        OrderDTO newOrderDTO = convertToDTO(newOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrderDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> updates
    ) {
        Order updatedOrder = orderService.updateOrder(id, updates);
        OrderDTO updatedOrderDTO = convertToDTO(updatedOrder);
        return ResponseEntity.ok(updatedOrderDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    private OrderDTO convertToDTO(Order order) {
        return new OrderDTO(order);
    }
}
