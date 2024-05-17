async function handleBuyButtonClick() {
    await handleOrderClick("CART");
}

async function handlePreorderButtonClick() {
    await handleOrderClick("PREORDER");
}

async function handleOrderClick(status) {
    const userId = getCookieValue("id");
    const productId = new URL(window.location.href).searchParams.get("id");
    if (!productId || !userId) return alert("Не удалось получить идентификатор продукта или пользователя");

    const productData = await fetchAPI(`board-games/${productId}`);
    if (!productData) return;

    const { title, price } = productData;

    try {
        const response = await fetch("http://localhost:8080/api/orders", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                userId: userId, status, orderDetails: title, totalPrice: price, orderDate: new Date()
            })
        });

        if (response.ok) {
            alert(status === "CART" ? "Заказ Добавлен в корзину" : "Предзаказ оформлен успешно");
        } else {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
    } catch (error) {
        console.error(`Error adding product to ${status.toLowerCase()}:`, error);
        alert(`Произошла ошибка при добавлении ${status === "CART" ? "заказа" : "предзаказа"}`);
    }
}
