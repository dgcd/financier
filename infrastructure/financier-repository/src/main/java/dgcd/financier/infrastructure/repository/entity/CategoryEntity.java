package dgcd.financier.infrastructure.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import static dgcd.financier.core.domain.Constants.CATEGORY_TITLE_MAX_LENGTH;
import static dgcd.financier.core.domain.Constants.CATEGORY_TITLE_MIN_LENGTH;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @GeneratedValue(generator = "categories_id_generator")
    @GenericGenerator(
            name = "categories_id_generator",
            strategy = "enhanced-sequence",
            parameters = {
                    @Parameter(name = "sequence_name", value = "categories_id_seq"),
                    @Parameter(name = "increment_size", value = "1"),
                    @Parameter(name = "optimizer", value = "pooled-lo"),
            }
    )
    private Long id;

    @NotNull
    @Size(min = CATEGORY_TITLE_MIN_LENGTH, max = CATEGORY_TITLE_MAX_LENGTH)
    private String title;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CategoryEntity parent;

}
