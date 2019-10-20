const cstmCatSelectAdd = document.getElementById("categorySelectAdd");
const cstmCatSelectModify = document.getElementById("categorySelectModify");
const subcatContainerAdd = document.getElementById("subCatContainerAdd");
const subcatContainerModify = document.getElementById("subCatContainerModify");
const checkAddProduct = function () {
    if (this.status === 200) {
        const product = JSON.parse(this.response);
        alert(`Product no ${product.productId} has been successfully added`);
    } else {
        alert("Something went wrong, contact maintenance");
    }
};

const checkModifyProduct = function () {
    if (this.status === 200) {
        alert("product has been successfully updated");
    } else {
        alert("Something went wrong, contact maintenance");
    }
};

function addProduct() {
    let obj = {};
    // REQUEST : {
    //     "name":"xyz",
    //         "price":"<>",
    //         "details":"<>",
    //         "category":"<>",
    //         "subcategory":  “[<>,<>]”
    // }
    obj.name = document.getElementById("productNameAdd").value.trim();
    obj.price = document.getElementById("productPriceAdd").value.trim();
    obj.details = document.getElementById("productDetailsAdd").value.trim();
    const catSelect = document.getElementById("categorySelectAdd");
    obj.category = catSelect.options[catSelect.options.selectedIndex].value;

    const subs = document.getElementsByName("subcategoryAdd");
    for (const sub of subs) {
        if (sub.checked) {
            if (!obj.subcategory) {
                obj.subcategory = "[";
            }
            obj.subcategory += sub.value + ",";
        }
    }
    if (obj.subcategory) {
        obj.subcategory += "]";
    }
    doRequest("POST", "/products/addProduct", checkAddProduct, obj);
}

function modifyProduct() {
    let obj = {};
    // REQUEST : {
    //     "name":"xyz",
    //         "price":"<>",
    //         "details":"<>",
    //         "category":"<>",
    //         "subcategory":  “[<>,<>]”
    // }
    obj.productId = document.getElementById("productIdModify").value;
    obj.name = document.getElementById("productNameModify").value.trim();
    obj.price = document.getElementById("productPriceModify").value.trim();
    obj.details = document.getElementById("productDetailsModify").value.trim();
    const catSelect = document.getElementById("categorySelectModify");
    obj.category = catSelect.options[catSelect.options.selectedIndex].value;

    const subs = document.getElementsByName("subcategoryModify");
    for (const sub of subs) {
        if (sub.checked) {
            if (!obj.subcategory) {
                obj.subcategory = "[";
            }
            obj.subcategory += sub.value + ",";
        }
    }
    if (obj.subcategory) {
        obj.subcategory = obj.subcategory.slice(0,obj.subcategory.length-1);
        obj.subcategory += "]";
    }
    doRequest("POST", "/products/update", checkModifyProduct, obj);
}

function addCustomCatAdd(s) {
    let str;
    if (s) {
        str = s;
    } else {
        str = document.getElementById("customCategoryAdd").value.trim();
    }
    if (str) {
        const opt = document.createElement("option");
        opt.value = str;
        opt.innerText = str;
        cstmCatSelectAdd.options.add(opt, 0);
        cstmCatSelectAdd.selectedIndex = 0;
    } else {
        alert("New Category cannot be empty");
    }
}

function addCustomCatModify(s) {
    let str;
    if (s) {
        str = s;
    } else {
        str = document.getElementById("customCategoryModify").value.trim();
    }
    if (str) {
        const opt = document.createElement("option");
        opt.value = str;
        opt.innerText = str;
        cstmCatSelectModify.options.add(opt, 0);
        cstmCatSelectModify.selectedIndex = 0;
    } else {
        alert("New Category cannot be empty");
    }
}


function addCustomSubcatAdd(s) {
    let str;
    if (s) {
        str = s;
    } else {
        str = document.getElementById("customSubcategoryAdd").value.trim();
    }
    if (str) {
        const newSubCat = document.getElementById("subcategoryTemplate").cloneNode();
        newSubCat.setAttribute("type", "checkbox");
        newSubCat.setAttribute("value", str);
        newSubCat.setAttribute("name","subcategoryAdd");
        subcatContainerAdd.appendChild(newSubCat);
        const label = document.createElement("label");
        label.innerText = str;
        subcatContainerAdd.appendChild(label);
    }
}

function addCustomSubcatModify(s) {
    let str;
    if (s) {
        str = s;
    } else {
        str = document.getElementById("customSubcategoryModify").value.trim();
    }
    if (str) {
        const newSubCat = document.getElementById("subcategoryTemplate").cloneNode();
        newSubCat.setAttribute("type", "checkbox");
        newSubCat.setAttribute("value", str);
        newSubCat.setAttribute("name","subcategoryModify");
        subcatContainerModify.appendChild(newSubCat);
        const label = document.createElement("label");
        label.innerText = str;
        subcatContainerModify.appendChild(label);
    }
}

function fillSubcategories(products_list) {
    let subCats = [];
    for (const product of products_list) {
        let subs = product["subcategory"];
        subs = subs.slice(1, subs.length - 1);
        for (const sub of subs.split(",")) {
            subCats.push(sub.trim());
        }
    }
    const subCatSet = [...new Set(subCats)];

    for (const subCat of subCatSet) {
        addCustomSubcatAdd(subCat);
        addCustomSubcatModify(subCat);
    }
}

const subcatProcessor = function () {
    if (this.status === 200) {
        products_list = JSON.parse(this.response);
        fillSubcategories(products_list);
    }
};

function loadProduct(){
    const productId = document.getElementById("productIdModify").value;
    doRequest("GET",`/products/getById/${productId}`,showProduct);
}

const showProduct = function(){
    if (this.status === 200){
        const product = JSON.parse(this.response);
        document.getElementById("productNameModify").value = product.name;
        document.getElementById("productPriceModify").value = product.price;
        document.getElementById("productDetailsModify").value = product.details;
        document.getElementById("categorySelectModify").value = product.category;
        const productSubs = product.subcategory.slice(1,product.subcategory.length-1).split(",");
        let productSubsArray = [];
        for(const pr of productSubs){
            productSubsArray.push(pr.trim());
        }
        const subs = document.getElementsByName("subcategoryModify");
        for(const sub of subs){
            if(productSubsArray.includes(sub.value)){
                sub.checked = true;
            }else{
                sub.checked = false;
            }
        }
    }else{
        console.log(this);
    }
};



doRequest("GET", `/products`, subcatProcessor);
const cats = JSON.parse(localStorage.getItem("cats"));
for (const cat of cats) {
    addCustomCatAdd(cat);
    addCustomCatModify(cat);
}