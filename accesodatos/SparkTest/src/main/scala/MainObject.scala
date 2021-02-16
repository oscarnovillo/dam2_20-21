import java.time.LocalDate

object MainObject {


  def main(args: Array[String]): Unit = {
    var m = new MainClass();
    val s = m.hazAlgo(args);
    println("hello" + s)
    val date = LocalDate.now();
    println(date)
  }
}
