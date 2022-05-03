<template>
    <div>
        <page-title />
        <h1>Miscellaneous</h1>

        <error-message v-if="error" :message="error" />

        <p>
            <ExportButton :errorHandler="(msg) => { error = msg }"/>
        </p>
    </div>
</template>

<script>
import ExportButton from './ExportButton.vue';

export default {
    name: 'MiscView',

    components: {
        ExportButton,
    },

    data() {
        return {
            error: null,
        };
    },
}
</script>
