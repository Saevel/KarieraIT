import org.scalacheck.Gen

/**
  * Created by Zielony on 2016-11-04.
  */
object Generators {

  private val nameGenerator: Gen[String] = Gen.oneOf("Anne", "Alan", "Ben", "Barbara", "Cedric", "Cecilia", "Dominic",
    "Daphne", "Edward", "Emily", "Frank", "Fanny", "George", "Gabriela", "Hubert", "Hannah", "Ian", "Ida", "James",
  "Jane", "Christopher", "Clarice", "Lenny", "Leyla", "Mark", "Madeleine", "Nick", "Nora", "Osmund", "Oprah", "Peter",
  "Penny", "Robert", "Rhonda", "Steven", "Sandra", "Travis", "Tamara", "Vincent", "Viola", "Walter", "Wendy", "Zoe");

  private val surnameGenerator: Gen[String] = Gen.oneOf("Baldwin", "Frank", "Lamoureux", "Gordon", "Diggory", "Greene",
  "Blunt", "Wesley", "Kelly", "Bond", "Doe", "Hitchens", "Stirling", "Kravitz", "Thicke", "Pan", "Laurie", "Black",
  "White")

  private val kpiGenerator: Gen[Double] = Gen.choose(0.0, 100.0)

  private val salaryGenerator: Gen[Double] = Gen.choose(1000.0, 10000.0)

  def employeeGenerator(count: Int): Gen[List[Employee]] = Gen.listOfN(count, for{
    name <- nameGenerator
    surname <- surnameGenerator
    kpi <- kpiGenerator
    salary <- salaryGenerator
    id <- Gen.choose(0, count*count)
  } yield Employee(id.toString, name, surname, kpi, salary))
}
