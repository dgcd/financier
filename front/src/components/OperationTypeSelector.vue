<template>
    <select-list
        v-model="internalOperationType"
        :valuesList="operationTypes"
        :disabled="disabledList"
    />
</template>

<script>
import dicts from '@/config/dicts.js';

export default {
    name: 'OperationTypeSelector',

    props: {
        value: {
            type: String,
            required: false,
        },
        operationTypes: {
            type: Array,
            required: true,
        },
    },

    data() {
        return {
            internalOperationType: this.value,
            disabledList: this.operationTypes.length === 1,
        };
    },

    created() {
        if (!this.operationTypes.length) {
            throw new Error("Operations types array must contain elements");
        }
        for (let optype of this.operationTypes) {
            if (!dicts.operationTypesSet.has(optype)) {
                throw new Error("Unknown operation type");
            }
        }
    },

    watch: {
        internalOperationType(newValue) {
            this.$emit('input', newValue);
        },
    },
}
</script>
