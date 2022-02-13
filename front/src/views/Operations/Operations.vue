<template>
    <div>
        <page-title />
        <h1>Operations</h1>

        <p>
            <redirect-button :title="'Create operation'" :path="'/operations/create'" />
        </p>

        <OperationsTable :operations="operations" />
    </div>
</template>

<script>
import { mapState } from 'vuex';
import OperationsTable from './components/OperationsTable.vue';

export default {
    name: 'Operations',

    components: {
        OperationsTable,
    },

    computed: {
        ...mapState(['operations']),
    },
}
</script>
