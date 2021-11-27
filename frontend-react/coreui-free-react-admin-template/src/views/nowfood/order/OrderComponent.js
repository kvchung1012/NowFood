import { React, useState } from 'react'
import { useSelector } from 'react-redux';
import 'ag-grid-community/dist/styles/ag-grid.css';
import 'ag-grid-community/dist/styles/ag-theme-alpine.css';
import TableComponent from 'src/components/nowfood/TableComponent';
import CreateCategoryComponent from './CreateCategoryComponent';

const OrderComponent = () => {
    const baseUrl = useSelector((state) => state.baseUrl);

    const head = [
        {
            field: 'id',
            flex: 1,
            sort: null,
            resizable: true,
            hide: false,
            pinned: '',
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

    const [config, setConfig] = useState({
        url: baseUrl + '/api/shops/orders',
        reLoadData: false
    })

    const [category, setCategory] = useState({
        data: {
            id: 0,
            code: '',
            name: ''
        },
        visible: false
    }
    )

    const openCreateForm = () => {
        setCategory({
            data: {
                id: 0,
                code: '',
                name: ''
            },
            visible: true
        })
    }

    const openEditForm = (data) => {
        console.log(data)
        setCategory({
            data: data,
            visible: true
        })
    }

    const changeVisible = (value) => {
        setCategory({
            ...category,
            visible: value
        })
    }

    return (
        <div>
            <TableComponent header={head} config={config} btnCreateClick={openCreateForm} editData={openEditForm} />
            (<CreateCategoryComponent changeVisible={changeVisible} data={category} />)
        </div>)

}
export default OrderComponent