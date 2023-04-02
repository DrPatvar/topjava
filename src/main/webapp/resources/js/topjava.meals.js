const mealAjaxUrl = "mealsUI/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl
};

function filter() {
    $.ajax({
        url: ctx.ajaxUrl + "filter",
        method: "get",
        data: $("#filter").serialize()
    }).done(function (data){ctx.datatableApi.clear().rows.add(data).draw()});
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ctx.ajaxUrl, function (data){ctx.datatableApi.clear().rows.add(data).draw()});
}

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    );
});