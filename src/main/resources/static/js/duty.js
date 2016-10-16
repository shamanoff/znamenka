$(document).ready(function () {

    var $calendar = $('#calendar');
    var $dutyModal = $('#duty-modal');
    var $dutyForm = $('#create-duty-form');

    $dutyForm.validator().on('submit', function (e) {
        if (!e.isDefaultPrevented) {
            $(e.target).submit();
        }
    });

    $calendar.fullCalendar({
        navLinks: true, // can click day/week names to navigate views
        selectable: true,
        minTime: "06:00:00",
        selectHelper: true,
        select: function (start,end) {
            $dutyModal.modal("show");
            var startDuty = start.format("DD/MM/YYYY HH:mm");
            $dutyForm.find('[name="plannedStart"]').val(startDuty).end();
            var endDuty = end.format("DD/MM/YYYY HH:mm");
            $dutyForm.find('[name="plannedEnd"]').val(endDuty).end();
            $calendar.fullCalendar('unselect');
        },
        editable: false,

        eventLimit: false,
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'agendaWeek,agendaDay' //month,
        },
        buttonText: {
            today: 'Сегодня',
            //month: 'month',
            week: 'по неделям',
            day: 'по дням'
        },
        events: {
            url: '/duty/events',
            type: 'GET',
            error: function () {
                console.log('there was an error while fetching events!');
            },
            success: function (data) {
                console.log(data);
            }
        },
        annotations: [{
            start: new Date(2016, 8, 20, 13, 30),
            end: new Date(2016, 8, 22, 14, 0),
            title: 'Blocked Day',
            cls: 'open',
            color: '#777777',
            background: '#000'
        }],
        defaultView: 'agendaWeek'
    });
});


