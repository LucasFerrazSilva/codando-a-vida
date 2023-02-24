console.log(2);

const navbarUlElement = document.querySelector("#navbar-ul");
const adminDropdownElement = document.getElementById('admin-dropdown');

fetch("http://localhost:8080/category/list-active")
    .then(response => response.json())
    .then(data => {
        const names = data.map(category => category.name);
        const navbarUl = document.querySelector("#navbar-ul");

        names.forEach(name => {
            var liElement = document.createElement("li");
            liElement.className = 'nav-item';

            var aElement = document.createElement("a");
            aElement.className = 'nav-link';
            aElement.href = 'http://localhost:8080/category/' + name;
            aElement.appendChild(document.createTextNode(name));

            liElement.appendChild(aElement);
            navbarUlElement.insertBefore(liElement, adminDropdownElement);
        })
    });