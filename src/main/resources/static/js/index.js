const catContainer = document.getElementById("categoryContainer");
const catTemplate = document.getElementById("categoryTemplate");
const welcomeTemplate = document.getElementById("welcomeTemplate");
const productTemplate = document.getElementById("productTemplate");
const bestSellersContainer = document.getElementById("bestSellers");
let user;
const welcomeUser = function (userName) {
    while (catContainer.firstChild) {
        catContainer.removeChild(catContainer.firstChild);
    }
    const copy = welcomeTemplate.cloneNode(true).content;
    copy.children[0].children[0].innerText = `Welcome, ${userName}`;
    catContainer.insertChildAtIndex(copy, catContainer.length);
};
const getUser = function () {
    console.log(this);
    if (this.status === 200) {
        user = JSON.parse(this.response);
        localStorage.setItem("user", JSON.stringify(user));
        welcomeUser(user.name);
    }
    if (localStorage.getItem("cats") === null) {
        doRequest("GET", "/products/getAllCategories", readCategories);
    } else {
        const cats = JSON.parse(localStorage.getItem("cats"));
        addToCategoryList(cats);
    }
};
let cats;
const readCategories = function () {
    if (this.status === 200) {
        cats = JSON.parse(this.response);
        localStorage.setItem("cats", JSON.stringify(cats));
        addToCategoryList(cats);
    }
};

function addToCategoryList(cats) {
    for (const cat of cats) {
        const copy = catTemplate.cloneNode(true).content;
        copy.children[0].children[0].innerText = cat
        copy.children[0].children[0].href = `/products/${cat}`;
        catContainer.insertChildAtIndex(copy, 0);
    }
}

if (localStorage.getItem("user") === null) {
    console.log("null user")
    doRequest("GET", "/loggedInUser", getUser);
} else {
    const user = JSON.parse(localStorage.getItem("user"));
    welcomeUser(user.name);
    if (localStorage.getItem("cats") === null) {
        doRequest("GET", "/products/getAllCategories", readCategories);
    } else {
        const cats = JSON.parse(localStorage.getItem("cats"));
        addToCategoryList(cats);
    }
}

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