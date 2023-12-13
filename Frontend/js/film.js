function loadPage() {
    const currentURL = window.location.href;
    const urlObject = new URL(currentURL);
    const filmId = urlObject.searchParams.get("id");

    fetchAPI(`movies/${filmId}`, data => {
        const imgElement = document.querySelector("#previewImage");
        imgElement.src = data.previewImage;
        imgElement.alt = data.title;

        const vidElement = document.querySelector("#trailerLink");
        vidElement.src = data.trailerLink;

        const positiveReviewsPercentage = parseInt(data.positiveReviewsPercentage);

        loadInformationById("positiveReviewsPercentage", positiveReviewsPercentage + "% положительных отзывов", "span");
        loadInformationById("releaseYear", data.releaseYear, "span");
        loadInformationById("description", data.description, "p");
        loadInformationById("genre", data.genre, "span");
        loadInformationById("country", data.country, "span");
        loadInformationById("directors", data.directors, "span");
        loadInformationById("actors", data.actors, "span");
        loadInformationById("runtime", data.runtime + " мин", "span");
        loadInformationById("languages", data.languages, "span");
        loadInformationByClass("title", data.title, "span");
        loadComments(data.id);

        document.title = `${data.title} (${data.releaseYear})`;
    });

}

function loadComments(movieId) {
    const endpoint = `ratings/byMovieId/${movieId}`;
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
                <span class="comm-author js-author">${commentary.fullName}</span>
                <div class="comm-complaint">
                    <span class="fas fa-exclamation-triangle"></span>
                </div>
            </div>
        `;

        const commentTwo = document.createElement('div');
        commentTwo.className = 'comm-two clearfix full-text';

        const commentText = document.createElement('div');
        commentText.id = `comm-id-${commentary.id}`;
        commentText.textContent = commentary.comment;

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
    const movieId = urlObject.searchParams.get("id");
    const userId = getCookieValue("id");
    const commentText = document.getElementById('comments').value;
    const liked = true;
    console.log("http://localhost:8080/api/ratings")
    console.log(JSON.stringify({
        movieId: movieId, userId: userId, comment: commentText, liked: liked
    }));
    if (getCookieValue('id')) {
        fetch("http://localhost:8080/api/ratings", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                movieId: movieId,
                userId: userId,
                liked: liked,
                comment: commentText
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