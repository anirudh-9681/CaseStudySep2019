const productCard = document.getElementById("productCard");
const productContainer = document.getElementById("productContainer");
const url = new URL(location);
let searchString = url.searchParams.get("searchString");
let category = url.searchParams.get("category");
let products_list;

function applyFilter() {

    const filters = {};
    const str = document.getElementById("searchBar").value.trim();
    if (str){
        filters.name = str;
    }
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
    if (filters.subcategory){
        filters.subcategory = filters.subcategory.slice(1,filters.subcategory.length-1);
    }
    doRequest("POST",`/products/${category}/getFilteredProducts`,searchProcessor,filters);
}

function fillProducts(products_list){
    while(productContainer.firstChild){
        productContainer.removeChild(productContainer.firstChild);
    }

    for (const product of products_list) {
        const copy = productCard.cloneNode(true).content;
        copy.children[0].children[1].children[0].innerText = product["name"];
        copy.children[0].children[1].children[1].innerText = product["price"];
        copy.children[0].children[1].children[2].innerText = product["details"];
        copy.children[0].children[2].children[0].setAttribute("onclick",`addToCart(${product["productId"]})`);
        copy.children[0].children[2].children[0].setAttribute("id",`product${product["productId"]}`);
        productContainer.appendChild(copy);
    }
}

function fillSubcategories(products_list){
    let subCats = [];
    for(const product of products_list){
        let subs = product["subcategory"];
        subs = subs.slice(1,subs.length -1);
        for (const sub of subs.split(",")){
            subCats.push(sub.trim());
        }
    }
    const subCatSet = [...new Set(subCats)];
    const subCatTemplate = document.getElementById("subCatTemplate");
    const subCatContainer = document.getElementById("subCatContainer");
    while (subCatContainer.firstChild){
        subCatContainer.removeChild(subCatContainer.firstChild);
    }
    for (const subCat of subCatSet){
        const copy = subCatTemplate.cloneNode(true).content;
        copy.children[0].value = subCat;
        copy.children[1].innerText = subCat;
        subCatContainer.appendChild(copy);
    }
}

const checkAddToCart = function(){
    if(this.status===200){
        const response = JSON.parse(this.response);
        const c = document.getElementById(`product${response.product["productId"]}`);
        c.removeAttribute("onclick");
        c.innerHTML = "Added To Cart!";
    }
};

function addToCart(id) {
    const user = JSON.parse(localStorage.getItem("user"));
    doRequest("GET",`/cart/${user.userId}/add/${id}`,checkAddToCart);
}

const searchProcessor = function(){
    if(this.status === 200){
        products_list = JSON.parse(this.response);
        fillProducts(products_list);
        fillSubcategories(products_list);
        //FIXME add a fill subcategory list method
    }
};

function doSearch() {
    const str = document.getElementById("searchBar").value.trim();
    if(str){
        doRequest("GET",`/products/search/${str}`, searchProcessor);
    }else{
        if (category){
            doRequest("GET",`/products/${category}`,searchProcessor);
        }else{
            doRequest("GET",`/products`,searchProcessor);
        }

    }
}

function applyCategory() {
    const str = document.getElementById("searchBar").value.trim();
    const url = new URL(location);
    if (str){
        url.searchParams.set("searchString",str);
    }else {
        url.searchParams.delete("searchString");
    }
    const cats = document.getElementsByName("category");
    for (const cat of cats){
        if(cat.checked){
            url.searchParams.set("category",cat.value);
            break;
        }
    }
    location.href = url.href;
}

const cats = JSON.parse(localStorage.getItem("cats"));
if(cats){
    const catFilterTemplate = document.getElementById("catFilterTemplate");
    for (const cat of cats){
        const inputcopy = catFilterTemplate.querySelector("input").cloneNode();
        const spancopy = catFilterTemplate.querySelector("span").cloneNode();
        inputcopy.setAttribute("value",cat);
        inputcopy.checked = false;
        spancopy.innerText = cat;
        const br = document.createElement("br");
        catFilterTemplate.appendChild(inputcopy);
        catFilterTemplate.appendChild(spancopy);
        catFilterTemplate.appendChild(br);
    }
}

if(searchString){
    document.getElementById("searchBar").value = searchString;
}
if(category && category!=="all"){
    const radios = document.getElementsByName("category");
    for(const radio of radios){
        if (radio.value===category){
            radio.checked=true;
            break;
        }
    }
    applyFilter();
}else{
    doSearch();
}