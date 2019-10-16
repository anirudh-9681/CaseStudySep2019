const catContainer = document.getElementById("categoryContainer");
const catTemplate = document.getElementById("categoryTemplate");
const welcomeTemplate = document.getElementById("welcomeTemplate");

const welcomeUser = function (userName) {
    while (catContainer.firstChild) {
        catContainer.removeChild(catContainer.firstChild);
    }
    const copy = welcomeTemplate.cloneNode(true).content;
    copy.children[0].children[0].innerText = `Welcome, ${userName}`;
    catContainer.insertChildAtIndex(copy, catContainer.length);
};

const readCategories = function () {
    if (this.status === 200) {
        const cats = JSON.parse(this.response);
        localStorage.setItem("cats", JSON.stringify(cats));
        addToCategoryList(cats);
    }
};

function addToCategoryList(cats) {
    for (const cat of cats) {
        const copy = catTemplate.cloneNode(true).content;
        copy.children[0].children[0].innerText = cat;
        copy.children[0].children[0].href = `/products/${cat}`;
        catContainer.insertChildAtIndex(copy, 0);
    }
}

const getUser = function () {
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


if (localStorage.getItem("user") === null) {
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