<template>
    <div>
        <h2>By category</h2>

        <table class="tbl">
            <tr>
                <th>Category</th>
                <th v-if="showSubcategories">Subcategory</th>
                <th v-for="col in monthsAndYearsColumns" :key="col">{{ col }}</th>
            </tr>
            <tr
                v-for="row in preparedRows"
                :key="row.id"
                :class="!row.isSubcat && showSubcategories ? 'boldRow' : ''"
            >
                <td>{{ row.isSubcat ? '' : row.categoryTitle }}</td>
                <td v-if="showSubcategories">{{ row.subcategoryTitle }}</td>
                <td
                    v-for="col in row.columns"
                    :key="col.id"
                    :class="!col.isMonth && !showOnlyYears ? 'boldRow' : ''"
                >{{ col.amount | formatMoneyToString }}</td>
            </tr>
        </table>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
    name: 'ByCategorySubview',

    props: {
        currency: {
            type: String,
            required: true,
        },
        showOnlyYears: {
            type: Boolean,
            required: true,
        },
        showSubcategories: {
            type: Boolean,
            required: true,
        },
    },

    computed: {
        monthsAndYearsColumns() {
            const monthsSpace = this.makeMonthsSpace();
            let columns = [];
            for (let year of monthsSpace) {
                if (!this.showOnlyYears) {
                    columns = columns.concat(year.monthsTokens);
                }
                columns.push(year.yearToken);
            }
            return columns.reverse();
        },


        // monthsSpaceLength() {
        //     let length = this.makeMonthsSpace().length;
        //     for (let year of this.makeMonthsSpace()) {
        //         length += year.monthsTokens.length;
        //     }
        //     return length;
        // },


        preparedData() {
            const preparedOps = this.groupOpsByMonthByCurrencyByCategories();
            const monthsSpace = this.makeMonthsSpace();
            console.log("monthsSpace: ", monthsSpace)
            const categoriesTree = this.getCategoriesTree();
            console.log("categoriesTree: ", categoriesTree)
    
            const monthsSpaceLength = this.monthsSpaceLength();
            console.log("monthsSpaceLength: ", monthsSpaceLength)
            
            const tableData = this.makeTableData(monthsSpace, monthsSpaceLength, preparedOps, categoriesTree, this.currency);
            console.log("tableData: ", tableData)
            return tableData;
        },


        preparedRows() {
            let tableData = this.preparedData;
            if (!this.showSubcategories) {
                tableData = tableData.filter(row => !row.isSubcat);
            }
            if (this.showOnlyYears) {
                return tableData.map(row => {
                    const newRow = { ...row };
                    newRow.columns = row.columns.filter(c => !c.isMonth);
                    return newRow;
                });
            }
            return tableData;
        },
    },

    methods: {
        ...mapGetters([
            'groupOpsByMonthByCurrencyByCategories',
            'makeMonthsSpace',
            'getCategoriesTree',
            'monthsSpaceLength',
        ]),

        makeTableData(monthsSpace, monthsSpaceLength, preparedOps, categoriesTree, currency) {
            let resultRows = [];
            for (let category of categoriesTree) {
                const categoryRows = this.getCategoryRows(category, monthsSpace, monthsSpaceLength, preparedOps, currency);
                resultRows = resultRows.concat(categoryRows);
            }
            resultRows.push(this.getSummaryRow(resultRows, monthsSpaceLength));
            return resultRows;
        },


        getSummaryRow(resultRows, monthsSpaceLength) {
            const onlyCatRows = resultRows.filter(row => !row.isSubcat);
            console.log("onlyCatRows: ", onlyCatRows);
            console.log("monthsSpaceLength: ", monthsSpaceLength);

            const columns = [];

            for (let i = 0; i < monthsSpaceLength; i++) {
                let amountSumm = 0;
                let isMonth;
                let id;
                for (let row of onlyCatRows) {
                    const column = row.columns[i];
                    amountSumm += column.amount;
                    isMonth = column.isMonth;
                    id = column.id;
                }
                columns.push({
                    amount: amountSumm,
                    isMonth,
                    id: id + '_summary',
                });
            }

            

            return {
                categoryTitle: 'Summary',
                id: 'Summary',
                isSummary: true,
                isSubcat: false,
                columns,
            };
        },


        getCategoryRows(category, monthsSpace, monthsSpaceLength, preparedOps, currency) {
            const subcategoriesRows = this.getSubcategoriesRows(category, monthsSpace, preparedOps, currency);
            const notEmptyRows = this.filterEmptySubcategoryRows(subcategoriesRows);
            if (!notEmptyRows.length) {
                return [];
            }
            const categoryRow = {
                categoryTitle: category.category,
                id: category.category,
                columns: [],
            };
            for (let i = 0; i < monthsSpaceLength; i++) {
                let amountSumm = 0;
                let isMonth;
                let id;
                for (let row of notEmptyRows) {
                    const column = row.columns[i];
                    amountSumm += column.amount;
                    isMonth = column.isMonth;
                    id = column.id;
                }
                categoryRow.columns.push({
                    amount: amountSumm,
                    isMonth,
                    id: id + '_year',
                });
            }
            return [categoryRow].concat(notEmptyRows);
        },

        filterEmptySubcategoryRows(subcategoriesRows) {
            return subcategoriesRows
                .filter(row => {
                    for (let column of row.columns) {
                        if (column.isMonth && column.amount !== 0) {
                            return true;
                        }
                    }
                    return false;
                });
        },

        getSubcategoriesRows(category, monthsSpace, preparedOps, currency) {
            return category.subcategories
                .map(subcategoryTitle => { return {
                    categoryTitle: category.category,
                    subcategoryTitle,
                    isSubcat: true,
                    id: category.category + '_' + subcategoryTitle,
                    columns: this.getYearsColumns(monthsSpace, preparedOps, currency, category.category, subcategoryTitle).reverse(),
                }})
        },

        getYearsColumns(monthsSpace, preparedOps, currency, categoryTitle, subcategoryTitle) {
            let allColumns = [];
            for (let year of monthsSpace) {
                const monthsColumns = this.getMonthsColumns(year, preparedOps, currency, categoryTitle, subcategoryTitle);
                const yearAmount = monthsColumns
                    .map(col => col.amount)
                    .reduce((acc, amount) => acc + amount, 0);

                monthsColumns.push({
                    amount: yearAmount,
                    id: year.yearToken,
                });
                allColumns = allColumns.concat(monthsColumns);
            }
            return allColumns;
        },

        getMonthsColumns(year, preparedOps, currency, categoryTitle, subcategoryTitle) {
            return year.monthsTokens
                .map(monthToken => { return {
                        amount: this.getMonthlyAmount(monthToken, preparedOps, currency, categoryTitle, subcategoryTitle),
                        isMonth: true,
                        id: monthToken,
                }});
        },

        getMonthlyAmount(monthToken, preparedOps, currency, categoryTitle, subcategoryTitle) {
            if (!preparedOps[monthToken]) return 0;
            if (!preparedOps[monthToken][currency]) return 0;
            if (!preparedOps[monthToken][currency][categoryTitle]) return 0;
            if (!preparedOps[monthToken][currency][categoryTitle][subcategoryTitle]) return 0;
            return preparedOps[monthToken][currency][categoryTitle][subcategoryTitle]
                .map(op => op.amount)
                .reduce((acc, amount) => acc + amount, 0);
        },
    },
}
</script>
