function performSearch() {
    const searchInput = document.getElementById("searchInput").value.trim();
    if (searchInput) {
        window.location.href = `catalog.html?id=${encodeURIComponent(searchInput)}`;
    }
}

document.getElementById("enterButton").addEventListener("click", performSearch);
document.getElementById("searchForm").addEventListener("submit", function (event) {
    event.preventDefault();
    performSearch();
});