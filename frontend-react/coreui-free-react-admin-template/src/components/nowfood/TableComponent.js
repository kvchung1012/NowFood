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
const TableComponent = (props) => {

    const [request, setRequest] = useState({
        pageIndex: 1,
        pageSize: 5,
        keyWord: '',
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
    const [urlConfig, setUrlConfig] = useState(props.url);

    // header trong agrid
    const [header, setHeader] = useState(fixHeader);

    // data in table
    const [rowData, setRowData] = useState([]);


    // callback from header
    const changeHeader = (data) => {
        setHeader(data);
    }

    const setKeyWord = (data) => {
        setRequest({ ...request, keyWord: data });
    }

    useEffect(() => {
        updateData();
    }, [request])

    const updateData = () => {
        axios({
            url: urlConfig.url,
            method: 'post',
            headers: {
                "Authorization": "Bearer " + localStorage.getItem('token'),
                "Content-Type": 'application/json'
            },
            data: request,
        }).then(res => {

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
                let sort = e.column.sort === 'asc' || e.column.sort == null ? true : false;
                updateData([])
            });

            column.addEventListener('visibleChanged', function (e) {
                let columns = params.columnApi.columnModel.gridColumns;
                tableEvent(columns);
            });

            column.addEventListener('movingChanged', function (e) {
                if (!e.column.moving) {
                    let columns = params.columnApi.columnModel.gridColumns;
                    tableEvent(columns);
                }
            });
        })
    };

    const deleteRow = (node) => {
        alert(JSON.stringify(node.data));
    }

    const editRow = (node) => {
        alert(JSON.stringify(node.data));
    }

    return <div className="ag-theme-alpine" style={{ height: 450 }}>
        <HeaderTable prop={header} callbackHeader={changeHeader} callbackKeyWord={setKeyWord} />
        <AgGridReact
            rowData={rowData}
            onGridReady={onGridReady}
            defaultColDef={{
                sortable: true,
                resizable: true,
            }}
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
                <CPaginationItem aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </CPaginationItem>
                {
                    totalPage.map((value, i) =>
                        <CPaginationItem key={i} active={value === request.pageIndex} onClick={() => setRequest({ ...request, pageIndex: value })}>{value}</CPaginationItem>)
                }
                <CPaginationItem aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </CPaginationItem>
            </CPagination>
        </div>

    </div>
}
export default TableComponent