
        var createCallback = function(i) {
            var count = $("#ticket-number").val();
            $(".green.lighten").removeClass("green lighten");
            for (let cnt = 0; cnt < count; cnt++) {
                $("#sqr"+(i+cnt)).addClass("green lighten");
            }
        }

        function ajaxSeat() {
        $.ajax({
            method: "GET",
            url: '/seat',
            success: function(seat) {
                var count = 0;
                var middle = "";
                var side1 = "";
                var side2 = "";
                middle += "<div id='rowseat'>"
                for (var i = 0; i < seat.length; i++) {
                    if (seat[i].type == "Close" || seat[i].type == "Middle" || seat[i].type == "Far") {
                        middle += "<div onclick='createCallback("+i+")' id='sqr" + i + "' class='square white'>" + (i+1) + "</div>";
                        if ((i+1)%6 == 0) {
                            count += 1;
                            middle += "</div>";

                            if (seat[i+2].type == "Side") {
                                continue;
                            }
                            middle += "<div id='rowseat'>";
                        }

                    }

                    else {
                        if ((i+1) < seat.length - ((seat.length - (seat.length*4/6))/2)-1) {
                            if ((i+1) == Math.floor((seat.length*4/6)-2)) {
                                side1 += "<div id='columnseat'>";
                            }
                            side1 += "<div onclick='createCallback("+i+")' id='sqr" + i + "' class='square white'>" + (i+1) + "</div>";
                            if ((i+1)%count == 0) {
                                side1 += "</div>";
                                if (i+2 >= seat.length - ((seat.length - (seat.length*4/6))/2)-1) {
                                    continue;
                                }
                                side1 +="<div id='columnseat'>";
                            }
                        } else {
                            if ((i+1) == Math.floor(seat.length - ((seat.length - (seat.length*4/6))/2))) {
                                side2 += "<div id='columnseat'>";
                            }
                            side2 += "<div onclick='createCallback("+i+")' id='sqr" + i + "' class='square white'>" + (i+1) + "</div>";
                            if ((i+1)%count == 0) {
                                side2 += "</div>";
                                if (i+2 > seat.length) {
                                    continue;
                                }
                                side2 +="<div id='columnseat'>";
                            }
                        }
                    }

                }
                $("#middle").append(middle);
                $("#side1").append(side1);
                $("#side2").append(side2);
            }
        });
        }

        $(document).ready(function () {
            $(window).load(function () {
                ajaxSeat();
            });
        });