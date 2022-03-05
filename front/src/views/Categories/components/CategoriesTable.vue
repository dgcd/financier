<template>
    <table class="tbl">
        <tr>
            <th>Id</th>
            <th>Title</th>
            <th><button class="btn btn-link" @click="onClickCreate(null)">add</button></th>
        </tr>
        <tr v-for="c in sortedCategories" :key="c.id">
            <td>{{ c.id }}</td>
            <td align="left">{{ c.title }}</td>
            <td><button class="btn btn-link" v-if="!c.parentId" @click="onClickCreate(c.id)">add</button></td>
        </tr>
    </table>
</template>

<script>
export default {
    name: 'CategoriesTable',

    props: {
        categories: {
            type: Array,
            required: true,
        },
    },

    computed: {
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
            this.$router.push('/categories/create' + parentIdParam);
        },


        getChildrenList(categories, parentId) {
            const tree = [];
            for (let cat of categories) {
                if (cat.parentId != parentId)
                    continue;

                const newCat = {
                    id: cat.id,
                    title: cat.title,
                    parentId: cat.parentId,
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
                if (cat.children.length) {
                    this.flattenCategories(cat.children, level + 1, accumulator);
                }
            }
        },
    },
}
</script>
