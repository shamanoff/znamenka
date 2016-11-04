function wobble() {
    var four = document.getElementById('wobble');
    var dist = 60;
    var id = setInterval(function () {
        var deg;
        if (dist % 2 == 0) {
            deg = 180 + dist;
        } else {
            deg = 180 - dist;
        }

        four.style.transform = "rotate(" + deg + "deg)";

        if (dist > 15) {
            dist -= 11;
        } else {
            dist = -dist;
        }
    }, 1000);
}

window.addEventListener('load', function () {
    wobble()
});
