const orderTemplate = document.getElementById("orderTemplate");
const orderItemTemplate = document.getElementById("orderItemTemplate");
const orderContainer = document.getElementById("orderContainer");
const user = JSON.parse(localStorage.getItem("user"));

function showNoOrders() {
    const noOrders = document.getElementById("noOrders").cloneNode(true).content;
    orderContainer.appendChild(noOrders);
}

function fillOrders(orders) {
    for (const order of orders) {
        const orderCopy = orderTemplate.cloneNode(true).content;
        orderCopy.children[0].children[0].innerText = `OrderId: ${order.orderId}`;
        for (const orderItem of order.products) {
            const orderItemCopy = orderItemTemplate.cloneNode(true).content;
            orderItemCopy.children[0].children[1].children[0].innerText = orderItem.product.name;
            orderItemCopy.children[0].children[1].children[1].innerText = orderItem.product.price;
            orderItemCopy.children[0].children[1].children[2].innerText = `Quantity: ${orderItem.quantity}`;
            orderCopy.children[0].children[1].appendChild(orderItemCopy);
        }
        orderContainer.appendChild(orderCopy);
    }
}

const orderProcessor = function () {
    if (this.status === 200) {
        const orders = JSON.parse(this.response);
        if (orders.length === 0) {
            showNoOrders();
        } else {
            fillOrders(orders);
        }
    } else if (this.status === 401) {
        location.pathname = "/login";
    } else {
        alert("Something went wrong, try later or contact support");
    }
};

if (user) {
    doRequest("GET", `/order/${user.userId}/getOrders`, orderProcessor);
} else {
    location.pathname = "/login";
}