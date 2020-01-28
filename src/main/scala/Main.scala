import cats.effect._
import cats.implicits._

import org.http4s.server.Router
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.syntax.kleisli._

import sttp.tapir._
import sttp.tapir.docs.openapi._
import sttp.tapir.openapi.circe.yaml._
import sttp.tapir.redoc.http4s._
import sttp.tapir.server.http4s._
import org.http4s.HttpRoutes

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {
    println(yaml)
    BlazeServerBuilder[IO]
      .bindHttp(8081, "0.0.0.0")
      .withHttpApp(Router("/" -> docs).orNotFound)
      .serve.compile.drain.as(ExitCode.Success)
  }

  private def docs: HttpRoutes[IO] = new RedocHttp4s("Animal App", yaml).routes[IO]

  private def yaml = (AnimalEndpoint.post :: Nil).toOpenAPI("info", "version").toYaml

  // private def yaml = scala.io.Source.fromResource("test.yaml").mkString

}
