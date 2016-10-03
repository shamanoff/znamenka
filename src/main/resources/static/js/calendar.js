/**
 * Created by Evgeny.Utkin on 24.09.2016.
 */
$(document).ready(function () {
    var calendar = $('#calendar');
    calendar.fullCalendar({
        navLinks: true, // can click day/week names to navigate views
        selectable: true,
        selectHelper: true,
        editable: true,

        eventLimit: true,
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'agendaWeek,agendaDay' //month,
        },
        buttonText: {
            today: 'today',
            //month: 'month',
            week: 'week',
            day: 'day'
        },
        events: {
            url: '/training/events/busy',
            type: 'GET',
            error: function () {
                console.log('there was an error while fetching events!');
            }
        }
        ,
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


    var socket = new SockJS('/calendar');
    var stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/calendar/event', function (message) {
            addEvent(JSON.parse(message.body));
        });
    });


    function sendColor(color) {
        stompClient.send("/color", {}, JSON.stringify({'colorString': color}));
    }

    function addEvent(event) {
        calendar.fullCalendar('renderEvent', event);
    }
})
;