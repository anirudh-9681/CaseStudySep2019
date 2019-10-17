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
    } else {
        xhr.setRequestHeader("Content-Type", "application/json");
    }

    xhr.onload = callback;
    xhr.send(JSON.stringify(body));
}



function search(event) {
    event.preventDefault();
    if (!location.pathname.startsWith("/search")) {
        const str = document.getElementById("searchBar").value.trim();
        if (str === "") {
            return;
        }
        let url = new URL(document.location);
        url.pathname = "search";
        url.searchParams.set("searchString",str);
        url.searchParams.set("category","all");
        location.assign(url.href);
    }else{
        applyCategory();
    }
}