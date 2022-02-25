$(document).ready(function () {

});

function load() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/todo/displayUndone',
        dataType: 'json'
    }).done(function (data) {
        for (let task of (data)) {
            $('#listUndone').append('<input class="checkbox_check" type="checkbox" '
                + 'onclick="makeDone(' + task.id + ')"> Завершить'
                + '<li id=' + "undone" + '>' + task.descr + " " + task.created + " " + task.user.name + '</li>');
        }
    });
}

function check() {
    if ($('#task_list').is(':checked')) {
        showAll();
    } else {
        hide();
    }
}

function showAll() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/todo/displayDone',
        dataType: 'json'
    }).done(function (data) {
        $('#listUndone').after('<ul id=' + "listDone" + '></ul>');
        for (const task of data) {
            $('#listDone').append('<li>' + task.descr + " " + task.created + " " + task.user.name + '</li>');
        }
    });
}

function hide() {
    let list = document.getElementById('listDone');
    list.remove();
}


function makeDone(number) {
    console.log(number);
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/makeDone',
        data: JSON.stringify({
            id: number
        })
    })
}

function add() {
    console.log('in add');
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/addTask',
        data: JSON.stringify({
            descr: $('#task_desc').val()
        })
    })
}
