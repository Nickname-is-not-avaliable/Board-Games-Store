function updateProducts(endpoint) {
    // Assuming productContainer is a global variable or declared somewhere accessible

    // Check if productContainer is defined
    if (typeof productContainer === 'undefined' || productContainer === null) {
        console.error('productContainer is not defined. Please define it before calling updateProducts.');
        return;
    }

    fetchAPI(`${endpoint}`, data => {
        data.forEach(product => {
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
            buyButton.textContent = "ПРЕДЗАКАЗ";
            buyButton.className = "btn btn-outline-success btn-lg mt-4";
            buyButton.style.width = "100%";

            const quantityContainer = document.createElement("div");
            quantityContainer.classList.add("quantity-container");
            quantityContainer.classList.add("mb-2");

            const quantityLabel = document.createElement("span");
            quantityLabel.textContent = "В наличии: ";
            quantityLabel.className = "ms-2 quantity-label text-white-50";

            const quantityValue = document.createElement("span");
            quantityValue.className = "quantity-value text-white-50";

            fetchAPI(`stocks/by-board-game/${product.id}`, stockDataArray => {
                const totalQuantity = stockDataArray.reduce((total, stockData) => {
                    return total + (stockData.quantity || 0);
                }, 0);

                if (totalQuantity > 0) {
                    quantityValue.textContent = totalQuantity;

                    quantityContainer.appendChild(quantityLabel);
                    quantityContainer.appendChild(quantityValue);

                    buyButton.textContent = "КУПИТЬ";
                    buyButton.className = "";
                    buyButton.className = "btn btn-primary btn-lg";
                    buyButton.style.width = "100%";

                }
            });

            preview.appendChild(image);
            preview.appendChild(title);
            preview.appendChild(price);
            preview.appendChild(quantityContainer);
            preview.appendChild(buyButton);

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
}


const currentURL = window.location.href;
const urlObject = new URL(currentURL);
const productId = urlObject.searchParams.get("id");
if(!productId)
{
    updateProducts('board-games');
}
else
{
    updateProducts(`board-games/search?searchString=${productId}`);
}