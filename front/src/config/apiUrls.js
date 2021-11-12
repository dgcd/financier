const serverUrl = window.location.origin;
const apiUrl = serverUrl + '/api';
const initDataUrl = apiUrl + '/init';
const accountCreateUrl = apiUrl + '/accounts/create';
const categoryCreateUrl = apiUrl + '/categories/create';

export default {
    initDataUrl,
    accountCreateUrl,
    categoryCreateUrl,
}
