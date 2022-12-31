<template>
    <div>
        <page-title />
        <h1>Categories</h1>

        <error-message v-if="error" :message="error" />

        <CategoriesTable /> 
    </div>
</template>

<script>
import CategoriesTable from './CategoriesTable.vue';

export default {
    name: 'CategoriesView',

    components: {
        CategoriesTable,
    },

    data() {
        return {
            error: null,
        };
    },

    created() {
        if (this.$route.query.reason === 'nocategories') {
            this.error = 'You can not create operations without at least one subcategory';
        }
    },
}
</script>
