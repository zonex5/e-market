import {Injectable} from '@angular/core';
import {gql} from "apollo-angular";

@Injectable({
  providedIn: 'root'
})
export class QueryService {

  queryAllCategories = gql`
    query GetAllCategories {
      getAllCategories {
        id
        title
        annotation
        active
      }
    }
  `

  queryMenuCategories = gql`
    query activeCategories {
      getActiveCategories {
        id
        title
      }
    }
  `

  queryProductsByCategory = gql`
    query productsByCategory($id: Int!, $sort: SortInput) {
      products: productsByCategory(id: $id, sort: $sort) {
        id
        title
        annotation
        description
        thumbnail
        available
        price {
          oldPrice
          newPrice
          currentPrice
          discount
          sale
        }
      }
    }
  `

  queryFavouritesProducts = gql`
    query favouritesProducts {
      products: favourites {
        id
        title
        annotation
        description
        thumbnail
        available
        price {
          oldPrice
          newPrice
          currentPrice
          discount
          sale
        }
      }
    }
  `

  queryProductsByText = gql`
    query productsByText($text: String!, $sort: SortInput) {
      products: productsByText(text: $text, sort: $sort) {
        id
        title
        annotation
        description
        thumbnail
        available
        price {
          oldPrice
          newPrice
          currentPrice
          discount
          sale
        }
      }
    }
  `

  queryProductsRandom = gql`
    query productsRandom($sort: SortInput) {
      products: productsRandom(sort: $sort) {
        id
        title
        annotation
        description
        thumbnail
        available
        price {
          oldPrice
          newPrice
          currentPrice
          discount
          sale
        }
      }
    }
  `

  queryProductDetails = gql`
    query productById($id: Int!) {
      product: getProductById(id:  $id) {
        id
        sku
        title
        annotation
        description
        available
        images,
        inWishList,
        price {
          oldPrice
          newPrice
          currentPrice
          discount
          sale
        }
        category {
          title
        }
        attributes {
          id
          name
          values
        }
        variants {
          productId
          sku
          inStock
          attributes {
            name
            value
          }
          price {
            oldPrice
            newPrice
            currentPrice
            discount
            sale
          }
        }
      }
    }
  `

  queryCartProducts = gql`
    query GetCustomerCart {
      customerCart : getCustomerCart {
        totalAmount
        savedAmount
        products {
          id
          title
          active
          available
          quantity
          oldPrice
          currentPrice
          sale
          thumbnail
          details
          discount }
      }
    }
  `

  queryCartCount = gql`
    query customerCartCount{
      customerCart: getCustomerCart {
        products {
          id
        }
      }
    }
  `

  queryProductsMainPage = gql`
    query productsMainPage($sort: SortInput) {
      products: productsMainPage(sort: $sort) {
        id
        title
        annotation
        description
        thumbnail
        available
        price {
          oldPrice
          newPrice
          currentPrice
          discount
          sale
        }
      }
    }
  `

  totalProductsCategory = gql`
    query TotalProductsByCategory($id: Int!) {
      totalProducts: totalProductsByCategory(id: $id)
    }
  `

  totalProductsText = gql`
    query TotalProductsByText($text: String!) {
      totalProducts: totalProductsByText(text: $text)
    }
  `

  allLanguages = gql`
    query Languages {
      languages {
        code
        name
        isDefault
      }
    }
  `

  allCountries = gql`
    query Countries {
      countries {
        id
        name
      }
    }
  `

  newOrderData = gql`
    query NewOrderData {
      newOrderData {
        customerData {
          firstName
          lastName
          email
          phone
          countryId
          address
          zip
          city
          save
        }
        amounts {
          subtotal
          shipping
          total
        }
        shippingMethod {
          methodName
          cost
        }
      }
    }
  `
}
