$(document).ready(function () {

    var $editButton = $(".editButton"),
        $aboutClient = $('#aboutClient'),
        $createClient = $('#formCreate'),
        $myModal = $('#myModal'),
        $clientsTable=$('#clients-table'),
        $alert = $('.alert');

    $('#loading-image').bind('ajaxStart', function () {
        $(this).hide();
    }).bind('ajaxStop', function () {
        $(this).show();
    });

    $editButton.click(function () {
        $('li > a[href="' + "#home" + '"]').tab("show");

    });

    $aboutClient.validator().on('submit', function (e) {
        if (!e.isDefaultPrevented()) {
            var $form = $(e.target),
                id = $form.find('[name="id"]').val();
            e.preventDefault();
            $.ajax({
                url: '/client',
                method: 'PUT',
                data: $form.serialize()
            }).success(function (response) {
                // Get the cells
                var $button = $('button[data-id="' + response.id + '"]'),
                    $tr = $button.closest('tr'),
                    $cells = $tr.find('td');
                $cells
                    .eq(1).html(response.fname + ' ' + response.sname).end()
                    .eq(2).html(response.phone).end();
                $myModal.modal('hide');
                Utils.showAlert($alert, 'Информация обновлена', 'success');
            }).error(function () {
                Utils.showAlert($alert, 'Произошла ошибка', 'danger');
            });
        }
    });

    $createClient.validator().on('submit', function (e) {
        if (!e.isDefaultPrevented()) {
            var $form = $(e.target);
            e.preventDefault();
            $.ajax({
              url: '/client',
                method: 'POST',
                data: $form.serialize()
            }).success(function (response) {
                $createClient.hide();
                var $cells = $clientsTable.find('tr').find('td');
                $cells
                    .eq(1).html(response.fname + ' ' + response.sname).end()
                    .eq(2).html(response.phone).end();
            }).error(function () {
                Utils.showAlert($alert, 'Произошла ошибка', 'danger');
            });
        }
    }) ;

    $editButton.on('click', function () {
        $aboutClient.trigger('reset'); // Reset form
        // Get the record's ID via attribute
        var id = $(this).attr('data-id');
        $aboutClient
            .find('[name="id"]').val("response.id").end();

        $.ajax({
            url: '/client/' + id,
            method: 'GET'
        }).success(function (response) {
            // Populate the form fields with the data returned from server
            //ДОБАВИТЬ ОБНУЛЕНИЕ ВСЕХ 4 ФОРМ НА ТАБАХ
            $aboutClient
                .find('[name="id"]').val(response.id).end()
                .find('[name="fname"]').val(response.fname).end()
                .find('[name="sname"]').val(response.sname).end()
                .find('[name="email"]').val(response.email).end()
                .find('[name="phone"]').val(response.phone).end()
                .find('[name="birthDate"]').val(moment(response.birthDate).format("DD/MM/YYYY")).end()
                .find('[name="comment"]').val(response.comment).end()
                .find('[name="carNumber"]').val(response.carNumber).end();
            if (response.male == true) {
                $aboutClient
                    .find('[name="male"][value="true"]').attr('checked', true).end()
            } else {
                $aboutClient
                    .find('[name="male"][value="false"]').attr('checked', true).end()
            }
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

    $(".showCreate").on('click', function () {
        $createClient.toggle(500);
    });


    $("a[href='#menu1']").on('shown.bs.tab', function (event) {
        var id = $aboutClient.find('[name="id"]').val();
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
        var id = $aboutClient.find('[name="id"]').val();
        var $modal = $('#modal-purchases');
        $.ajax({
            url: '/client/' + id + '/purchases',
            method: 'GET'
        }).success(function (data) {
            $modal.find('tbody').children('tr').remove();
            $.each(data, function (i, purchase) {
                var $row = $("<tr>");
                $row.append($("<td>" + purchase.purchaseDate + "</td>"))
                    .append($("<td>" + purchase.productName + "</td>"))
                    .append($("<td>" + purchase.price + "</td>"))
                    .append($("<td>" + purchase.paid + "</td>"))
                    .append($("<td>" + purchase.discountAmount + "</td>"))
                    .append($("<td>" + purchase.priceDisc + "</td>"))
                    .append($("<td>" + purchase.trainerName + "</td>"));
                $modal.find('tbody').append($row);

                $.each(purchase.payments, function (i, payment) {
                    $row = $("<tr>");
                    $row.append($("<td>" + payment.paymentDate + "</td>"))
                        .append($("<td></td>"))
                        .append($("<td></td>"))
                        .append($("<td>" + payment.amount + "</td>"))
                        .append($("<td></td>"))
                        .append($("<td></td>"));
                    $modal.find('tbody').append($row);
                })

            })
        });
    });
});

