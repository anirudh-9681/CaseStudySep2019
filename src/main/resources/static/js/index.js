const productTemplate = document.getElementById("productTemplate");
const bestSellersContainer = document.getElementById("bestSellers");

const bestSellersUpdate = function () {
    if (this.status === 200) {
        const products = JSON.parse(this.response);
        addToBestSellers(products);
    }
};

function addToBestSellers(products) {
    for (const product of products) {
        const copy = productTemplate.cloneNode(true).content;
        copy.children[0].children[0].href = `${location.host}/getProduct/${product.productId}`;
        copy.children[0].children[0].children[1].innerText = product.name;
        copy.children[0].children[0].children[2].innerText = `Rs. ${product.price}`;
        copy.children[0].children[0].children[3].innerText = product.details;
        bestSellersContainer.appendChild(copy);
    }
}

doRequest("GET", "/products", bestSellersUpdate);