@GenericGenerator(
        name = "accounts_id_generator",
        strategy = "enhanced-sequence",
        parameters = {
                @Parameter(name = "sequence_name", value = "accounts_id_seq"),
                @Parameter(name = "increment_size", value = "1"),
                @Parameter(name = "optimizer", value = "pooled-lo"),
        }
)
@GenericGenerator(
        name = "categories_id_generator",
        strategy = "enhanced-sequence",
        parameters = {
                @Parameter(name = "sequence_name", value = "categories_id_seq"),
                @Parameter(name = "increment_size", value = "1"),
                @Parameter(name = "optimizer", value = "pooled-lo"),
        }
)
@GenericGenerator(
        name = "operations_id_generator",
        strategy = "enhanced-sequence",
        parameters = {
                @Parameter(name = "sequence_name", value = "operations_id_seq"),
                @Parameter(name = "increment_size", value = "1"),
                @Parameter(name = "optimizer", value = "pooled-lo"),
        }
)
package dgcd.financier.infra.repository.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
