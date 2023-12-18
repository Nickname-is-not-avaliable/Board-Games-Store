function updateEditUser(userId) {
console.log(userId);
    fetch(`http://localhost:8080/api/users/${userId}`)
        .then((response) => response.json())
        .then((userData) => {
            document.getElementById("editUserId").value = userData.userId;
            document.getElementById("editUsername").value = userData.username;
            document.getElementById("editUserPassword").value = "oleg";
            document.getElementById("editUserEmail").value = userData.email;
        });
}

submitEditButton.onclick = function () {
    const userId = document.getElementById("editUserId").value;
    const username = document.getElementById("editUsername").value;
    console.log(username);
    const password = document.getElementById("editUserPassword").value;
    const email = document.getElementById("editUserEmail").value;

    editUser(userId, username, password, email);
};

function editUser(userId, username, password, email) {
    fetch(`http://localhost:8080/api/users/${userId}`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            username: username,
            password: password,
            email: email,
        }),
    }).then((response) => {
        if (response.status === 200) {
            alert("Данные успешно изменены");
            updateUsers();
        }
        else {
            alert("Произола ошибка. Проверьте правильность введённых данных");
        }
    });
}

updateEditUser(getCookieValue('id'));