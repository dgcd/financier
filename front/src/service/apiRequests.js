import apiUrls from '../config/apiUrls.js';

const apiRequests = {
    getInitData(success, fail) {
        performRequest(
            apiUrls.initDataUrl,
            null,
            'Init data',
            success,
            fail           
        );
    },

    createAccount(account, success, fail) {
        performRequest(
            apiUrls.accountCreateUrl,
            account,
            'Create account',
            success,
            fail           
        );
    },

    createCategory(category, success, fail) {
        performRequest(
            apiUrls.categoryCreateUrl,
            category,
            'Create category',
            success,
            fail           
        );
    },


    async createOperation(operation, success, fail) {
        performRequest(
            apiUrls.operationCreateUrl,
            operation,
            'Create operation',
            success,
            fail           
        );
    },
}

export default apiRequests;


async function performRequest(url, requestBody, title, successCallback, failCallback) {
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
        console.log(title + ' request succeed: ', body);
        if (successCallback)
            successCallback(body.payload);
    } else {
        console.warn(title + ' request failed: ', body);
        if (failCallback)
            failCallback(title + ' request failed: ' + body.message);
    }
}
