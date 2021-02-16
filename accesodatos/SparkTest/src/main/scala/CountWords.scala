import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._


object CountWords extends App {
  Logger.getLogger("org").setLevel(Level.ERROR)


//  val spark = SparkSession.builder().
//    master("local").getOrCreate()

  val spark = SparkSession.builder().
    master("local").getOrCreate()

  val textFile = spark.read.textFile("datos/el_quijote.txt")


  import spark.implicits._


  println(textFile.count())

  val text2 = textFile.flatMap(linea => linea.split(" "))
  val chars = textFile.flatMap(linea => linea.chars().toArray())
  println(chars.count())
  println(text2.count());


  val wordCounts = textFile.flatMap(line => line.split(" "))
    .filter(palabra => palabra.length > 1)
    .groupByKey(identity).count().withColumnRenamed("count(1)", "count")

  wordCounts.sort(desc("count"))
  val array = wordCounts.orderBy(col("count").desc).limit(10).collect()

  println(array(0).getLong(1))

  val lista = wordCounts.orderBy($"count".desc).limit(10).collectAsList()

  println(65.toChar)

  println(array.deep.mkString("\n"))

  println(chars.groupByKey(identity).count().withColumnRenamed("count(1)", "count")
    .sort(desc("count"))
    .limit(10).map(row => row(0).##.toChar + " " + row(1)).collect().deep.mkString("\n"))


}
