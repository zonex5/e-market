import {Injectable} from '@angular/core';
import {gql, Mutation} from "apollo-angular";

/*@Injectable({
  providedIn: 'root'
})
export class MutationService {

  constructor() { }

  addToCart = gql`
    mutation addProductToCart($id: Int!, $quantity: Int!) {
      addProductToCart(id:  $id, quantity: $quantity)
    }
  `
}*/

@Injectable({providedIn: 'root'})
export class MutationAddProductToCart extends Mutation {
  override document = gql`
    mutation addProductToCart($id: Int!, $quantity: Int!) {
      addProductToCart(id:  $id, quantity: $quantity)
    }`
}

@Injectable({providedIn: 'root'})
export class MutationProductToWishList extends Mutation {
  override document = gql`
    mutation productToWishList($id: Int!, $flag: Boolean!) {
      productToWishList(id: $id, flag: $flag)
    }`
}

@Injectable({providedIn: 'root'})
export class MutationDeleteProductFromCart extends Mutation {
  override document = gql`
    mutation DeleteProductFromCart($id: Int!) {
      deleteProductFromCart(id: $id)
    }`
}

@Injectable({providedIn: 'root'})
export class MutationUpdateQuantityInCart extends Mutation {
  override document = gql`
    mutation UpdateQuantityInCart($id: Int!, $quantity: Int!) {
      updateQuantityInCart(id: $id, quantity: $quantity)
    }`
}

@Injectable({providedIn: 'root'})
export class MutationFinishOrder extends Mutation {
  override document = gql`
    mutation finishOrder($data: OrderDataInput!) {
      finishOrder(data: $data){
        success
        unavailableProducts
      }
    }`
}
