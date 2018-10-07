/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//var statusJ = "<%=status%>";
////            alert(statusJ);


function loadMessage(message) {
    
    alert(message);
    if (message == 'SUCCESS') {

        $(function ()
        {
            $("#dialog-message").dialog({
                modal: true,
                buttons: {
                    Ok: function () {
                        $(this).dialog("close");
                    }
                }
            });
        });
    }
}