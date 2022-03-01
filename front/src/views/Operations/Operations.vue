<template>
    <div>
        <page-title />
        <h1>Operations</h1>

        <p>
            <redirect-button :title="'Create Expense'"   :path="'/operations/create?type=EXPENSE'" />
            &nbsp;
            <redirect-button :title="'Create Income'"    :path="'/operations/create?type=INCOME'" />
            &nbsp;
            <redirect-button :title="'Create Transfert'" :path="'/operations/create?type=TRANS'" />
        </p>

        <check-operation-types />

        <OperationsTable :operations="operations" />
    </div>
</template>

<script>
import { mapState } from 'vuex';
import OperationsTable from './comps/OperationsTable.vue';

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
