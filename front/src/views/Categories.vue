<template>
    <div>
        <title-component :title="$route.meta.title" />

        <h1>Categories</h1>

        <table class="tbl">
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th><button @click="onClickCreate(null)">+</button></th>
            </tr>
            <tr v-for="c in sortedCategories" :key="c.id">
                <td>{{ c.id }}</td>
                <td align="left">{{ c.title }}</td>
                <td><button @click="onClickCreate(c.id)">+</button></td>
            </tr>
        </table>
    </div>
</template>

<script>
import { mapState } from 'vuex';
import router from '@/router';

export default {
    name: 'Categories',

    computed: {
        ...mapState(['categories']),

        sortedCategories() {
            const categoriesAsTree = this.getChildrenList(this.categories, null);
            const categoriesAsList = [];
            this.flattenCategories(categoriesAsTree, 0, categoriesAsList);
            return categoriesAsList;
        },
    },

    methods: {
        onClickCreate(parentId) {
            const parentIdParam = parentId ? `?parentId=${parentId}` : '';
            console.log(parentId);
            router.push('/categories/create' + parentIdParam);
        },


        getChildrenList(categories, parentId) {
            const tree = [];
            for (let cat of categories) {
                if (cat.parentId != parentId)
                    continue;

                const newCat = {
                    id: cat.id,
                    title: cat.title,
                    children: this.getChildrenList(categories, cat.id),
                }

                tree.push(newCat);
            }
            return tree;
        },


        flattenCategories(categories, level, accumulator) {
            const prefix = '\xa0\xa0\xa0'.repeat(level);
            const categoriesSorted = categories.sort((c1,c2) => c1.title.localeCompare(c2.title));
            for (let cat of categoriesSorted) {
                cat.title = prefix + cat.title;
                accumulator.push(cat);
                if (cat.children.length)
                    this.flattenCategories(cat.children, level + 1, accumulator);
            }
        },

    },
}
</script>
