import apiUrls from '../config/apiUrls.js'

const apiRequests = {
    async getCurrencies(success, fail) {
        let response = await fetch(apiUrls.testUrl);
        if (response.ok) {
            let json = await response.json();
            success(json);
        } else {
            console.warn('Request error: ' + response.status);
            fail('Get currencies request failed:' + response.status);
        }
    },

    async getAccounts(success, fail) {
        let response = await fetch(apiUrls.accounstUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                'Accept': 'application/json;charset=UTF-8',
            },
        });
        if (response.ok) {
            let json = await response.json();
            success(json);
        } else {
            console.warn('Request error: ' + response.status);
            fail('Get accounts request failed:' + response.status);
        }
    },
}

export default apiRequests;
