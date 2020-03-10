scalaVersion :=  "2.13.1"
mainClass := Some("Main")

val Log4jVersion     = "2.13.0"
val TapirVersion     = "0.12.19"
val ScalatestVersion = "3.1.1"

libraryDependencies := Seq(
  "com.softwaremill.sttp.tapir"   %% "tapir-core"                % TapirVersion,
  "com.softwaremill.sttp.tapir"   %% "tapir-http4s-server"       % TapirVersion,
  "com.softwaremill.sttp.tapir"   %% "tapir-json-circe"          % TapirVersion,
  "com.softwaremill.sttp.tapir"   %% "tapir-openapi-circe-yaml"  % TapirVersion,
  "com.softwaremill.sttp.tapir"   %% "tapir-openapi-docs"        % TapirVersion,
  "com.softwaremill.sttp.tapir"   %% "tapir-redoc-http4s"        % TapirVersion,
  "com.softwaremill.sttp.tapir"   %% "tapir-swagger-ui-http4s"   % TapirVersion,
  "org.apache.logging.log4j"       % "log4j-api"                 % Log4jVersion,
  "org.apache.logging.log4j"       % "log4j-core"                % Log4jVersion,
  "org.apache.logging.log4j"       % "log4j-slf4j-impl"          % Log4jVersion,
  "org.scalatest"                 %% "scalatest"                 % ScalatestVersion)
