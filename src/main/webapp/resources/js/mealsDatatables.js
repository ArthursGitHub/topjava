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
                        [0,"asc"]
                    ]
                }
            );


        makeEditable();
    }
);