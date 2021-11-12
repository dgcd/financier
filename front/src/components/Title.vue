<script>
export default {
    props: ['title'],
    watch: {
        title: {
            immediate: true,
            handler() {
                document.title = this.title ?
                    this.title + ' - ' + defaultTitle :
                    defaultTitle;
            },
        },
    },
    render() {},
}

const defaultTitle = 'Financier';
</script>
