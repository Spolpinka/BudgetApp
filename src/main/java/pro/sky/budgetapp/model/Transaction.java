package pro.sky.budgetapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class Transaction {
    private int summ;
    private Category category;
    private String comment;

}
