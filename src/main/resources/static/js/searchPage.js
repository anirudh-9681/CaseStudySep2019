const productCard = document.getElementById("productCard");
const productContainer = document.getElementById("productContainer");
const url = new URL(location);
let searchString = url.searchParams.get("searchString");
let products_list;

function applyFilter() {

    const radios = document.getElementsByName("category");
    let category;
    for(const radio of radios){
        if (radio.checked){
            category = radio.value;
            break;
        }
    }
    if (!category){
        return;
    }

    const filters = {};
    const minPrice = document.getElementById("minPrice").value;
    const maxPrice = document.getElementById("maxPrice").value;

    if (Number(minPrice)){
        filters.minPrice = minPrice;
    }
    if (Number(maxPrice)){
        filters.maxPrice = maxPrice;
    }
    const subs = document.getElementsByName("subcategory");
    for(const sub of subs){
        if (sub.checked){
            if(!filters.subcategory){
                filters.subcategory ="[";
            }
            filters.subcategory += sub.value + ",";
        }
    }
    filters.subcategory = filters.subcategory.slice(0,filters.subcategory.length-1);
    doRequest("POST",`/products/${category}/getFilteredProducts`,searchProcessor,filters);
}

function fillProducts(products_list){
    while(productContainer.firstChild){
        productContainer.removeChild(productContainer.firstChild);
    }

    for (const product of products_list) {
        const copy = productCard.cloneNode(true).content;
        copy.children[0].setAttribute("onclick",`redirect(${product["productId"]})`);
        copy.children[0].children[1].children[0].innerText = product["name"];
        copy.children[0].children[1].children[1].innerText = product["price"];
        copy.children[0].children[1].children[2].innerText = product["details"];
        productContainer.appendChild(copy);
    }
}

function redirect(id) {
    const url = new URL(location);
    url.pathname = `products/productPage`;
    url.searchParams.set("productId",id);
    location.href = url.href;
}

const searchProcessor = function(){
    if(this.status === 200){
        products_list = JSON.parse(this.response);
        fillProducts(products_list);
    }
};

function doSearch() {
    const str = document.getElementById("searchBar").value.trim();
    if(str){
        doRequest("GET",`/products/search/${str}`, searchProcessor);
    }
}
if(searchString){
    doRequest("GET",`/products/search/${searchString}`, searchProcessor);
}