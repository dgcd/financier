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

    // ----------------------------------------------------

    createAccount(account, success, fail) {
        apiHelper.performApiRequest(
            apiUrls.accountCreateUrl,
            account,
            'Create account',
            success,
            fail,
        );
    },

    closeAccount(idDto, success, fail) {
        apiHelper.performApiRequest(
            apiUrls.accountCloseUrl,
            idDto,
            'Close account',
            success,
            fail,
        );
    },

    // ----------------------------------------------------

    createCategory(category, success, fail) {
        apiHelper.performApiRequest(
            apiUrls.categoryCreateUrl,
            category,
            'Create category',
            success,
            fail,
        );
    },

    // ----------------------------------------------------

    createOperation(operation, success, fail) {
        apiHelper.performApiRequest(
            apiUrls.operationCreateUrl,
            operation,
            'Create operation',
            success,
            fail,
        );
    },

    editOperation(operation, success, fail) {
        apiHelper.performApiRequest(
            apiUrls.operationEditUrl,
            operation,
            'Edit operation',
            success,
            fail,
        );
    },

    cancelOperation(idDto, success, fail) {
        apiHelper.performApiRequest(
            apiUrls.operationCancelUrl,
            idDto,
            'Cancel operation',
            success,
            fail,
        );
    },

    // ----------------------------------------------------

    exportAllData(success, fail) {
        apiHelper.performDownloadRequest(
            apiUrls.dataExportUrl,
            null,
            'Export all data',
            success,
            fail,
        );
    },

    importAllData(request, success, fail) {
        apiHelper.performUploadRequest(
            apiUrls.dataImportUrl,
            request,
            'Import all data',
            success,
            fail,
        );
    },
}
