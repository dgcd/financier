<template>
    <span>
        <input
            hidden="true"
            type="file"
            @change="onFileChange"
            id="selectedFile"
            style="display: none;"
            accept=".xlsx"
        />

        <button
            class="btn btn-primary"
            @click="browseFile"
        >Import all data</button>
    </span>

</template>

<script>
import apiRequests from '@/service/apiRequests.js';

export default {
    name: 'ImportButton',

    props: {
        errorHandler: {
            type: Function,
            required: true,
        },
    },

    methods: {
        browseFile() {
            document.getElementById('selectedFile').click();
        },

        flushFileValue() {
            document.getElementById('selectedFile').value = null;
        },


        onFileChange(event) {
            const files = event.target.files || event.dataTransfer.files;
            if (!files.length) {
                return;
            }

            const file = files[0];
            new FileReader().readAsDataURL(file);

            apiRequests.importAllData(
                { file },
                this.onSuccessHandler,
                this.onErrorHandler,
            );
        },


        onSuccessHandler(payload) {
            this.flushFileValue();
            this.errorHandler(null);
        },

        onErrorHandler(message) {
            this.flushFileValue();
            this.errorHandler(message);
        },
    },
}
</script>
