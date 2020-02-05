import cats.effect._
import cats.implicits._

import org.http4s.server.Router
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.syntax.kleisli._

import sttp.tapir._
import sttp.tapir.docs.openapi._
import sttp.tapir.openapi.circe.yaml._
import sttp.tapir.redoc.http4s._
import sttp.tapir.swagger.http4s._
import sttp.tapir.server.http4s._
import org.http4s.HttpRoutes
import sttp.tapir.SchemaType.SProduct

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {
    BlazeServerBuilder[IO]
      .bindHttp(8081, "0.0.0.0")
      .withHttpApp(
        Router(
          "/docs"  -> swaggerUi,
          "/"      -> (AnimalEndpoint.get.toRoutes(handleGet)
                  <+>  AnimalEndpoint.post.toRoutes(handle)
                  <+>  AnimalEndpoint.put.toRoutes(handle)))
          .orNotFound)
      .serve.compile.drain.as(ExitCode.Success)
  }
  private def handleGet(name: String): IO[Either[Unit, Unit]] = IO(Right(()))

  private def handle(holder: AnimalHolder): IO[Either[Unit, Unit]] = IO(Right(()))

  private def redoc: HttpRoutes[IO] = new RedocHttp4s("Animal App", yaml).routes[IO]

  private def swaggerUi: HttpRoutes[IO] = new SwaggerHttp4s(yaml).routes[IO]

  private def openApi = List(AnimalEndpoint.post, AnimalEndpoint.put).toOpenAPI("info", "version")

  private def yaml = openApi.toYaml

  // private def yaml = scala.io.Source.fromResource("test2.yaml").mkString

}
