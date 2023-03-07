package pro.sky.budgetapp.services;

import pro.sky.budgetapp.model.Transaction;

public interface BudgetService {
    int getDailyBudget();

    int getBalance();

    long addTransaction(Transaction transaction);

    int getDailyBalance();

    int getAllSpends();

    int getVacationBonus(int daysCount);

    int getSalaryWithVacation(int vacationDays, int vacationWorkingDays, int monthWorkingDays);

    Transaction getTransactionById(long id);
}
