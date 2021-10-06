import React, { useState, useEffect } from "react";
import ProductService from "../services/ProductService";

function ListProductComponent() {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    getAllProducts();
  }, []);

  const getAllProducts = () => {
    ProductService.getAllProducts()
      .then((response) => {
        setProducts(response.data.content);
      })
      .catch((error) => console.log(error));
  };

  return (
    <div>
      <ul>
        {
          products.map(product => 
            <li key={product.id}> {product.name} </li>
          )
        }
      </ul>
    </div>
  );
}

ListProductComponent.propTypes = {};

export default ListProductComponent;
