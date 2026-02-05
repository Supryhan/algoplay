package core;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompletableFutureExample {
  public static void main(String[] args) {

    Map<String, String> stringStringMap;

    CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return "Result";
    });

    future.thenApply(result -> result.toUpperCase())
        .thenAccept(System.out::println)
        .exceptionally(ex -> {
          System.err.println("Error: " + ex.getMessage());
          return null;
        })
        .join();
  }


  private void exampleRun() throws InterruptedException {
    ExecutorService executor = Executors.newFixedThreadPool(4);
    List<DataPortion> dataPortions = List.of(new DataPortion(), new DataPortion(), new DataPortion());

    for (DataPortion data : dataPortions) {
      executor.submit(() -> {
        // read from DB
      });
    }
    executor.shutdown();
    executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
  }

  class DataPortion {
  }
}
