async function uploadFile() {
    const fileInput = document.getElementById('previewImage');
    const file = fileInput.files[0];

    const formData = new FormData();
    formData.append('file', file);

    try {
        const response = await fetch("http://localhost:8080/api/files/uploadFile", {
            method: "POST",
            body: formData
        });
    } catch (error) {
        console.error('Произошла ошибка:', error);
    }
}

document
    .getElementById("createFilmForm")
    .addEventListener("submit", function (event) {
        event.preventDefault();
        const title = document.getElementById("title").value;
        const description = document.getElementById("description").value;
        const category = document.getElementById("category").value;
        const releaseDate = document.getElementById("releaseDate").value;
        const countryOfManufacture = document.getElementById("countryOfManufacture").value;
        const price = parseFloat(document.getElementById("price").value);
        const numberOfPlayers = parseInt(document.getElementById("numberOfPlayers").value);
        const age = parseInt(document.getElementById("age").value);
        const playtime = document.getElementById("playtime").value;
        const publisher = document.getElementById("publisher").value;
        const reviewLink = document.getElementById("reviewLink").value;

        const inputElement = document.getElementById("previewImage");
        const selectedFile = inputElement.files[0];

        const previewImage = selectedFile.name;

        fetch("http://localhost:8080/api/board-games", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title: title,
                description: description,
                publisher: publisher,
                category: category,
                releaseDate: releaseDate,
                countryOfManufacture: countryOfManufacture,
                price: price,
                numberOfPlayers: numberOfPlayers,
                age: age,
                playtime: playtime,
                reviewLink: reviewLink,
                previewImage: "http://localhost:8080/api/files/search/" + previewImage
            })
        }).then((response) => {
            if (response.status === 201) {
                alert("Товар добавлен успешно");
            }
            else
            {
                alert("Произошла ошибка. Проверьте правильность введённых данных");
            }
        });
    })
;
