package ordersapi.controller;

import io.swagger.v3.oas.annotations.media.Content;
import ordersapi.model.ProductOrder;
import ordersapi.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/orders", consumes = "application/json", produces = "application/json")
@Tag(name = "Order API", description = "Order CRUD operations")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    @Operation(description = "List all orders with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of orders"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<Page<ProductOrder>> getAllOrders(@Parameter(description = "Pagination details", required = false) Pageable pageable) {
        return ResponseEntity.ok(orderService.getAllOrders(pageable));
    }

    @GetMapping("/{id}")
    @Operation(description = "Get an order by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the order"),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<ProductOrder> getOrderById(@Parameter(description = "ID of the order to retrieve") @PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created the order"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<ProductOrder> createOrder(@Parameter(description = "Order details")
                                                    @RequestBody ProductOrder productOrder) {
        return ResponseEntity.ok(orderService.createOrder(productOrder));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an order by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the order"),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<ProductOrder> updateOrder(@Parameter(description = "ID of the order to update") @PathVariable Long id, @Parameter(description = "Updated order details") @RequestBody ProductOrder productOrder) {
        ProductOrder updatedProductOrder = orderService.updateOrder(id, productOrder);
        if (updatedProductOrder != null) {
            return ResponseEntity.ok(updatedProductOrder);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an order by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the order"),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<Void> deleteOrder(@Parameter(description = "ID of the order to delete") @PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
