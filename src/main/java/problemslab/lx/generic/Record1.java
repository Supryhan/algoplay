package problemslab.lx.generic;

import java.time.LocalDateTime;

class Record2 extends Record1 {

  public Record2(Record1 record1) {
    super(record1.getId(), record1.getLocalDate(), record1.getMessage());
  }
  @Override
  public String getMessage() {
    System.out.println(">>>>>>>>>");
    return super.getMessage();
  }

}


public class Record1 {
  private int id;
  private String message;
  private LocalDateTime LocalDateTime;//TODO remove

  public int getId() {
    return id;
  }

  public LocalDateTime getLocalDate() {
    return LocalDateTime;
  }

  public String getMessage() {
    return message;
  }

  public Record1(int id, LocalDateTime LocalDateTim, String message) {
    this.id = id;
    this.LocalDateTime = LocalDateTime;
    this.message = message;
  }

}
