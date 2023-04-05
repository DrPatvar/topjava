const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
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
                    "asc"
                ]
            ]
        })
    );
    $("table.table-striped").find('input[type=checkbox]').change(function () {
        $(this).is(':checked') ? $(this).closest('tr').css('background', 'gray')
            :$(this).closest('tr').css('background', 'blue');
        var enabled = $(this).is(':checked');
        var id = $(this).closest('tr').attr('id');
        $.ajax({
            url: ctx.ajaxUrl + "updateEnable",
            method: "POST",
            data: {  id : id, enable: enabled}
        })
    })
});