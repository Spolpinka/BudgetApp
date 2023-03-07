package pro.sky.budgetapp.services.impl;

import org.springframework.stereotype.Service;
import pro.sky.budgetapp.model.Transaction;
import pro.sky.budgetapp.services.BudgetService;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Service
public class BudgetServiceImpl implements BudgetService {

    public static final int SALARY = 20_000;
    public static final int SAVINGS = 3_000;
    public static final int DAILY_BUDGET = (SALARY - SAVINGS) / LocalDate.now().lengthOfMonth();

    public static int balance = 0;
    public static final int[] ANNUAL_SALARY = {10000, 11000, 12000, 13000, 14000, 15000,
            15000, 15000, 16000, 17000, 18000, 20000};
    public static final int AVG_SALARY = Arrays.stream(ANNUAL_SALARY).sum() / ANNUAL_SALARY.length;
    public static final double AVG_DAYS = 29.3;
    private static Map<Month, Map<Long, Transaction>> transactions = new TreeMap<>();
    private static long lastId = 0;
    @Override
    public int getDailyBudget() {
        return DAILY_BUDGET;
    }

    @Override
    public int getBalance() {
        return SALARY - SAVINGS - getAllSpends();
    }
    @Override
    public long addTransaction(Transaction transaction) {
        Map<Long, Transaction> monthTransactions = transactions.getOrDefault(LocalDate.now().getMonth(),
                new LinkedHashMap<>());
        monthTransactions.put(lastId, transaction);
        return lastId++;
    }
    @Override
    public int getDailyBalance() {
        return (DAILY_BUDGET * LocalDate.now().getDayOfMonth() - getAllSpends());

    }
    @Override
    public int getAllSpends(){
        Map<Long, Transaction> monthTransactions = transactions.getOrDefault(LocalDate.now().getMonth(),
                new LinkedHashMap<>());
        int summ = 0;
        for (Transaction transaction : monthTransactions.values()) {
            summ += transaction.getSumm();
        }
        return summ;
    }
    @Override
    public int getVacationBonus(int daysCount) {
        double avgDaySalary = (AVG_SALARY) / AVG_DAYS;
        return (int) (daysCount * avgDaySalary);
    }
@Override
    public int getSalaryWithVacation(int vacationDays, int vacationWorkingDays, int monthWorkingDays) {
        return getVacationBonus(vacationDays) + ((monthWorkingDays - vacationWorkingDays) * SALARY / monthWorkingDays);
    }
}
