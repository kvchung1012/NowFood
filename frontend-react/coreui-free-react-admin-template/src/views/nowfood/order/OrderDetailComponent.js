import { React, useState } from 'react'
import { useSelector } from 'react-redux';
import 'ag-grid-community/dist/styles/ag-grid.css';
import 'ag-grid-community/dist/styles/ag-theme-alpine.css';
import TableComponent from 'src/components/nowfood/TableComponent';

const OrderDetailComponent = (props) => {
    const baseUrl = useSelector((state) => state.baseUrl);

    const head = [
        {
            field: 'product.name',
            sort: null,
            flex:1,
            resizable: true,
            hide: false,
            pinned: ''
        },
        {
            field: 'price',
            sort: null,
            flex:1,
            resizable: true,
            hide: false,
            pinned: ''
        },
        {
            field: 'quantity',
            sort: null,
            flex:1,
            resizable: true,
            hide: false,
            pinned: ''
        },
        {
            field: 'node',
            sort: null,
            flex:1,
            resizable: true,
            hide: false,
            pinned: ''
        }
    ]

    const [config, setConfig] = useState({
        // eslint-disable-next-line react/prop-types
        url: baseUrl + 'api/orders/'+props.match.params.id,
        reLoadData: false,
        type:'get',
        table : 'order-detail'
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
export default OrderDetailComponent