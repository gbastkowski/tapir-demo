import io.circe._
import sttp.model.StatusCode._
import sttp.tapir._
import sttp.tapir.docs.openapi._
import sttp.tapir.json.circe._
import sttp.tapir.model._
import sttp.tapir.openapi.circe.yaml._
import sttp.tapir.generic.Derived

object AnimalEndpoint extends TapirJsonCirce with SchemaDocumentation {

  def get: Endpoint[String, Unit, Unit, Nothing] = endpoint
    .get
    .in("animals")
    .in(path[String].name("name"))
    .out(statusCode(Ok))

  def post: Endpoint[AnimalHolder, Unit, Unit, Nothing] = endpoint
    .post
    .in("animals")
    .in(jsonBody[AnimalHolder])
    .out(statusCode(Created))

  def put: Endpoint[AnimalHolder, Unit, Unit, Nothing] = endpoint
    .put
    .in("animals")
    .in(jsonBody[AnimalHolder])
    .out(statusCode(Ok))
}

trait SchemaDocumentation {
  implicit lazy val customAnimalHolder: Schema[AnimalHolder] = implicitly[Derived[Schema[AnimalHolder]]].value
    .modify(_.value)(_.description("the value"))

  implicit lazy val customCat: Schema[Cat] = implicitly[Derived[Schema[Cat]]].value
    .modify(_.name)(_.description("Cats are pets and therefore should have a name"))

  implicit lazy val customSquare: Schema[Square] = implicitly[Derived[Schema[Square]]].value
}

