function updateEditUser(userId) {
console.log(userId);
    fetch(`http://localhost:8080/api/users/${userId}`)
        .then((response) => response.json())
        .then((userData) => {
            document.getElementById("editUserId").value = userData.id;
            document.getElementById("editUserFullName").value = userData.fullName;
            document.getElementById("editUserPassword").value = "oleg";
            document.getElementById("editUserEmail").value = userData.email;
        });

    submitEditButton.onclick = function () {
        const userId = document.getElementById("editUserId").value;
        const fullName = document.getElementById("editUserFullName").value;
        console.log(fullName);
        const password = document.getElementById("editUserPassword").value;
        const email = document.getElementById("editUserEmail").value;

        editUser(userId, fullName, password, email);
    };

    function editUser(userId, fullName, password, email) {
        fetch(`http://localhost:8080/api/users/${userId}`, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                fullName: fullName,
                password: password,
                email: email,
            }),
        }).then((response) => {
            if (response.status === 200) {
                const modal = document.getElementById("editUserModal");
                modal.style.display = "none";
                updateUsers();
            }
        });
    }
}

updateEditUser(/*getCookieValue('id')*/'1');