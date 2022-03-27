<template>
    <div>
        <span>Trans/Exchange: </span>
        <input type="checkbox" v-model="internalShowTrans" />
    </div>
</template>

<script>
import { mapMutations } from 'vuex';

export default {
    name: 'ShowTransCheckbox',

    data() {
        return {
            internalShowTrans: this.$store.state.selections.showTrans,
        };
    },

    watch: {
        internalShowTrans(newValue) {
            this.setShowTrans(newValue);
        },
    },

    methods: {
        ...mapMutations(['setShowTrans']),
    },
}
</script>
