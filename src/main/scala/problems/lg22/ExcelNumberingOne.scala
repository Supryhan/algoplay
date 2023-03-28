package problems.lg22

object ExcelNumberingOne extends App {

  for(i <-  1 to 29) {
    if(i <= 16)
      println(s"Student $i => Variant $i")
    else if (i <= 29)
      println(s"Student $i => Variant ${i-16}")
    else
      println("Such variant does not exist!")
  }
}
