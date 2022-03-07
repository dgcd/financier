<template>
    <select-list v-model="internalTitle" :valuesList="titles" />
</template>

<script>
import { mapState } from 'vuex';

export default {
    name: 'CategorySelector',

    props: {
        value: {                // category or subcategory ID
            type: Number,
            required: false,
        },
        parentId: {             // if present - component selects subcategory
            type: Number,
            required: false,
        },
    },

    data() {
        return {
            internalTitle: null,
        };
    },

    computed: {
        ...mapState(['categories']),

        filteredCategories() {
            return this.parentId ?
                this.categories.filter(c => c.parentId === this.parentId) :
                this.categories.filter(c => !c.parentId);
        },

        titles() {
            return this.filteredCategories.map(a => a.title);
        },
    },

    watch: {
        internalTitle(newInternalTitle) {
            if (!newInternalTitle) {
                return null;
            }
            const category = this.filteredCategories.find(c => c.title === newInternalTitle);
            this.$emit('input', category.id);
        },
    },
}
</script>
