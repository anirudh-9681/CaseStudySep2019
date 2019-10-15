const catContainer = document.getElementById("categoryContainer");
const catTemplate = document.getElementById("categoryTemplate");
const welcomeTemplate = document.getElementById("welcomeTemplate");
let user;
const welcomeUser = function (userName) {
    while (catContainer.firstChild) {
        catContainer.removeChild(catContainer.firstChild);
    }
    const copy = welcomeTemplate.cloneNode(true).content;
    copy.children[0].children[0].innerText = `Welcome, ${userName}`;
    catContainer.insertChildAtIndex(copy, catContainer.length);
};
const getUserName = function () {
    if (this.status === 200) {
        user = JSON.parse(this.response);
        welcomeUser(user.name);
    }
    doRequest("GET", "/products/getAllCategories", readCategories);
};
doRequest("GET", "/loggedInUserName", getUserName);

let cats;
const readCategories = function () {
    if (this.status === 200) {
        cats = JSON.parse(this.response);
        for (cat of cats) {
            addToCategoryList(cat);
        }
    }
};

function addToCategoryList(cat) {
    const copy = catTemplate.cloneNode(true).content;
    copy.children[0].children[0].innerText = cat
    copy.children[0].children[0].href = `/products/${cat}`;
    catContainer.insertChildAtIndex(copy, 0);
}

