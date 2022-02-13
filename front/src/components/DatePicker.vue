<template>
    <input type="date" v-model="valueInternal" :min="min" :max="max" @change="onChange" />
</template>

<script>
export default {
    name: 'DatePicker',

    props: {
        value: {
            type: String,
            required: false,
        },
        min: {
            type: String,
            required: false,
        },
        max: {
            type: String,
            required: false,
        },
    },

    data() {
        return {
            valueInternal: this.value,
        };
    },

    methods: {
        onChange(event) {
            this.$emit('input', event.target.value);
        }
    },
}
</script>
