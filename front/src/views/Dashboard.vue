<template>
    <div>
        <title-component :title="$route.meta.title" />

        <h1>Dashboard</h1>

        <HelloWorld />
    </div>
</template>

<script>
import HelloWorld from '@/components/HelloWorld.vue';

export default {
    name: 'Dashboard',
    components: {
        HelloWorld,
    },
}
</script>
