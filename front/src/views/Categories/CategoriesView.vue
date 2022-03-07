<template>
    <div>
        <page-title />
        <h1>Categories</h1>

        <error-message v-if="error" :message="error" />

        <CategoriesTable :categories="categories" />
    </div>
</template>

<script>
import { mapState } from 'vuex';
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

    computed: {
        ...mapState(['categories']),
    },

    created() {
        if (this.$route.query.reason === 'nocategories') {
            this.error = 'You can not create operations without at least one subcategory';
        }
    },
}
</script>
