package vn.sparkminds.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "t_publishers")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @Size(min = 5, max = 100, message = "Name must be between 5 and 100 characters")
    @NotNull(message = "Name is required")
    private String name;

    @Column(name = "email")
    @Email(message = "Invalid email format")
    private String email;

    @Column(name = "country")
    private String country;

    @Column(name = "phone")
    @Pattern(regexp = "^[0-9]+$", message = "Number phone invalid")
    private String phone;

    @Column(name = "createAt")
    @PastOrPresent(message = "Create date must be in the past or present")
    private LocalDateTime createAt;

    @Column(name = "updateAt")
    @PastOrPresent(message = "Update date must be in the past or present")
    private LocalDateTime updateAt;


    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    @OneToMany
    private List<Book> books = new ArrayList<>();
}
