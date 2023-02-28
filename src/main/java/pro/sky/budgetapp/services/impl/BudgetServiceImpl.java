package pro.sky.budgetapp.services.impl;

import org.springframework.stereotype.Service;
import pro.sky.budgetapp.services.BudgetService;

import java.time.LocalDate;
import java.util.Arrays;

@Service
public class BudgetServiceImpl implements BudgetService {

    public static final int SALARY = 20_000;
    public static final int[] ANNUAL_SALARY = {10000, 11000, 12000, 13000, 14000, 15000,
            15000, 15000, 16000, 17000, 18000, 20000};

    public static final double AVG_DAYS = 29.3;
    @Override
    public int getDailyBudget() {
        return SALARY / 31;
    }

    @Override
    public int getBalance() {
        return SALARY - LocalDate.now().getDayOfMonth() * getDailyBudget();
    }
    @Override
    public int getVacationBonus(int daysCount) {
        double avgDaySalary = (Arrays.stream(ANNUAL_SALARY).sum() / ANNUAL_SALARY.length) / AVG_DAYS;
        return (int) (daysCount * avgDaySalary);
    }
@Override
    public int getSalaryWithVacation(int vacationDays, int vacationWorkingDays, int monthWorkingDays) {
        return getVacationBonus(vacationDays) + ((monthWorkingDays - vacationWorkingDays) * SALARY / monthWorkingDays);
    }
}
