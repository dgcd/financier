<template>
    <div>
        <page-title />
        <h1>Categories</h1>

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

    computed: {
        ...mapState(['categories']),
    },
}
</script>
