<script>
export default {
    name: 'PageTitle',

    created() {
        document.title = this.$route.meta.title ?
            this.$route.meta.title + ' - ' + defaultTitle :
            defaultTitle;
    },

    render() {},
}

const defaultTitle = 'Financier';
</script>
