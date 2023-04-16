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
    //get score form, home and away scores
    const scoreForm = document.getElementById('score-form');
    const homeScore = document.getElementById('homeScore');
    const awayScore = document.getElementById('awayScore');

    // hide the time and score form if date is not set
    function showTimeIfDateIsSet(e) {
        const selectedDate = e.target.value;
        if (selectedDate) {
            timeLabel.style.display = 'inline-block';
            timeInput.style.display = 'inline-block';
            scoreForm.style.display = 'inline-block';
        } else {
            timeLabel.style.display = 'none';
            timeInput.style.display = 'none';
            scoreForm.style.display = 'none';
            timeInput.value = '';
            homeScore.value = '';
            awayScore.value = '';
        }
    }

    dateInput.addEventListener('change', showTimeIfDateIsSet);
    showTimeIfDateIsSet({target: dateInput});

    // set time and scores to empty if date is cleared
    dateInput.addEventListener('input', function(event) {
        if (event.target.value === '') {
            timeInput.value = '';
            homeScore.value = '';
            awayScore.value = '';
        }
    });
});


