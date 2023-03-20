package pro.sky.budgetapp.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Schema(description = "сумма траты")
    private int summ;
    @Schema(description = "категория из Enum")
    private Category category;
    @Schema(description = "комментарий, что именно куплено")
    private String comment;

}
