Element.prototype.insertChildAtIndex = function (child, index) {
    if (!index) index = 0
    if (index >= this.children.length) {
        this.appendChild(child)
    } else {
        this.insertBefore(child, this.children[index])
    }
}

var catContainer = document.getElementById("categoryContainer");
var catTemplate = document.getElementById("categoryTemplate");
var cats = ["Anime", "Video Game", "Comics", "Animal", "Profession"];
for (const cat of cats) {
    addToCategoryList(cat);
}
function addToCategoryList(cat) {
    var copy = catTemplate.cloneNode(true).content;
    copy.children[0].children[0].innerText = cat
    copy.children[0].children[0].href = `/category/${cat}`;
    catContainer.insertChildAtIndex(copy, 0);
}