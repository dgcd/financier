export default {
    async performApiRequest(
        url,
        requestBody,
        title,
        successCallback,    // accepts payload
        failCallback,       // accepts error message
    ) {
        logBefore(title, requestBody);

        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                'Accept': 'application/json;charset=UTF-8',
            },
            body: requestBody ? JSON.stringify(requestBody) : null,
        });

        const body = await response.json();

        if (response.ok) {
            console.log(`${title} request succeeded: `, body.payload);
            if (successCallback) {
                successCallback(body.payload);
            }
        } else {
            console.warn(`${title} request failed: `, body.errorMessage);
            if (failCallback) {
                failCallback(body.errorMessage);
            }
        }
    },


    async performDownloadRequest(
        url,
        requestBody,
        title,
        successCallback,    // accepts nothing
        failCallback,       // accepts error message
    ) {
        logBefore(title, requestBody);

        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=UTF-8',
            },
            body: requestBody ? JSON.stringify(requestBody) : null,
        });

        if (response.ok) {
            const blob = await response.blob();
            const filename = response.headers.get("content-disposition").split('filename=')[1];
            console.log(`${title} request succeeded:`, filename);
            const a = document.createElement('a');
            a.href = URL.createObjectURL(blob);
            a.download = filename;
            document.body.appendChild(a);
            a.click();
            a.remove();
            if (successCallback) {
                successCallback();
            }
        } else {
            const body = await response.json();
            console.warn(`${title} request failed: `, body.errorMessage);
            if (failCallback) {
                failCallback(body.errorMessage);
            }
        }
    },


    async performUploadRequest(
        url,
        requestBody,
        title,
        successCallback,    // accepts payload
        failCallback,       // accepts error message
    ) {
        logBefore(title, requestBody);

        const reqBody = new FormData();
        for (let name in requestBody) {
            reqBody.append(name, requestBody[name]);
        }

        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Accept': 'application/json;charset=UTF-8',
            },
            body: reqBody,
        });

        const body = await response.json();

        if (response.ok) {
            console.log(`${title} request succeeded: `, body.payload);
            if (successCallback) {
                successCallback(body.payload);
            }
        } else {
            console.warn(`${title} request failed: `, body.errorMessage);
            if (failCallback) {
                failCallback(body.errorMessage);
            }
        }
    },
}


function logBefore(title, requestBody) {
    if (requestBody) {
        console.log(title + ' request with body:', requestBody);
    } else {
        console.log(title + ' request');
    }
}
