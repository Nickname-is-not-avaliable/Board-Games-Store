if (getCookieValue("role") === "ADMIN") {
chandgePageElement('sidebar','Админ панель','admin_panel.html');
chandgePageElement('info','Аналитика','info.html');
}

if (getCookieValue("role") === "ADMIN" || getCookieValue("role") === "USER") {
chandgePageElement('enter','Профиль','profile.html');
}

if (window.location.pathname.endsWith("profile.html")) {
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