async function loadGameRating(boardGameId) {
    const data = await fetchAPI(`ratings/by-board-game/${boardGameId}`);
    if (!data) return;

    const positiveReviews = data.filter(review => review.liked).length;
    const positivePercentage = data.length ? (positiveReviews / data.length) * 100 : null;
    updateRatingBar(positivePercentage, data.length);

    const currentUserId = getCookieValue("id");
    const userRating = data.find(review => review.userId == currentUserId);

    const likeButton = document.querySelector('.like-button');
    const dislikeButton = document.querySelector('.dislike-button');

    if (likeButton) likeButton.replaceWith(likeButton.cloneNode(true));
    if (dislikeButton) dislikeButton.replaceWith(dislikeButton.cloneNode(true));

    const newLikeButton = document.querySelector('.like-button');
    const newDislikeButton = document.querySelector('.dislike-button');

    newLikeButton.addEventListener('click', () => handleRating(boardGameId, currentUserId, true, userRating));
    newDislikeButton.addEventListener('click', () => handleRating(boardGameId, currentUserId, false, userRating));

    if (userRating) {
        if (userRating.liked) {
            newLikeButton.classList.add('active');
            newDislikeButton.classList.remove('active');
        } else {
            newLikeButton.classList.remove('active');
            newDislikeButton.classList.add('active');
        }
    } else {
        newLikeButton.classList.remove('active');
        newDislikeButton.classList.remove('active');
    }
}

async function handleRating(boardGameId, userId, liked, userRating) {
    if (!userId) {
        alert("Пожалуйста, зарегистрируйтесь, чтобы оценить игру.");
        return;
    }

    if (userRating) {
        if (userRating.liked === liked) {
            alert("Вы уже оценили эту игру.");
            return;
        } else {
            await updateRating(userRating.ratingId, liked);
        }
    } else {
        await addRating(boardGameId, userId, liked);
    }

    await loadGameRating(boardGameId);
}

async function addRating(boardGameId, userId, liked) {
    try {
        await fetch("http://localhost:8080/api/ratings", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ boardGameId, userId, liked })
        });
    } catch (error) {
        console.error("Error adding rating:", error);
    }
}

async function updateRating(ratingId, liked) {
    try {
        await fetch(`http://localhost:8080/api/ratings/${ratingId}`, {
            method: "PATCH",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ liked })
        });
    } catch (error) {
        console.error("Error updating rating:", error);
    }
}

function updateRatingBar(positivePercent, generalAmount) {
    const ratingContainer = document.querySelector('.rating-container');
    const positiveBar = document.querySelector('.rating-bar.positive');
    const negativeBar = document.querySelector('.rating-bar.negative');

    if (!ratingContainer || !positiveBar || !negativeBar) return;

    if (positivePercent !== null) {
        positiveBar.style.width = `${positivePercent}%`;
        negativeBar.style.width = `${100 - positivePercent}%`;
        ratingContainer.title = `${positivePercent.toFixed(1)}% положительных отзывов из ${generalAmount}`;
        ratingContainer.style.backgroundColor = 'transparent';
    } else {
        positiveBar.style.width = `0%`;
        negativeBar.style.width = `0%`;
        ratingContainer.title = `Игру никто пока не оценил`;
        ratingContainer.style.backgroundColor = 'gray';
    }
}

const boardGameId = new URL(window.location.href).searchParams.get("id");
loadGameRating(boardGameId);
