<template>
    <div>
        <page-title />
        <h1>Operations</h1>

        <p>
            <redirect-button :title="'Create Expense'" :path="'/operations/create?type=EXPENSE'" />
            <redirect-button :title="'Create Income/Base'" :path="'/operations/create?type=INCOME'" />
            <redirect-button :title="'Create Trans/Exchange'" :path="'/operations/create?type=TRANS'" />
        </p>

        <p>
            <show-expense-checkbox />
            <show-income-checkbox />
            <show-trans-checkbox />
        </p>

        <error-message v-if="error" :message="error" />

        <OperationsTable :operations="operations" />
    </div>
</template>

<script>
import { mapState } from 'vuex';
import OperationsTable from './OperationsTable.vue';

export default {
    name: 'OperationsView',

    components: {
        OperationsTable,
    },

    data() {
        return {
            error: null,
        };
    },

    computed: {
        ...mapState(['operations']),
    },
}
</script>
