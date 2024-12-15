function updateProducts(endpoint) {


    if (typeof productContainer === 'undefined' || productContainer === null) {
        console.error('productContainer is not defined. Please define it before calling updateProducts.');
        return;
    }

    fetchAPI(`${endpoint}`, data => {

        fetchAPI(`${endpoint}`, async data => {
            const isPopular = urlObject.searchParams.get("popular") === "1";
            const isBest = urlObject.searchParams.get("best") === "1";

            let stockFetchPromises = [];

            data.forEach(product => {
                const stockPromise = new Promise((resolve, reject) => {
                    fetchAPI(`stocks/by-board-game/${product.id}`, stockDataArray => {
                        const totalQuantity = stockDataArray.reduce((total, stockData) => {
                            return total + (stockData.quantity || 0);
                        }, 0);
                        product.totalQuantity = totalQuantity;
                        resolve();
                    });
                });
                stockFetchPromises.push(stockPromise);
            });

            Promise.all(stockFetchPromises).then(() => {
                if (isPopular) {
                    data.sort((a, b) => {
                        const quantityA = a.totalQuantity || 0;
                        const quantityB = b.totalQuantity || 0;
                        return quantityB - quantityA;
                    });
                }

                if (isBest) {
                    data.sort((a, b) => {
                        const priceA = a.price || 0;
                        const priceB = b.price || 0;
                        return priceB - priceA;
                    });
                }


                data.forEach(product => {

                    const productId = document.createElement("span");
                    productId.textContent = product.id;
                    productId.style.display = "none";

                    const preview = document.createElement("div");
                    preview.classList.add("product-card");

                    const image = document.createElement("img");
                    image.src = product.previewImage;
                    image.alt = product.title;
                    image.title = product.title;
                    image.classList.add("product-image");

                    const title = document.createElement("div");
                    title.textContent = product.title;
                    title.classList.add("product-title");

                    const price = document.createElement("h4");
                    price.textContent = `Цена: ${product.price} руб.`;
                    price.className = "ms-2 text-light";


                    const buyButton = document.createElement("button");
                    buyButton.style.display = "none";

                    const category = document.createElement("span");
                    category.textContent = `${product.category}  ${product.age || 0}+`;
                    category.className = "ms-2 text-light";
                    
                    buyButton.style.display = "inline-block";
                    buyButton.textContent = "★ В избранное";
                    buyButton.className = "btn btn-outline-success btn-lg";
                    buyButton.style.width = "100%";
                    buyButton.addEventListener("click", function () {
                        handleBuyButtonClick(product.id);
                    });
                    
                    preview.appendChild(image);
                    preview.appendChild(title);
                    preview.appendChild(price);
                    preview.appendChild(category);
                    preview.appendChild(buyButton);
                    preview.appendChild(productId);


                    productContainer.appendChild(preview);

                    preview.addEventListener("click", function () {
                        window.location.href = `game.html?id=${product.id}`;
                    });

                    buyButton.addEventListener("click", function (event) {
                        console.log(`Buy button clicked for product ID: ${product.id}`);
                        event.stopPropagation();
                    });
                });

                if (getCookieValue("role") === "ADMIN") {
                    const productCard = document.createElement("div");
                    productCard.classList.add("product-card");

                    const productImage = document.createElement("img");
                    productImage.src = "http://localhost:8080/api/files/search/plus.png";
                    productImage.alt = "new";
                    productImage.title = "Добавить товар";
                    productImage.classList.add("new-image");

                    const productTitle = document.createElement("div");
                    productTitle.textContent = "Добавить товар";
                    productTitle.classList.add("product-title");

                    productCard.appendChild(productImage);
                    productContainer.appendChild(productCard);

                    productCard.addEventListener("click", function () {
                        window.location.href = `addGame.html`;
                    });
                }
            });
        });
    });
}

function handleBuyButtonClick(productId) {

    const userId = getCookieValue("id");

    if (productId && userId) {
        fetch(`http://localhost:8080/api/board-games/${productId}`)
            .then(response => response.json())
            .then(productData => {
                const title = productData.title;
                const price = productData.price;

                const currentDate = new Date();


                fetch("http://localhost:8080/api/orders", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        userId: userId,
                        status: "CART",
                        orderDetails: title,
                        totalPrice: price,
                        orderDate: currentDate
                    }),
                })
                    .then(response => {
                        if (response.ok) {
                            alert("Игра добавлена в избранное");
                        } else {
                            throw new Error(`HTTP error! Status: ${response.status}`);
                        }
                    })
                    .catch(error => {
                        console.error("Error adding product to order:", error);
                        alert("Произошла ошибка при добавлении игры в избранное");
                    });
            })
            .catch(error => {
                console.error("Error fetching product details:", error);
                alert("Произошла ошибка при получении деталей товара");
            });
    } else {
        alert("Не удалось получить идентификатор продукта или пользователя");
    }
}

const currentURL = window.location.href;
const urlObject = new URL(currentURL);
const productId = urlObject.searchParams.get("id");
const productCategory = urlObject.searchParams.get("category");
if (!productId && !productCategory) {
    updateProducts('board-games');
} else if (productId) {
    updateProducts(`board-games/search?searchString=${productId}`);
} else {
    updateProducts(`board-games/by-category/${productCategory}`);
}