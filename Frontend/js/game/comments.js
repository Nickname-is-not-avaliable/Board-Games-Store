function formatDate(dateString) {
    const date = new Date(dateString);
    if (isNaN(date)) {
        return 'Invalid Date';
    }
    
    const options = {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    };
    
    return date.toLocaleDateString('ru-RU', options);
}

async function loadComments(productId) {
    const data = await fetchAPI(`comments/by-board-game/${productId}`);
    if (!data) return;

    const commentsTreeList = document.getElementById('commentsTreeList');
    commentsTreeList.innerHTML = '';

    const currentUserId = getCookieValue("id");

    data.forEach(({ commentId, username, userId, text, date }) => {
        const commentItem = document.createElement('li');
        commentItem.className = 'comments-tree-item';
        commentItem.id = `comments-tree-item-${commentId}`;

        const formattedDate = formatDate(date);

        const deleteButton = (getCookieValue("role") === "ADMIN" || userId == currentUserId)
            ? `<button class="btn btn-danger btn-sm ml-2 mt-1" type="button" onclick="deleteComment(${commentId})">Удалить</button>`
            : '';

        commentItem.innerHTML = `
            <div id="comment-id-${commentId}" class="comm-item js-comm">
                <div class="comm-avatar img-box img-fit js-avatar">
                    <div class="comm-letter"> </div>
                </div>
                <div class="comm-right fx-1">
                    <div class="comm-one nowrap">
                        <span class="comm-author js-author">${username}</span>
                        <span class="comm-date">${formattedDate}</span>
                    </div>
                </div>
                <div class="comm-two clearfix full-text">
                    <div id="comm-id-${commentId}">${text}</div>
                    ${deleteButton}
                </div>
            </div>
        `;
        commentsTreeList.appendChild(commentItem);
    });
}

async function deleteComment(commentId) {
    try {
        const response = await fetch(`http://localhost:8080/api/comments/${commentId}`, { method: "DELETE" });
        if (response.ok) {
            document.getElementById(`comments-tree-item-${commentId}`).remove();
            loadComments(new URL(window.location.href).searchParams.get("id"));
        } else {
            throw new Error('Failed to delete comment');
        }
    } catch (error) {
        console.error('Delete comment error:', error);
    }
}

async function addComment(event) {
    event.preventDefault();
    const commentText = document.getElementById('comments').value;
    const userId = getCookieValue("id");
    const boardGameId = new URL(window.location.href).searchParams.get("id");

    if (userId) {
        try {
            const response = await fetch("http://localhost:8080/api/comments", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ boardGameId, userId, text: commentText })
            });
            if (response.ok) {
                loadComments(boardGameId);
                document.getElementById('comments').value = '';
            } else {
                alert("Максимальная длина комментария 2048 символов.");
            }
        } catch (error) {
            console.error("Error adding comment:", error);
            alert("Произошла ошибка при добавлении комментария");
        }
    } else {
        alert("Пожалуйста, зарегистрируйтесь на сайте, чтобы оставлять комментарий");
    }
}
