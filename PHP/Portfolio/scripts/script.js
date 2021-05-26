/*jslint browser: true, devel: true */

// get frequently used elements of page
const nav = document.getElementsByTagName("nav")[0];
const menu = nav.getElementsByClassName("icon-menu")[0];
const links = nav.getElementsByClassName("regular-links")[0];

// change regular menu to hamburger menu
function hamburgerMenu() {
    menu.style.display = "block";
    links.style.display = "none";
    links.classList.remove("mobile-menu");
}

// change hamburger menu to regular menu
function regularMenu() {
    menu.style.display = "none";
    links.style.display = "flex";
    links.classList.remove("mobile-menu");
}

// show drop-down menu
function showMenu() {
    links.style.display = "flex";
    links.classList.add("mobile-menu");
    menu.onclick = hideMenu;
    links.style.animation = "slideDown 1s ease-in-out";
    setTimeout(() => {
        menu.classList.add("icon-cancel");
        menu.classList.remove("icon-menu");
    }, 500);
}

// hide drop-down menu
function hideMenu() {
    links.style.animation = "slideUp 1s ease-in-out";
    setTimeout(() => {
        links.classList.remove("mobile-menu");
        links.style.display = "none";
    }, 1000)
    setTimeout(() => {
        menu.classList.add("icon-menu");
        menu.classList.remove("icon-cancel");
    }, 500);
    menu.onclick = showMenu;
}

// if javascript is allowed in browser, change menu type accordingly
window.onload = chooseMenu;
window.addEventListener("resize", chooseMenu);
function chooseMenu() {
    const width  = window.innerWidth ||
                    document.documentElement.clientWidth ||
                    document.body.clientWidth;
    if (width < 480){
        hamburgerMenu();
    } else {
        regularMenu();
    }
}
