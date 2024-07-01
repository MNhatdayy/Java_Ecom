import axios from "axios";
export const fetchOrders = async (setOrders,notification) =>{
    try {
      
        const response = await axios.get('http://localhost:8099/api/orders');
        setOrders(response.data);
      } catch (error) {
        console.error('Error fetching orders:', error);
        notification.error({
          message: 'Fetch Error',
          description: 'Failed to fetch orders.',
        });
      }
};
export const fetchOrderDetails = async (id,getProductById,setOrderDetails,setLoading) =>{
    try {
        const response = await axios.get(`http://localhost:8099/api/detail/${id}`);
        const orderDetails = response.data;

        // Fetch additional product details
        const updatedOrderDetails = await Promise.all(orderDetails.map(async (orderDetail) => {
            const product = await getProductById(orderDetail.productId);
            return {
                ...orderDetail,
                imageUrl: product.imageUrl,
                unitPrice: product.price,
                totalPrice: product.price * orderDetail.quantity,
            };
        }));

        setOrderDetails(updatedOrderDetails);
        setLoading(false);
    } catch (error) {
        console.error('Error fetching order details:', error);
        setLoading(false);
    }
}