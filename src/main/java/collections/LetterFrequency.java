package collections;

import java.util.HashMap;
import java.util.Map;

public class LetterFrequency {
  public static void main(String[] args) {

    String text = "Пример текста, в котором нужно подсчитать количество каждой буквы.";
    Map<Character, Integer> frequencyMap = new HashMap<>();

    // Приведение текста к нижнему регистру для унификации
    text = text.toLowerCase();

    // Итерация по символам текста
    for (char ch : text.toCharArray()) {
      // Проверка, что символ является буквой
      if (Character.isLetter(ch)) {
        frequencyMap.put(ch, frequencyMap.getOrDefault(ch, 0) + 1);
      }
    }

    // Вывод частоты встречаемости каждой буквы
    frequencyMap.forEach((k, v) -> System.out.println(k + ": " + v));
  }
}
