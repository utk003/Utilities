function openDropdown(dropdownID) {
    if (document.getElementById(dropdownID + "_menu").classList.toggle("show")) {
        document.getElementById(dropdownID + "_up").classList.remove("hide");
        document.getElementById(dropdownID + "_down").classList.add("hide");
    } else {
        document.getElementById(dropdownID + "_up").classList.add("hide");
        document.getElementById(dropdownID + "_down").classList.remove("hide");
    }
}

window.onclick = function (e) {
    let dropdowns = document.getElementsByClassName("my-dropdown-button");
    for (let i = dropdowns.length - 1; i >= 0; i--) {
        const dropdown = dropdowns.item(i);
        if (e.target !== dropdown) {
            const dropdownContent = dropdown.nextElementSibling;
            if (dropdownContent.classList.contains("show")) {
                dropdownContent.classList.remove("show");
                dropdown.firstElementChild.classList.add("hide"); // up arrow
                dropdown.lastElementChild.classList.remove("hide"); // down arrow
            }
        }
    }
}