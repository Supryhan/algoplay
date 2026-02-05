package problemslab.lx.generic;


import java.util.List;

public interface SelfClearingCache {

  public Record1 put(Record1 record);

  public Record1 get(int id);

  public List<Record1> get();

  void clear();

}
