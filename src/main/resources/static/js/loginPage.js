const message = document.getElementById("message");
let email = document.getElementById("email");
let password = document.getElementById("password");
const valid = function(){
    const reg1 = new RegExp("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$");
    const reg2 = new RegExp("^([1-zA-Z0-1@.]{8,20})$");
    if(reg1.test(email.value)){
        if (reg2.test(password.value)) {
            return true;
        }else{
            message.innerText = "Password must be eight characters long\n Password can only contain '0-9,a-z,A-Z,_,.,@'";
        }
    }else{
        message.innerText = "Bad email";
        return false;
    }
};
const formSubmit = function(event){
    event.preventDefault();
    if(!valid()){
        email.innerText = "";
        password.innerText = "";
        return;
    }
    const form = document.getElementById("loginForm");
    const FD = new FormData(form);
    FD.set("password",btoa(FD.get("password")));
    doRequest("POST","/login",responseProcessor,FD,true);
};
const responseProcessor = function(){
    if(this.status===200){
        location.href = `http://${location.host}/`;
    }else{
        message.innerText = "Bad Credentials";
        password.value = "";
    }
}