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

    // clicking delete team shows an "are you sure" alert, if user clicks yes team and it's matches and players will be deleted
    const deleteTeamButton = document.getElementById("delete-team-button");

    deleteTeamButton.addEventListener("click", function () {
        const confirmed = confirm("Deleting this team will delete all it's players and all played and upcoming matches the team is in." +
            "\n\nAre you sure you want to delete this team AND all it's players and matches?")
        if (confirmed) {
            const teamId = document.getElementById("team-id").value;
            window.location.href = "/delete-team/" + teamId;
        }
    })
});