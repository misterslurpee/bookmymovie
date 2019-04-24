
        var createCallback = function(i) {
            var count = $("#ticket-number").val();
            $(".pressed").removeClass("pressed");
            for (let cnt = 0; cnt < count; cnt++) {
                $("#sqr"+(i+cnt)).addClass("pressed");
                console.log(cnt)
            }
            $('.footer').show();
        }

        function ajaxSeat() {
        $.ajax({
            method: "GET",
            url: '/seat',
            success: function(data) {
                var seat = data[0].rows;
                var rowDivision = Math.round((seat.length - (seat.length*2/3))/2);
                var count = 0;
                var middle = "";
                var far = "";
                for (var i = 0; i < seat.length; i++) {
                    if (seat[i].type == "Middle") {
                        if (count == 0) {
                            middle += "<div id='rowseat'>";
                        }
                        middle += "<div onclick='createCallback("+i+")' id='sqr" + i + "' class='square white'>" + (i+1) + "</div>";
                        count += 1;
                        if (count == rowDivision) {
                            middle += "</div>";
                            count = 0;
                        }
                    } else {
                        if (count == 0) {
                            far += "<div id='rowseat'>";
                        }
                        far += "<div onclick='createCallback("+i+")' id='sqr" + i + "' class='square white'>" + (i+1) + "</div>";
                        count += 1;
                        if (count == rowDivision) {
                            far += "</div>";
                            count = 0;
                        }
                    }
                }
                $("#middle").append(middle);
                $("#far").append(far);
            }
        });
        }

        $(document).ready(function () {
            $(window).load(function () {
                $('.footer').hide();
                ajaxSeat();
            });
        });