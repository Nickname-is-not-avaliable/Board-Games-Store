function loadPage() {
    const currentURL = window.location.href;
    const urlObject = new URL(currentURL);
    const productId = urlObject.searchParams.get("id");

    fetchAPI(`board-games/${productId}`, data => {
        const imgElement = document.querySelector("#previewImage");
        imgElement.src = data.previewImage;
        imgElement.alt = data.title;

        const vidElement = document.querySelector("#trailerLink");
        vidElement.src = data.reviewLink;

        loadInformationById("releaseYear", data.releaseDate, "span");
        loadInformationById("description", data.description, "p");
        loadInformationById("category", data.category, "category");
        loadInformationById("price", data.price, "span");
        loadInformationById("publisher", data.publisher, "span");
        loadInformationById("numberOfPlayers", data.numberOfPlayers, "span");
        loadInformationById("playtime", data.playtime + " мин", "span");
        loadInformationById("countryOfManufacture", data.countryOfManufacture, "span");
        loadInformationById("age", data.age + "+ лет", "span");
        loadInformationByClass("title", data.title, "span");
        loadComments(data.id);

        document.title = `${data.title}`;

        fetch(`http://localhost:8080/api/stocks/by-board-game/${productId}`)
            .then(response => response.json())
            .then(stockDataArray => {
                const totalQuantity = stockDataArray.reduce((total, stockData) => {
                    return total + (stockData.quantity || 0);
                }, 0);

                const buyButtonContainer = document.getElementById("buyButtonContainer");

                const button = document.createElement("a");
                button.classList.add("btn", "ny-img");

                if (totalQuantity > 0) {
                    button.classList.add("btn-primary");
                    button.textContent = "КУПИТЬ";
                    button.addEventListener("click", handleBuyButtonClick);
                } else {
                    button.classList.add("btn-outline-success");
                    button.textContent = "ПРЕДЗАКАЗ";
                    button.addEventListener("click", handlePreorderButtonClick);
                }

                buyButtonContainer.appendChild(button);
            })
            .catch(error => {
                console.error("Error fetching quantity information:", error);
                console.log("Network error details:", error.message);
            });

        function handleBuyButtonClick() {
            console.log("Buy button clicked!");
        }

        function handlePreorderButtonClick() {
            console.log("Preorder button clicked!");
        }
    });

}

function loadComments(productId) {
    const endpoint = `comments/by-board-game/${productId}`;
    fetchAPI(endpoint, updateComments);
}

function updateComments(data) {
    const commentsTreeList = document.getElementById('commentsTreeList');
    commentsTreeList.innerHTML = '';

    data.forEach(commentary => {
        const commentItem = document.createElement('li');
        commentItem.className = 'comments-tree-item';
        commentItem.id = `comments-tree-item-${commentary.id}`;

        const commentContainer = document.createElement('div');
        commentContainer.id = `comment-id-${commentary.id}`;
        commentContainer.className = 'comm-item js-comm';

        const commentAvatar = `
            <div class="comm-avatar img-box img-fit js-avatar">
                <div class="comm-letter"> </div>
            </div>
        `;

        const commentRight = document.createElement('div');
        commentRight.className = 'comm-right fx-1';

        const commentOne = `
            <div class="comm-one nowrap">
                <span class="comm-author js-author">${commentary.username}</span>
            </div>
        `;

        const commentTwo = document.createElement('div');
        commentTwo.className = 'comm-two clearfix full-text';

        const commentText = document.createElement('div');
        commentText.id = `comm-id-${commentary.id}`;
        commentText.textContent = commentary.text;

        const deleteButton = document.createElement('button');

        if (getCookieValue("role") === "ADMIN") {
            deleteButton.textContent = 'Удалить';
            deleteButton.className = 'btn btn-danger btn-sm ml-2';
            deleteButton.addEventListener('click', function (event) {
                deleteButton.onclick = (event) => {
                    event.preventDefault();
                    deleteComment(commentary.id);
                };

            });
        }
        commentTwo.appendChild(commentText);
        if (getCookieValue("role") === "ADMIN") {
            commentTwo.appendChild(deleteButton); // Добавляем кнопку к commentTwo
        }

        commentRight.innerHTML = commentOne;
        commentContainer.innerHTML = commentAvatar + commentRight.outerHTML + commentTwo.outerHTML;
        commentItem.appendChild(commentContainer);
        commentsTreeList.appendChild(commentItem);
    });
}

function deleteComment(commentId) {
    const endpoint = `ratings/${commentId}`;
    fetchAPI(endpoint, loadComments, { method: 'DELETE' });
}

function addComment(event) {
    event.preventDefault();
    const currentURL = window.location.href;
    const urlObject = new URL(currentURL);
    const boardGameId = urlObject.searchParams.get("id");
    const userId = getCookieValue("id");
    const commentText = document.getElementById('comments').value;
    console.log("http://localhost:8080/api/comments")
    console.log(JSON.stringify({
        boardGameId: boardGameId, userId: userId, text: commentText
    }));
    if (getCookieValue('id')) {
        fetch("http://localhost:8080/api/comments", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                boardGameId: boardGameId,
                userId: userId,
                text: commentText
            })

        }).then((response) => {
            if (response.status === 201) {
            } else {
                alert("Максимальная длина комментария 2048 символов.");
            }
        });
        loadPage();
        document.getElementById('comments').value = '';
    }
    else{ alert("Пожалуйста, зарегистрируйтесь на сайте, чтобы оставлять комментарий")}
}

loadPage();