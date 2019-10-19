const cstmCatSelect = document.getElementById("categorySelectAdd");
const subcatContainer = document.getElementById("subCatContainerAdd");
const checkAddProduct = function () {
    if(this.status === 200){
        alert("product has been successfully added");
    }else{
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
    doRequest("POST","/products/addProduct",checkAddProduct,obj);
}
function addCustomCatAdd(s) {
    let str;
    if (s){
        str = s;
    }else{
        str = document.getElementById("customCategoryAdd").value.trim();
    }
    if(str){
        const opt = document.createElement("option");
        opt.value = str;
        opt.innerText = str;
        cstmCatSelect.options.add(opt,0);
        cstmCatSelect.selectedIndex = 0;
    }else{
        alert("New Category cannot be empty");
    }
}
function addCustomSubcatAdd(s) {
    let str;
    if (s){
        str = s;
    }else{
        str = document.getElementById("customSubcategoryAdd").value.trim();
    }
    if(str){
        const newSubCat = document.getElementById("subcategoryTemplate").cloneNode();
        newSubCat.setAttribute("type","checkbox");
        newSubCat.setAttribute("value",str);
        subcatContainer.appendChild(newSubCat);
        const label = document.createElement("label");
        label.innerText = str;
        subcatContainer.appendChild(label);
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
    }
}

const subcatProcessor = function () {
    if (this.status === 200) {
        products_list = JSON.parse(this.response);
        fillSubcategories(products_list);
    }
};

doRequest("GET", `/products`, subcatProcessor);
const cats = JSON.parse(localStorage.getItem("cats"));
for(const cat of cats){
    addCustomCatAdd(cat);
}