
$(document).ready(function() {
    let listUsers = [];

    $( "#checkAll" ).click(function() {
        $('.checkbox').each(function(){
            //if ($( "#checkbox" ).is(':checked'))
            $(this).prop('checked', $( "#checkAll" ).is(':checked'));
            makeListUsers(this);
            writeInInput();
        });
    });
    $( ".checkbox" ).click(function() {
        makeListUsers(this);
        writeInInput();
    });

    function makeListUsers(element) {
        let id = $(element).attr("data-id");
        if ($(element).is(':checked')){
            if ($.inArray(id, listUsers)<0) {
                listUsers.push(id);
            }
        }
        else {
            let indexUsers = $.inArray(id, listUsers);
            if(indexUsers>=0){
                listUsers.splice(indexUsers,1);
            }
        }
    }
    function writeInInput(){
        $('#unblock_users').val(listUsers.join());
        $('#block_users').val(listUsers.join());
        $('#delete_users').val(listUsers.join());
    }
});