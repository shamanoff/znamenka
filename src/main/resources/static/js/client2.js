$(document).ready(function () {

    var editButton = $(".editButton");
    var aboutClient = $('#aboutClient');
    var createClient = $('#formCreate');
    var myModal = $('#myModal');

    $('#startTime').datetimepicker({
        defaultDate: '05/09/2016',
        format: 'DD/MM/YYYY'
    });
    $('#startTime-edit').datetimepicker({
        format: 'DD/MM/YYYY'
    });

    $('#loading-image').bind('ajaxStart', function () {
        $(this).hide();
    }).bind('ajaxStop', function () {
        $(this).show();
    });

    editButton.click(function () {
        $('li > a[href="' + "#home" + '"]').tab("show");

    });


    aboutClient.validator().on('submit', function (e) {

        if (!e.isDefaultPrevented()) {
            var $form = $(e.target),
                id = $form.find('[name="id"]').val();
            $.ajax({
                url: '/client',
                method: 'PUT',
                data: $form.serialize()
            }).success(function (response) {
                // Get the cells
                var $button = $('button[data-id="' + response.id + '"]'),
                    $tr = $button.closest('tr'),
                    $cells = $tr.find('td');
                // Update the cell data
                $cells
                    .eq(1).html(response.fname + ' ' + response.sname).end()
                    .eq(2).html(response.phone).end();
                // Hide the dialog
                myModal.modal('hide');
                // You can inform the user that the data is updated successfully
                // by highlighting the row or showing a message box
            });
        }
    });

    createClient.validator().on('submit', function (e) {
        if (!e.isDefaultPrevented()) {
            $(e.target).submit();
        }
    });


    editButton.on('click', function () {
        // Get the record's ID via attribute
        var id = $(this).attr('data-id');
        aboutClient
            .find('[name="id"]').val("response.id").end();

        $.ajax({
            url: '/client/' + id,
            method: 'GET'
        }).success(function (response) {
            // Populate the form fields with the data returned from server
            //ДОБАВИТЬ ОБНУЛЕНИЕ ВСЕХ 4 ФОРМ НА ТАБАХ
            aboutClient
                .find('[name="id"]').val(response.id).end()
                .find('[name="fname"]').val(response.fname).end()
                .find('[name="sname"]').val(response.sname).end()
                .find('[name="email"]').val(response.email).end()
                .find('[name="phone"]').val(response.phone).end()
                .find('[name="birthDate"]').val(moment(response.birthDate).format("DD/MM/YYYY")).end()
                .find('[name="comment"]').val(response.comment).end()
                .find('[name="carNumber"]').val(response.carNumber).end();
            if (response.male == true) {
                aboutClient
                    .find('[name="male"][value="true"]').attr('checked', true).end()
            } else {
                aboutClient
                    .find('[name="male"][value="false"]').attr('checked', true).end()
            }
            // Show the dialog
            myModal
                .on('shown.bs.modal', function () {
                    aboutClient.trigger('reset'); // Reset form
                })
                .on('hide.bs.modal', function (e) {
                    // Bootbox will remove the modal (including the body which contains the login form)
                    // after hiding the modal
                    // Therefor, we need to backup the form
                    ////$('#modal1').hide().appendTo('body');
                })

        });
    });
    ////////////////// Поиск начало

    $(".search").keyup(function () {
        var searchTerm = $(".search").val();
        var listItem = $('.clientsTable tbody').children('tr');
        var searchSplit = searchTerm.replace(/ /g, "'):containsi('");

        $.extend($.expr[':'], {
            'containsi': function (elem, i, match, array) {
                return (elem.textContent || elem.innerText || '').toLowerCase().indexOf((match[3] || "").toLowerCase()) >= 0;
            }
        });

        $(".clientsTable tbody tr").not(":containsi('" + searchSplit + "')").each(function (e) {
            $(this).attr('visible', 'false');
        });

        $(".clientsTable tbody tr:containsi('" + searchSplit + "')").each(function (e) {
            $(this).attr('visible', 'true');
        });

        var jobCount = $('.clientsTable tbody tr[visible="true"]').length;
        $('.counter').text('Клиентов найдено: ' + jobCount);

        if (jobCount == '0') {
            $('.no-result').show();
        }
        else {
            $('.no-result').hide();
        }
    });

    ////////////////// Поиск конец

    ////////////////// showCreate
    $(".showCreate").on('click', function () {
        $(".formCreate").toggle(500);

        //$(".formCreate").hide(500);
    });
    ////////////////// showCreate

    /// createNew
    $(".createNew").on('click', function () {
        $(".formCreate").hide(500);

    });
    /// createNew
    $("a[href='#menu1']").on('shown.bs.tab', function (event) {
        var id = aboutClient.find('[name="id"]').val();
        var tabTraining = $('#modal-trainings');
        $.ajax({
            url: '/client/' + id + '/trainings',
            method: 'GET'
        }).success(function (data) {
            tabTraining.find('tbody').children('tr').remove();
            $.each(data, function (i, training) {
                var row = $("<tr>");
                row.append($("<td>" + training.start + "</td>"))
                    .append($("<td>" + training.trainerName + "</td>"))
                    .append($("<td>" + training.statusName + "</td>"));
                tabTraining.find('tbody').append(row);
            })
        }).error(function () {
            tabTraining.find('tbody').children('tr').remove();
        });
    });

    $("a[href='#menu2']").on('shown.bs.tab', function (event) {
        var id = aboutClient.find('[name="id"]').val();
        $.ajax({
            url: '/client/' + id + '/purchases',
            method: 'GET'
        }).success(function (data) {
            $('#modal-purchases').find('tbody').children('tr').remove();
            $.each(data, function (i, purchase) {
                var row = $("<tr>");
                row.append($("<td>" + purchase.purchaseDate + "</td>"))
                    .append($("<td>" + purchase.productName + "</td>"))
                    .append($("<td>" + purchase.price + "</td>"))
                    .append($("<td>" + purchase.paid + "</td>"))
                    .append($("<td>" + purchase.discountAmount + "</td>"))
                    .append($("<td>" + purchase.trainerName + "</td>"));
                $('#modal-purchases').find('tbody').append(row);

                $.each(purchase.payments, function (i, payment) {
                    row = $("<tr>");
                    row.append($("<td>" + payment.paymentDate + "</td>"))
                        .append($("<td></td>"))
                        .append($("<td></td>"))
                        .append($("<td>" + payment.amount + "</td>"))
                        .append($("<td></td>"))
                        .append($("<td></td>"));
                    $('#modal-purchases').find('tbody').append(row);
                })

            })
        });
    });
});

