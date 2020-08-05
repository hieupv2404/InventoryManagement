
        <div class="right_col" role="main">
            <div class="">
                <div class="page-title">
                    <div class="title_left">
                        <p>Group 1</p>
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