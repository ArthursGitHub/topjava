var ajaxUrl = "ajax/admin/users/";
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
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
    });
    makeEditable();
});

function changeState() {
    var components = $(this);
    var attrId = components.attr("id");
    var attrState = components[0].checked;

    var data = "id=" + attrId + "&state=" + attrState;

    $.ajax({
            type: "POST",
            url: ajaxUrl + "state",
            data: data,
            success: function () {
                updateTable();
            }
        }
    );
}
