<template>
    <button
        class="btn btn-primary"
        @click="onClickExport"
    >Export all data</button>
</template>

<script>
import apiRequests from '@/service/apiRequests.js';

export default {
    name: 'ExportButton',

    props: {
        errorHandler: {
            type: Function,
            required: true,
        },
    },

    methods: {
        onClickExport() {
            apiRequests.exportAllData(
                () => this.errorHandler(null),
                this.errorHandler,
            );
        },
    },
}
</script>
