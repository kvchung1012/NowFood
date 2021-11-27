import { React, useState } from 'react'
import { useSelector } from 'react-redux';
import 'ag-grid-community/dist/styles/ag-grid.css';
import 'ag-grid-community/dist/styles/ag-theme-alpine.css';
import TableComponent from 'src/components/nowfood/TableComponent';

const OrderComponent = () => {
    const baseUrl = useSelector((state) => state.baseUrl);

    const head = [
        {
            field: 'id',
            sort: null,
            resizable: true,
            hide: false,
            pinned: '',
        },
        {
            field: 'shipName',
            sort: null,
            resizable: true,
            hide: false,
            pinned: ''
        },
        {
            field: 'shipAddress',
            sort: null,
            resizable: true,
            hide: false,
            pinned: ''
        },
        {
            field: 'shipMobie',
            sort: null,
            resizable: true,
            hide: false,
            pinned: ''
        },
        {
            field: 'shipPrice',
            sort: null,
            resizable: true,
            hide: false,
            pinned: ''
        },
        {
            field: 'fee',
            sort: null,
            resizable: true,
            hide: false,
            pinned: ''
        },
        {
            field: 'totalPrice',
            sort: null,
            resizable: true,
            hide: false,
            pinned: ''
        },
        {
            field: 'orderStatus',
            sort: null,
            resizable: true,
            hide: false,
            pinned: ''
        },
        {
            field: 'orderType',
            sort: null,
            resizable: true,
            hide: false,
            pinned: ''
        },
        {
            field: 'paymentPayMethod',
            sort: null,
            resizable: true,
            hide: false,
            pinned: ''
        },
        {
            field: 'paymentPayStatus',
            sort: null,
            resizable: true,
            hide: false,
            pinned: ''
        },
        {
            field: 'Detail',
            width: 120,
            sortable: false,
            hide: false,
            resizable: false,
            pinned: 'right',
            cellRenderer: "cellLinkOrderDetail"
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
        url: baseUrl + 'api/shops/orders',
        reLoadData: false
    })

    

    const openCreateForm = () => {
        
    }

    const openEditForm = (data) => {
        
    }

    return (
        <div>
            <TableComponent header={head} config={config} btnCreateClick={openCreateForm} editData={openEditForm} />
        </div>)

}
export default OrderComponent