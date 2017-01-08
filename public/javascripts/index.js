$(function() {
    // jquery elements
    var input = $("#input");
    var messages = $("#messages");

    // Adds an item to messages list
    var appendToMessages = function(txt) {
        $("<li>").text(txt).appendTo(messages);
    };

    // Web socket
    var wsAddress = $("body").data("chat-ws-url");
    var ws = new WebSocket(wsAddress);

    // handle on message from WebSocket
    ws.onmessage = function (event) {
        console.log("Incoming " + event.data);

        var msg = JSON.parse(event.data);

        console.log("Incoming text " + msg.text);
        appendToMessages(msg.text);
    };

    // handle enter key on input
    input.keypress(function(e) {
        if(e.which == 13) {

            var msg = input.val();

            appendToMessages(msg);

            var json = { text: msg };
            var jsonString = JSON.stringify(json);

            console.log("Outgoing " + jsonString);

            // send to WS
            ws.send(jsonString);

            // empty the input
            input.val("");
        }
    });
});