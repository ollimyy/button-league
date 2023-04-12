window.addEventListener('load', function () {

    const teamSelect = document.getElementById("team");
    const numberSelect = document.getElementById("number");
    // save the current team and number, so that if user selects the current team again the current number is available
    const currentTeam = teamSelect.value;
    const currentNumber = numberSelect.value;

    // when user changes the team
    teamSelect.addEventListener('change', () => {
        const teamId = teamSelect.value;

        console.log(currentTeam);
        fetch('/get-available-numbers?teamId=' + teamId)
            .then(response => response.json())
            .then(numbers  => {
                // if the team is the current (before editing) team,
                if(teamId === currentTeam){
                // add the players current number to the available numbers
                    numbers.unshift(currentNumber);
                    // sort the numbers so the current number is at the right place
                    numbers.sort((a, b) => a - b);
                }

                // remove current options
                numberSelect.innerHTML = '';

                // add available numbers as new options
                numbers.forEach(number => {
                    const option = document.createElement('option');
                    option.value = number;
                    option.textContent = number;
                    numberSelect.appendChild(option);
                })
            })
    })

});
