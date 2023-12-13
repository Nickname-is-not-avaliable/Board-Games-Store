function updateMovies(endpoint) {
    fetchAPI(`${endpoint}`, data => {
        data.forEach(film => {
            const filmCard = document.createElement("div");
            filmCard.classList.add("film-card");

            const filmImage = document.createElement("img");
            filmImage.src = film.previewImage;
            filmImage.alt = film.title;
            filmImage.title = film.title;
            filmImage.classList.add("film-image");

            const filmTitle = document.createElement("div");
            filmTitle.textContent = film.title;
            filmTitle.classList.add("film-title");

            filmCard.appendChild(filmImage);
            // filmCard.appendChild(filmTitle);
            filmContainer.appendChild(filmCard);

            filmCard.addEventListener("click", function () {
                window.location.href = `film.html?id=${film.id}`;
            });
        });
        if (getCookieValue("role") === "ADMIN") {
            const filmCard = document.createElement("div");
            filmCard.classList.add("film-card");

            const filmImage = document.createElement("img");
            filmImage.src = "http://localhost:8080/api/files/search/plus.png";
            filmImage.alt = "new";
            filmImage.title = "Добавить фильм";
            filmImage.classList.add("new-image");


            const filmTitle = document.createElement("div");
            filmTitle.textContent = "Добавить фильм";
            filmTitle.classList.add("film-title");

            filmCard.appendChild(filmImage);
            filmContainer.appendChild(filmCard);

            filmCard.addEventListener("click", function () {
                window.location.href = `addFilm.html`;
            });
        }
    });

}

const currentURL = window.location.href;
const urlObject = new URL(currentURL);
const filmId = urlObject.searchParams.get("id");
switch (filmId) {
    default:
        updateMovies('movies');
        break;
    case'POPULAR':
        updateMovies('movies/popular');
        break;
    case'BEST':
        updateMovies('movies/best');
        break;
    case'FAMILY':
        updateMovies('movies/genre?category=Семейный');
        break;
    case'DRAMA':
        updateMovies('movies/genre?category=Драма');
        break;
    case'FIGHTING':
        updateMovies('movies/genre?category=Боевик');
        break;
}