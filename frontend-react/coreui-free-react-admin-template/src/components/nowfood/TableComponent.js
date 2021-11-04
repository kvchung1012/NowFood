import { React, useState } from 'react'
import { AgGridColumn, AgGridReact } from 'ag-grid-react'

import { CPagination, CPaginationItem } from '@coreui/react';

import { CButton } from '@coreui/react'
import CIcon from '@coreui/icons-react'
import { cilTrash, cilPencil } from '@coreui/icons'

import 'ag-grid-community/dist/styles/ag-grid.css';
import 'ag-grid-community/dist/styles/ag-theme-alpine.css';
import HeaderTable from './HeaderTable';

const TableComponent = () => {
    const head = [
        {
            field: 'Id',
            flex: 1,
            sort: null,
            resizable: true,
            hide: false,
            pinned: ''
        },
        {
            field: 'Code',
            flex: 1,
            sort: null,
            resizable: true,
            hide: false,
            pinned: ''
        },
        {
            field: 'Name',
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
    const data = [
        {
            Id: 1,
            Code: 'C00',
            Name: 'Khuất văn Chung'
        },
        {
            Id: 2,
            Code: 'C02',
            Name: 'Khuất văn Chung'
        },
        {
            Id: 3,
            Code: 'C03',
            Name: 'Khuất văn Chung'
        },
        {
            Id: 4,
            Code: 'C04',
            Name: 'Khuất văn Chung'
        },
        {
            Id: 5,
            Code: 'C05',
            Name: 'Khuất văn Chung'
        },
    ]
    const pagination = {
        pageNumber: 1,
        pageCount: 3
    }

    const [rowData, setRowData] = useState(data);
    const [header, setHeader] = useState(head);

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
            setRowData(data);
        };

        const tableEvent = (columns) => {
            setHeader(columns.map((e, i) => {
                if (head[e.instanceId].field !== "Action")
                    return {
                        field: head[e.instanceId].field,
                        width: e.actualWidth,
                        sort: e.sort,
                        resizable: head[e.instanceId].resizable,
                        hide: !e.visible,
                        pinned: e.pinned,
                    }
                else
                    return {
                        field: head[e.instanceId].field,
                        width: e.actualWidth,
                        sort: e.sort,
                        resizable: false,
                        hide: false,
                        pinned: 'right',
                        cellRenderer: "cellActionComponent"
                    }
            }))
        }

        updateData(data)

        // config sort in server
        head.forEach((el, i) => {
            // add lắng nghe sự kiện cho từng header
            const column = params.columnApi.getColumn(el.field);
            column.addEventListener('sortChanged', function (e) {
                let sort = e.column.sort === 'asc' || e.column.sort == null ? true : false;
                updateData(sort ? data : data.sort((a, b) => data[a] - data[b]))
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