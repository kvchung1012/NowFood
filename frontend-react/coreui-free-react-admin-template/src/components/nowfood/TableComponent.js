import { React, useEffect, useState } from 'react'
import { AgGridColumn, AgGridReact } from 'ag-grid-react'

import { CPagination, CPaginationItem } from '@coreui/react';

import { CButton, CFormSelect } from '@coreui/react'
import CIcon from '@coreui/icons-react'
import { cilTrash, cilPencil } from '@coreui/icons'

import 'ag-grid-community/dist/styles/ag-grid.css';
import 'ag-grid-community/dist/styles/ag-theme-alpine.css';
import HeaderTable from './HeaderTable';
import axios from 'axios'
import { Link } from 'react-router-dom';
import swal from 'sweetalert';
const TableComponent = (props) => {
    const [request, setRequest] = useState({
        pageIndex: 1,
        pageSize: 5,
        keyword: '',
        asc: '',
        desc: '',
        id: null,
        shopId: null
    });

    const [totalPage, setTotalPage] = useState([]);

    // lưu lại thông tin header
    // eslint-disable-next-line react/prop-types
    const [fixHeader, setFixHeader] = useState(props.header);

    //cấu hình chung api
    // eslint-disable-next-line react/prop-types
    const [urlConfig, setUrlConfig] = useState(props.config);

    // header trong agrid
    const [header, setHeader] = useState(fixHeader);

    // data in table
    const [rowData, setRowData] = useState([]);

    const [orderType, setOrderType] = useState([]);
    const [orderStatus, setOrderStatus] = useState([]);


    // callback from header
    const changeHeader = (data) => {
        setHeader(data);
    }

    const setKeyWord = (data) => {
        setRequest({ ...request, keyword: data });
    }

    useEffect(() => {
        updateData();
    }, [request,props])

    const updateData = () => {
        // eslint-disable-next-line react/prop-types
        if(props.config.table ==='order-detail'){
            axios({
                url: urlConfig.url,
                method: 'get',
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem('token'),
                    "Content-Type": 'application/json'
                },
                data: {},
            }).then(res => {
                console.log(res.data.data);
                setRowData(res.data.data.orderDetails)
            }).catch(err => {
            })
        }
        else{
            axios({
                url: urlConfig.url,
                method: 'post',
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem('token'),
                    "Content-Type": 'application/json'
                },
                data: request,
            }).then(res => {
                console.log(res);
                let start = 1, end = res.data.data.totalPages;
                if (end > 4) {
                    if (request.pageIndex >= 2 && request.pageIndex <= end - 2) {
                        start = request.pageIndex - 1;
                        end = request.pageIndex + 2;
                    }
                    else if (request.pageIndex > res.data.data.totalPages - 2) {
                        start = res.data.data.totalPages - 3;
                        end = res.data.data.totalPages;
                    }
                    else {
                        start = 1;
                        end = 4;
                    }
                }
    
                setTotalPage(Array(end - start + 1).fill().map((_, idx) => start + idx))
                setRowData(res.data.data.content)
            }).catch(err => {
                console.log(err);
            })
        }
        
    };

    const onGridReady = (params) => {

        const tableEvent = (columns) => {
            setHeader(columns.map((e, i) => {
                if (fixHeader[e.instanceId].field !== "Action")
                    return {
                        field: fixHeader[e.instanceId].field,
                        width: e.actualWidth,
                        sort: e.sort,
                        resizable: fixHeader[e.instanceId].resizable,
                        hide: !e.visible,
                        pinned: e.pinned,
                    }
                else
                    return {
                        field: fixHeader[e.instanceId].field,
                        width: e.actualWidth,
                        sort: e.sort,
                        resizable: false,
                        hide: false,
                        pinned: 'right',
                        cellRenderer: "cellActionComponent"
                    }
            }))
        }

        // updateData(data)

        // config sort in server
        fixHeader.forEach((el, i) => {
            // add lắng nghe sự kiện cho từng header
            const column = params.columnApi.getColumn(el.field);
            column.addEventListener('sortChanged', function (e) {
                let sort = e.column.sort;
                debugger
                if (sort === 'asc') {
                    setRequest({ ...request, asc: e.column.colId })
                }
                else if (sort === 'desc') {
                    setRequest({ ...request, desc: e.column.colId })
                }
                else {
                    setRequest({ ...request, asc: '', desc: '' })
                }
            });

            column.addEventListener('visibleChanged', function (e) {
                let columns = params.columnApi.columnModel.gridColumns;
                tableEvent(columns);
            });

            column.addEventListener('movingChanged', function (e) {
                try {
                    if (!e.column.moving) {
                        //let columns = params.columnApi.columnModel.gridColumns;
                        //tableEvent(columns);
                        return;
                    }
                }
                catch {

                }

            });
        })
    };

    const deleteRow = (node) => {
        alert(JSON.stringify(node.data));
    }
    
    const editRow = (node) => {
        // eslint-disable-next-line react/prop-types
        props.editData(node.data)
        //alert(JSON.stringify(node.data));
    }

    const btnCreateClick = () => {
        // eslint-disable-next-line react/prop-types
        props.btnCreateClick()
    }


    // mở rộng order

    useEffect(() => {
        axios({
            url: "http://localhost:8081/api/common/system-keys",
            method: 'get',
            headers: {
                "Authorization": "Bearer " + localStorage.getItem('token'),
                "Content-Type": 'application/json'
            },
            data: {},
        }).then(res => {
            setOrderStatus(res.data.data.ORDER_STATUS)
            setOrderType(res.data.data.ORDER_TYPE)
        }).catch(err => {
            console.log(err);
        })
    }, [])


    function ChangeStatus(id,val){
        if(id>0)
            axios({
                url: `http://localhost:8081/api/orders/${val==1?"approve":"reject"}/${id}`,
                method: 'get',
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem('token'),
                    "Content-Type": 'application/json'
                },
                data: {},
            }).then(res => {
                swal("Bạn vừa đổi trạng thái đơn hàng")
            }).catch(err => {
                console.log(err);
            })
    }

    return <div className="ag-theme-alpine" style={{ height: 500 }}>
        <HeaderTable prop={header} callbackHeader={changeHeader} callbackKeyWord={setKeyWord} btnCreateClick={btnCreateClick} />
        <AgGridReact
            rowData={rowData}
            onGridReady={onGridReady}
            defaultColDef={{
                sortable: true,
                resizable: true,
            }}
            suppressDragLeaveHidesColumns={true}
            animateRows={true}
            frameworkComponents={{
                cellActionComponent: (node) => {
                    return (
                        <div>
                            <CButton
                                onClick={() => editRow(node)}
                                color=""
                                size="sm">
                                <CIcon icon={cilPencil} className="" />
                            </CButton>

                            <CButton
                                onClick={() => deleteRow(node)}
                                color=""
                                size="sm">
                                <CIcon icon={cilTrash} className="text-danger" />
                            </CButton>
                        </div>)
                },
                cellLinkOrderDetail: (node) => {
                    return <Link to={{
                        pathname: `order-detail/${node.data.id}`,
                        state: { id: node.data.id }
                      }}
                    >Chi tiết</Link>
                },

                changeOrderStatus : (node) =>{
                    return (<CFormSelect aria-label="select" onChange={(e)=>ChangeStatus(node.data.id,e.target.value)}>
                                   <option selected={node.data.orderStatus === "PENDING"?true:false} value={0}>Đang chờ</option>
                                   <option selected={node.data.orderStatus === "CONFIRMED"?true:false} value={1}>Duyệt</option>
                                   <option selected={node.data.orderStatus === "CANCELED"?true:false} value={2}>Từ chối</option>
                            </CFormSelect>)
                }
            }}
        >
            {header.map(column => (<AgGridColumn {...column} key={column.field} hide={column.hide} />))}
        </AgGridReact>


        <div className="w-100 pt-2 d-flex justify-content-between border-top-0" style={{ border: "solid 1px #babfc7 !important" }}>
            <div>
                <CFormSelect size="sm" onChange={(e) => { setRequest({ ...request, pageSize: e.target.options[e.target.options.selectedIndex].innerText }) }}>
                    <option value="5">5</option>
                    <option value="10">10</option>
                    <option value="15">20</option>
                </CFormSelect>
            </div>

            <CPagination aria-label="Page navigation example" className="align-items-center justify-content-end m-0">
                <CPaginationItem aria-label="Previous" onClick={() => setRequest({ ...request, pageIndex: 1 })}>
                    <span aria-hidden="true">&laquo;</span>
                </CPaginationItem>
                {
                    totalPage.map((value, i) =>
                        <CPaginationItem key={i} active={value === request.pageIndex} onClick={() => setRequest({ ...request, pageIndex: value })}>{value}</CPaginationItem>)
                }
                <CPaginationItem aria-label="Next" onClick={() => setRequest({ ...request, pageIndex: request.totalPages })}>
                    <span aria-hidden="true">&raquo;</span>
                </CPaginationItem>
            </CPagination>
        </div>

    </div>
}
export default TableComponent