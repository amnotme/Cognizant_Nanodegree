/* Create an array named products which you will use to add all of your product object literals that you create in the
next step. */
let products = [];
/* Create 3 or more product objects using object literal notation
   Each product should include five properties
   - name: name of product (string)
   - price: price of product (number)
   - quantity: quantity in cart should start at zero (number)
   - productId: unique id for the product (number)
   - image: picture of product (url string)
*/

let cherry = {
    "name": "cherry",
    "price": 10.0,
    "quantity": 0,
    "productId": 1001,
    "image": "../images/cherry.jpg"
};
let orange = {
    "name": "orange",
    "price": 11.0,
    "quantity": 0,
    "productId": 1002,
    "image": "../images/orange.jpg"
};
let strawberry = {
    "name": "strawberry",
    "price": 12.0,
    "quantity": 0,
    "productId": 1003,
    "image": "../images/strawberry.jpg"
};
products.push(cherry, orange, strawberry);

/* Images provided in /images folder. All images from Unsplash.com
   - cherry.jpg by Mae Mu
   - orange.jpg by Mae Mu
   - strawberry.jpg by Allec Gomes
*/

/*
    Helper functions:
*/

// Function to find the index in an array that contains an object with a given product Id
function findProductIndex(productId, array) {
    return array.findIndex(product => product.productId === productId);
};

// Function to add or subtract quantity as well as remove an object from an array if quantity is zero.
function modifyProductQuantity(productId, change) {
    const index = findProductIndex(productId, cart);
    if (index !== -1) {
        cart[index].quantity += change;
        if (cart[index].quantity <= 0) {
            cart.splice(index, 1);
        }
    }
}

/* Declare an empty array named cart to hold the items in the cart */
let cart = [];
/* Create a function named addProductToCart that takes in the product productId as an argument
  - addProductToCart should get the correct product based on the productId
  - addProductToCart should then increase the product's quantity
  - if the product is not already in the cart, add it to the cart
*/
function addProductToCart(productId) {

    let productIdIndex = findProductIndex(productId, products);
    if (productIdIndex > -1) {
        products[productIdIndex].quantity += 1;
        let inCartIndex = findProductIndex(productId, cart);

        if (inCartIndex === -1) {
            cart.push(products[productIdIndex])
        }
    }
};


/* Create a function named increaseQuantity that takes in the productId as an argument
  - increaseQuantity should get the correct product based on the productId
  - increaseQuantity should then increase the product's quantity
*/

function increaseQuantity(productId) {
    modifyProductQuantity(productId, 1);
};
/* Create a function named decreaseQuantity that takes in the productId as an argument
  - decreaseQuantity should get the correct product based on the productId
  - decreaseQuantity should decrease the quantity of the product
  - if the function decreases the quantity to 0, the product is removed from the cart
*/

function decreaseQuantity(productId) {
    modifyProductQuantity(productId, -1);
};

/* Create a function named removeProductFromCart that takes in the productId as an argument
  - removeProductFromCart should get the correct product based on the productId
  - removeProductFromCart should update the product quantity to 0
  - removeProductFromCart should remove the product from the cart
*/

function removeProductFromCart(productId) {
    const index = findProductIndex(productId, cart);
    if (index !== -1) {
        cart[index].quantity = 0;
        cart.splice(index, 1);
    }
};

/* Create a function named cartTotal that has no parameters
  - cartTotal should iterate through the cart to get the total cost of all products
  - cartTotal should return the total cost of the products in the cart
  Hint: price and quantity can be used to determine total cost
*/

function cartTotal() {
    let total = 0.0;
    for (let i = 0; i < cart.length; i++) {
        total += cart[i].price * cart[i].quantity;
    }
    return total;
};

/* Create a function called emptyCart that empties the products from the cart */
function emptyCart() {
    for (let i = 0; i < cart.length; i++) {
        cart[i].quantity = 0;
    }
    cart.splice(0);
};
/* Create a function named pay that takes in an amount as an argument
  - amount is the money paid by customer
  - pay will return a negative number if there is a remaining balance
  - pay will return a positive number if money should be returned to customer
  Hint: cartTotal function gives us cost of all the products in the cart
*/

function pay(amount) {
    return amount - cartTotal();
}
/* Place stand out suggestions here (stand out suggestions can be found at the bottom of the project rubric.)*/


/* The following is for running unit tests.
   To fully complete this project, it is expected that all tests pass.
   Run the following command in terminal to run tests
   npm run test
*/

module.exports = {
   products,
   cart,
   addProductToCart,
   increaseQuantity,
   decreaseQuantity,
   removeProductFromCart,
   cartTotal,
   pay,
   emptyCart,
   /* Uncomment the following line if completing the currency converter bonus */
   // currency
}