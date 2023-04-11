window.addEventListener('load', function () {

    // clicking the edit name button shows a field to edit team name and hides the original name
    const editButton = document.getElementById("edit-team-name-button");
    const heading = document.getElementById("team-name");
    const editForm = document.getElementById("team-name-form");

    editButton.addEventListener("click", () => {
        heading.style.display = "none";
        editButton.style.display = "none";
        editForm.style.display = "block";
    });
});