$(document).ready(function () {

    var $calendar = $('#calendar');

    $calendar.fullCalendar({
        defaultView : 'listWeek',
        editable: false,
        events: {
            url: '/training/events',
            type: 'GET',
            error: function () {
                console.log('there was an error while fetching events!');
            }
        },
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'listWeek,listDay' //month,
        },
        buttonText: {
            today: 'Сегодня',
            week: 'по неделям',
            day: 'по дням'
        },
        buttonIcons: {
            prev: 'left-single-arrow',
            next: 'right-single-arrow'
        },
        views: {
            listDay: { buttonText: 'list day' },
            listWeek: { buttonText: 'list week' }
        },
        navLinks: true

    });
});
