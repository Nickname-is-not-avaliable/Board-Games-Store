async function fetchAPI(endpoint, callback = null) {
    try {
        const response = await fetch(`http://localhost:8080/api/${endpoint}`);
        const data = await response.json();
        if (callback) callback(data);
        return data;
    } catch (error) {
        console.error(`Error fetching ${endpoint}:`, error);
    }
}
