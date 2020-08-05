
        <div class="right_col" role="main">
            <div class="">
                <div class="page-title">
                    <div class="title_left" style="width: 150%">
                        <div>
                        <p>Group 1</p>
                        </div>
                        <div>
                        <h3 style="margin: 100px 400px 100px 200px; padding: 100px 200px 100px 200px;" >The Management System for Phone</h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            $(document).ready(function(){
                processMessage();
            });
            function processMessage(){
                var msgSuccess = '${msgSuccess}';
                var msgError = '${msgError}';
                if(msgSuccess){
                    new PNotify({
                        title: ' Success',
                        text: msgSuccess,
                        type: 'success',
                        styling: 'bootstrap3'
                    });
                }
                if(msgError){
                    new PNotify({
                        title: ' Error',
                        text: msgError,
                        type: 'error',
                        styling: 'bootstrap3'
                    });
                }
            }


        </script>