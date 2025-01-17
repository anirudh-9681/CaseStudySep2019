const message = document.getElementById("message");
let email = document.getElementById("email");
let password = document.getElementById("password");
const valid = function () {
    const reg1 = new RegExp("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,6})+$");
    const reg2 = new RegExp("^([1-zA-Z0-1@.]{6,20})$");
    if (reg1.test(email.value)) {
        if (reg2.test(password.value)) {
            return true;
        } else {
            message.innerText = "Password must be six characters long\n Password can only contain '0-9,a-z,A-Z,_,.,@'";
            return false;
        }
    } else {
        message.innerText = "Bad email";
        return false;
    }
};
const formSubmit = function (event) {
    event.preventDefault();
    if (!valid()) {
        return;
    }
    const data = new FormData();
    data.append("username", email.value);
    data.append("password", btoa(password.value));
    const xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.onload = responseProcessor;

    xhr.open("POST", `http://${location.host}/login`);
    xhr.setRequestHeader("cache-control", "no-cache");
    xhr.send(data);
};
const responseProcessor = function () {
    if (this.status === 200) {
        localStorage.removeItem("user");
        location.href = `http://${location.host}/`;
    } else {
        message.innerText = "Bad Credentials";
        password.value = "";
    }
};