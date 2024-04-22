export type TCartProductItem = {
  id: number
  title: string
  active: boolean
  available: boolean
  quantity: number
  oldPrice: number
  currentPrice: number
  sale: boolean
  details?: string
  discount: number
  thumbnail?: number

  totalAmount?: number
}
