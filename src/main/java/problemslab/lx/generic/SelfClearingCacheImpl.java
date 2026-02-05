package problemslab.lx.generic;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SelfClearingCacheImpl implements SelfClearingCache {

  private LocalDateTime localDateTime;

  Map<Integer, Record1> recordsMap = new HashMap<>();
  Map<Integer, LocalDateTime> recordsMapTimeMap = new HashMap<>();

  public SelfClearingCacheImpl(LocalDateTime localDateTime) {
    this.localDateTime = localDateTime;
    clear();
  }

  @Override
  public Record1 put(Record1 record) {
    clear();
    return recordsMap.put(record.getId(), record);
  }

  @Override
  public Record1 get(int id) {
    clear();
    return recordsMap.get(id);
  }

  @Override
  public List<Record1> get() {
    clear();
    return recordsMap.values().stream().toList();
  }

  @Override
  public void clear() {

    LocalDateTime dateTimeMax = localDateTime.minusSeconds(10000L); //TODO
    //N/core
    List<Record1> toRemove = recordsMap
        .values()
        .stream()
        .filter((Record1 r) -> r.getLocalDate().isBefore(dateTimeMax))
        .collect(Collectors.toList());
    //N/core * N/2
    List<Record1> newRecords = recordsMap.values().stream().filter((Record1 r) -> toRemove.contains(r)).collect(Collectors.toList());
    Map<Integer, Record1> recordMap = new HashMap<>();
    //2N + N
    var i = newRecords.stream().map((Record1 r) -> recordMap.put(r.getId(), r)).collect(Collectors.counting());
    System.out.println("Removed: " + i + " old records.");
    this.recordsMap = recordMap;
    // N + N * N/2 + 2N + N = 5N + N/2


  }
}
