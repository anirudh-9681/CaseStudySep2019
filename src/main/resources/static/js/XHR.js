function doRequest(method, url, callback, body, form) {
    console.log("Aa");
    const xhr = new XMLHttpRequest();
    const newUrl = `http://${location.host}/${url}`;
    xhr.open(method, newUrl, true);
    if (form) {
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    } else {
        xhr.setRequestHeader("Content-Type", "application/json");
    }

    xhr.onload = callback;
    xhr.send(JSON.stringify(body));
}