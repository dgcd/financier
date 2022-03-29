export default {
    async performApiRequest(
        url,
        requestBody,
        title,
        successCallback,    // accepts payload
        failCallback,       // accepts error message
    ) {
        if (requestBody) {
            console.log(title + ' request with body: ', requestBody);
        }

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
            console.warn(`${title} request failed: `, body.message);
            if (failCallback) {
                failCallback(body.message);
            }
        }
    },
}
