package pro.sky.budgetapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pro.sky.budgetapp.model.Category;
import pro.sky.budgetapp.model.Transaction;
import pro.sky.budgetapp.services.BudgetService;
import pro.sky.budgetapp.services.FilesService;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Service
public class BudgetServiceImpl implements BudgetService {
    final private FilesService filesService;
    public static final int SALARY = 20_000;
    public static final int SAVINGS = 3_000;
    public static final int DAILY_BUDGET = (SALARY - SAVINGS) / LocalDate.now().lengthOfMonth();

    public static int balance = 0;
    public static final int[] ANNUAL_SALARY = {10000, 11000, 12000, 13000, 14000, 15000,
            15000, 15000, 16000, 17000, 18000, 20000};
    public static final int AVG_SALARY = Arrays.stream(ANNUAL_SALARY).sum() / ANNUAL_SALARY.length;
    public static final double AVG_DAYS = 29.3;
    private static Map<Month, LinkedHashMap<Long, Transaction>> transactions = new TreeMap<>();
    private static long lastId = 0;

    public BudgetServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }

    @PostConstruct
    void init() {
        try {
            readFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        LinkedHashMap<Long, Transaction> monthTransactions = transactions.getOrDefault(LocalDate.now().getMonth(),
                new LinkedHashMap<>());
        monthTransactions.put(lastId, transaction);
        transactions.put(LocalDate.now().getMonth(), monthTransactions);
        lastId++;
        saveToFile();
        return lastId;
    }

    @Override
    public Transaction editTransaction(long id, Transaction transaction) {
        for (Map<Long, Transaction> transactionMap : transactions.values()) {
            if (transactionMap.containsKey(id)) {
                transactionMap.put(id, transaction);
                return transaction;
            }
        }
        saveToFile();
        return null;
    }

    @Override
    public Transaction getTransactionById(long id) {
        for (Map<Long, Transaction> transactionMap : transactions.values()) {
            if (transactionMap.containsKey(id)) {
                Transaction transaction = transactionMap.get(id);
                if (transaction != null) {
                    return transaction;
                }
            }
        }
        return null;
    }

    @Override
    public boolean deleteTransaction(long id) {
        for (Map<Long, Transaction> transactionMap : transactions.values()) {
            if (transactionMap.containsKey(id)) {
                transactionMap.remove(id);
                return true;
            }
        }
        saveToFile();
        return false;
    }

    @Override
    public void deleteAllTransactions() {
        transactions = new TreeMap<>();
    }

    @Override
    public int getDailyBalance() {
        return (DAILY_BUDGET * LocalDate.now().getDayOfMonth() - getAllSpends());

    }

    @Override
    public int getAllSpends() {
        Map<Long, Transaction> monthTransactions = transactions.getOrDefault(LocalDate.now().getMonth(),
                new LinkedHashMap<>());
        int summ = 0;
        for (Transaction transaction : monthTransactions.values()) {
            summ += transaction.getSum();
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

    @Override
    public Path createMonthlyReport(Month month) throws IOException {
        LinkedHashMap<Long, Transaction> monthlyTransactions = transactions.getOrDefault(month, new LinkedHashMap<>());
        Path path = filesService.createTempFile("monthlyReport");
        for (Transaction transaction : monthlyTransactions.values()) {
            try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                writer.append(transaction.getCategory().getText() + "|"
                                + transaction.getSum() + "|"
                                + transaction.getComment())
                        .append("\n");
            }
        }
        return path;
    }

    private void saveToFile() {
        try {
            DataFile dataFile = new DataFile(lastId+1, transactions);
            String s = new ObjectMapper().writeValueAsString(dataFile);
            filesService.saveToFile(s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile() {
        try {
            String json = filesService.readFromFile();
            DataFile dataFile = new ObjectMapper().readValue(filesService.readFromFile(),
                    new TypeReference<DataFile>() {
                    });
            lastId = dataFile.lastId;
            transactions = dataFile.transactions;


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addTransactionsFromInputStream(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] array = StringUtils.split(line, '|');
                Transaction transaction = new Transaction(Category.getCategoryByText(array[0]), Integer.parseInt(array[1]), array[2]);
                addTransaction(transaction);
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class DataFile {
        private long lastId;
        private Map<Month, LinkedHashMap<Long, Transaction>> transactions;
    }
}
