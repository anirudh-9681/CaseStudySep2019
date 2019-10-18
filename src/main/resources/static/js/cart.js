const cartItemContainer = document.getElementById("cartItemContainer");
const cartItemTemplate = document.getElementById("cartItemTemplate");

function showEmptyCart() {
    const emptyCart = document.getElementById("emptyCart").cloneNode(true).content;
    cartItemContainer.appendChild(emptyCart);
}

function fillCart(cartItems) {
    let cartTotal = 0;
    for (const cartItem of cartItems) {
        const copy = cartItemTemplate.cloneNode(true).content;
        copy.children[0].children[1].children[0].innerText = cartItem.product.name;
        copy.children[0].children[1].children[1].innerText = cartItem.product.price;
        copy.children[0].children[1].children[3].value = cartItem.quantity;
        copy.children[0].children[1].children[3].setAttribute("id", `cartItemQty${cartItem.cartItemId}`);
        copy.children[0].children[1].children[4].setAttribute("onclick", `changeQty(${cartItem.cartItemId})`);
        copy.children[0].children[1].children[4].setAttribute("id", `buttonQty${cartItem.cartItemId}`);
        copy.children[0].children[2].children[0].setAttribute("onclick", `removeCartItem(${cartItem.product.productId})`);
        cartItemContainer.appendChild(copy);
        cartTotal += cartItem.quantity * cartItem.product.price;
    }
    document.getElementById("cartTotal").innerText = cartTotal;
}

const cartUpdateProcessor = function () {
    if (this.status === 200) {
        location.reload();
    } else if (this.status === 401) {
        location.pathname = "/login";
    } else {
        alert("Something went wrong. Please try later or contact support");
    }
};

function changeQty(cartItemId) {
    const qtyInput = document.getElementById(`cartItemQty${cartItemId}`);
    if (qtyInput.disabled) {
        qtyInput.disabled = false;
        document.getElementById(`buttonQty${cartItemId}`).innerText = "Save";
    } else {

        qtyInput.disabled = true;
        let obj = {};
        obj.quantity = qtyInput.value;
        if(Number(qtyInput)<=0){
            alert("Quantity cannot be zero");
            return;
        }
        doRequest("POST", `/cart/changeQuantity/${cartItemId}`, cartUpdateProcessor, obj);
    }
}

function removeCartItem(productId) {
    const user = JSON.parse(localStorage.getItem("user"));
    if (user) {
        doRequest("GET", `/cart/${user.userId}/remove/${productId}`, cartUpdateProcessor);
    } else {
        location.pathname = "/login";
    }
}

const cartProcessor = function () {
    if (this.status === 200) {
        const cartItems = JSON.parse(this.response).products;
        if (cartItems.length === 0) {
            showEmptyCart();
        } else {
            fillCart(cartItems);
        }
    }
};
if (localStorage.getItem("user")) {
    const user = JSON.parse(localStorage.getItem("user"));
    doRequest("GET", `/cart/${user.userId}/getCart`, cartProcessor);
}

const checkOrder = function () {
    if (this.status === 200) {
        alert("Order has been placed");
        location.reload();
    } else if (this.status === 401) {
        location.pathname = "/login";
    } else {
        alert("Something went wrong. Please try later or contact support");
    }
};

function doCheckOut() {
    const user = JSON.parse(localStorage.getItem("user"));
    if (user) {
        doRequest("GET", `/order/${user.userId}/createOrder`, checkOrder);
    } else {
        location.pathname = "/login";
    }

}