import { React, useState } from 'react'
import { AgGridColumn, AgGridReact } from 'ag-grid-react'

import { CPagination, CPaginationItem } from '@coreui/react';

import { CButton } from '@coreui/react'
import CIcon from '@coreui/icons-react'
import { cilTrash, cilPencil } from '@coreui/icons'

import 'ag-grid-community/dist/styles/ag-grid.css';
import 'ag-grid-community/dist/styles/ag-theme-alpine.css';
import HeaderTable from './HeaderTable';
import axios from 'axios'
const TableComponent = (props) => {

    const pagination = {
        pageNumber: 1,
        pageCount: 3,
        pageSize: 10
    }

    // eslint-disable-next-line react/prop-types
    const [fixHeader, setFixHeader] = useState(props.header);
    // eslint-disable-next-line react/prop-types
    const [urlConfig, setUrlConfig] = useState(props.url);
    const [header, setHeader] = useState(fixHeader);
    const [rowData, setRowData] = useState([]);

    const deleteRow = (node) => {
        alert(JSON.stringify(node.data));
    }

    const editRow = (node) => {
        alert(JSON.stringify(node.data));
    }

    const changeView = (data) => {
        setHeader(data);
    }

    const onGridReady = (params) => {
        const updateData = (data) => {
            axios({
                url: urlConfig.url,
                method: 'get',
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem('token'),
                    "Content-Type": 'application/json'
                }
            }).then(res => {
                console.log(res)
                setRowData(res.data)
            }).catch(err => {
                console.log(err);
            })
        };

        updateData([])

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

    return <div className="ag-theme-alpine" style={{ height: 450 }}>
        <HeaderTable prop={header} callback={changeView} />
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


        <div className="w-100 bg-white border-bottom border-start d-flex justify-content-end">
            <CPagination aria-label="Page navigation example" className="align-items-center justify-content-end m-0">
                <CPaginationItem aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </CPaginationItem>
                {
                    Array.from(Array(pagination.pageCount).keys()).map((value, i) => <CPaginationItem key={i} >{i + 1}</CPaginationItem>)
                }
                <CPaginationItem aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </CPaginationItem>
            </CPagination>
        </div>

    </div>
}
export default TableComponent