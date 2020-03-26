<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!--Success Modal Templates-->
<div id="modal-success" class="modal modal-message modal-success fade" style="display: none;" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <i class="glyphicon glyphicon-check"></i>
            </div>
            <div class="modal-title" id="modal-title-success">Success</div>

            <!--                 <div class="modal-body">You have done great!</div> -->
            <div class="modal-footer">
                <button type="button" class="btn btn-success" data-dismiss="modal">OK</button>
            </div>
        </div> <!-- / .modal-content -->
    </div> <!-- / .modal-dialog -->
</div>
<!--End Success Modal Templates-->
<!--Info Modal Templates-->
<div id="modal-info" class="modal modal-message modal-info fade" style="display: none;" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <i class="fa fa-envelope"></i>
            </div>
            <div class="modal-title" id="modal-title-info">Alert</div>

            <!--                 <div class="modal-body">You'vd got mail!</div> -->
            <div class="modal-footer">
                <button type="button" class="btn btn-info" data-dismiss="modal">OK</button>
            </div>
        </div> <!-- / .modal-content -->
    </div> <!-- / .modal-dialog -->
</div>
<!--End Info Modal Templates-->
<!--Danger Modal Templates-->
<div id="modal-danger" class="modal modal-message modal-danger fade" style="display: none;" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <i class="glyphicon glyphicon-fire"></i>
            </div>
            <div class="modal-title" id="modal-title-danger">Alert</div>

            <!--                 <div class="modal-body">You'vd done bad!</div> -->
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">OK</button>
            </div>
        </div> <!-- / .modal-content -->
    </div> <!-- / .modal-dialog -->
</div>
<!--End Danger Modal Templates-->
<!--Danger Modal Templates-->
<div id="modal-warning" class="modal modal-message modal-warning fade" style="display: none;" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <i class="fa fa-warning"></i>
            </div>
            <div class="modal-title" id="modal-title-warning">Warning</div>

            <!--                 <div class="modal-body">Is something wrong?</div> -->
            <div class="modal-footer">
                <button type="button" class="btn btn-warning" data-dismiss="modal">OK</button>
            </div>
        </div> <!-- / .modal-content -->
    </div> <!-- / .modal-dialog -->
</div>
<!--End Danger Modal Templates-->

<!--Danger Modal Templates-->
<div id="modal-warning1" class="modal modal-message modal-warning fade" style="display: none;" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <i class="fa fa-warning"></i>
            </div>
            <div class="modal-title" id="modal-title-warning1">Warning</div>
            <div class="modal-title" id="modal-title-warning2">Warning</div>
            <div class="modal-title" id="modal-title-warning3">Warning</div>

            <!--                 <div class="modal-body">Is something wrong?</div> -->
            <div class="modal-footer">
                <a href="#" id="zm-view"  class="btn btn-primary btn-xs edit" onclick="openNewWindow()">look</a>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-warning" data-dismiss="modal">close</button>
            </div>
        </div> <!-- / .modal-content -->
    </div> <!-- / .modal-dialog -->
</div>
<!--End Danger Modal Templates-->


<!--Danger Modal Templates-->
<div id="modal-warning2" class="modal modal-message modal-warning fade" style="display: none;" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <i class="fa fa-warning"></i>
            </div>
            <div class="modal-title" id="modal-title-warning21">Warning</div>
            <div class="modal-title" id="modal-title-warning22">Warning</div>
            <!--                 <div class="modal-body">Is something wrong?</div> -->
            <div class="modal-footer">
                <a href="#" id="device-view"  class="btn btn-primary btn-xs edit" onclick="openNewWindow()">look</a>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-warning" data-dismiss="modal">close</button>
            </div>
        </div> <!-- / .modal-content -->
    </div> <!-- / .modal-dialog -->
</div>
<!--End Danger Modal Templates-->

<script>
    window.viewUrl = "";
    function successAlert(message) {
        $('#modal-title-success').html(message);
        $('#modal-success').modal('toggle')
    }

    function infoAlert(message) {
        $('#modal-title-info').html(message);
        $('#modal-info').modal('toggle')
    }

    function dangerAlert(message) {
        $('#modal-title-danger').html(message);
        $('#modal-danger').modal('toggle')
    }

    function warningAlert(message) {
        $('#modal-title-warning').html(message);
        $('#modal-warning').modal('toggle')
    }
    function warningAlert1(event,url) {
        $('#modal-title-warning1').html(event.monitorId +"_"+ event.name);
        $('#modal-title-warning2').html(event.cause);
        $('#modal-title-warning3').html(event.startTime);
//        $('#zm-view').html(url);
        window.viewUrl = url;
//        document.getElementById('zm-view').href=url;
        $('#modal-warning1').modal('show')
    }

    function warningAlert2(event,url) {
        $('#modal-title-warning21').html(event.deviceId +"_"+ event.deviceName);
        $('#modal-title-warning22').html(event.alarmMessage);

//        $('#zm-view').html(url);
        window.viewUrl = url;
//        document.getElementById('zm-view').href=url;
        $('#modal-warning2').modal('show')
    }

    function openNewWindow() {
        window.open(window.viewUrl,'newwindow', 'height=800, width=800, top=400, left=400, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
    }

</script>