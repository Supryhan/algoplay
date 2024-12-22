package servers.http4s.models

trait Itema

//import cats.implicits.toBifunctorOps
//import derevo.cats.show
//import derevo.{Derivation, NewTypeDerivation, derive}
//import eu.timepit.refined.api.{Refined, Validate}
//import eu.timepit.refined.auto.autoUnwrap
//import eu.timepit.refined.refineV
//import eu.timepit.refined.types.string.NonEmptyString
//import io.circe.`export`.Exported
//import io.circe.refined._
//import io.circe.{Decoder, Encoder}
//import io.estatico.newtype.macros.newtype
//import org.http4s.{EntityEncoder, ParseFailure}
//import squants.market.Money
//
//import java.util.UUID
//import scala.annotation.implicitNotFound
//
//object Itema {
//  trait Itemas[F[_]] {
//    def findAll: F[ItemCC] //F[List[Item]]
//    def findBy(brand: BrandName): F[ItemCC]//F[List[Item]]
////    def findById(itemId: ItemId): F[Option[Item]]
////    def create(item: CreateItem): F[ItemId]
////    def update(item: UpdateItem): F[Unit]
//  }
//
//  object Itemas {
//    def make[F[_]]: Itemas[F] =
//      new Itemas[F] {
//        def findAll: F[ItemCC] = ???
//        def findBy(brand: BrandName): F[ItemCC] = ???
//      }
//  }
//
////  @derive(queryParam, show)
//  @newtype case class BrandParam(value: NonEmptyString) {
//    def toDomain: BrandName =
//      BrandName(value.toLowerCase.capitalize)
//  }
//
//  object BrandParam {
//    implicit val jsonEncoder: Encoder[BrandParam] =
//      Encoder.forProduct1("name")(_.value)
//
//    implicit val jsonDecoder: Decoder[BrandParam] =
//      Decoder.forProduct1("name")(BrandParam.apply)
//  }
//
//  trait Derive[F[_]] extends Derivation[F] with NewTypeDerivation[F] {
//    {
//      def instance(implicit ev: OnlyNewtypes): Nothing = ev.absurd
//
//      @implicitNotFound("Only newtypes instances can be derived")
//      abstract final class OnlyNewtypes {
//        def absurd: Nothing = ???
//      }
//    }
//  }
//
//  import org.http4s.QueryParamDecoder
//  object queryParam extends Derive[QueryParamDecoder]
//
//  implicit val qpd: QueryParamDecoder[BrandParam] = ???
//  implicit val ee: Exported[Encoder[ItemCC]] = ???
//
//  implicit def refinedParamDecoder[T: QueryParamDecoder, P](implicit
//                                                            ev: Validate[T, P]): QueryParamDecoder[T Refined P] =
//    QueryParamDecoder[T]
//      .emap(refineV[P](_)
//        .leftMap(m => ParseFailure(m, m)))
//
//
//  @newtype case class ItemId(value: UUID)
//  @newtype case class ItemName(value: String)
//  @newtype case class ItemDescription(value: String)
//  @newtype case class Brand(value: BrandName)
//  @newtype case class BrandName(value: String)
//  @newtype case class Category(value: String)
//  @newtype case class CreateItem(value: ItemCC)
//  @newtype case class UpdateItem(value: ItemCC)
//
//  case class ItemCC(
//                   uuid: ItemId,
//                   name: ItemName,
//                   description: ItemDescription,
//                   price: Money,
//                   brand: Brand,
//                   category: Category
//                 )
//
//
//}
