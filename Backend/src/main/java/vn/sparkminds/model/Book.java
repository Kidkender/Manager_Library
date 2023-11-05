package vn.sparkminds.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.sparkminds.model.enums.BookStatus;

@Entity
@Table(name = "t_books")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must be at most 255 characters")
    private String title;

    @Column(name = "description")
    @Size(max = 1000, message = "Description must be at most 1000 characters")
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "price")
    @DecimalMin(value = "0.0", inclusive = false, message = "price must be greater than 0")
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "book_status")
    private BookStatus status;

    @Column(name = "discounted_price")
    @DecimalMin(value = "0.0", message = "Discounted price must be greater than or equal to 0")
    private double discountedPrice;

    @Column(name = "discount_persent")
    @DecimalMin(value = "0.0",
            message = "Discount percentage price must be greater than or equal to 0")
    @DecimalMax(value = "100.0",
            message = "Discount percentage price must be less than or equal to 100")
    private double discountPersent;

    @Column(name = "quantity")
    @Min(value = 0, message = "Quantity price must be greater than or equal to 0")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    // @URL
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "num_ratings")
    @DecimalMin(value = "0.0", message = "Number of ratings must be greater than or equal to 0")
    private double numRatings;

    @Column(name = "createAt")
    private LocalDateTime createAt;

    @Column(name = "updateAt")
    private LocalDateTime updateAt;
}
