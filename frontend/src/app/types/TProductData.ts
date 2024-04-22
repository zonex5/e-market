import {TProductPrice} from "./TProductPrice";
import {TCategoryData} from "./TCategoryData";
import {TAttributes} from "./TAttributes";
import {TProductVariant} from "./TProductVariant";

export interface TProductData {
  id: number
  sku: string
  title: string
  annotation?: string
  description?: string
  thumbnail?: number
  available: boolean
  inWishList?: boolean
  images: number[]
  price?: TProductPrice
  category?: TCategoryData
  attributes: TAttributes[]
  variants: TProductVariant[]
}
