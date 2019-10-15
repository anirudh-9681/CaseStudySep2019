const catContainer = document.getElementById("categoryContainer");
const catTemplate = document.getElementById("categoryTemplate");
const welcomeTemplate = document.getElementById("welcomeTemplate");
var userName = document.getElementById("userName");
var userEmail = document.getElementById("userEmail");
var userPhone = document.getElementById("userPhone");
var userAddress = document.getElementById("userAddress");
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

function editProfile(event) {
    event.preventDefault();
    const user = JSON.parse(localStorage.getItem("user"));
    document.getElementById("saveButton").classList.remove("d-none");
    var input = document.createElement("input");
    input.setAttribute("type", "text");
    userName.innerHTML = "";
    userPhone.innerHTML = "";
    userAddress.innerHTML = "";
    var temp = input.cloneNode();
    temp.setAttribute("id", "name");
    temp.setAttribute("value", user.name);
    userName.appendChild(temp);
    temp = input.cloneNode();
    temp.setAttribute("id", "phone");
    if (user.phone) {
        temp.setAttribute("value", user.phone);
    } else {
        temp.setAttribute("value", "Phone");
    }
    userPhone.appendChild(temp);
    temp = input.cloneNode();
    temp.setAttribute("id", "street");
    if (user.address) {
        temp.setAttribute("value", user.address.street);
    } else {
        temp.setAttribute("value", "Street");
    }
    userAddress.appendChild(temp);
    temp = input.cloneNode();
    temp.setAttribute("id", "city");
    if (user.address) {
        temp.setAttribute("value", user.address.city);
    } else {
        temp.setAttribute("value", "City");
    }
    userAddress.appendChild(temp);
    temp = input.cloneNode();
    temp.setAttribute("id", "state");
    if (user.address) {
        temp.setAttribute("value", user.address.state);
    } else {
        temp.setAttribute("value", "State");
    }
    userAddress.appendChild(temp);
    temp = input.cloneNode();
    temp.setAttribute("id", "pincode");
    if (user.address) {
        temp.setAttribute("value", user.address.pincode);
    } else {
        temp.setAttribute("value", "Pincode");
    }
    userAddress.appendChild(temp);
}

function updateProfile(event) {
    event.preventDefault();
    var user = JSON.parse(localStorage.getItem("user"));
    document.getElementById("saveButton").classList.add("d-none");
    user.name = document.getElementById("name").value;
    user.phone = document.getElementById("phone").value;
    if (!user.address) {
        user.address = {};
    }
    user.address.street = document.getElementById("street").value;
    user.address.city = document.getElementById("city").value;
    user.address.state = document.getElementById("state").value;
    user.address.pincode = document.getElementById("pincode").value;
    doRequest("POST", "/updateProfile", checkUpdate, user);
}

const checkUpdate = function () {
    console.log(this);
    if (this.status === 200) {
        location.reload();
    }
};

const pageData = function () {
    if (this.status === 200) {
        const user = JSON.parse(this.response);
        localStorage.removeItem("user");
        localStorage.setItem("user", this.response);
        welcomeUser(user.name);
        userName.innerHTML = user.name;
        userEmail.innerHTML = user.email;
        userPhone.innerHTML = user.phone;
        if (user.address) {
            userAddress.innerHTML = `${user.address.street}<br>${user.address.city}<br>${user.address.state}<br>${user.address.pincode}`;
        } else {
            userAddress.innerHTML = "Not Provided";
        }
    }
    if (localStorage.getItem("cats") === null) {
        doRequest("GET", "/products/getAllCategories", readCategories);
    } else {
        const cats = JSON.parse(localStorage.getItem("cats"));
        addToCategoryList(cats);
    }
};

if (localStorage.getItem("user") === null) {
    // FIXME(Shift): Uncomment this on server live
    location.href = `http://${location.host}/login`;
} else {
    const user = JSON.parse(localStorage.getItem("user"));
    doRequest("GET", `/getProfile/${user.userId}`, pageData);
}