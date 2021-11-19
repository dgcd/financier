import apiUrls from '../config/apiUrls.js';
import apiHelper from '../service/apiHelper.js';

export default {
    getInitData(success, fail) {
        apiHelper.performApiRequest(
            apiUrls.initDataUrl,
            null,
            'Init data',
            success,
            fail,
        );
    },

    createAccount(account, success, fail) {
        apiHelper.performApiRequest(
            apiUrls.accountCreateUrl,
            account,
            'Create account',
            success,
            fail,
        );
    },

    createCategory(category, success, fail) {
        apiHelper.performApiRequest(
            apiUrls.categoryCreateUrl,
            category,
            'Create category',
            success,
            fail,
        );
    },

    createOperation(operation, success, fail) {
        apiHelper.performApiRequest(
            apiUrls.operationCreateUrl,
            operation,
            'Create operation',
            success,
            fail,
        );
    },
}
