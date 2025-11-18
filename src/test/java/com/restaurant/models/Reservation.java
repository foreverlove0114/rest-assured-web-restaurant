package com.restaurant.models;

import java.time.LocalDateTime;

public class Reservation {
    private Integer id;
    private String tableType;
    private LocalDateTime reservationTime;
    private String status;

    // 构造器
    public Reservation() {}

    public Reservation(String tableType, LocalDateTime reservationTime) {
        this.tableType = tableType;
        this.reservationTime = reservationTime;
        this.status = "active";
    }

    // Getter和Setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTableType() { return tableType; }
    public void setTableType(String tableType) { this.tableType = tableType; }

    public LocalDateTime getReservationTime() { return reservationTime; }
    public void setReservationTime(LocalDateTime reservationTime) { this.reservationTime = reservationTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // 转换为Map（用于表单提交）
    public java.util.Map<String, String> toMap() {
        java.util.Map<String, String> map = new java.util.HashMap<>();
        map.put("table_type", tableType);
        map.put("time", reservationTime.toString());
        return map;
    }
}