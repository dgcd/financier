<template>
    <div>
        <select v-model="valueInternal">
            <option v-for="v in valuesList" :key="v" :value="v">{{ v }} </option>
        </select>
    </div>
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
        if (!this.valuesList.length) {
            throw new Error('valuesList can not be empty');
        }

        if (!this.value) {
            this.valueInternal = this.valuesList[0];
            return;
        }

        if (!this.valuesList.includes(this.value)) {
            throw new Error('value must be in valuesList');
        }

        this.valueInternal = this.value;
    },

    watch: {
        valueInternal() {
            this.$emit('input', this.valueInternal);
        },
    },
}
</script>
