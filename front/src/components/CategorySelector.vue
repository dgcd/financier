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

    // created() {
    //     this.setInternalValues();
    // },

    computed: {
        ...mapState(['categories']),

        titles() {
            return this.categories.map(a => a.title);
        },
    },

    // watch: {
    //     internalTitle(newInternalTitle) {
    //         if (!newInternalTitle) {
    //             return null;
    //         }
    //         const account = this.accounts.find(a => a.title === newInternalTitle);
    //         this.$emit('input', account.id);
    //     },
    //     currency() {
    //         this.setInternalValues();
    //     },
    // },

    // methods: {
    //     setInternalValues() {
    //         this.internalTitle = null;
    //         this.filteredAccounts = this.currency ?
    //             this.accounts.filter(a => a.currency === this.currency) :
    //             this.accounts;
    //     },
    // },
}
</script>
