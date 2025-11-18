package com.restaurant.models;

public class MenuItem {
    private Integer id;
    private String name;
    private String ingredients;
    private String description;
    private Double price;
    private String weight;
    private Boolean active;

    // 构造器
    public MenuItem() {}

    public MenuItem(String name, String ingredients, String description, Double price, String weight) {
        this.name = name;
        this.ingredients = ingredients;
        this.description = description;
        this.price = price;
        this.weight = weight;
        this.active = true;
    }

    // Getter和Setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIngredients() { return ingredients; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getWeight() { return weight; }
    public void setWeight(String weight) { this.weight = weight; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    // 转换为Map（用于表单提交）
    public java.util.Map<String, Object> toMap() {
        java.util.Map<String, Object> map = new java.util.HashMap<>();
        map.put("name", name);
        map.put("ingredients", ingredients);
        map.put("description", description);
        map.put("price", price.toString());
        map.put("weight", weight);
        return map;
    }
}