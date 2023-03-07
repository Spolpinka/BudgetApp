package pro.sky.budgetapp.services;

import pro.sky.budgetapp.model.Transaction;

public interface BudgetService {
    int getDailyBudget();

    int getBalance();

    long addTransaction(Transaction transaction);

    boolean deleteTransaction(long id);

    void deleteAllTransactions();

    int getDailyBalance();

    int getAllSpends();

    int getVacationBonus(int daysCount);

    int getSalaryWithVacation(int vacationDays, int vacationWorkingDays, int monthWorkingDays);

    Transaction editTransaction(long id, Transaction transaction);

    Transaction getTransactionById(long id);
}
