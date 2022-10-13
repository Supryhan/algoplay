package servers.http4s.models

import cats.implicits.toBifunctorOps
import eu.timepit.refined.api.{Refined, Validate}
import eu.timepit.refined.auto.autoUnwrap
import eu.timepit.refined.refineV
import eu.timepit.refined.types.string.NonEmptyString
import io.estatico.newtype.macros.newtype
import org.http4s.{ParseFailure, QueryParamDecoder}
import squants.market.Money

import java.util.UUID

object Item {
  trait Items[F[_]] {
    def findAll: F[List[Item]]
    def findBy(brand: BrandName): F[List[Item]]
    def findById(itemId: ItemId): F[Option[Item]]
    def create(item: CreateItem): F[ItemId]
    def update(item: UpdateItem): F[Unit]
  }

  @newtype case class BrandParam(value: NonEmptyString) {
    def toDomain: BrandName =
      BrandName(value.toLowerCase.capitalize)
  }
  implicit def refinedParamDecoder[T: QueryParamDecoder, P](implicit
                                                            ev: Validate[T, P]): QueryParamDecoder[T Refined P] =
    QueryParamDecoder[T]
      .emap(refineV[P](_)
        .leftMap(m => ParseFailure(m, m)))


  @newtype case class ItemId(value: UUID)
  @newtype case class ItemName(value: String)
  @newtype case class ItemDescription(value: String)
  @newtype case class Brand(value: BrandName)
  @newtype case class BrandName(value: String)
  @newtype case class Category(value: String)
  @newtype case class CreateItem(value: Item)
  @newtype case class UpdateItem(value: Item)

  case class Item(
                   uuid: ItemId,
                   name: ItemName,
                   description: ItemDescription,
                   price: Money,
                   brand: Brand,
                   category: Category
                 )


}
