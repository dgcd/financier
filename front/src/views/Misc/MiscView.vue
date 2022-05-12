<template>
    <div>
        <page-title />
        <h1>Miscellaneous</h1>

        <error-message v-if="error" :message="error" />

        <p>
            <ExportButton :errorHandler="(msg) => { error = msg }"/>
        </p>

        <p>
            <ImportButton :errorHandler="(msg) => { error = msg }"/>
        </p>
    </div>
</template>

<script>
import ExportButton from './ExportButton.vue';
import ImportButton from './ImportButton.vue';

export default {
    name: 'MiscView',

    components: {
        ExportButton,
        ImportButton,
    },

    data() {
        return {
            error: null,
        };
    },
}
</script>
