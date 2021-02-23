import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object Madrid extends App {

  Logger.getLogger("org").setLevel(Level.ERROR)
  val spark = SparkSession.builder().
    master("local").getOrCreate()

  import spark.implicits._

  spark.sparkContext.setLogLevel("ERROR");

  val madrid = spark.read
    .option("header", "true")
    .option("encoding", "windows-1252")
    .csv("datos/206974-0-agenda-eventos-culturales-100.csv")

  madrid.printSchema()
  madrid.show(100,false)

  def lastIndexSplit(s: String): String = {
    if (s != null) {
      val array = s.split(",")
      if (array.length > 0)
        return array(array.length - 1)
      else
        return s
    }
    "no hay dia"
  }

  val lastIndexSplitUDF = udf[String, String](lastIndexSplit)


  madrid.filter(col("NOMBRE-INSTALACION").like("%Moratalaz%"))
    .withColumn("TIPO", lastIndexSplitUDF(col("DIAS-SEMANA")))
    .select(col("TITULO"), col("TIPO").as("TIPO2"))
    .foreach(row => println(row(0) + " " + row(1)))

//
//
  madrid.filter(col("NOMBRE-INSTALACION").like("%Moratalaz%"))
    .withColumn("ULTIMO_DIA-SEMANA",
      substring_index($"DIAS-SEMANA", ",", -1))
    .select(col("TITULO"), col("ULTIMO_DIA-SEMANA"))
    .foreach(row => println(row(0) + " " + row(1)))
//
//
  madrid.filter(month(col("FECHA")).equalTo(1))
    .select(col("TITULO"), date_format(to_date(col("FECHA")), "dd/MM/yyyy")).foreach(row => println(row(0) + " " + row(1)))

  val lista = madrid.filter(month($"FECHA").equalTo(1))
    .select(col("TITULO"), date_format(to_date(col("FECHA")), "dd/MM/yyyy")).collect()

  lista.foreach(println);
  for (row <- lista) {
    println(row)
  }
//
  madrid.filter(!col("NOMBRE-INSTALACION").isNull)
    .groupBy($"NOMBRE-INSTALACION")
    .agg(max("HORA").as("maxHora")
      ,min("HORA").as("minHora"))
    .sort(desc("maxHora"))
    .limit(1)
    .show(false)
//
//
  val d = madrid.filter(col("DIAS-SEMANA").like("%L%"))
    .groupBy($"DIAS-SEMANA").count().sort(desc("count"))
    .select(avg($"count").as("media")).first().getDouble(0);
  println(d);
//
  madrid.filter(col("DIAS-SEMANA").like("%L%"))
    .groupBy($"DIAS-SEMANA").count()
    .sort(desc("count"))
    .withColumn("media", lit(d))
    .filter(col("count") > col("media")).show(false)
//
//
  madrid.select(date_format(to_date($"FECHA"), "yyyy-MM")).distinct().show(false)

  madrid.withColumn("FECHA2", date_format(to_date($"FECHA"), "yyyy-MM"))
    .stat.crosstab("FECHA2", "CODIGO-POSTAL-INSTALACION").sort(asc("FECHA2_CODIGO-POSTAL-INSTALACION"))
    .show(false)
  madrid.stat.freqItems(Seq("FECHA")).show(false)
}
