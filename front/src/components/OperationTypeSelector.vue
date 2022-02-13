<template>
    <select-list
        v-model="internalOperationType"
        :valuesList="operationTypes"
    />
</template>

<script>
import dicts from '@/config/dicts.js';

export default {
    name: 'OperationTypeSelector',

    props: {
        value: {                    // operationType,
            type: String,
            required: false,
        },
    },

    data() {
        return {
            internalOperationType: this.value,
            operationTypes: dicts.operationTypes,
        };
    },

    watch: {
        internalOperationType(newValue) {
            this.$emit('input', newValue);
        },
    },
}
</script>
