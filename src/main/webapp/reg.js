function regUser() {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/reg',
        data: JSON.stringify({
            name: $('#login').val(),
            password: $('#password').val()
        })
    }).fail(function () {
        alert('Ошибка!')
    })
}

function redirect() {
    window.location.href = 'http://localhost:8080/todo/reg'
}