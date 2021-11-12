import apiUrls from '../config/apiUrls.js'

const apiRequests = {
    async getInitData(success, fail) {
        let response = await fetch(apiUrls.initDataUrl, {
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
            fail('Init data request failed: ' + response.status);
        }
    },

    async createAccount(account, success, fail) {
        let response = await fetch(apiUrls.accountCreateUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                'Accept': 'application/json;charset=UTF-8',
            },
            body: JSON.stringify(account),
        });
        if (response.ok) {
            let body = await response.json();
            console.log(body)
            
            if (body.code == 'OK') {
                success(body.payload);
            } else {
                fail(body.message);
            }

        } else {
            console.warn('Request error: ' + response.status);
            fail('Create account request failed:' + response.status);
        }
    },

    async createCategory(category, success, fail) {
        let response = await fetch(apiUrls.categoryCreateUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                'Accept': 'application/json;charset=UTF-8',
            },
            body: JSON.stringify(category),
        });
        if (response.ok) {
            let body = await response.json();
            console.log(body)
            
            if (body.code == 'OK') {
                success(body.payload);
            } else {
                fail(body.message);
            }

        } else {
            console.warn('Request error: ' + response.status);
            fail('Create category request failed:' + response.status);
        }
    },
}

export default apiRequests;
