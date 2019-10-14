Element.prototype.insertChildAtIndex = function (child, index) {
    if (!index) index = 0
    if (index >= this.children.length) {
        this.appendChild(child)
    } else {
        this.insertBefore(child, this.children[index])
    }
};

function doRequest(method, url, callback, body, form) {
    const xhr = new XMLHttpRequest();
    const newUrl = `http://${location.host}${url}`;
    xhr.open(method, newUrl, true);
    if (form) {
        xhr.withCredentials = true;
        console.log("a form is sent");
    } else {
        xhr.setRequestHeader("Content-Type", "application/json");
    }

    xhr.onload = callback;
    xhr.send(JSON.stringify(body));
}