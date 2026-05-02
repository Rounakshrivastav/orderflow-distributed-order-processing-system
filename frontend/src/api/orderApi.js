import axios from "axios";

const BASE_URL = "http://localhost:8080/orders";

export const createOrder = (data) => {
    return axios.post(BASE_URL, data);
};

export const getOrderById = (id) => {
    return axios.get(`${BASE_URL}/${id}`);
};