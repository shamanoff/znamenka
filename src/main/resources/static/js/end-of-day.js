$(document).ready(init(new Date()));

function getTraining(date) {
    $.ajax({
        url: "/end-of-day",
        type: "get",
        data: {
            "date": date.format('DD/MM/YYYY').toString()
        },
        success: function (data) {
            $('#container').replaceWith(data);
            init(date.toDate())
        }
    });
}

function init(date) {

    var startTime = $('#startTime');

    startTime.datetimepicker({
        defaultDate: date,
        format: 'DD/MM/YYYY'
    });
    startTime.on('dp.change', function (e) {
        getTraining(e.date);
    });

}