function deleteHost(domId, hostId) {
    $.ajax({
        type: "DELETE",
        url: "/host/delete",
        data: "host_id="+hostId,
        success: function () {
            $("#" + domId).remove();
        }
    });
}

function confirmAndDeleteHost(domId, hostId, hostName) {
    if (confirm(hostName + " を本当に削除しますか？")) {
       deleteHost(domId, hostId);
    } else {
        return false;
    }
}
