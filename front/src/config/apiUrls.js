const serverUrl = window.location.origin;
const apiUrl = serverUrl + '/api';
const initDataUrl = apiUrl + '/init';
const accountCloseUrl = apiUrl + '/accounts/close';
const accountCreateUrl = apiUrl + '/accounts/create';
const categoryCreateUrl = apiUrl + '/categories/create';
const operationCreateUrl = apiUrl + '/operations/create';
const dataExportUrl = apiUrl + '/data/export';
const dataImportUrl = apiUrl + '/data/import';

export default {
    initDataUrl,
    accountCloseUrl,
    accountCreateUrl,
    categoryCreateUrl,
    operationCreateUrl,
    dataExportUrl,
    dataImportUrl,
}
