if (getCookieValue("role") === "ADMIN") {
chandgePageElement('sidebar','Пользователи','users.html');
}

if (getCookieValue("role") === "ADMIN" || getCookieValue("role") === "ADMIN") {
chandgePageElement('enter','Настройки','settings.html');
}

if (window.location.pathname.endsWith("settings.html")) {
    chandgePageElement('enter','Выйти','logout.html');
    const divElement = document.querySelector(`#enter`);
}

function chandgePageElement(selector,name,href){
    const divElement = document.querySelector(`#${selector}`);
    divElement.innerHTML = "";
    const tag = document.createElement(`a`);
    tag.innerHTML = `${name}`;
    tag.href = `${href}`;
    tag.className = 'link-offset-2 link-light link-underline link-underline-opacity-0';
    divElement.appendChild(tag);
}