<template>
    <select v-model="valueInternal">
        <option v-for="v in valuesList" :key="v" :value="v">{{ v }} </option>
    </select>
</template>

<script>
export default {
    name: 'SelectList',

    props: {
        value: {
            type: String,
            required: false,
        },
        valuesList: {
            type: Array,
            required: true,
        },
    },

    data() {
        return {
            valueInternal: null,
        };
    },

    created() {
        this.setInternalValue();
    },

    watch: {
        valueInternal() {
            this.emitInternalValue();
        },
        valuesList() {
            this.setInternalValue();
        },
    },

    methods: {
        setInternalValue() {
            if (!this.valuesList.length) {
                return this.valueInternal = null;
            }
            if (!this.valuesList.includes(this.value)) {
                return this.valueInternal = this.valuesList[0];
            }
            this.valueInternal = this.value;
        },

        emitInternalValue() {
            this.$emit('input', this.valueInternal);
        },
    },
}
</script>
