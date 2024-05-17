function loadInformationById(id, text, tagName) {
    const someDiv = document.querySelector(`#${id}`);
    if (!someDiv) {
        console.warn(`Element with ID '${id}' not found.`);
        return;
    }
    someDiv.innerHTML = "";
    const tag = document.createElement(tagName);
    tag.innerHTML = text;
    someDiv.appendChild(tag);
}

function loadInformationByClass(className, text, tagName) {
    const someDivs = document.querySelectorAll(`.${className}`);
    if (someDivs.length === 0) {
        console.warn(`Elements with class '${className}' not found.`);
        return;
    }
    someDivs.forEach(divElement => {
        divElement.innerHTML = "";
        const tag = document.createElement(tagName);
        tag.innerHTML = text;
        divElement.appendChild(tag);
    });
}
