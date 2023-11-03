package vn.sparkminds.services.dto.request;

import java.time.LocalDateTime;

public class BookRequest {
    private String title;
    private String description;
    private double price;
    private int quantity;
    private String imageUrl;

    public BookRequest(String title, String description, double price, int quantity,
            String imageUrl, LocalDateTime updateAt) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }



    @Override
    public String toString() {
        return "BookRequest [title=" + title + ", description=" + description + ", price=" + price
                + ", quantity=" + quantity + ", imageUrl=" + imageUrl + ", updateAt=" + "]";
    }
}
