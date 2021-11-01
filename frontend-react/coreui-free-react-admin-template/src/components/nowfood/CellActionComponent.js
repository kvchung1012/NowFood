import React from 'react';

const CellActionComponent = (props) => {
  console.log(props)
  // eslint-disable-next-line react/prop-types
  const data = props.data;
  return (
    <span>
      <button className="btn btn-danger">Xóa</button>
    </span>
  );
};
export default CellActionComponent