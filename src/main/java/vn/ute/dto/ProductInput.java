package vn.ute.dto;

public class ProductInput {
    private String title;
    private String description;
    private Double price;
    private String images;
    private Integer amount;
    private Long categoryId;

    public ProductInput() {
    }

    public ProductInput(String title, String description, Double price, String images, Integer amount, Long categoryId) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.images = images;
        this.amount = amount;
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
