import { useState } from "react";
import { createOrder, getOrderById } from "../api/orderApi";
import { useEffect } from "react";

function OrderPage() {

    const [orderId, setOrderId] = useState(null);
    const [status, setStatus] = useState("");
    const [loading, setLoading] = useState(false);

    const [userId, setUserId] = useState("");
    const [productId, setProductId] = useState("");
    const [quantity, setQuantity] = useState("");



    const handleCreate = async () => {
        setLoading(true);

        if (!userId || !productId || !quantity) {
            alert("Please fill all fields");
            return;
        }

        const res = await createOrder({
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

            {orderId && (
                <div className="status">
                    <p>Order ID: {orderId}</p>
                    <p style={{ color: getColor() }}>Status: {status}</p>
                </div>
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