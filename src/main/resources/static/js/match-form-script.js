// execute js only after page has finished loading, all elements are created and available
window.addEventListener('load', function (){

    // get the away and home team selects
    let homeTeamSelect = document.getElementById('homeTeam');
    let awayTeamSelect = document.getElementById('awayTeam');

    //prevent the user from selecting the same team as both home and away
    //when the homeTeam value is changed
    function removeCurrentAwayTeamFromHomeTeamOptions() {
        let selectedHomeTeam = this.value;


        // go through the awayTeam options
        for(let i = 0; i < awayTeamSelect.options.length; i++) {
            // reset all the away team options
            // without this a previously selected team will remain unavailable, even if the selection was changed
            awayTeamSelect.options[i].disabled = false;
            awayTeamSelect.options[i].hidden = false;

            // hide and disable the selected home team from the away team options
            if (awayTeamSelect.options[i].value === selectedHomeTeam) {
                awayTeamSelect.options[i].disabled = true;
                awayTeamSelect.options[i].hidden = true;
            }
        }
    }

    // if team is selected as away hide and disable that team from home team select
    function removeCurrentHomeTeamFromAwayTeamOptions() {

        let selectedAwayTeam = this.value;
        for(let i = 0; i < homeTeamSelect.options.length; i++) {

            homeTeamSelect.options[i].disabled = false;
            homeTeamSelect.options[i].hidden = false;

            if (homeTeamSelect.options[i].value === selectedAwayTeam) {
                homeTeamSelect.options[i].disabled = true;
                homeTeamSelect.options[i].hidden = true;
            }
        }
    }

    // remove options on page load
    removeCurrentAwayTeamFromHomeTeamOptions.call(homeTeamSelect);
    removeCurrentHomeTeamFromAwayTeamOptions.call(awayTeamSelect);

    // remove options when user changes one of the teams
    homeTeamSelect.addEventListener('change', removeCurrentAwayTeamFromHomeTeamOptions);
    awayTeamSelect.addEventListener('change', removeCurrentHomeTeamFromAwayTeamOptions);

    // get the time label and input and the date input
    const dateInput = document.getElementById('date');
    const timeLabel = document.getElementById('timeLabel');
    const timeInput = document.getElementById('time');

    // hide the time input and label if date is not set
    dateInput.addEventListener('change', function(event) {
        const selectedDate = event.target.value;
        if (selectedDate) {
            timeLabel.style.display = 'inline-block';
            timeInput.style.display = 'inline-block';
        } else {
            timeLabel.style.display = 'none';
            timeInput.style.display = 'none';
            timeInput.value = '';
        }
    });

    // set time to empty if date is cleared
    dateInput.addEventListener('input', function(event) {
        if (event.target.value === '') {
            timeInput.value = '';
        }
    });
});


