import { useState } from "react";
import { createOrder, getOrderById, getAllOrders, getOrdersByUser } from "../api/orderApi";
import { useEffect } from "react";

function OrderPage() {

    const [orderId, setOrderId] = useState(null);
    const [status, setStatus] = useState("");
    const [loading, setLoading] = useState(false);

    const [userId, setUserId] = useState("");
    const [productId, setProductId] = useState("");
    const [quantity, setQuantity] = useState("");

    const [orders, setOrders] = useState([]);



    const handleCreate = async () => {
        setLoading(true);

        if (!userId || !productId || !quantity) {
            alert("Please fill all fields");
             setLoading(false);
            return;
        }
        const requestId = crypto.randomUUID();
       // const requestId = "test-123";

        const res = await createOrder({
            requestId,
            userId: Number(userId),
            productId: Number(productId),
            quantity: Number(quantity)
        });

        setOrderId(res.data.orderId);
        setStatus(res.data.status);

        setLoading(false);
    };

    const handleFetch = async () => {
        if (!orderId) return;

        const res = await getOrderById(orderId);
        setStatus(res.data.status);
    };

    const handleGetByUser = async () => {

        if (!userId) {
            alert("Enter userId first");
            return;
        }

        try {
            const res = await getOrdersByUser(userId);
            setOrders(res.data);
        } catch (err) {
            console.error(err);
        }
    };

    const handleGetAll = async () => {
        const res = await getAllOrders();
        setOrders(res.data);
    };

    useEffect(() => {
        if (!orderId) return;

        const interval = setInterval(async () => {
            try {
                const res = await getOrderById(orderId);
                setStatus(res.data.status);

                if (res.data.status === "COMPLETED" || res.data.status === "FAILED") {
                    clearInterval(interval);
                }

            } catch (err) {
                console.error(err);
            }
        }, 2000);

        return () => clearInterval(interval);

    }, [orderId]);

    return (
        <div className="container">
            <h2>OrderFlow System</h2>

            <input
                type="number"
                placeholder="User ID"
                value={userId}
                onChange={(e) => setUserId(e.target.value)}
            />

            <input
                type="number"
                placeholder="Product ID"
                value={productId}
                onChange={(e) => setProductId(e.target.value)}
            />

            <input
                type="number"
                placeholder="Quantity"
                value={quantity}
                onChange={(e) => setQuantity(e.target.value)}
            />

            <button onClick={handleCreate} disabled={loading}>
                {loading ? "Creating..." : "Create Order"}
            </button>

            <button onClick={handleFetch} disabled={!orderId}>
                Check Status
            </button>
            <button onClick={handleGetByUser}>
                View My Orders
            </button>
            <button onClick={handleGetAll}>
                View All Orders
            </button>

            {orderId && (
                <div className="status">
                    <p>Order ID: {orderId}</p>
                    <p style={{ color: getColor() }}>Status: {status}</p>
                </div>
            )}

            {orders.length > 0 && (
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>User</th>
                            <th>Product</th>
                            <th>Qty</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        {orders.map((o) => (
                            <tr key={o.id}>
                                <td>{o.id}</td>
                                <td>{o.userId}</td>
                                <td>{o.productId}</td>
                                <td>{o.quantity}</td>
                                <td>{o.status}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}


const getColor = () => {
    if (status === "COMPLETED") return "green";
    if (status === "FAILED") return "red";
    if (status === "PROCESSING") return "orange";
    return "black";
};

export default OrderPage;