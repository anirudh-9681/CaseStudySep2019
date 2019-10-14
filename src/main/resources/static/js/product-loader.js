Element.prototype.insertChildAtIndex = function (child, index) {
    if (!index) index = 0
    if (index >= this.children.length) {
        this.appendChild(child)
    } else {
        this.insertBefore(child, this.children[index])
    }
}

const catContainer = document.getElementById("categoryContainer");
const catTemplate = document.getElementById("categoryTemplate");
let cats = [];
const readCategories = function () {
    if (this.status == 200) {
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

doRequest("GET","products/getAllCategories",readCategories);