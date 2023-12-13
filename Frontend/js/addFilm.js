async function uploadFile() {
    const fileInput = document.getElementById('previewImage');
    const file = fileInput.files[0];

    const formData = new FormData();
    formData.append('file', file);

    try {
        const response = await fetch("http://localhost:8080/api/uploadFile", {
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
        const genre = document.getElementById("genre").value;
        const releaseYear = parseInt(document.getElementById("releaseYear").value);
        const country = document.getElementById("country").value;
        const directors = document.getElementById("directors").value;
        const actors = document.getElementById("actors").value;
        const runtime = parseInt(document.getElementById("runtime").value);
        const languages = document.getElementById("languages").value;
        const trailerLink = document.getElementById("trailerLink").value;

        const inputElement = document.getElementById("previewImage");
        const selectedFile = inputElement.files[0];

        const previewImage = selectedFile.name;

       /* console.log(title)
        console.log(description)
        console.log(genre)
        console.log(releaseYear)
        console.log(country)
        console.log(directors)
        console.log(actors)
        console.log(runtime)
        console.log(languages)
        console.log(trailerLink)
        console.log(previewImage);*/

        fetch("http://localhost:8080/api/movies", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title: title,
                description: description,
                genre: genre,
                releaseYear: releaseYear,
                country: country,
                directors: directors,
                actors: actors,
                runtime: runtime,
                languages: languages,
                trailerLink: trailerLink,
                previewImage: "http://localhost:8080/api/files/search/" + previewImage
            })
        }).then((response) => {
            if (response.status === 201) {
                alert("Фильм добавлен успешно");
            }
            else
            {
                alert("Произошла ошибка. Проверьте правильность введённых данных");
            }
        });
    })
;
