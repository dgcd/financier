<template>
    <div>
        <p>{{ msg }}</p>
    </div>
</template>

<script>
import apiRequests from '@/service/apiRequests.js';

export default {
    name: 'HelloWorld',
    data() {
        return {
            msg: 'loading ...',
        };
    },
    created() {
        apiRequests.getCurrencies(this.success, this.fail);
    },
    methods: {
        success(responseBody) {
            this.msg = responseBody.currencies.join(',');
        },
        fail(errorMessage) {
            this.msg = errorMessage;
        },
    },
 };
</script>
