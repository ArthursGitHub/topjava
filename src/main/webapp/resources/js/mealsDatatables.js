var ajaxUrl = "ajax/admin/meals/";
var datatableApi;

$(function () {
        datatableApi =  $("#datatable")
            .DataTable(
                {
                    "paging": false,
                    "info": true,
                    "columns": [
                        {"data": "dateTime"},
                        {"data": "description"},
                        {"data": "calories"},
                        {"defaultContent": "Edit", "orderable": false},
                        {"defaultContent": "Delete", "orderable": false}
                    ],
                    "order": [
                        [0,"desc"]
                    ]
                }
            );


        makeEditable();
    }
);

function filter() {
    var form = $("#FilterForm");

    $.ajax({
            type: "POST",
            url: ajaxUrl + "filter",
            data: form.serialize(),
            success: function (data) {
                datatableApi.clear().rows.add(data).draw();
            }
        }
    );
}

function resetFilter() {
    var components = $("#FilterForm :input");
    var size = components.length;
    var i = 0;

    do {
        components[i].value = "";
    }
    while (++i < size);

    updateTable();
}
