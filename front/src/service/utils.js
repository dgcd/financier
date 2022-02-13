const utils = {
    getTodayDateString() {
        return new Date().toISOString().slice(0, 10);
    },
};

export default utils;
