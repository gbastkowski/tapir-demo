import io.circe._
import io.circe.Json._
import io.circe.generic.semiauto._

object AnimalHolder {

  implicit val codec: Codec[AnimalHolder] = Codec.from(decode, encode)

  private def decode(c: HCursor) = for {
    animalType  <-  c.downField("value").downField("type").as[String]
    value       <-  animalType match {
                      case "Boar" => c.downField("value").as[Boar]
                      case "Cat"  => c.downField("value").as[Cat]
                    }
    square      <- c.downField("square").as[Square]
  } yield AnimalHolder(value, square)

  private def encode(h: AnimalHolder) = obj(
    "value" -> {
      h.value match {
        case b: Boar => Animal.boarCodec(b)
        case c: Cat => Animal.catCodec(c)
      }
    },
    "square" -> Square.codec(h.square)
  )
}
object AnimalHolder2 {
  implicit val codec: Codec[AnimalHolder2] = Codec.from(decode2, encode2)
  private def decode2(c: HCursor) = for {
    animalType  <-  c.downField("value").downField("type").as[String]
    value       <-  animalType match {
                      case "Boar" => c.downField("value").as[Boar]
                      case "Cat"  => c.downField("value").as[Cat]
                    }
    square      <- c.downField("square").as[Square]
  } yield AnimalHolder2(value, square)

  private def encode2(h: AnimalHolder2) = obj(
    "value" -> {
      h.value match {
        case b: Boar => Animal.boarCodec(b)
        case c: Cat => Animal.catCodec(c)
      }
    },
    "square" -> Square.codec(h.square)
  )

}

case class AnimalHolder2(value: Animal, square: Square)

case class AnimalHolder(value: Animal, square: Square)

sealed trait Animal

case class Boar(weight: BigDecimal) extends Animal

case class Cat(name: String, weight: BigDecimal) extends Animal

object Animal {

  // implicit val codec: io.circe.Codec[Animal] = Codec.from(decode, encode)

  implicit val catCodec: io.circe.Codec[Cat] = deriveCodec[Cat]
  implicit val boarCodec: io.circe.Codec[Boar] = deriveCodec[Boar]

  private def decode(c: HCursor) = {
    c.downField("type").as[String].flatMap {
      case "Boar" => deriveDecoder[Boar].apply(c)
      case "Cat"  => deriveDecoder[Cat].apply(c)
    }
  }

  private def encode(a: Animal) = a match {
    case b: Boar => deriveEncoder[Boar].apply(b)
    case c: Cat => deriveEncoder[Cat].apply(c)
  }
}


sealed trait Shape

object Square {

  implicit val codec: io.circe.Codec[Square] = deriveCodec[Square]

}

case class Square(a: Int) extends Shape
