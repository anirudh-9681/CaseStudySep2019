const message = document.getElementById("message");
let email = document.getElementById("email");
let password = document.getElementById("password");
let name = document.getElementById("name")
const valid = function () {
    const reg1 = new RegExp("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,6})+$");
    const reg2 = new RegExp("^([1-zA-Z0-1@.]{6,20})$");
    const reg3 = new RegExp("^([a-zA-Z\\ .]{0,20})$");
    if (reg1.test(email.value)) {
        if (reg2.test(password.value)) {
            if (reg3.test(name.value)) {
                return true;
            } else {
                message.innerText = "Name can have only a-z and A-Z";
            }
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
    var data = JSON.stringify({
        "email": email.value,
        "name": name.value,
        "password": btoa(password.value)
    });

    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.onload = responseProcessor;

    xhr.open("POST", `http://${location.host}/signup`);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("cache-control", "no-cache");

    xhr.send(data);
};

const responseProcessor = function () {
    if (this.status === 200) {
        message.classList.remove("text-danger");
        message.classList.add("text-success");
        message.innerHTML = "Sign Up successful. Continue to <a class='text-success' href='/login'>Login</a>";
    } else {
        message.innerText = `User with ${email.value} already exists!`
    }
};