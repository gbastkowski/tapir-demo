import org.scalatest.funsuite.AnyFunSuite

import sttp.tapir._
import sttp.tapir.SchemaType
import sttp.tapir.generic.Derived
import sttp.tapir.model._
import sttp.tapir.openapi.circe.yaml._

class AnimalEndpointSpec extends AnyFunSuite {

  test("customCat.schemaType") {
    assert(AnimalEndpoint.customCat.schemaType.isInstanceOf[SchemaType.SProduct])
  }

  test("customCat.weight") {
    val weightSchema = AnimalEndpoint.customCat.schemaType.asInstanceOf[SchemaType.SProduct].fields.collectFirst {
      case ("weight", schema) => schema.schemaType
    }
    assert(weightSchema.get == SchemaType.SString)
  }

}
