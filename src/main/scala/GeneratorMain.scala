import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
import org.scalacheck.Gen

/**
  * Created by Zielony on 2016-11-04.
  */
object GeneratorMain extends App {

  val sparkContext = new SparkContext(
    new SparkConf().
      setMaster("local[*]").
      setAppName("Generator")
  )

  val sqlContext = new SQLContext(sparkContext)

  var data: Option[List[Employee]] = None;

  while(data == None) {
    data = Generators.employeeGenerator(100000).sample
  }

  import sqlContext.implicits._

  sparkContext.parallelize(data.get)
    .toDF()
    .coalesce(1)
    .write
    .option("header", "true")
    .format("com.databricks.spark.csv")
    .save("data")
}
