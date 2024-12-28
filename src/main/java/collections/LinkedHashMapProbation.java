package collections;

import java.util.HashMap;
import java.util.Map;

public class LinkedHashMapProbation {

  public static void main(String[] args) {
    Map<String, String> userMessages = new HashMap<>();

    userMessages.put("alice", "Hello, ");
    userMessages.put("bob", "Hi, ");

    addMessage(userMessages, "alice", "how are you?");
    addMessage(userMessages, "charlie", "nice to meet you!");

    userMessages.forEach((user, message) -> System.out.println(user + ": " + message));
  }

  private static void addMessage(Map<String, String> map, String key, String msg) {
    map.compute(key, (k, v) -> (v == null) ? msg : v.concat(msg));
  }
}
