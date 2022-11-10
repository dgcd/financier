package dgcd.financier.infra.repository.entity;

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
    private Long id;

    @NotNull
    @Size(min = CATEGORY_TITLE_MIN_LENGTH, max = CATEGORY_TITLE_MAX_LENGTH)
    private String title;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CategoryEntity parent;

}
