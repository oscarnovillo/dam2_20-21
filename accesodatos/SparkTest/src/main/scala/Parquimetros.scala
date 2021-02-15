
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{SparkSession, functions}

object Parquimetros extends App {
  Logger.getLogger("org").setLevel(Level.ERROR)

  val spark = SparkSession.builder().
    master("local").getOrCreate()

  val madrid = spark.read.option("header", "true")
    .option("delimiter", ";")
    .option("encoding", "windows-1252").csv("datos/tiques_ser_3T_2020.csv")

  madrid.printSchema()
  madrid.groupBy("matricula_parquimetro").count().show()

  madrid.show()
  val parquimetros = spark.read.option("header", "true")
    .option("delimiter", ";")
    .option("encoding", "windows-1252").csv("datos/parquimetros.csv")

  parquimetros.printSchema()
  parquimetros.withColumn("fecha", month(to_date(col("Fecha de Alta"), "d-M-yyyy"))).show()


  madrid.limit(20)
    .withColumn("matricula", functions.concat(lit("0"), col("matricula_parquimetro")))
    .join(parquimetros, col("matricula") === parquimetros("Matrícula"), "inner")
    .withColumn("coordenadas", struct(col("Gis_X"), col("Gis_Y")))
    .drop("Gis_X", "Gis_Y")
    .withColumn("tique", struct(col("minutos_tique").as("minutos")
                                        , col("importe_tique").as("importe")))
    .groupBy("matricula").agg(collect_list("tique").as("tique"))
    .show()
  // parquimetros.show()
}
