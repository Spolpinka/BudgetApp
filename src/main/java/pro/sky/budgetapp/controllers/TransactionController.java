package pro.sky.budgetapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.budgetapp.model.Category;
import pro.sky.budgetapp.model.Transaction;
import pro.sky.budgetapp.services.BudgetService;

import java.time.Month;

@RestController
@RequestMapping("/transaction")
@Tag(name = "Транзакции", description = "Добавление, редактирование, получение, удаление")
public class TransactionController {

    private final BudgetService budgetService;

    public TransactionController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    public ResponseEntity<Long> addTransaction(@RequestBody Transaction transaction) {
        long id = budgetService.addTransaction(transaction);
        return ResponseEntity.ok().body(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionByMonth(@PathVariable long id) {
        Transaction transaction = budgetService.getTransactionById(id);
        if (transaction == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(transaction);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> editTransaction(@PathVariable long id, @RequestBody Transaction transaction) {
        Transaction transactionById = budgetService.editTransaction(id, transaction);
        if (transactionById == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transactionById);
    }

    @GetMapping()
    @Operation(
            summary = "Поиск транзакций по месяцу и (или) категории",
            description = "можно поискать по месяцу или категории или без фильтров совсем"
    )
    @Parameters(
            value = {
                    @Parameter(
                            name = "month",
                            description = "месяц из соответствующего Enum",
                            example = "December"
                    )
            }
    )
    @ApiResponses (value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Транзакции найдены",
                    content = {
                            @Content (
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Transaction.class))
                            )
                    }
            )
    }
    )
    public ResponseEntity<Transaction> getAllTransactions(@RequestParam (required = false) Month month,
                                                          @RequestBody (required = false)Category category) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteTransaction(@PathVariable long id) {
        if (budgetService.deleteTransaction(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllTransactions() {
        budgetService.deleteAllTransactions();
        return ResponseEntity.ok().build();
    }

}
