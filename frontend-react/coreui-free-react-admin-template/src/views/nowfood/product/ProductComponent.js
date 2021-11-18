import { React, useState } from 'react'
import { useSelector } from 'react-redux';
import 'ag-grid-community/dist/styles/ag-grid.css';
import 'ag-grid-community/dist/styles/ag-theme-alpine.css';
import TableComponent from 'src/components/nowfood/TableComponent';

const ProductComponent = () => {
    const baseUrl = useSelector((state) => state.baseUrl);
    const head = [
        {
            field: 'id',
            flex: 1,
            sort: null,
            resizable: true,
            hide: false,
            pinned: ''
        },
        {
            field: 'code',
            flex: 1,
            sort: null,
            resizable: true,
            hide: false,
            pinned: ''
        },
        {
            field: 'name',
            flex: 1,
            sort: null,
            resizable: true,
            hide: false,
            pinned: ''
        },
        {
            field: 'Action',
            width: 120,
            sortable: false,
            hide: false,
            resizable: false,
            pinned: 'right',
            cellRenderer: "cellActionComponent"
        }
    ]
    const urlConfig={
        url : baseUrl+'api/categories/search-adv'
    }

    return <TableComponent header={head} url={urlConfig}/>
}
export default ProductComponent