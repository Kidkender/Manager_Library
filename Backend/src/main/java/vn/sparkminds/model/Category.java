package vn.sparkminds.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @Column(name = "description")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    @Column(name = "createAt")
    private LocalDateTime createdAt;

    @Column(name = "quantity_invetory")
    @Min(value = 0, message = "Quantity inventory must be greater than or equal to 0")
    private int quantityInvetory;

    @Column(name = "total_book")
    @Min(value = 0, message = "Total books must be greater than or equal to 0")
    private int totalBook;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Book> books = new ArrayList<>();

}
