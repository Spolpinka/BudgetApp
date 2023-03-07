package pro.sky.budgetapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.budgetapp.model.Transaction;
import pro.sky.budgetapp.services.BudgetService;

@RestController
@RequestMapping("/transaction")
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
}
