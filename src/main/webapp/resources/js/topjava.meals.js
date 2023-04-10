const mealAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl,
    updateTable: function () {
        $.ajax({
            type: "GET",
            url: mealAjaxUrl + "filter",
            data: $("#filter").serialize()
        }).done(updateTableByData);
    }
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealAjaxUrl, updateTableByData);
}

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (date, type, row) {
                        if (type === "display") {
                            return date;
                        }
                        return date;
                    }
                },
                {
                    "data": "description",
                },
                {
                    "data": "calories",
                },
                {
                    "defaultContent": "",
                    "orderable": false,
                    "render": renderEditBtn
                },
                {
                    "defaultContent": "",
                    "orderable": false,
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                if (!data.excess) {
                    $(row).attr("data-meal-excess", false);
                }
            }
        })
    );
});


$('#startDate').datetimepicker({
    timepicker: false,
    format: 'Y-m-d'
});
$('#endDate').datetimepicker({
    timepicker: false,
    format: 'Y-m-d'
});
$('#startTime').datetimepicker({
    datepicker:false,
    format: 'HH:mm'
})
$('#endTime').datetimepicker({
    datepicker: false,
    format: 'HH:mm'
})

