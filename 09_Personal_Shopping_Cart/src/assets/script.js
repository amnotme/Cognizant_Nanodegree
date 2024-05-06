// Initialize an array to store product objects.
let products = [];

// Define product objects with properties like name, price, quantity, product ID, and image URL.
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
// Add these product objects to the products array.
products.push(cherry, orange, strawberry);

// Function to find a product's index in an array by its product ID.
function findProductIndex(productId, array) {
    return array.findIndex(product => product.productId === productId);
}

// Function to modify the quantity of a product in the cart.
function modifyProductQuantity(productId, change) {
    const index = findProductIndex(productId, cart); // Find the index of the product in the cart.
    if (index !== -1) { // Check if the product is found.
        cart[index].quantity += change; // Modify the quantity.
        if (cart[index].quantity <= 0) { // If quantity falls to zero or less, remove the product.
            cart.splice(index, 1);
        }
    }
}

// Initialize an array to store cart items.
let cart = [];

// Function to add a product to the cart.
function addProductToCart(productId) {
    let productIdIndex = findProductIndex(productId, products); // Find the product in the products array.
    if (productIdIndex > -1) { // If found, proceed.
        products[productIdIndex].quantity += 1; // Increment the product's quantity in the main array.
        let inCartIndex = findProductIndex(productId, cart); // Check if the product is already in the cart.

        if (inCartIndex === -1) { // If not in cart, add it.
            cart.push(products[productIdIndex]);
        }
    }
}

// Function to increase the quantity of a product in the cart.
function increaseQuantity(productId) {
    modifyProductQuantity(productId, 1);
}

// Function to decrease the quantity of a product in the cart.
function decreaseQuantity(productId) {
    modifyProductQuantity(productId, -1);
}

// Function to completely remove a product from the cart.
function removeProductFromCart(productId) {
    const index = findProductIndex(productId, cart); // Find the product in the cart.
    if (index !== -1) { // If found, proceed.
        cart[index].quantity = 0; // Set quantity to zero.
        cart.splice(index, 1); // Remove the product from the cart.
    }
}

// Function to calculate the total cost of items in the cart.
function cartTotal() {
    let total = 0.0;
    for (let i = 0; i < cart.length; i++) { // Iterate through cart items.
        total += cart[i].price * cart[i].quantity; // Multiply price by quantity and add to total.
    }
    return total;
}

// Initialize variables to track payment and remaining amount.
let remainingAmount = 0.0;
let totalPaid = 0.0;

// Function to empty the cart.
function emptyCart() {
    for (let i = 0; i < cart.length; i++) { // Reset quantity for each item in the cart.
        cart[i].quantity = 0;
    }
    cart.splice(0); // Clear the cart array.
    totalPaid = 0.0; // Reset total paid.
}

// Function to handle payment transactions.
function pay(amount) {
    totalPaid += amount; // Add the paid amount to total paid.
    remainingAmount = totalPaid - cartTotal(); // Calculate remaining amount after deducting the cart total.
    if (remainingAmount >= 0) { // Check if payment covers the total cost.
        emptyCart(); // If yes, empty the cart.
    }
    return remainingAmount; // Return the remaining amount (negative or positive).
}

// Exporting functions and variables for use in other parts of the application or for testing.
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
   // Uncomment the following line if completing the currency converter bonus
   // currency
}
