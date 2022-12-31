<template>
    <table class="tbl">
        <tr>
            <th>Id</th>
            <th>Title</th>
            <th><button class="btn btn-link" @click="onClickCreate(null)">add parent</button></th>
            <th>Ops</th>
        </tr>
        <tr v-for="c in sortedCategories" :key="c.id">
            <td>{{ c.id }}</td>
            <td align="left">{{ c.title }}</td>
            <td><button class="btn btn-link" v-if="!c.parentId" @click="onClickCreate(c.id)">add child</button></td>
            <td>{{ c.count }}</td>
        </tr>
    </table>
</template>

<script>
import { mapState } from 'vuex';

export default {
    name: 'CategoriesTable',

    computed: {
        ...mapState(['categories', 'operations']),

        sortedCategories() {
            const categoriesAsTree = this.getChildrenList(this.categories, null);
            this.countOperations(categoriesAsTree, this.operations);
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
                if (cat.parentId != parentId) {
                    continue;
                }
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

        countOperations(categoriesAsTree, operations) {
            const map = {};
            for (let parent of categoriesAsTree) {
                parent.count = 0;
                map[parent.title] = {
                    category: parent,
                    children: {},
                };
                for (let child of parent.children) {
                    child.count = 0;
                    map[parent.title].children[child.title] = child;
                }
            }
            for (let op of operations) {
                if (op.categoryTitle !== 'n/a') {
                    map[op.categoryTitle].category.count++;
                    map[op.categoryTitle].children[op.subcategoryTitle].count++;
                }
            }
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
