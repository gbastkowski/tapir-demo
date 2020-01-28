import io.circe._
import sttp.model.StatusCode._
import sttp.tapir._
import sttp.tapir.docs.openapi._
import sttp.tapir.json.circe._
import sttp.tapir.model._
import sttp.tapir.openapi.circe.yaml._
import sttp.tapir.generic.Derived

object AnimalEndpoint extends TapirJsonCirce  {

  def post: Endpoint[AnimalHolder, Unit, Unit, Nothing] = endpoint
    .post
    .in(jsonBody[AnimalHolder])
    .out(statusCode(Created))

}

trait SchemaDocumentation {
  implicit lazy val customAnimalHolder: Schema[AnimalHolder] = implicitly[Derived[Schema[AnimalHolder]]].value
    .modify(_.value)(_.description("the value"))

  implicit lazy val customCat: Schema[Cat] = implicitly[Derived[Schema[Cat]]].value
    .modify(_.name)(_.description("Cats are pets and therefore should have a name"))
}

