package problemslab.lx.streams1;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ManagerTask {
  private final List<Transaction> transactions;

  public ManagerTask() {
    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("Mario", "Milan");
    Trader alan = new Trader("Alan", "Cambridge");
    Trader brian = new Trader("Brian", "Cambridge");
    transactions = Arrays.asList(
        new Transaction(brian, 2011, 300),
        new Transaction(raoul, 2012, 1000),
        new Transaction(raoul, 2011, 400),
        new Transaction(mario, 2012, 710),
        new Transaction(mario, 2012, 700),
        new Transaction(alan, 2012, 950)
    );
  }

  public static void main(String[] args) {
    ManagerTask managerTask = new ManagerTask();
    managerTask.getTransactionsByYearSortedByValue(2011).forEach(System.out::println);


  }

  public List<Transaction> getTransactionsByYearSortedByValue(int year) {
    return transactions
        .stream()
        .filter(y -> y.getYear() == year)
        .sorted(Comparator.comparing(Transaction::getValue))
        .collect(Collectors.toList());
  }

}
