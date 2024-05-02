package projectspringboot.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accessories")
public class Accessories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accessory_id")
    private Long id;
    private String name;
    private String specifications;
    private double costPrice;
    private int currentQuantity;
    @Transient
    private MultipartFile image;
    private String filename;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;
    private boolean is_activated;
    private boolean is_deleted;
}
