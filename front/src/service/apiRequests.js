import apiUrls from '../config/apiUrls.js'

const apiRequests = {
    async getAccounts(success, fail) {
        let response = await fetch(apiUrls.accounstUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                'Accept': 'application/json;charset=UTF-8',
            },
        });
        if (response.ok) {
            let body = await response.json();
            console.log(body)
            success(body.payload);
        } else {
            console.warn('Request error: ' + response.status);
            fail('Get accounts request failed:' + response.status);
        }
    },
}

export default apiRequests;
