import io.circe._
import io.circe.Json._
import io.circe.generic.semiauto._

object AnimalHolder {
  implicit val codec: Codec[AnimalHolder] = deriveCodec[AnimalHolder]
}

case class AnimalHolder(value: Animal, square: Square)

sealed trait Animal

case class Boar(weight: Int) extends Animal

case class Cat(name: String, weight: Int) extends Animal

object Animal {

  implicit val codec: io.circe.Codec[Animal] = Codec.from(decode, encode)

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

object Square {

  implicit val codec: io.circe.Codec[Square] = deriveCodec[Square]

}

case class Square(a: Int)
