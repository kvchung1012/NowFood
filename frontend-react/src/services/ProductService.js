import axios from "axios";

const PRODUCT_REST_API_URL = "http://localhost:8081/api/product/";

class ProductService {
    getAllProducts = () => axios.get(PRODUCT_REST_API_URL+'searchByPage');
}

export default new ProductService();