package vn.sparkminds.services.dto.request;

public class AddBookRequest {
    private String title;
    private String description;
    private Long categoryId;
    private double price;
    private Long publisherId;
    private Long authorId;
    private String imageUrl;
    private double discountedPrice;
    private double discountPercent;
    private int totalBook;

    public AddBookRequest(String title, String description, Long categoryId, double price,
            Long publisherId, Long authorId, String imageUrl, double discountedPrice,
            double discountPercent, int totalBook) {
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.price = price;
        this.publisherId = publisherId;
        this.authorId = authorId;
        this.imageUrl = imageUrl;
        this.discountedPrice = discountedPrice;
        this.discountPercent = discountPercent;
        this.totalBook = totalBook;
    }

    public int getTotalBook() {
        return totalBook;
    }

    public void setTotalBook(int totalBook) {
        this.totalBook = totalBook;
    }

    public AddBookRequest() {}

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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPersent(double discountPercent) {
        this.discountPercent = discountPercent;
    }
}
