package pro.sky.budgetapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.budgetapp.services.BudgetService;

@RestController
@RequestMapping("/budget")
public class BudgetController {
    private BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping("/daily")
    public int dailyBudget() {
        return budgetService.getDailyBudget();
    }

    @GetMapping("/balance")
    public int balance() {
        return budgetService.getBalance();
    }

    @GetMapping("/vacation/bonus")
    public int getVacationBonus(@RequestParam int days){
        return budgetService.getVacationBonus(days);
    }

    @GetMapping("/vacation/monthSalary")
    public int getSalaryWithVacation(@RequestParam int vacDays,@RequestParam int vacWorkingDays,
                                     @RequestParam int monthWorkingDays) {
        return budgetService.getSalaryWithVacation(vacDays, vacWorkingDays, monthWorkingDays);
    }
}
