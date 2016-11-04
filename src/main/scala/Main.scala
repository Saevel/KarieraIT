import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Zielony on 2016-11-05.
  */
object Main extends App {

  val sparkContext = new SparkContext(
    new SparkConf()
      .setMaster("local[*]")
      .setAppName("Bonuses")
  )

  val sqlContext = new SQLContext(sparkContext)
  import sqlContext.implicits._

  //Wczytanie pliku CSV linia po linii
  val lines = sparkContext.textFile("data")

  //sprawdzenie nagłówka
  val header = lines.first

  //Usunięcie nagłówka i budowa obiektu
  val employees = lines.filter(line => line != header)
    .map{line =>
      val elements = line.split(",")
      Employee(elements(0), elements(1), elements(2),
        elements(3).toDouble, elements(4).toDouble)
    }

  //Wybranie setki najlepszych pracowników
  val topHundred = employees.sortBy((employee => (employee.kpi / employee.salary)), false)
    .zipWithIndex
    .filter{ pair =>
      val (employee, index) = pair
      (index < 100)
    } map(_._1)

  //Powrót do formatu CSV i zapis
  topHundred
    .map(employee => Employee(employee.employeeId, employee.name, employee.surname, employee.kpi, employee.salary * 1.1))
    .coalesce(1)
    .toDF()
    .write
    .option("header", "true")
    .format("com.databricks.spark.csv")
    .save("bonus")
}
