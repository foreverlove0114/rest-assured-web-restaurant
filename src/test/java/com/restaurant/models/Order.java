package com.restaurant.models;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private Integer id;
    private LocalDateTime orderDate;
    private String status;
    private Double totalPrice;
    private List<OrderItem> items;

    public static class OrderItem {
        private String productName;
        private Integer quantity;
        private Double price;

        // 构造器、Getter、Setter
        public OrderItem(String productName, Integer quantity, Double price) {
            this.productName = productName;
            this.quantity = quantity;
            this.price = price;
        }

        public String getProductName() { return productName; }
        public Integer getQuantity() { return quantity; }
        public Double getPrice() { return price; }
        public Double getSubtotal() { return quantity * price; }
    }

    // Getter和Setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    public void addItem(OrderItem item) {
        this.items.add(item);
    }
}