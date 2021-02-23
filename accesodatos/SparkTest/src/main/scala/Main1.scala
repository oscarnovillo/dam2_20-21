import Parquimetros.spark
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.IntegerType

object Main1 extends App {

  Logger.getLogger("org").setLevel(Level.ERROR)

  val spark = SparkSession.builder().
    master("local").getOrCreate()

  val ser = spark.read.option("header", "true")
    .option("delimiter", ";")
    .option("encoding", "windows-1252")
    .csv("datos/calles_SER_2020.csv")

  ser.printSchema()

  ser.groupBy(col("Distrito"),col("Color"))
    .agg(sum(col("Número de Plazas").cast(IntegerType))
      .as("plazas"))
    .withColumn("tam",length(col("Distrito")))
    .withColumn("Distrito",
      substring(col("Distrito"),4,col("tam"))
    .sort("Distrito")
    .show()




}
