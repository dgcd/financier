export default {
    async performApiRequest(url, requestBody, title, successCallback, failCallback) {
        let response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                'Accept': 'application/json;charset=UTF-8',
            },
            body: requestBody ? JSON.stringify(requestBody) : null,
        });

        if (!response.ok) {
            var message = title + ' request failed with code: ' + response.status;
            console.warn(message);
            if (failCallback)
                failCallback(message);
            return;
        }

        let body = await response.json();
        if (body.code == 'OK') {
            console.log(title + ' request succeeded: ', body);
            if (successCallback)
                successCallback(body.payload);
        } else {
            console.warn(title + ' request failed: ', body);
            if (failCallback)
                failCallback(title + ' request failed: ' + body.message);
        }
    },
}
