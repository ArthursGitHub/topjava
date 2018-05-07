var ajaxUrl = "ajax/profile/meals/";
var datatableApi;

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

$(function () {
    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime",
                "render": function (date, type, row) {
                    if (type === 'display') {
                        return date.replace('T', ' ').substr(0, 16);
                    }
                    return date;
                }
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [0, "desc"]
        ],
        "createdRow": function (row, data, dataIndex) {
            $(row).addClass(data.exceed ? 'exceeded' : 'normal');
        }
    });
    makeEditable();

    jQuery('#startDate').datetimepicker(
        {
            timepicker:false,
            format: 'Y-m-d'
        }
    );

    jQuery('#endDate').datetimepicker(
        {
            timepicker:false,
            format: 'Y-m-d'
        }
    );

    jQuery('#startTime').datetimepicker(
        {
            datepicker:false,
            format: 'H:i'
        }
    );

    jQuery('#endTime').datetimepicker(
        {
            datepicker:false,
            format: 'H:i'
        }
    );

    jQuery('#dateTime').datetimepicker(
        {
            format: 'Y-m-d H:i'
        }
    );

});