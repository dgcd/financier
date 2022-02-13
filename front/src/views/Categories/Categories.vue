<template>
    <div>
        <page-title />
        <h1>Categories</h1>

        <CategoriesTable :categories="categories" />
    </div>
</template>

<script>
import { mapState } from 'vuex';
import CategoriesTable from './components/CategoriesTable.vue';

export default {
    name: 'Categories',

    components: {
        CategoriesTable,
    },

    computed: {
        ...mapState(['categories']),
    },
}
</script>
