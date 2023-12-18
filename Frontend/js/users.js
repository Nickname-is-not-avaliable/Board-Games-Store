function updateUsers() {
    fetchAPI("users", data => {
        const tableBody = document.querySelector("#userTable tbody");
        tableBody.innerHTML = "";
        let i = 0;
        data.forEach((user) => {
            i++;
            const row = document.createElement("tr");
            row.innerHTML = `
                  <td>${i}</td>
                  <td>${user.username}</td>
                  <td>${user.email}</td>
                  <td>${user.dateOfRegistration}</td>
                  <td>
                      <button class="btn btn-outline-light" type="button" data-bs-toggle="modal" data-bs-target="#editUserModal" data-bs-whatever="@getbootstrap" onclick="showEditUserModal(${user.userId})">Редактировать</button>
                      <button type="button" class="btn btn-outline-danger" data-bs-toggle="modal" data-bs-target="#deleteUserModal" onclick="showDeleteUserModal(${user.userId})">Удалить</button>
                  </td>
              `;
            tableBody.appendChild(row);
        });
    });
}


function showEditUserModal(userId) {
    const modal = document.getElementById("editUserModal");

    fetch(`http://localhost:8080/api/users/${userId}`)
        .then((response) => response.json())
        .then((userData) => {
            document.getElementById("editUserId").value = userData.userId;
            document.getElementById("editUsername").value = userData.username;
            document.getElementById("editUserPassword").value = "oleg";
            document.getElementById("editUserEmail").value = userData.email;
            document.getElementById("editUserRole").value = userData.role;
        });

    submitEditButton.onclick = function () {
        const userId = document.getElementById("editUserId").value;
        const username = document.getElementById("editUsername").value;
        console.log(username);
        const password = document.getElementById("editUserPassword").value;
        const email = document.getElementById("editUserEmail").value;
        const role = document.getElementById("editUserRole").value;

        editUser(userId, username, password, email, role);
    };


    modal.style.display = "block";
}


function editUser(userId, username, password, email, role) {
    fetch(`http://localhost:8080/api/users/${userId}`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            username: username,
            password: password,
            email: email,
            role: role
        }),
    }).then((response) => {
        if (response.status === 200) {
            const modal = document.getElementById("editUserModal");
            modal.style.display = "none";
            updateUsers();
        }
    });
}

function showDeleteUserModal(userId) {
    const confirmButton = document.getElementById("confirmDeleteUser");
    confirmButton.onclick = function () {
        deleteUser(userId);
    };
}

function deleteUser(userId) {
    if (userId == getCookieValue("id")) {
        alert("Вы не можете удалить себя!");
    } else {
        fetch(`http://localhost:8080/api/users/${userId}`, {
            method: "DELETE",
        }).then((response) => {
            if (response.status === 204) {
                updateUsers();
            }
        });
    }
}

updateUsers();
