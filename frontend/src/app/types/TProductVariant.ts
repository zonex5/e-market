import {TProductPrice} from "./TProductPrice";
import {TAttribute} from "./TAttribute";

export type TProductVariant = {
  productId: number
  inStock: boolean
  price: TProductPrice
  attributes: TAttribute[]
}
