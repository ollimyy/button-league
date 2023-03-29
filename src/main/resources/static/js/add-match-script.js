// execute js only after page has finished loading, all elements are created and available
window.addEventListener('load', function (){

    //prevent the user from selecting the same team as both home and away

    let homeTeamSelect = document.getElementById('homeTeam');
    let awayTeamSelect = document.getElementById('awayTeam');

    //when the homeTeam value is changed
    homeTeamSelect.addEventListener('change', function () {
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

    })

    // if team is selected as away hide and disable that team from home team select
    awayTeamSelect.addEventListener('change', function () {

        let selectedAwayTeam = this.value;
        for(let i = 0; i < homeTeamSelect.options.length; i++) {

            homeTeamSelect.options[i].disabled = false;
            homeTeamSelect.options[i].hidden = false;

            if (homeTeamSelect.options[i].value === selectedAwayTeam) {
                homeTeamSelect.options[i].disabled = true;
                homeTeamSelect.options[i].hidden = true;
            }
        }
    })
})


