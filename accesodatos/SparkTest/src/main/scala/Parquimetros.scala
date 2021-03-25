
import BreezeTest.{g2, p}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{DecimalType, DoubleType, IntegerType}
import org.apache.spark.sql.{SaveMode, SparkSession, functions}
import net.liftweb.json._
import net.liftweb.json.Serialization.write

import scala.collection.mutable.ListBuffer

object Parquimetros extends App {
  Logger.getLogger("org").setLevel(Level.ERROR)

  val spark = SparkSession.builder().
    master("local").getOrCreate()

  val madrid = spark.read.option("header", "true")
    .option("delimiter", ";")
    .option("encoding", "windows-1252").csv("datos/tiques_ser_3T_2020.csv")

  madrid.printSchema()
//  madrid.groupBy("matricula_parquimetro").count().show()

//  madrid.limit(20).withColumn("fecha", to_timestamp(col("fecha_inicio"), "yyyy-MM-dd HH:mm:ss"))
//    .write.option("header", "true")
//    .mode(SaveMode.Overwrite)
//    .csv("datos/test.csv")
//
  madrid.withColumn("fecha", to_timestamp(col("fecha_inicio"), "yyyy-MM-dd HH:mm:ss"))
    .drop("fecha_inicio")
    .write
    .mode(SaveMode.Overwrite)
    .parquet("datos/test.parquet")

//  val madrid1 = spark.read
//    .option("header", "true").option("encoding", "windows-1252")
//    .option("inferSchema", "true")
//    .csv("datos/test.csv")

//  madrid1.printSchema()

  val madrid2 = spark.read.parquet("datos/test.parquet")
  madrid2.printSchema()

//  madrid.show()
  val parquimetros = spark.read.option("header", "true")
    .option("delimiter", ";")
    .option("encoding", "windows-1252").csv("datos/parquimetros.csv")

//  parquimetros.printSchema()
//  parquimetros.withColumn("fecha", month(to_date(col("Fecha de Alta"), "d-M-yyyy"))).show()
//
//
  madrid.limit(20)
    .withColumn("matricula", functions.concat(lit("0"), col("matricula_parquimetro")))
    .join(parquimetros, col("matricula") === parquimetros("Matrícula"), "inner")
    .withColumn("coordenadas", struct(col("Gis_X"), col("Gis_Y")))
    .drop("Gis_X", "Gis_Y")
    .withColumn("tique", struct(col("minutos_tique").as("minutos")
                                        , col("importe_tique").as("importe")))
    .groupBy("matricula").agg(collect_list("tique").as("tique"))
    .show()
//
//
//  madrid.limit(20).withColumn("importe",regexp_replace(col("importe_tique"),",",".").cast(DoubleType)).sort("minutos_tique").show();
//  madrid.limit(20).agg(max(regexp_replace(col("importe_tique"),",",".").cast(DoubleType)).as("max_minutos"),
//    min(col("minutos_tique").cast(IntegerType)).as("min_minutos")).show();
//
//
  val windowSpec  = Window.partitionBy("matricula_parquimetro").orderBy("fecha")

  madrid2.withColumn("row_number",row_number.over(windowSpec))
    .withColumn("fecha_finAnterior",lag("fecha_fin",1).over(windowSpec))
    .withColumn("max_importe",sum(regexp_replace(col("importe_tique"),",",".").cast(DoubleType))
      //.withColumn("ticketInt",col("tique").cast(IntType))
      .over(windowSpec))
    .filter(col("fecha") <  to_timestamp(col("fecha_finAnterior"), "yyyy-MM-dd HH:mm:ss"))
    .show()

  val lista = new ListBuffer[Barra]();

  madrid2.groupBy("matricula_parquimetro").count().limit(20)
    .collectAsList().stream().forEach(row => {
    lista += new Barra(row(0).toString,row(1).toString.toInt)
    })


  implicit val formats = DefaultFormats
  val jsonString = write(lista)
  val htmlR = new PrintHTML
  htmlR.pintar(jsonString)


}
