async function loadPage() {
    const productId = new URL(window.location.href).searchParams.get("id");
    const data = await fetchAPI(`board-games/${productId}`);
    if (!data) return;

    const {
        previewImage, title, reviewLink, releaseDate, description, category,
        price, publisher, numberOfPlayers, playtime, countryOfManufacture, age, id
    } = data;

    const elements = {
        img: document.querySelector("#previewImage"),
        vid: document.querySelector("#trailerLink"),
        buyBtnContainer: document.getElementById("buyButtonContainer")
    };

    elements.img.src = previewImage;
    elements.img.alt = title;
    elements.vid.src = reviewLink;

    const info = {
        releaseYear: [releaseDate, "span"], description: [description, "p"],
        category: [category, "a"], price: [price, "span"], publisher: [publisher, "span"],
        numberOfPlayers: [numberOfPlayers, "span"], playtime: [`${playtime} мин`, "span"],
        countryOfManufacture: [countryOfManufacture, "span"], age: [`${age}+ лет`, "span"]
    };

    Object.keys(info).forEach(key => loadInformationById(key, ...info[key]));
    loadInformationByClass('title', title, 'span');

    loadComments(id);
    await loadGameRating(id);

    document.title = title;

    fetch(`http://localhost:8080/api/stocks/by-board-game/${productId}`)
        .then(res => res.json())
        .then(stockDataArray => {
            const totalQuantity = stockDataArray.reduce((total, stock) => total + (stock.quantity || 0), 0);

            const button = document.createElement("a");
            button.classList.add("btn", "ny-img", totalQuantity > 0 ? "btn-primary" : "btn-outline-success");
            button.textContent = totalQuantity > 0 ? "КУПИТЬ" : "ПРЕДЗАКАЗ";
            button.addEventListener("click", totalQuantity > 0 ? handleBuyButtonClick : handlePreorderButtonClick);

            elements.buyBtnContainer.appendChild(button);
        })
        .catch(error => console.error("Error fetching quantity information:", error));
}

loadPage();
