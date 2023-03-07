package pro.sky.budgetapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.budgetapp.services.BudgetService;

@RestController
@RequestMapping("/vacation")
public class VacationController {

    private final BudgetService budgetService;

    public VacationController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping("/bonus")
    public int getVacationBonus(@RequestParam int days){
        return budgetService.getVacationBonus(days);
    }

    @GetMapping("/monthSalary")
    public int getSalaryWithVacation(@RequestParam int vacDays,@RequestParam int vacWorkingDays,
                                     @RequestParam int monthWorkingDays) {
        return budgetService.getSalaryWithVacation(vacDays, vacWorkingDays, monthWorkingDays);
    }
}
